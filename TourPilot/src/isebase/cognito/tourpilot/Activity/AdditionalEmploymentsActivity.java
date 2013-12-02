package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.AdditionalTasks.CatalogsActivity;
import isebase.cognito.tourpilot.Connection.ConnectionAsyncTask;
import isebase.cognito.tourpilot.Connection.ConnectionStatus;
import isebase.cognito.tourpilot.Data.AdditionalEmployment.AdditionalEmployment;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTourManager;
import isebase.cognito.tourpilot.Dialogs.BaseDialogListener;
import isebase.cognito.tourpilot.Dialogs.BaseInfoDialog;
import isebase.cognito.tourpilot.EventHandle.SynchronizationHandler;
import isebase.cognito.tourpilot.Templates.AdditionalEmploymentAdapter;
import isebase.cognito.tourpilot.Templates.AdditionalPatientAdapter;
import isebase.cognito.tourpilot.Utils.StringParser;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

public class AdditionalEmploymentsActivity extends BaseActivity implements BaseDialogListener {

	private enum eAdditionalPatientsMode { getIP, removeIP, getCP, removeCP, getAP }
	private eAdditionalPatientsMode additionalEmploymentsMode;
	
	private SynchronizationHandler syncHandler;
	private ConnectionStatus connectionStatus;
	private ConnectionAsyncTask connectionTask;
	
	ListView listView;
	List<AdditionalEmployment> addEmployments = new ArrayList<AdditionalEmployment>();
	List<Patient> addPatients = new ArrayList<Patient>();
	List<Employment> employments;
	ProgressBar pbSync;
	Button btOK;
	DialogFragment noPatientsDialog;
	AdditionalEmploymentAdapter employmentAdapter;
	AdditionalPatientAdapter patientAdapter;
	//List<Patient> patients;
	//Hashtable<String, Integer> items = new Hashtable<String, Integer>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_additional_employments);
 		syncHandler = new SynchronizationHandler() {
 			
 			@Override
			public void onSynchronizedFinished(boolean isOK, String text) {
 			}
 			
 			@Override
 			public void onItemSynchronized(String text) {
 				nextStage();
				connectionTask = new ConnectionAsyncTask(connectionStatus);
				connectionTask.execute();
				if (isGetSyncEnded() || isRemoveSyncEnded())
				{
					startSyncActivity();
					pbSync.setVisibility(View.INVISIBLE);
					btOK.setEnabled(true);
				}
				else if (connectionStatus.CurrentState == 6)
					getPatientStr();
 			}
 			
 			@Override
 			public void onProgressUpdate(String text, int progress){
 				
 			}

			@Override
			public void onProgressUpdate(String text) {
				
			}				
 		};
 		initControls();
 		initDialogs();
		setMode();
 		if (additionalEmploymentsMode == eAdditionalPatientsMode.removeIP 
 				|| additionalEmploymentsMode == eAdditionalPatientsMode.removeCP)
 		{
 			reloadData();
 			fillUp();
 		}
 		else
 			sendFillRequest();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	private void fillUp() {
		pbSync.setVisibility(View.INVISIBLE);
		if ((additionalEmploymentsMode != eAdditionalPatientsMode.getAP && addEmployments.size() == 0) || (additionalEmploymentsMode == eAdditionalPatientsMode.getAP && addPatients.size() == 0))
		{
			noPatientsDialog.show(getSupportFragmentManager(), "noPatientsDialog");
			getSupportFragmentManager().executePendingTransactions();
			return;
		}
		btOK.setEnabled(true);
		listView = (ListView) findViewById(R.id.lvAddEmployments);
		switch(additionalEmploymentsMode) {
		case getAP:
			patientAdapter = new AdditionalPatientAdapter(this, R.layout.row_single_additional_employment_emplate, addPatients);
			listView.setAdapter(patientAdapter);
			break;
		default:
			employmentAdapter = new AdditionalEmploymentAdapter(this, R.layout.row_multi_additional_employment_template, addEmployments);
			listView.setAdapter(employmentAdapter);
			break;
		}
	}
	
	private void setMode() {
		additionalEmploymentsMode = eAdditionalPatientsMode.values()[getIntent().getIntExtra("Mode", 0)];
	}
	
	private void sendFillRequest() {
		pbSync.setVisibility(View.VISIBLE);
		btOK.setEnabled(false);
		connectionStatus = new ConnectionStatus(syncHandler);
		connectionStatus.CurrentState = 0;
		connectionTask = new ConnectionAsyncTask(connectionStatus);
		String requestForServer = "";
		switch(additionalEmploymentsMode) {
		case getIP:
			requestForServer = "GET_WIP;";
			break;
		case getCP:
			requestForServer = "GET_CTP;";
			break;
		case getAP:
			requestForServer = "GET_ALL_PAT;";
			break;
		default:
			break;
		}
		requestForServer += PilotTourManager.Instance().loadPilotTour(Option.Instance().getPilotTourID()).getTourID() + ";" + Option.Instance().getVersion();
		connectionStatus.setRequestForServer(requestForServer);
		connectionTask.execute();
	}
	
	private void sendExecuteRequest() {
		connectionStatus = new ConnectionStatus(syncHandler);
		connectionStatus.CurrentState = 0;
		connectionTask = new ConnectionAsyncTask(connectionStatus);
		String requestForServer = "";
		String tourID = PilotTourManager.Instance().loadPilotTour(Option.Instance().getPilotTourID()).getTourID() + ";";
		switch(additionalEmploymentsMode) {
		case getIP:
			requestForServer = "SEL_WIP;";
			break;
		case removeIP:
			requestForServer = "REMOVE_WIP;";
			break;
		case getCP:
			requestForServer = "SEL_CTP;";
			break;
		case removeCP: 
			requestForServer = "REMOVE_CTP;";
			break;
		case getAP:
			//requestForServer = "GET_ALL;";
			//break;
			return;
		}
		requestForServer += tourID + getPatientsStr() + ";";
		connectionStatus.setRequestForServer(requestForServer);
		connectionTask.execute();
	}
	
	
	private void nextStage() {
		switch(connectionStatus.CurrentState) {
		case 0:	connectionStatus.CurrentState = 1;
			break;
		case 1:	connectionStatus.CurrentState = 8;
			break;	
		case 8:	connectionStatus.CurrentState = 6;
			break;
		}
	}
	
	private void reloadData() {
		employments = EmploymentManager.Instance().load(Employment.PilotTourIDField, String.valueOf(Option.Instance().getPilotTourID()));
		for (Employment employment : employments)
			addEmployments.add(new AdditionalEmployment(employment.getID(), String.format("%s %s\n%s", employment.getTime(), employment.getName(), employment.getDayPart())));
	}
	
	public void btOkClick(View view) {
		if (additionalEmploymentsMode != eAdditionalPatientsMode.getAP)
		{
			pbSync.setVisibility(View.VISIBLE);
			btOK.setEnabled(false);
			sendExecuteRequest();
		}
		else
		{
			Patient patient = PatientManager.Instance().load(patientAdapter.getSelectedAdditionalPatient().getID());
			if (patient == null)
			{
				patient = patientAdapter.getSelectedAdditionalPatient();
				patient.setIsAdditional(true);
				PatientManager.Instance().save(patient);
			}
			Option.Instance().setSelectedAdditionalPatient(patient);
			Employment employment = EmploymentManager.createEmployment(Option.Instance().getSelectedAdditionalPatient());
			Option.Instance().setEmploymentID(employment.getID());
			Option.Instance().save();
			startCatalogsActivity();
		}
	}
	
	private String getPatientsStr() {
		String str = "";
		for (AdditionalEmployment additionalEmployment : employmentAdapter.getSelectedAdditionalEmployments())
			str += (str.equals("") ? "" : ",") + additionalEmployment.getID();
		return str;
	}
	
	private void getPatientStr() {
		boolean isNotEmpty = true;
		StringParser stringParser = new StringParser(connectionStatus.getAnswerFromServer());
		while (isNotEmpty)
			isNotEmpty = parseCommonTouremployments(stringParser.next("\0"));
		fillUp();
	}
	
	private boolean parseCommonTouremployments(String strElement) {
		if (strElement.equals("."))
			return false;
		if (additionalEmploymentsMode != eAdditionalPatientsMode.getAP)
			addEmployments.add(new AdditionalEmployment(Integer.parseInt(strElement.split("@")[1]), strElement.split("@")[0]));
		else
			addPatients.add(new Patient(strElement));
		return true;
	}
	
	private void initControls() {
		pbSync = (ProgressBar) findViewById(R.id.pbSync);
		btOK = (Button) findViewById(R.id.btOK);
	}

	private void initDialogs() {
		noPatientsDialog = new BaseInfoDialog(getString(R.string.dialog_empty_tour), getString(R.string.dialog_no_patients));
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		startPatientsActivity();		
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}
	
	private boolean isRemoveSyncEnded() {
		return connectionStatus.CurrentState == 6 && (additionalEmploymentsMode == eAdditionalPatientsMode.removeIP 
				|| additionalEmploymentsMode == eAdditionalPatientsMode.removeCP);
	}
	
	private boolean isGetSyncEnded() {
		return connectionStatus.getAnswerFromServer().startsWith("OK") && (additionalEmploymentsMode == eAdditionalPatientsMode.getIP 
				|| additionalEmploymentsMode == eAdditionalPatientsMode.getCP);
	}
	
	private void startCatalogsActivity() {
		Intent catalogsActivity = new Intent(getApplicationContext(), CatalogsActivity.class);
		startActivity(catalogsActivity);
	}
	
}
