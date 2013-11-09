 package isebase.cognito.tourpilot.Activity;
 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Connection.ConnectionAsyncTask;
import isebase.cognito.tourpilot.Connection.ConnectionStatus;
import isebase.cognito.tourpilot.EventHandle.SynchronizationHandler;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
 
 public class SynchronizationActivity extends BaseActivity{
 	
	private ListView lvConnectionLog;
 	private SynchronizationHandler syncHandler;
	private ArrayAdapter<String> adapter;
	private ConnectionStatus connectionStatus;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
 	
 	@Override
 	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
 		setContentView(R.layout.activity_synchronization);
		lvConnectionLog = (ListView) findViewById(R.id.lvSyncText);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, new ArrayList<String>());
		lvConnectionLog.setAdapter(adapter);
		adapter.add("Waiting open sockets to automatically close...");
 		syncHandler = new SynchronizationHandler() {
 			
 			@Override
			public void onSynchronizedFinished(boolean isOK, String text) {
				if(!text.equals(""))
					adapter.add(dateFormat.format(new Date()) + " " + text);	
				if(isOK){
					Intent workersActivity= new Intent(getApplicationContext()
							, WorkersActivity.class);
					startActivity(workersActivity);	
				}
 			}
 			
 			@Override
 			public void onItemSynchronized(String text) {
				adapter.add(dateFormat.format(new Date()) + " " + text);	
				new ConnectionAsyncTask(connectionStatus).execute(); 
 			}
				
 		};	
	
		connectionStatus = new ConnectionStatus(syncHandler);
		new ConnectionAsyncTask(connectionStatus).execute();
 	}	
 }
