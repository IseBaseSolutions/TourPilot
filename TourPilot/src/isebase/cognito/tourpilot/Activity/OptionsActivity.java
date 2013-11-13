package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Connection.ConnectionInfo;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;
import isebase.cognito.tourpilot.DataBase.DataBaseWrapper;
import isebase.cognito.tourpilot.Dialogs.DialogInfoBase;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class OptionsActivity extends BaseActivity {

	private DialogFragment dialogNoConnection;
	private DialogFragment dialogNoIPEntered;
	private DialogFragment dialogVersionFragment;

	private EditText etServerIP;
	private EditText etServerPort;
	private EditText etPhoneNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		StaticResources.setBaseContext(getBaseContext());
		switchToLastActivity();
		initControls();
		initDialogs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_clear_database:
			// clear database
			return true;
		case R.id.action_show_program_info:
			dialogVersionFragment.show(getSupportFragmentManager(),
					"dialogVersion");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void btStartSyncClick(View view) {
		if (etServerIP.getText().toString().equals("")) {
			dialogNoIPEntered.show(getSupportFragmentManager(),
					"dialogNoIPEntered");
			return;
		}
		if (ConnectionInfo.Instance().getNetworkInfo() == null
				|| !ConnectionInfo.Instance().getNetworkInfo().isConnected()) {
			dialogNoConnection.show(getSupportFragmentManager(),
					"dialogNoConnection");
			return;
		}
		saveOptions();
		startSyncActivity();
	}
	
	private void startSyncActivity() {
		Intent synchActivity = new Intent(getApplicationContext(), SynchronizationActivity.class);
		startActivity(synchActivity);
	}
	
	private void startWorkersActivity() {
		Intent workersActivity = new Intent(getApplicationContext(), WorkersActivity.class);
		startActivity(workersActivity);
	}
	
	private void startToursActivity() {
		Intent toursActivity = new Intent(getApplicationContext(), ToursActivity.class);
		startActivity(toursActivity);
	}
	
	private void startPatientsActivity() {
		Intent patientsActivity = new Intent(getApplicationContext(), PatientsActivity.class);
		startActivity(patientsActivity);
	}
	
	private void startTasksActivity() {
		Intent tasksActivity = new Intent(getApplicationContext(), TasksActivity.class);
		startActivity(tasksActivity);
	}

	public void initControls() {
		etServerIP = (EditText) findViewById(R.id.etServerIP);
		etServerPort = (EditText) findViewById(R.id.etServerPort);
		etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
		etPhoneNumber.setText(Option.Instance().getPhoneNumber());
		etServerIP.setText(Option.Instance().getServerIP());
		etServerPort.setText(String.valueOf(Option.Instance().getServerPort()));
	}

	private void saveOptions() {
		Option.Instance().setWorkerID(BaseObject.EMPTY_ID);
		Option.Instance().setServerIP(etServerIP.getText().toString());
		Option.Instance().setServerPort(Integer.parseInt(etServerPort.getText().toString()));
		Option.Instance().save();
	}

	private void initDialogs() {
		dialogVersionFragment = new DialogInfoBase(
				getString(R.string.program_info), String.format("%s %s\n%s %s",
						getString(R.string.program_version), Option.Instance()
								.getVersion(),
						getString(R.string.data_base_version),
						DataBaseWrapper.DATABASE_VERSION));
		dialogNoIPEntered = new DialogInfoBase(
				getString(R.string.connection_problems),
				getString(R.string.no_ip_entered));
		dialogNoConnection = new DialogInfoBase(
				getString(R.string.connection_problems),
				getString(R.string.no_connection));
	}
	
	private void switchToLastActivity() {
		Intent intent = getIntent();
        String activity = intent.getStringExtra("activity");
		if ((activity == null || activity.compareTo("workers") == 0) && Option.Instance().getWorkerID() == -1)
			return;
		else if (Option.Instance().getTourID() == -1)
			startToursActivity();
		else if (Option.Instance().getPatientID() == -1)
			startPatientsActivity();
		else
			startTasksActivity();
	}
}
