 package isebase.cognito.tourpilot.Activity;
 
import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Connection.AutoUpdate;
import isebase.cognito.tourpilot.Connection.ConnectionAsyncTask;
import isebase.cognito.tourpilot.Connection.ConnectionStatus;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Dialogs.BaseDialog;
import isebase.cognito.tourpilot.Dialogs.BaseDialogListener;
import isebase.cognito.tourpilot.EventHandle.SynchronizationHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
 
 public class SynchronizationActivity extends BaseActivity implements BaseDialogListener {
 	
	private ListView lvConnectionLog;
 	private SynchronizationHandler syncHandler;
	private ArrayAdapter<String> adapter;
	private ConnectionStatus connectionStatus;
	private ConnectionAsyncTask connectionTask;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
	private ProgressBar progressBar;
	private TextView progressText;
 	
 	@Override
 	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
 		setContentView(R.layout.activity_synchronization);
 		initControls();
 		initAdapter();
 		syncHandler = new SynchronizationHandler() {
 			
 			@Override
			public void onSynchronizedFinished(boolean isOK, String text) {
				if(!text.equals("")){
					adapter.insert(dateFormat.format(new Date()) + " " + text, 0);	
					if(!isOK){
						progressText.setText(text);
					}
				}
				if (Option.Instance().getPalmVersion() != null 
 						&& Integer.parseInt(Option.Instance().getPalmVersion()) > Integer.parseInt(Option.Instance().getVersion()))
 				{
					DialogFragment newVersionDialog = new BaseDialog("New version", "There is new version of appliacation. Click yes to update or cancel to return to the options");
					newVersionDialog.show(getSupportFragmentManager(), "newVersionDialog");
					return;
 				}
				if(isOK) {		
					if(Option.Instance().getWorkerID() != -1)
						EmploymentManager.Instance().createEmployments();

					Intent nextActivity = (Option.Instance().getPilotTourID() != -1) 
							? new Intent(getApplicationContext(), PatientsActivity.class) 
							: (Option.Instance().getWorkerID() != -1)
									? new Intent(getApplicationContext(), ToursActivity.class)
									: new Intent(getApplicationContext(), WorkersActivity.class);
					startActivity(nextActivity);					
				}
 			}
 			
 			@Override
 			public void onItemSynchronized(String text) {
				adapter.insert(dateFormat.format(new Date()) + " " + text, 0);	
				connectionStatus.nextState();
				connectionTask = new ConnectionAsyncTask(connectionStatus);
				connectionTask.execute(); 
 			}
 			
 			@Override
 			public void onProgressUpdate(String text, int progress){
 				progressBar.setProgress(progress);
 				progressText.setText(text);
 			}

			@Override
			public void onProgressUpdate(String text) {
				progressBar.setMax(connectionStatus.getTotalProgress());				
			}				
 		};	
	
		connectionStatus = new ConnectionStatus(syncHandler);
		connectionTask = new ConnectionAsyncTask(connectionStatus);
		connectionTask.execute();
 	}	
 	
 	@Override
 	public void onBackPressed() {
		connectionTask.terminate();
		super.onBackPressed();
 	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		if (dialog.getTag().equals("newVersionDialog"))
		{
			AutoUpdate autoUpdate = new AutoUpdate();
			autoUpdate.execute();	
		}	
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		finish();
	}
	
	private void initControls() {
		lvConnectionLog = (ListView) findViewById(R.id.lvSyncText);
		progressBar = (ProgressBar) findViewById(R.id.pbSync);
		progressText = (TextView)findViewById(R.id.tvProgress);
	}
	
	private void initAdapter() {
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, new ArrayList<String>());
		lvConnectionLog.setAdapter(adapter);
		adapter.add(getString(R.string.waitng_sockets_to_close));
	}
 	

}
