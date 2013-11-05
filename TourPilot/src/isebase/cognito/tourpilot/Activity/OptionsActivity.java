package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Connection.ConnectionAsyncTask;
import isebase.cognito.tourpilot.Connection.ConnectionInfo;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Option.OptionManager;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class OptionsActivity extends BaseActivity {

	private Dialog dialogNoConnection;
	private Dialog dialogNoIPEntered;

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
		default:
			return null;
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
		if (ConnectionInfo.Instance().getNetWorkInfo() == null
				|| !ConnectionInfo.Instance().getNetWorkInfo().isConnected()) {
			showDialog(1);
			return;
		}
		ConnectionAsyncTask m = new ConnectionAsyncTask();
		m.execute();
		saveOptions();
		Intent workersActivity = new Intent(getApplicationContext(),
				WorkersActivity.class);
		startActivity(workersActivity);
	}

	private void initOptions() {
		TelephonyManager tMgr = (TelephonyManager) getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		String phoneNumber = (String) (tMgr.getLine1Number() == null ? getString(R.string.unknown_number)
				: tMgr.getLine1Number());
		etPhoneNumber.setText(phoneNumber);
		etServerIP.setText(Option.Instance().getServerIP());
		etServerPort.setText(String.valueOf(Option.Instance().getServerPort()));
	}

	private void saveOptions() {
		Option.Instance().setServerIP(etServerIP.getText().toString());
		Option.Instance().setServerPort(
				Integer.parseInt(etServerPort.getText().toString()));
		if (Option.Instance().getId() == Option.Instance().emptyID)
			OptionManager.Instance().add(Option.Instance());
		else
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

}
