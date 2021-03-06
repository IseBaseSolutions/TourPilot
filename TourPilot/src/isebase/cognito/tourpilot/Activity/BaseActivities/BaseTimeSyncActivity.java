package isebase.cognito.tourpilot.Activity.BaseActivities;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.AdditionalWorksActivity;
import isebase.cognito.tourpilot.Activity.ManualInputActivity;
import isebase.cognito.tourpilot.Activity.PatientsActivity;
import isebase.cognito.tourpilot.Activity.SendLogActivity;
import isebase.cognito.tourpilot.Activity.SynchronizationActivity;
import isebase.cognito.tourpilot.Activity.ToursActivity;
import isebase.cognito.tourpilot.Activity.UserRemarksActivity;
import isebase.cognito.tourpilot.Activity.VerificationActivity;
import isebase.cognito.tourpilot.Activity.TasksAssessmentsActivity.TasksAssessementsActivity;
import isebase.cognito.tourpilot.Activity.WorkersOptionActivity.WorkerOptionActivity;
import isebase.cognito.tourpilot.Connection.ConnectionAsyncTask;
import isebase.cognito.tourpilot.Connection.ConnectionStatus;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Dialogs.BaseDialog;
import isebase.cognito.tourpilot.EventHandle.SynchronizationHandler;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class BaseTimeSyncActivity extends FragmentActivity {

	private ConnectionStatus connectionStatus;
	private ConnectionAsyncTask connectionTask;
	protected BaseDialog closeDialog;
	private boolean timeSync;

	public void setTimeSync(boolean timeSync) {
		this.timeSync = timeSync;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StaticResources.setBaseContext(getBaseContext());
		if (!isMainActivity())
			closeDialog = new BaseDialog(getString(R.string.dialog_close),
					getString(R.string.dialog_closing));
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread thread, Throwable e) {
				handleUncaughtException(thread, e);
			}

		});
	}

	protected boolean isMainActivity() {
		return false;
	}

	protected void criticalClose() {
		Option.Instance().clearSelected();
		Option.Instance().save();
		Intent optionActivity = new Intent(getApplicationContext(),
				WorkerOptionActivity.class);
		startActivity(optionActivity);
	}

	@Override
	protected void onResume() {
		if (timeSync) {
			Option.Instance().setTimeSynchronised(false);
			SynchronizationHandler syncHandler = new SynchronizationHandler() {

				@Override
				public void onSynchronizedFinished(boolean isOK, String text) {
					if (!text.equals("")) {

					}
				}

				@Override
				public void onItemSynchronized(String text) {
					if (connectionStatus.CurrentState == 1)
						connectionStatus.CurrentState = 9;
					else if (connectionStatus.CurrentState == 9)
						connectionStatus.CurrentState = 6;
					else
						connectionStatus.nextState();
					connectionTask = new ConnectionAsyncTask(connectionStatus);
					connectionTask.execute();
				}

				@Override
				public void onProgressUpdate(String text, int progress) {
				}

				@Override
				public void onProgressUpdate(String text) {
				}
			};

			connectionStatus = new ConnectionStatus(syncHandler);
			connectionTask = new ConnectionAsyncTask(connectionStatus);
			connectionTask.execute();
		}
		super.onResume();
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
			closeDialog.show(getSupportFragmentManager(), "closeDialog");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void startWorkersActivity() {
		Intent workersActivity = new Intent(getApplicationContext(),
				WorkerOptionActivity.class);
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
		Intent toursActivity = new Intent(getApplicationContext(),
				ToursActivity.class);
		startActivity(toursActivity);
	}

	protected void startTasksActivity() {
		Intent tasksActivity = new Intent(getApplicationContext(),
				TasksAssessementsActivity.class);
		startActivity(tasksActivity);
	}

	protected void startAdditionalWorksActivity() {
		Intent additionalWorksActivity = new Intent(getApplicationContext(),
				AdditionalWorksActivity.class);
		startActivity(additionalWorksActivity);
	}

	protected void startOptionsActivity() {
		Intent optionsActivity = new Intent(getApplicationContext(),
				WorkerOptionActivity.class);
		startActivity(optionsActivity);
	}

	protected void startManualInputActivity() {
		Intent manualInputActivity = new Intent(getApplicationContext(),
				ManualInputActivity.class);
		startActivity(manualInputActivity);
	}

	protected void startUserRemarksActivity() {
		Intent userRemarksActivity = new Intent(getApplicationContext(),
				UserRemarksActivity.class);
		startActivity(userRemarksActivity);
	}

	protected void startVerificationActivity(Integer requestCode,
			boolean isAllOK) {
		Intent VerificationActivity = new Intent(getApplicationContext(),
				VerificationActivity.class);
		VerificationActivity.putExtra("isAllOK", isAllOK);
		startActivityForResult(VerificationActivity, requestCode);
	}

	public void handleUncaughtException(Thread thread, Throwable e) {
		e.printStackTrace();
		Intent intent = new Intent(getBaseContext(), SendLogActivity.class);
		intent.setAction("com.mydomain.SEND_LOG");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		System.exit(1);
	}

}
