package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.DataBase.DataBaseWrapper;
import isebase.cognito.tourpilot.Dialogs.InfoBaseDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends FragmentActivity{

	protected DialogFragment versionFragmentDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		if(!isMainActivity())
			versionFragmentDialog = new InfoBaseDialog(
					getString(R.string.menu_program_info), 
					String.format("%s %s\n%s %s"
							, getString(R.string.program_version)
							, Option.Instance().getVersion()
							, getString(R.string.data_base_version)
							, DataBaseWrapper.DATABASE_VERSION)
					);
	}

	protected boolean isMainActivity(){
		return false;
	}
	
	protected void criticalClose(){
		Option.Instance().clearSelected();
		Option.Instance().save();
		Intent optionActivity =  new Intent(getApplicationContext(), OptionsActivity.class);
		startActivity(optionActivity);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.version_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_show_program_info:
				versionFragmentDialog.show(getSupportFragmentManager(),
						"versionDialog");
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
	}

	protected void startWorkersActivity() {
		Intent workersActivity = new Intent(getApplicationContext(),
				WorkersActivity.class);
		startActivity(workersActivity);
	}

	protected void startPatientsActivity() {
		Intent patientsActivity = new Intent(getApplicationContext(),
				PatientsActivity.class);
		startActivity(patientsActivity);
	}

	protected void startSyncActivity() {
		Intent synchActivity = new Intent(getApplicationContext(),
				SynchronizationActivity.class);
		startActivity(synchActivity);
	}
	
	protected void startToursActivity() {
		Intent toursActivity = new Intent(getApplicationContext(), ToursActivity.class);
		startActivity(toursActivity);
	}
	
	protected void startTasksActivity() {
		Intent tasksActivity = new Intent(getApplicationContext(), TasksActivity.class);
		startActivity(tasksActivity);
	}
	
	protected void startAdditionalWorksActivity() {
		Intent additionalWorksActivity = new Intent(getApplicationContext(), AdditionalWorksActivity.class);
		startActivity(additionalWorksActivity);
	}

	protected void startOptionsActivity() {
		Intent optionsActivity = new Intent(getApplicationContext(), OptionsActivity.class);
		startActivity(optionsActivity);
	}
	
	protected void startManualInputActivity() {
		Intent manualInputActivity = new Intent(getApplicationContext(), ManualInputActivity.class);
		startActivity(manualInputActivity);
	}
	

	protected void startUserRemarksActivity() {
		Intent userRemarksActivity = new Intent(getApplicationContext(), UserRemarksActivity.class);
		startActivity(userRemarksActivity);
	}

	protected void startVerificationActivity(Integer requestCode,boolean isFlegeOK) {
		Intent VerificationActivity = new Intent(getApplicationContext(), VerificationActivity.class);
		VerificationActivity.putExtra("isFlegeOK", isFlegeOK);
		startActivityForResult(VerificationActivity, requestCode);
	}
	
}
