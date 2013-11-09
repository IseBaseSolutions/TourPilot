package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Connection.ConnectionAsyncTask;
import isebase.cognito.tourpilot.Connection.ConnectionInfo;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Option.OptionManager;
import isebase.cognito.tourpilot.Data.Tour.Tour;
import isebase.cognito.tourpilot.Data.Tour.TourManager;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class OptionsActivity extends BaseActivity {

	private Dialog dialogNoConnection;
	private Dialog dialogNoIPEntered;
	private Dialog dialogVersion;

	private EditText etServerIP;
	private EditText etServerPort;
	private EditText etPhoneNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		StaticResources.setBaseContext(getBaseContext());
						
		initControls();
		initOptions();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case 0:
			return getDialogNoIPEntered();
		case 1:
			return getDialogNoConnection();
		case 2:
			return getDialogVersion();
		default:
			return null;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_clear_database:
			// clear database
			return true;
		case R.id.action_show_version:
			showDialog(2);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void initControls() {
		etServerIP = (EditText) findViewById(R.id.etServerIP);
		etServerPort = (EditText) findViewById(R.id.etServerPort);
		etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
	}

	public void startSync(View view) {
		if (etServerIP.getText().toString().equals("")) {
			showDialog(0);
			return;
		}
		if (ConnectionInfo.Instance().getNetworkInfo() == null
				|| !ConnectionInfo.Instance().getNetworkInfo().isConnected()) {
			showDialog(1);
			return;
		}
		saveOptions();
		Intent synchActivity= new Intent(getApplicationContext(), SynchronizationActivity.class);
		startActivity(synchActivity);
	}

	private void initOptions() {
		etPhoneNumber.setText(Option.Instance().getPhoneNumber());
		etServerIP.setText(Option.Instance().getServerIP());
		etServerPort.setText(String.valueOf(Option.Instance().getServerPort()));
	}

	private void saveOptions() {
		Option.Instance().setServerIP(etServerIP.getText().toString());
		Option.Instance().setServerPort(Integer.parseInt(etServerPort.getText().toString()));
		OptionManager.Instance().save(Option.Instance());
	}

	private Dialog getDialogNoIPEntered() {
		if (dialogNoIPEntered != null)
			return dialogNoIPEntered;
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.AppBaseTheme));

		builder.setPositiveButton(getString(R.string.ok),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int buttonId) {
						return;
					}

				});
		builder.setTitle(R.string.connection_problems);
		builder.setMessage(R.string.no_ip_entered);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		dialogNoIPEntered = builder.create();
		return dialogNoIPEntered;
	}

	private Dialog getDialogNoConnection() {
		if (dialogNoConnection != null)
			return dialogNoConnection;
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.AppBaseTheme));

		builder.setPositiveButton(getString(R.string.ok),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int buttonId) {
						return;
					}

				});
		builder.setTitle(R.string.connection_problems);
		builder.setMessage(R.string.no_connection);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		dialogNoConnection = builder.create();
		return dialogNoConnection;
	}

	private Dialog getDialogVersion() {
		if (dialogVersion != null)
			return dialogVersion;
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.AppBaseTheme));

		builder.setPositiveButton(getString(R.string.ok),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int buttonId) {
						return;
					}

				});
		builder.setTitle(R.string.version);
		try {
			builder.setMessage(Option.Instance().getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		builder.setIcon(android.R.drawable.ic_dialog_info);
		dialogVersion = builder.create();
		return dialogVersion;
	}
}
