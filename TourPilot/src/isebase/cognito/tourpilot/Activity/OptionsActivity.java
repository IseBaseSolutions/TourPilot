package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.BaseActivities.BaseActivity;
import isebase.cognito.tourpilot.Connection.ConnectionInfo;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.DataBase.DataBaseWrapper;
import isebase.cognito.tourpilot.Dialogs.InfoBaseDialog;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import isebase.cognito.tourpilot.Utils.DataBaseUtils;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class OptionsActivity extends BaseActivity {
	
	private static final int PICKFILE_RESULT_CODE = 0;
	
	private DialogFragment noConnectionDialog;
	private DialogFragment noIPEnteredDialog;

	private EditText etServerIP;
	private EditText etServerPort;
	private EditText etPhoneNumber;
	private EditText etPin;
	
	private ProgressBar pbBusy;
	private Button syncButton;
	private CheckBox cbSavePin;

	private static final int BACKUP_MODE = 0;
	private static final int CLEAR_MODE = 1;
	private static final int RESTORE_MODE = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			StaticResources.setBaseContext(getBaseContext());
			setContentView(R.layout.activity_options);
			switchToLastActivity();
			initControls();
			fillUp();
			initDialogs();
		}
		catch(Exception e) {
			e.printStackTrace();
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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_show_program_info:
			versionFragmentDialog.show(getSupportFragmentManager(), "dialogVersion");
			return true;
		case R.id.action_clear_database:
			busy(CLEAR_MODE);
			return true;
		case R.id.action_db_backup:		
			busy(BACKUP_MODE);
			return true;
		case R.id.action_db_restore:		
			chooseFile();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {	
		if (data == null || data.getData() == null)
			return;
		String path = data.getData().getPath();
		if(requestCode == PICKFILE_RESULT_CODE && resultCode == RESULT_OK)
			if(path.endsWith(".db"))
				busy(RESTORE_MODE, path);
			else
				Toast.makeText(StaticResources.getBaseContext()
					, R.string.err_not_db_file
					, Toast.LENGTH_SHORT).show();
				
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

	private void busy(final int mode, final String... data){
		pbBusy.setVisibility(View.VISIBLE);
		syncButton.setEnabled(false);
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try{
					switch(mode){
						case BACKUP_MODE:
							DataBaseUtils.backup();
							break;
						case CLEAR_MODE:
							DataBaseWrapper.Instance().clearAllData();
							break;
						case RESTORE_MODE:
							DataBaseUtils.restore(data[0]);
							break;
					}							
				}
				catch(Exception e){
					e.printStackTrace();
				}
				return null;
			}
							
			@Override
			protected void onPostExecute(Void result) {
				pbBusy.setVisibility(View.INVISIBLE);
				syncButton.setEnabled(true);
				int textID = 0;
				switch(mode){
					case BACKUP_MODE:
						textID = R.string.db_backup_created;
						break;
					case CLEAR_MODE:
						textID = R.string.db_cleared;
						break;
					case RESTORE_MODE:
						textID = R.string.db_backup_restored;
						fillUp();
						break;
					default:
						return;
				}
				Toast.makeText(StaticResources.getBaseContext()
						, textID
						, Toast.LENGTH_SHORT).show();
			}
		}.execute();
	}

	private void initControls() {
		syncButton = (Button)findViewById(R.id.btSynchronization);
		pbBusy = (ProgressBar) findViewById(R.id.pbClearDB);
		etServerIP = (EditText) findViewById(R.id.etServerIP);
		etServerPort = (EditText) findViewById(R.id.etServerPort);
		etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
		etPin = (EditText) findViewById(R.id.etPinCode);
		cbSavePin = (CheckBox)findViewById(R.id.cb_SavePin);
	}
	
	private void fillUp(){
		etPhoneNumber.setText(Option.Instance().getPhoneNumber());
		etServerIP.setText(Option.Instance().getServerIP());
		etServerPort.setText(String.valueOf(Option.Instance().getServerPort()));
		etPin.setText(String.valueOf(Option.Instance().getPin()));
	}

	private void saveOptions() {		
		Option.Instance().setPrevWorkerID(BaseObject.EMPTY_ID);
		Option.Instance().setWorkerID(BaseObject.EMPTY_ID);
		Option.Instance().setServerIP(etServerIP.getText().toString());
		Option.Instance().setServerPort(Integer.parseInt(etServerPort.getText().toString()));
		Option.Instance().save();
		Option.Instance().setPin(etPin.getText().toString());
		if(cbSavePin.isChecked())
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
	
	private void chooseFile(){
	   Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
       intent.setType("file/*");
       try{
    	   startActivityForResult(intent, PICKFILE_RESULT_CODE);
       }
       catch(Exception e){
			Toast.makeText(StaticResources.getBaseContext()
					, R.string.err_no_filemanager
					, Toast.LENGTH_LONG).show();
       }
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
