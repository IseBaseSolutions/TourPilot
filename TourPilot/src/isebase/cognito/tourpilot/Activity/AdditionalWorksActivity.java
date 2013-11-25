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
import isebase.cognito.tourpilot.Dialogs.BaseDialog;
import isebase.cognito.tourpilot.Dialogs.PatientsDialog;
import isebase.cognito.tourpilot.StaticResources.StaticResources;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AdditionalWorksActivity extends BaseActivity implements BaseDialogListener {

	private DialogFragment addWorkInputDialog;
	private DialogFragment stopDialog;
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
		initDialogs();
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
				addWorkInputDialog.show(getSupportFragmentManager(), "addWorkDialog");
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
	
	private void startManualInputActivity() {
		Intent manualInputActivity = new Intent(getApplicationContext(), ManualInputActivity.class);
		manualInputActivity.putExtra("addWorkID", addWork.getID());
		startActivity(manualInputActivity);
	}
	
	private void startPatientsActivity() {
		Intent patientsActivity = new Intent(StaticResources.getBaseContext().getApplicationContext(), PatientsActivity.class);
		startActivity(patientsActivity);
	}
	
	private void initDialogs() {
		addWorkInputDialog = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
						.setTitle(addWork.getName())
						.setPositiveButton(
								isebase.cognito.tourpilot.R.string.start, new OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,	int which) {
										work = new Work(new Date(), addWork.getID(), Option.Instance().getPilotTourID(), addWork.getName());
										WorkManager.Instance().save(work);
										Option.Instance().setWorkID(work.getID());
										Option.Instance().save();
										stopDialog.show(getSupportFragmentManager(), "stopDialog");
									}

								})
						.setNegativeButton(
								isebase.cognito.tourpilot.R.string.manual_input, new OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										startManualInputActivity();
									}

								});

				return adb.create();
			}
		};
		
		stopDialog = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
				AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
						.setTitle(work.getName())
						.setMessage(String.format("%s %s", getString(R.string.started_at), format.format(work.getStartTime())))
						.setNegativeButton(
								isebase.cognito.tourpilot.R.string.stop, new OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										work.setStopTime(new Date());
										WorkManager.Instance().save(work);
										patientsDialog.setTitle(work.getName());
										patientsDialog.show(getSupportFragmentManager(), "patientsDialog");
									}

								});

				return adb.create();
			}
		};		
		patientsDialog = new PatientsDialog(patients, "");
	}
	
	private void switchTolatest() { 
		if (Option.Instance().getWorkID() != -1)
			stopDialog.show(getSupportFragmentManager(), "stopDialog");
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
		
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub		
	}

}
