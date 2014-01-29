package isebase.cognito.tourpilot.Activity.WorkersOptionActivity;

import isebase.cognito.tourpilot.R;
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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class OptionsFragment extends Fragment {

	public EditText etServerIP;
	public EditText etServerPort;
	public EditText etPhoneNumber;
	
	private ProgressBar pbBusy;
	
	private CheckBox cbLockOptions;
	
	private View rootView;
	
	public DialogFragment versionFragmentDialog;
	public DialogFragment noConnectionDialog;
	public DialogFragment noIPEnteredDialog;
	
	private final int PICKFILE_RESULT_CODE = 0;
	
	private final int BACKUP_MODE = 0;
	private final int CLEAR_MODE = 1;
	private final int RESTORE_MODE = 2;
	
	public int getBackupMode() {
		return BACKUP_MODE;
	}

	public int getClearMode() {
		return CLEAR_MODE;
	}

	public int getRestoreMode() {
		return RESTORE_MODE;
	}
	
	public OptionsFragment() {
		
	}	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(
				R.layout.new_activity_options, container, false);
		initControls();
		fillUp();
		initDialogs();
		return rootView;
	}

	public void onLockOptionsChecked() {
		initControlsState();
		Option.Instance().setLockOptions(cbLockOptions.isChecked());
		Option.Instance().save();
	}
	
	
	private void initControls() {		
		etServerIP = (EditText) rootView.findViewById(R.id.etServerIP);
		etServerPort = (EditText) rootView.findViewById(R.id.etServerPort);
		etPhoneNumber = (EditText) rootView.findViewById(R.id.etPhoneNumber);
		cbLockOptions = (CheckBox) rootView.findViewById(R.id.cb_LockOptions);
		pbBusy = (ProgressBar) rootView.findViewById(R.id.pbClearDB);
		cbLockOptions.setChecked(Option.Instance().isLockOptions());
	}
	
	private void fillUp() {
		etServerIP.setText(Option.Instance().getServerIP());
		etServerPort.setText(String.valueOf(Option.Instance().getServerPort()));
		etPhoneNumber.setText(Option.Instance().getPhoneNumber());
		initControlsState();
	}
	
	private void initControlsState() {
		etServerIP.setEnabled(!cbLockOptions.isChecked());
		etServerPort.setEnabled(!cbLockOptions.isChecked());
		etPhoneNumber.setEnabled(!cbLockOptions.isChecked());
	}
	
	public void busy(final int mode, final String... data){
		pbBusy.setVisibility(View.VISIBLE);
		//syncButton.setEnabled(false);
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
				//syncButton.setEnabled(true);
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
	
	public void chooseFile(){
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
	
}
