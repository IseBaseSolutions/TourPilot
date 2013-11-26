package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Connection.ConnectionInfo;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.DataBase.DataBaseWrapper;
import isebase.cognito.tourpilot.Dialogs.InfoBaseDialog;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import isebase.cognito.tourpilot.Utils.DataBaseUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class OptionsActivity extends BaseActivity {

	private DialogFragment noConnectionDialog;
	private DialogFragment noIPEnteredDialog;

	private EditText etServerIP;
	private EditText etServerPort;
	private EditText etPhoneNumber;
	
	private ProgressBar pbClearDB;
	private Button syncButton;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			StaticResources.setBaseContext(getBaseContext());
			setContentView(R.layout.activity_options);
			switchToLastActivity();
			initControls();
			initDialogs();
		}
		catch(Exception ex){
		}
	}

	@Override
	public void onBackPressed() {
		
	}
	
	@Override
	protected boolean isMainActivity() {
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}

	private void busy(final boolean dbBackup){
		pbClearDB.setVisibility(View.VISIBLE);
		syncButton.setEnabled(false);
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try{
					if(dbBackup)
						DataBaseUtils.backup();
					else
						DataBaseWrapper.Instance().clearAllData();	
				}
				catch(Exception ex){
					ex.printStackTrace();
				}

				return null;
			}
							
			@Override
			protected void onPostExecute(Void result) {
				pbClearDB.setVisibility(View.INVISIBLE);
				syncButton.setEnabled(true);
			}
		}.execute();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_clear_database:
			busy(false);
			return true;
		case R.id.action_show_program_info:
			versionFragmentDialog.show(getSupportFragmentManager(), "dialogVersion");
			return true;
		case R.id.action_db_backup:		
			busy(true);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void btStartSyncClick(View view) {
		if (etServerIP.getText().toString().equals("")) {
			noIPEnteredDialog.show(getSupportFragmentManager(),"dialogNoIPEntered");
			return;
		}
		if (ConnectionInfo.Instance().getNetworkInfo() == null
				|| !ConnectionInfo.Instance().getNetworkInfo().isConnected()) {
			noConnectionDialog.show(getSupportFragmentManager(), "dialogNoConnection");
			return;
		}
		saveOptions();
		startSyncActivity();
	}

	public void initControls() {
		syncButton = (Button)findViewById(R.id.btSynchronization);
		pbClearDB = (ProgressBar) findViewById(R.id.pbClearDB);
		etServerIP = (EditText) findViewById(R.id.etServerIP);
		etServerPort = (EditText) findViewById(R.id.etServerPort);
		etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
		etPhoneNumber.setText(Option.Instance().getPhoneNumber());
		etServerIP.setText(Option.Instance().getServerIP());
		etServerPort.setText(String.valueOf(Option.Instance().getServerPort()));
	}

	private void saveOptions() {
		Option.Instance().setPrevWorkerID(BaseObject.EMPTY_ID);
		Option.Instance().setWorkerID(BaseObject.EMPTY_ID);
		Option.Instance().setServerIP(etServerIP.getText().toString());
		Option.Instance().setServerPort(Integer.parseInt(etServerPort.getText().toString()));
		Option.Instance().save();
	}

	private void initDialogs() {			
		versionFragmentDialog = new InfoBaseDialog(
			getString(R.string.menu_program_info), 
			String.format("%s %s\n%s %s"
					, getString(R.string.program_version)
					, Option.Instance().getVersion()
					, getString(R.string.data_base_version)
					, DataBaseWrapper.DATABASE_VERSION)
			);
		noIPEnteredDialog = new InfoBaseDialog(
				getString(R.string.dialog_connection_problems),
				getString(R.string.dialog_no_ip_entered));
		noConnectionDialog = new InfoBaseDialog(
				getString(R.string.dialog_connection_problems),
				getString(R.string.dialog_no_connection));
	}
		
	private void switchToLastActivity() {
		if (Option.Instance().getWorkID() != -1)
			startAdditionalWorksActivity();
		else if (Option.Instance().getEmploymentID() != -1)
			startTasksActivity();
		else if (Option.Instance().getPilotTourID() != -1)
			startPatientsActivity();
		else if (Option.Instance().getWorkerID() != -1)
			startToursActivity();
		else if (Option.Instance().isWorkerActivity())
			startWorkersActivity();
			return;
	}
}
