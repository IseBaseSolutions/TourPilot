package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Connection.ConnectionAsyncTask;
import isebase.cognito.tourpilot.Connection.ConnectionStatus;
import isebase.cognito.tourpilot.Data.AdditionalEmployment.AdditionalEmployment;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTour;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTourManager;
import isebase.cognito.tourpilot.EventHandle.SynchronizationHandler;
import isebase.cognito.tourpilot.Utils.StringParser;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AdditionalEmploymentsActivity extends BaseActivity {

	private enum eAdditionalPatientsMode { getIP, removeIP, getCP, removeCP, getAP }
	private eAdditionalPatientsMode additionalEmploymentsMode;
	
	private SynchronizationHandler syncHandler;
	private ConnectionStatus connectionStatus;
	private ConnectionAsyncTask connectionTask;
	
	ListView listView;
	List<AdditionalEmployment> addEmployments = new ArrayList<AdditionalEmployment>();
	List<Employment> employments;
	//List<Patient> patients;
	//Hashtable<String, Integer> items = new Hashtable<String, Integer>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_additional_employments);
 		syncHandler = new SynchronizationHandler() {
 			
 			@Override
			public void onSynchronizedFinished(boolean isOK, String text) {
//				if(!text.equals("")){
//					adapter.insert(dateFormat.format(new Date()) + " " + text, 0);	
//					if(!isOK){
//						progressText.setText(text);
//					}
//				}
//				if(isOK) {		
//					if(Option.Instance().getWorkerID() != -1)
//						EmploymentManager.Instance().createEmployments();
//
//					Intent nextActivity = (Option.Instance().getPilotTourID() != -1) 
//							? new Intent(getApplicationContext(), PatientsActivity.class) 
//							: (Option.Instance().getWorkerID() != -1)
//									? new Intent(getApplicationContext(), ToursActivity.class)
//									: new Intent(getApplicationContext(), WorkersActivity.class);
//					startActivity(nextActivity);					
//				}
 			}
 			
 			@Override
 			public void onItemSynchronized(String text) {
//				adapter.insert(dateFormat.format(new Date()) + " " + text, 0);	
 				nextStage();
				connectionTask = new ConnectionAsyncTask(connectionStatus);
				connectionTask.execute();
				if (connectionStatus.CurrentState == 6 && (additionalEmploymentsMode == eAdditionalPatientsMode.removeIP 
						|| additionalEmploymentsMode == eAdditionalPatientsMode.removeCP))
					startSyncActivity();
				else if (connectionStatus.getAnswerFromServer().startsWith("OK") && (additionalEmploymentsMode == eAdditionalPatientsMode.getIP 
						|| additionalEmploymentsMode == eAdditionalPatientsMode.getCP))
					startSyncActivity();
				else if (connectionStatus.CurrentState == 6)
					getPatientStr();
 			}
 			
 			@Override
 			public void onProgressUpdate(String text, int progress){
// 				progressBar.setProgress(progress);
// 				progressText.setText(text);
 			}

			@Override
			public void onProgressUpdate(String text) {
//				progressBar.setMax(connectionStatus.getTotalProgress());				
			}				
 		};
 		
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
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu., menu);
		return true;
	}
	
	private void fillUp() {
		listView = (ListView) findViewById(R.id.lvAddEmployments);
		switch(additionalEmploymentsMode) {
		case getAP:
//			ArrayAdapter<Patient> patAdapter = new ArrayAdapter<Patient>(this,
//					android.R.layout.select_dialog_singlechoice, patients);
//			listView.setAdapter(patAdapter);
//			listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			break;
		default:
			ArrayAdapter<AdditionalEmployment> adapter = new ArrayAdapter<AdditionalEmployment>(this,
					android.R.layout.select_dialog_multichoice, addEmployments);
			
			listView.setAdapter(adapter);
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			break;
		}
		listView.setOnItemClickListener( new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

			}
			
		});
	}
	
	private void setMode() {
		additionalEmploymentsMode = eAdditionalPatientsMode.values()[getIntent().getIntExtra("Mode", 0)];
	}
	
	private void sendFillRequest() {
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
		requestForServer += PilotTourManager.Instance().load(Option.Instance().getPilotTourID()).getTourID() + ";" + Option.Instance().getVersion();
		connectionStatus.setRequestForServer(requestForServer);
		connectionTask.execute();
	}
	
	private void sendExecuteRequest() {
		connectionStatus = new ConnectionStatus(syncHandler);
		connectionStatus.CurrentState = 0;
		connectionTask = new ConnectionAsyncTask(connectionStatus);
		String requestForServer = "";
		String tourID = PilotTourManager.Instance().load(Option.Instance().getPilotTourID()).getTourID() + ";";
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
			requestForServer = "GET_ALL;";
			break;
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
			addEmployments.add(new AdditionalEmployment(employment.getID(), String.format("%s %s", employment.getName(), employment.getTime())));
	}
	
	public void btOkClick(View view) {
		sendExecuteRequest();
	}
	
	private String getPatientsStr() {
		String str = "";
		for (int i = 0; i < listView.getCheckedItemPositions().size(); i++)
			str += (str.equals("") ? "" : ",") + addEmployments.get(listView.getCheckedItemPositions().keyAt(i)).getID();
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
			addEmployments.add(new AdditionalEmployment(Integer.parseInt(strElement.split("@")[1]), getNameFromPatientInitStr(strElement)));
		return true;
	}
	
	private String getNameFromPatientInitStr(String str)
	{
		String[] arr = str.split(";");
		String name = arr[2] + " " + arr[3].split(" ")[0];
		return name;
	}

}
