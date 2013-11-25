package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.EmploymentInterval.EmploymentInterval;
import isebase.cognito.tourpilot.Data.EmploymentInterval.EmploymentIntervalManager;
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
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class PatientsActivity extends BaseActivity {

	private List<Employment> employments;
	private List<Work> works;
	private List<IJob> items;
	private Work work;
	private DialogFragment patientsDialog;
	private String[] patientsArr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_patients);
			reloadData();
			fillUpTitle();
			fillUp();
			initDialogs();

		}catch(Exception ex){
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add_additional_work:
			startAdditionalWorksActivity();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onBackPressed() {
		startToursActivity();
	}
	
	private void startToursActivity() {
		Intent toursActivity = new Intent(getApplicationContext(), ToursActivity.class);
		startActivity(toursActivity);
	}
	
	private void startTasksActivity() {
		Intent tasksActivity = new Intent(getApplicationContext(), TasksActivity.class);
		startActivity(tasksActivity);
	}
	
	private void startAdditionalWorksActivity() {
		Intent additionalWorksActivity = new Intent(getApplicationContext(), AdditionalWorksActivity.class);
		startActivity(additionalWorksActivity);
	}

	public void fillUp() {
		WorkEmploymentAdapter adapter = new WorkEmploymentAdapter(this,R.layout.row_work_employment_template, items);
		ListView lvEmployments = (ListView) findViewById(R.id.lvEmployments);
		lvEmployments.setAdapter(adapter);
		lvEmployments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if (items.get(position) instanceof Employment)
				{
					saveSelectedEmploymentID(((Employment)items.get(position)).getID());
					startTasksActivity();
				}
				else
				{
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
					patientsDialog.show(getSupportFragmentManager(), "patientsDialog");
				}
			}
		});
	}

	public void reloadData() {
		employments = EmploymentManager.Instance().load(Employment.PilotTourIDField, String.valueOf(Option.Instance().getPilotTourID()));
		works = WorkManager.Instance().loadAll(Work.PilotTourIDField, String.valueOf(Option.Instance().getPilotTourID()));
		List<EmploymentInterval> s = EmploymentIntervalManager.Instance().load();
		items = new ArrayList<IJob>();
		items.addAll(employments);
		items.addAll(works);
		Collections.sort(items, new JobComparer());
	}
	
	private void fillUpTitle() {
		PilotTour pt = PilotTourManager.Instance().loadPilotTour(Option.Instance().getPilotTourID());
		Worker worker = Option.Instance().getWorker();
		setTitle(String.format("%1$s, %2$s - %3$s"
				, worker.getName()
				, pt.getName()
				, DateUtils.WeekDateFormat.format(pt.getPlanDate())));
	}
	
	private void saveSelectedEmploymentID(int emplID) {
		Option.Instance().setEmploymentID(emplID);
		Option.Instance().save();
	}
	
	private void initDialogs() {
		patientsDialog = new DialogFragment() {
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
	
}
