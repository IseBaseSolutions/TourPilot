package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.AdditionalTasks.CatalogsActivity;
import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTask;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Diagnose.Diagnose;
import isebase.cognito.tourpilot.Data.Diagnose.DiagnoseManager;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.EmploymentInterval.EmploymentInterval;
import isebase.cognito.tourpilot.Data.EmploymentInterval.EmploymentIntervalManager;
import isebase.cognito.tourpilot.Data.Information.Information;
import isebase.cognito.tourpilot.Data.Information.InformationManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.PatientRemark.PatientRemark;
import isebase.cognito.tourpilot.Data.PatientRemark.PatientRemarkManager;
import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.Data.Task.Task.eTaskState;
import isebase.cognito.tourpilot.Data.Task.TaskManager;
import isebase.cognito.tourpilot.Dialogs.BaseDialog;
import isebase.cognito.tourpilot.Dialogs.BaseDialogListener;
import isebase.cognito.tourpilot.Dialogs.InfoBaseDialog;
import isebase.cognito.tourpilot.Dialogs.Tasks.BlutdruckTaskDialog;
import isebase.cognito.tourpilot.Dialogs.Tasks.StandardTaskDialog;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import isebase.cognito.tourpilot.Templates.TaskAdapter;
import isebase.cognito.tourpilot.Utils.DateUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TasksActivity extends BaseActivity implements BaseDialogListener {

	private TaskAdapter taskAdapter;
	private Employment employment;
	
	private Task startTask;
	private List<Task> tasks;
	private Task endTask;
	
	private ListView lvTasks;
	
	private TextView tvStartTaskTime;
	private TextView tvEndTaskTime;
	private TextView tvStartTaskDate;
	private TextView tvEndTaskDate;
		
	private Button btEndTask;
	private Button btStartTask;

	private List<Information> infos;

	private PatientRemark patientRemark;
	
	private boolean isClickable(){
		return !startTask.getRealDate().equals(DateUtils.EmptyDate) 
				&& endTask.getRealDate().equals(DateUtils.EmptyDate);
	}
	
	private boolean isAllDone(){
		return !startTask.getRealDate().equals(DateUtils.EmptyDate) 
				&& !endTask.getRealDate().equals(DateUtils.EmptyDate)
				|| DateUtils.getTodayDateOnly().getTime() < DateUtils.getDateOnly(startTask.getPlanDate()).getTime();
	}
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_tasks);
			initControls();	
			reloadData();	
			fillUpTasks();
			checkAllIsDone();
			loadPatientInfos(false);
		}catch(Exception ex){
			ex.printStackTrace();
			criticalClose();
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu){
		if(infos.size() == 0){
			MenuItem item = menu.findItem(R.id.info);
			item.setEnabled(false);
		}
		if((patientRemark == null) ||(patientRemark.getName().length() == 0)){
			MenuItem item = menu.findItem(R.id.comments);
			item.setEnabled(false);
		}
		return true;
	}
	
	private void checkAllIsDone(){
		if(isAllDone()){
			btStartTask.setEnabled(false);
			btEndTask.setEnabled(false);
		}
	}
	
	private void fillUpEndButtonEnabling(){
		btEndTask.setEnabled(false);
		for(int i=1; i < tasks.size(); i++){
			Task task = tasks.get(i);
			if(task.getState() == eTaskState.Empty 
					&& task != startTask && task != endTask)
				return;
		}
		btEndTask.setEnabled(true);
	}
	
	private void fillUpStartTask() {
		fillUpDate(tvStartTaskTime, tvStartTaskDate, startTask);
	}
	
	private void fillUpEndTask() {
		fillUpDate(tvEndTaskTime, tvEndTaskDate, endTask);
	}
	
	private void fillUpDate(TextView tvTime, TextView tvDate, Task task){
		tvTime.setText(task.getRealDate().equals(DateUtils.EmptyDate)
				? getString(R.string.def_empty_time)
				: DateUtils.HourMinutesFormat.format(task.getManualDate().equals(DateUtils.EmptyDate) 
						? task.getRealDate() 
						: task.getManualDate()));
		tvDate.setText(task.getRealDate().equals(DateUtils.EmptyDate)
				? getString(R.string.def_empty_date)
				: DateUtils.DateFormat.format(task.getManualDate().equals(DateUtils.EmptyDate) 
						? task.getRealDate() 
						: task.getManualDate()));
	}
	
	private void fillUpTitle(){
		setTitle(employment.getName() + ", " + startTask.getDayPart());
	}
	
	private void fillUpTasks(){
		List<Task> tasksWithoutFirstAndLast = new ArrayList<Task>(tasks);
		tasksWithoutFirstAndLast.remove(startTask);
		tasksWithoutFirstAndLast.remove(endTask);
		taskAdapter = new TaskAdapter(this, R.layout.row_task_template, tasksWithoutFirstAndLast);
		lvTasks.setAdapter(taskAdapter);
		fillUpTitle();
		fillUpEndButtonEnabling();
		fillUpStartTask();
		fillUpEndTask();
	}	


	public void reloadData() {
		employment = EmploymentManager.Instance().load(Option.Instance().getEmploymentID());
		patientRemark = PatientRemarkManager.Instance().load(employment.getPatientID());
		tasks = TaskManager.Instance().load(Task.EmploymentIDField, Option.Instance().getEmploymentID()+"");
		patientRemark = PatientRemarkManager.Instance().load(employment.getPatientID());
		startTask = tasks.get(0);
		int i = 1;
		while(endTask == null){
			Task task = tasks.get(tasks.size() - i++);
			if(!task.getIsAdditionalTask())
				endTask = task;
		}
		startTask.setState(eTaskState.Done);
		endTask.setState(eTaskState.Done);
	}
	
	public void btStartTaskTimerClick(View view) {
		checkAllTasksAndFillUp(eTaskState.Empty);
		startTask.setRealDate(new Date());
		startTask.setState(eTaskState.Done);
		endTask.setRealDate(DateUtils.EmptyDate);

		TaskManager.Instance().save(startTask);
		TaskManager.Instance().save(endTask);
		fillUpStartTask();
		fillUpEndTask();
		fillUpEndButtonEnabling();
	}
	
	public void btEndTaskTimerClick(View view) {
		endTask.setRealDate(new Date());
		endTask.setState(eTaskState.Done);
		TaskManager.Instance().save(endTask);
		fillUpEndTask();
		saveEmployment();
		clearEmployment();
		if (Option.Instance().getIsAuto())
			startSyncActivity();
		else
			startPatientsActivity();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.tasks, menu);
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.tasks, menu);
	}

	@Override
	public void onBackPressed() {
		if(isClickable()){
			BaseDialog dialog = new BaseDialog(getString(R.string.dialog_task_proof_back));
			dialog.show(getSupportFragmentManager(), "dialogBack");
			getSupportFragmentManager().executePendingTransactions();
		}
		else
		{
			clearEmployment();
			startPatientsActivity();
		}
	}
				
	public void onTaskClick(View view) {
		Task task = (Task) view.getTag();
		if(task.getQualityResult().isEmpty())
			return;
		DialogFragment dialog = null;
		switch(task.getQuality()){
			case AdditionalTask.WEIGHT:
				dialog = new StandardTaskDialog(task, getString(R.string.weight), task.getQualityResult());
				break;
			case AdditionalTask.DETECT_RESPIRATION:
				dialog = new StandardTaskDialog(task, getString(R.string.detect_respiration), task.getQualityResult());
				break;
			case AdditionalTask.BALANCE:
				dialog = new StandardTaskDialog(task, getString(R.string.balance), task.getQualityResult());
				break;
			case AdditionalTask.BLUTZUCKER:
				dialog = new StandardTaskDialog(task, getString(R.string.blood_sugar), task.getQualityResult());
				break;
			case AdditionalTask.TEMPERATURE:
				dialog = new StandardTaskDialog(task, getString(R.string.temperature), task.getQualityResult());
				break;
			case AdditionalTask.BLUTDRUCK:
				dialog = new BlutdruckTaskDialog(task, task.getQualityResult());
				break;
			case AdditionalTask.PULS:
				dialog = new StandardTaskDialog(task, getString(R.string.pulse), task.getQualityResult());
				break;
			default:
				return;
		}
		dialog.show(getSupportFragmentManager(), "dialogTasks");
		getSupportFragmentManager().executePendingTransactions();
	}
	
	public void onChangeState(View view) {
		if(!isClickable())
			return;
		Task task = (Task) view.getTag();
		task.setRealDate(new Date());
		if (startTask.getManualDate().equals(DateUtils.EmptyDate))
			task.setManualDate(DateUtils.getAverageDate(startTask.getManualDate(), endTask.getManualDate()));
		task.setState((task.getState() == eTaskState.Done) 
				? eTaskState.UnDone
				: eTaskState.Done);
		try {
			((ImageView) view).setImageDrawable(StaticResources.getBaseContext()
				.getResources().getDrawable((task.getState() == eTaskState.UnDone) 
						? R.drawable.ic_action_cancel
						: R.drawable.ic_action_accept));
			TaskManager.Instance().save(task);
			fillUpEndButtonEnabling();
		} catch (Exception e) {
			e.printStackTrace();
		}
		openDialogForAdditionalTask(task);
	}
	
	private void openDialogForAdditionalTask(Task task){
		DialogFragment dialog = null;
		switch(task.getQuality()){
			case AdditionalTask.WEIGHT:
				dialog = new StandardTaskDialog(task, getString(R.string.weight));
				break;
			case AdditionalTask.DETECT_RESPIRATION:
				dialog = new StandardTaskDialog(task, getString(R.string.detect_respiration));
				break;
			case AdditionalTask.BALANCE:
				dialog = new StandardTaskDialog(task, getString(R.string.balance));
				break;
			case AdditionalTask.BLUTZUCKER:
				dialog = new StandardTaskDialog(task, getString(R.string.blood_sugar));
				break;
			case AdditionalTask.TEMPERATURE:
				dialog = new StandardTaskDialog(task, getString(R.string.temperature));
				break;
			case AdditionalTask.BLUTDRUCK:
				dialog = new BlutdruckTaskDialog(task);
				break;
			case AdditionalTask.PULS:
				dialog = new StandardTaskDialog(task, getString(R.string.pulse));
				break;
			default:
				return;
		}
		dialog.show(getSupportFragmentManager(), "dialogTasks");
		getSupportFragmentManager().executePendingTransactions();
	}

	private void initControls() {
		lvTasks = (ListView) findViewById(R.id.lvTasksList);
		btEndTask = (Button) findViewById(R.id.btEndTask);
		btStartTask = (Button) findViewById(R.id.btStartTask);
		
		tvStartTaskTime = (TextView) findViewById(R.id.tvStartTaskTime);
		tvStartTaskDate = (TextView) findViewById(R.id.tvStartTaskDate);
		
		tvEndTaskTime = (TextView) findViewById(R.id.tvEndTaskTime);
		tvEndTaskDate = (TextView) findViewById(R.id.tvEndTaskDate);
	}

	private void checkAllTasks(eTaskState state){
		checkAllTasks(state, new Date());
	}
	
	private void checkAllTasks(eTaskState state, Date date){
		for(Task task : tasks) {
			task.setState(state);
			task.setRealDate(date);
			task.setQualityResult("");
		}
		TaskManager.Instance().save(tasks);	
	}
	
	private void checkAllTasksAndFillUp(eTaskState state){
		checkAllTasks(state);
		fillUpTasks();
	}
	
	private void saveEmployment() {
		if (Option.Instance().getEmploymentID() == BaseObject.EMPTY_ID)
			return;			
		Employment empl = EmploymentManager.Instance().load(Option.Instance().getEmploymentID());
		EmploymentInterval emplInterval = new EmploymentInterval(empl.getID(), 
				(startTask.getManualDate().equals(DateUtils.EmptyDate) 
						? startTask.getRealDate()
						: startTask.getManualDate()), 
							(endTask.getManualDate().equals(DateUtils.EmptyDate) 
								? endTask.getRealDate()
								: endTask.getManualDate()));
		EmploymentIntervalManager.Instance().save(emplInterval);
		empl.setStartTime(emplInterval.getStartTime());
		empl.setStopTime(emplInterval.getStopTime());
		empl.setIsDone(true);
		EmploymentIntervalManager.Instance().save(new EmploymentInterval(empl.getID(), empl.getStartTime(), empl.getStopTime()));
		EmploymentManager.Instance().save(empl);
		if(empl != null){
			empl.setIsDone(true);
			EmploymentManager.Instance().save(empl);
		}
	}
	
	private void clearEmployment() {
		Option.Instance().setEmploymentID(BaseObject.EMPTY_ID);
		Option.Instance().save();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.catalogs:			
			if(isAllDone())
				return false;
			Intent catalogsActivity = new Intent(getApplicationContext(), CatalogsActivity.class);
			startActivity(catalogsActivity);
			return true;
		case R.id.cancelAllTasks:
			if(isAllDone())
				return false;
			checkAllTasksAndFillUp(eTaskState.UnDone);
			saveEmployment();
			clearEmployment();
			if (Option.Instance().getIsAuto())
				startSyncActivity();
			else
				startPatientsActivity();
			return true;
		case R.id.notes:
			Intent notesActivity = new Intent(getApplicationContext(),
					UserRemarksActivity.class);
			startActivity(notesActivity);
			return true;
		case R.id.info:
			loadPatientInfos(true);
			return true;
		case R.id.manualInput:
			Intent manualInputActivity = new Intent(getApplicationContext(),
					ManualInputActivity.class);
			startActivity(manualInputActivity);
			return true;
		case R.id.address:
			Intent addressActivity = new Intent(getApplicationContext(),
					AddressPatientActivity.class);
			startActivity(addressActivity);
			return true;
		case R.id.doctors:
			Intent doctorsActivity = new Intent(getApplicationContext(),
					DoctorsActivity.class);
			startActivity(doctorsActivity);
			return true;
		case R.id.relatives:
			Intent relativesActivity = new Intent(getApplicationContext(),
					RelativesActivity.class);
			startActivity(relativesActivity);
			return true;
		case R.id.comments:
			loadComments();
			return true;
		case R.id.diagnose:
			showDiagnose();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void showDiagnose(){
		Diagnose diagnose = DiagnoseManager.Instance().load(employment.getPatientID());
		InfoBaseDialog dialog = new InfoBaseDialog(getString(R.string.menu_diagnose),diagnose.getName());
		dialog.show(getSupportFragmentManager(), "");
		getSupportFragmentManager().executePendingTransactions();
	}
	
	private void removeAdditionalTasks(){
		for(Task t : tasks)
			if(t.getIsAdditionalTask())
				TaskManager.Instance().delete(t.getID());
	}
	
	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		if(dialog.getTag().equals("dialogBack")){
			checkAllTasks(eTaskState.Empty, DateUtils.EmptyDate);
			removeAdditionalTasks();
			clearEmployment();
			startPatientsActivity();
		}
		else{
			StandardTaskDialog taskDialog = (StandardTaskDialog)dialog;
			Task task = taskDialog.getTask();
			String value = taskDialog.getValue();
			task.setQualityResult(value);
			TaskManager.Instance().save(task);
		}
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		return;
	}

	private void loadPatientInfos(boolean is_from_menu){
		infos = InformationManager.Instance().load(Information.EmploymentCodeField, String.valueOf(employment.getID()));
		String strInfos = "";
		Date today = new Date();

		for(Information info : infos){
			if((DateUtils.getDateOnly(today).getTime() != DateUtils.getDateOnly(info.getReadTime()).getTime()) || is_from_menu)
				if((today.getTime() >= info.getFromDate().getTime()) && (today.getTime() <= info.getTillDate().getTime())){
					if(strInfos != "")
						strInfos += "\n";
					strInfos += info.getName();
					info.setReadTime(today);
				}
		}
		if(strInfos.length() > 0){
			InformationManager.Instance().save(infos);
			InfoBaseDialog dialog = new InfoBaseDialog(getString(R.string.dialog_info),strInfos);
			dialog.show(getSupportFragmentManager(), "");
		}
	}

	private void loadComments(){
		InfoBaseDialog dialog = new InfoBaseDialog(getString(R.string.dialog_comments),patientRemark.getName());
		dialog.show(getSupportFragmentManager(), "");
	}
}
