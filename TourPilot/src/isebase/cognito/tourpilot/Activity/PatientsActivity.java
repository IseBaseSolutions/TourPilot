package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Information.Information;
import isebase.cognito.tourpilot.Data.Information.InformationManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTour;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTourManager;
import isebase.cognito.tourpilot.Data.Work.Work;
import isebase.cognito.tourpilot.Data.Work.WorkManager;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.DataInterfaces.Job.IJob;
import isebase.cognito.tourpilot.DataInterfaces.Job.JobComparer;
import isebase.cognito.tourpilot.Dialogs.InfoBaseDialog;
import isebase.cognito.tourpilot.Dialogs.BaseDialogListener;
import isebase.cognito.tourpilot.Templates.WorkEmploymentAdapter;
import isebase.cognito.tourpilot.Utils.DateUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class PatientsActivity extends BaseActivity implements BaseDialogListener {

	private List<Employment> employments;
	private List<Work> works;
	private List<IJob> items;
	private List<Information> infos = new ArrayList<Information>();
	private Work work;
	private DialogFragment selectedPatientsDialog;
	private String[] patientsArr;
	private PilotTour pilotTour;
	
	private Button btTourEnd;
	private InfoBaseDialog infoDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_patients);
			init();
			reloadData();
			fillUpTitle();
			fillUp();
			initDialogs();
			showTourInfos(false);
		} catch(Exception ex) {
			ex.printStackTrace();
			criticalClose();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.patients_menu, menu);		
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem commonTourMenu = menu.findItem(R.id.action_common_tours);
		MenuItem tourInfoMenu = menu.findItem(R.id.tour_info);
		commonTourMenu.setEnabled(pilotTour.getIsCommonTour());
		tourInfoMenu.setEnabled(infos.size() != 0);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.tour_info:
			showTourInfos(true);
			return true;
		case R.id.action_add_additional_work:
			startAdditionalWorksActivity();
			return true;
		case R.id.action_add_patient_to_illness:
			startAdditionalPatientsActivity(0);
			return true;
		case R.id.action_remove_patient_from_illness:
			startAdditionalPatientsActivity(1);
			return true;
		case R.id.action_add_patient_to_common:
			startAdditionalPatientsActivity(2);
			return true;
		case R.id.action_remove_patient_from_common:
			startAdditionalPatientsActivity(3);
			return true;
		case R.id.action_show_all_patients:
			startAdditionalPatientsActivity(4);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}	
	
	@Override
	public void onBackPressed() {
		startToursActivity();
	}
	
	public void btEndTourClick(View view){
		for(IJob job : items)
			if(!job.isDone()){
				infoDialog.show(getSupportFragmentManager(), "");
				getSupportFragmentManager().executePendingTransactions();
				return;
			}

		Option.Instance().setPilotTourID(BaseObject.EMPTY_ID);
		Option.Instance().save();
		startSyncActivity();
	}

	private void init(){
		btTourEnd = (Button) findViewById(R.id.btEndTour);
	}
	
	private void fillUp() {
		WorkEmploymentAdapter adapter = new WorkEmploymentAdapter(this, R.layout.row_work_employment_template, items);
		ListView lvEmployments = (ListView) findViewById(R.id.lvEmployments);
		lvEmployments.setAdapter(adapter);
		lvEmployments.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if (items.get(position) instanceof Employment)
				{
					saveSelectedEmploymentID(((Employment)items.get(position)).getID());
					startTasksActivity();
				}
				else
					showPatientsDialog(position);
			}
		});
	}

	private void reloadData() {
		employments = EmploymentManager.Instance().load(Employment.PilotTourIDField, String.valueOf(Option.Instance().getPilotTourID()));
		works = WorkManager.Instance().loadAll(Work.PilotTourIDField, String.valueOf(Option.Instance().getPilotTourID()));
		pilotTour = PilotTourManager.Instance().loadPilotTour(Option.Instance().getPilotTourID());
		items = new ArrayList<IJob>();
		items.addAll(employments);
		items.addAll(works);
		Collections.sort(items, new JobComparer());
		infos = InformationManager.Instance().load(Information.EmploymentCodeField, BaseObject.EMPTY_ID);
		checkTourEndButton();
	}
	
	private void checkTourEndButton(){
		int taskCount = items.size();
		int syncTaskCount = 0;
		for(IJob job : items)
			if(job.wasSent())
				syncTaskCount++;
		btTourEnd.setEnabled(!(syncTaskCount == taskCount 
				|| !DateUtils.isToday(pilotTour.getPlanDate())));		
	}
	
	private void fillUpTitle() {
		Worker worker = Option.Instance().getWorker();
		setTitle(String.format("%1$s, %2$s - %3$s"
				, worker.getName()
				, pilotTour.getName()
				, DateUtils.WeekDateFormat.format(pilotTour.getPlanDate())));
	}
	
	private void saveSelectedEmploymentID(int emplID) {
		Option.Instance().setEmploymentID(emplID);
		Option.Instance().save();
	}
	
	private void initDialogs() {
		infoDialog = new InfoBaseDialog(getString(R.string.attention)
				,getString(R.string.dialog_complete_all_tasks));
		selectedPatientsDialog = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
						.setTitle(work.getName())
						.setItems(patientsArr, new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
							}
						})
						.setPositiveButton(
								isebase.cognito.tourpilot.R.string.ok, new OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										
									}
								});
				return adb.create();
			}
		};
	}
	
	private void showPatientsDialog(int position) {
		work = (Work) items.get(position);
		List<Patient> patients = PatientManager.Instance().loadByIDs(work.getPatientIDs());
		if (patients.size() > 0)
		{
			patientsArr = new String[patients.size()];
			int counter = 0;
			for (Patient patient : patients)
				patientsArr[counter++] = patient.getFullName(); 
		}
		else						
			patientsArr = new String[]{getString(R.string.no_any_patient)};
		selectedPatientsDialog.show(getSupportFragmentManager(), "patientsDialog");
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
	
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {

	}
	
	private void startAdditionalPatientsActivity(int mode) {
		Intent additionalPatientsActivity = new Intent(getApplicationContext(), AdditionalEmploymentsActivity.class);
		additionalPatientsActivity.putExtra("Mode", mode);
		startActivity(additionalPatientsActivity);
	}
	
	private void showTourInfos(boolean isFromMenu) {		
		String strInfos = "";
		Date tourDay = pilotTour.getPlanDate();
		for(Information info : infos) {
			if(DateUtils.isToday(info.getReadTime()) && !isFromMenu)
				continue;
			if(tourDay.getTime() >= info.getFromDate().getTime() && tourDay.getTime() <= info.getTillDate().getTime()) {
				strInfos += (strInfos.equals("") ? "" : "\n") + info.getName();
				info.setReadTime(new Date());
			}
		}
		if(!strInfos.equals("")) {
			InformationManager.Instance().save(infos);
			InfoBaseDialog dialog = new InfoBaseDialog(getString(R.string.menu_info), strInfos);
			dialog.show(getSupportFragmentManager(), "informationDialog");
		}
	}
}
