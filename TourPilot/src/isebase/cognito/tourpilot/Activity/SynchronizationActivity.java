package isebase.cognito.tourpilot.Activity;

import java.util.Date;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Connection.ServerCommandParser;
import isebase.cognito.tourpilot.EventHandle.SynchronizationHandler;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class SynchronizationActivity extends BaseActivity{
	
	private Context context;
	private ListView lvText;
	private SynchronizationHandler syncHandler;
	private ServerCommandParser serverCommandParser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_synchronization);
		lvText = (ListView) findViewById(R.id.lvSyncText);
		syncHandler = new SynchronizationHandler() {
			
			@Override
			public void onSynchronizedFinished(boolean isOK) {
				
			}
			
			@Override
			public void onItemSynchronized(String text) {
				TextView tv = new TextView(context);
				tv.setText(new Date().toLocaleString() + " " + text);
				lvText.addView(tv);			
			}
		};
		serverCommandParser = new ServerCommandParser(syncHandler);
		
	}	
}
