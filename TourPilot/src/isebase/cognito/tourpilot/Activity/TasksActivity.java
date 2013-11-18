package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.AdditionalTasks.CatalogsActivity;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.Data.Task.Task.eTaskState;
import isebase.cognito.tourpilot.Data.Task.TaskManager;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import isebase.cognito.tourpilot.Templates.TaskAdapter;
import isebase.cognito.tourpilot.Utils.DateUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
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

public class TasksActivity extends BaseActivity {

	private TaskAdapter taskAdapter;
	
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
	
	private boolean isClickable(){
		return !startTask.getRealDate().equals(DateUtils.EmptyDate) 
				&& endTask.getRealDate().equals(DateUtils.EmptyDate);
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
		}catch(Exception ex){
			ex.printStackTrace();
			criticalClose();
		}
	}
	
	private void checkAllIsDone(){
		if(!startTask.getRealDate().equals(DateUtils.EmptyDate) 
			&& !endTask.getRealDate().equals(DateUtils.EmptyDate)){
			btEndTask.setEnabled(false);
			btStartTask.setEnabled(false);
		}
	}
	
	private void fillUpEndButtonEnabling(){
		btEndTask.setEnabled(false);
		for(int i=1; i < tasks.size() -1 ; i++){
			Task task = tasks.get(i);
			if(task.getTaskState() == eTaskState.Empty)
				return;
		}
		btEndTask.setEnabled(true);
	}
	
	private void fillUpStartTask(){
		tvStartTaskTime.setText(DateUtils.HourMinutesFormat.format(startTask.getRealDate()));
		tvStartTaskDate.setText(DateUtils.DateFormat.format(startTask.getRealDate()));
	}
	
	private void fillUpEndTask(){
		tvEndTaskTime.setText(DateUtils.HourMinutesFormat.format(endTask.getRealDate()));
		tvEndTaskDate.setText(DateUtils.DateFormat.format(endTask.getRealDate()));
	}
	
	private void fillUpTitle(){
		String title = startTask.getName().substring(15,startTask.getName().length()-1);
		setTitle(title);
	}
	
	private void fillUpTasks(){
		List<Task> tasksWithoutFirstAndLast = new ArrayList<Task>(tasks);
		tasksWithoutFirstAndLast.remove(0);
		tasksWithoutFirstAndLast.remove(tasksWithoutFirstAndLast.size() - 1);
		taskAdapter = new TaskAdapter(this
				, R.layout.row_task_template
				, tasksWithoutFirstAndLast);
		lvTasks.setAdapter(taskAdapter);
		fillUpTitle();
		fillUpEndButtonEnabling();
		fillUpStartTask();
		fillUpEndTask();
	}	

	public void reloadData() {		
		tasks = TaskManager.Instance().load(Task.EmploymentIDField, Option.Instance().getEmploymentID()+"");
		startTask = tasks.get(0);
		endTask = tasks.get(tasks.size() - 1);
	}
	
	public void btStartTaskTimerClick(View view) {
		checkAllTasks(eTaskState.Empty);
		startTask.setRealDate(new Date());
		endTask.setRealDate(DateUtils.EmptyDate);
		TaskManager.Instance().save(startTask);
		TaskManager.Instance().save(endTask);
		fillUpStartTask();
		fillUpEndTask();
		fillUpEndButtonEnabling();
	}
	
	public void btEndTaskTimerClick(View view) {
		endTask.setRealDate(new Date());
		TaskManager.Instance().save(endTask);
		fillUpEndTask();
		saveEmployment(true,false);	
		switchToPatientsActivity();
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
		if(!isClickable())
			switchToPatientsActivity();
	}

	public void onChangeState(View view) {
		if(!isClickable())
			return;
		Task task = (Task) view.getTag();
		task.setRealDate(new Date());
		task.setTaskState((task.getTaskState() == eTaskState.Done) 
				? eTaskState.UnDone
				: eTaskState.Done);
		try {
			((ImageView) view).setImageDrawable(StaticResources.getBaseContext()
				.getResources().getDrawable((task.getTaskState() == eTaskState.UnDone) 
						? R.drawable.ic_action_cancel
						: R.drawable.ic_action_accept));
			TaskManager.Instance().save(task);
			fillUpEndButtonEnabling();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	private void checkAllTasks(Task.eTaskState state){
		Date newDate = new Date();
		for(Task t : tasks){
			t.setTaskState(state);
			t.setRealDate(newDate);
		}
		TaskManager.Instance().save(tasks);
		fillUpTasks();
	}
	
	private void switchToPatientsActivity() {
		Intent patientsActivity = new Intent(getApplicationContext(), PatientsActivity.class);
		startActivity(patientsActivity);
	}
	
	private void saveEmployment(boolean isDone, boolean isAborted){
		if(!isAborted){
			for(Task t: tasks){
				isAborted =  true;
				if(t.getTaskState() == eTaskState.Done){
					isAborted = false;
					break;
				}
			}
		}
		
		Employment empl = EmploymentManager.Instance().load(Option.Instance().getEmploymentID());
		empl.setDone(isDone);
		empl.setAborted(isAborted);
		EmploymentManager.Instance().save(empl);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.add_task_category:
			Intent addTasksCategoryActivity = new Intent(
					getApplicationContext(), CatalogsActivity.class);
			startActivity(addTasksCategoryActivity);
			return true;
		case R.id.cancelAllTasks:
			checkAllTasks(eTaskState.UnDone);
			saveEmployment(false,true);
			switchToPatientsActivity();
			return true;
		case R.id.notes:
			Intent notesActivity = new Intent(getApplicationContext(),
					NotesActivity.class);
			startActivity(notesActivity);
			return true;
		case R.id.info:
			Intent infoActivity = new Intent(getApplicationContext(),
					InfoActivity.class);
			startActivity(infoActivity);
			return true;
		case R.id.manualInput:
			Intent manualInputActivity = new Intent(getApplicationContext(),
					ManualInputActivity.class);
			startActivity(manualInputActivity);
			return true;
		case R.id.address:
			Intent addressActivity = new Intent(getApplicationContext(),
					AddressActivity.class);
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
			Intent commentsActivity = new Intent(getApplicationContext(),
					CommentsActivity.class);
			startActivity(commentsActivity);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
