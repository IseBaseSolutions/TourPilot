package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.AdditionalWork.AdditionalWork;
import isebase.cognito.tourpilot.Data.AdditionalWork.AdditionalWorkManager;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Data.Work.Work;
import isebase.cognito.tourpilot.Data.Work.WorkManager;
import isebase.cognito.tourpilot.Dialogs.BaseDialogListener;
import isebase.cognito.tourpilot.Dialogs.PatientsDialog;
import isebase.cognito.tourpilot.Dialogs.WorkStopDialog;
import isebase.cognito.tourpilot.Dialogs.WorkTypeDialog;

import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AdditionalWorksActivity extends BaseActivity implements BaseDialogListener {

	private DialogFragment workInputDialog;
	private DialogFragment workStopDialog;
	private PatientsDialog patientsDialog;

	List<AdditionalWork> additionalWorks;
	List<Patient> patients;
	String[] patientNames;
	boolean[] selectedPatients;
	
	AdditionalWork addWork;
	Work work;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_additional_works);
		reloadData();
		fillUp();
		fillUpTitle();
		switchTolatest();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void fillUp() {
		ListView listView = (ListView) findViewById(R.id.lvAdditionalWorks);
		ArrayAdapter<AdditionalWork> adapter = new ArrayAdapter<AdditionalWork>(
				this, android.R.layout.simple_list_item_1, additionalWorks);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				addWork = additionalWorks.get(position);
				workInputDialog = new WorkTypeDialog(addWork.getName());
				workInputDialog.show(getSupportFragmentManager(), "addWorkDialog");
			}

		});
	}

	private void fillUpTitle() {
		setTitle(R.string.additional_work);
	}

	private void reloadData() {
		additionalWorks = AdditionalWorkManager.Instance().load();
		patients = PatientManager.Instance().loadByPilotTourID(Option.Instance().getPilotTourID());
		if (Option.Instance().getWorkID() != -1)
			work = WorkManager.Instance().load(Option.Instance().getWorkID());
	}
	
	private void switchTolatest() { 
		if (Option.Instance().getWorkID() != -1)
			workStopDialog.show(getSupportFragmentManager(), "stopDialog");
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		if (dialog == patientsDialog)
		{
			work.setPatientIDs(patientsDialog.getSelectedPatientIDs());
			work.setIsDone(true);
			WorkManager.Instance().save(work);
			Option.Instance().setWorkID(BaseObject.EMPTY_ID);
			Option.Instance().save();
			startPatientsActivity();
		}
		if (dialog == workInputDialog)
		{
			work = new Work(new Date(), addWork.getID(), Option.Instance().getPilotTourID(), addWork.getName());
			WorkManager.Instance().save(work);
			Option.Instance().setWorkID(work.getID());
			Option.Instance().save();
			workStopDialog = new WorkStopDialog(addWork.getName(), work.startTime());
			workStopDialog.show(getSupportFragmentManager(), "stopDialog");
		}
		if (dialog == workStopDialog)
		{
			work.setStopTime(new Date());
			WorkManager.Instance().save(work);
			patientsDialog = new PatientsDialog(patients, work.getName());
			patientsDialog.show(getSupportFragmentManager(), "patientsDialog");
		}
		
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		if (dialog == workInputDialog)
			startManualInputActivity();
	}
	
	@Override
	protected void startManualInputActivity() {
		Intent manualInputActivity = new Intent(getApplicationContext(), ManualInputActivity.class);
		manualInputActivity.putExtra("addWorkID", addWork.getID());
		startActivity(manualInputActivity);
	}

}
