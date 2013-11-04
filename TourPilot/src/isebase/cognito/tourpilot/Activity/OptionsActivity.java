package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.R.string;
import isebase.cognito.tourpilot.Connection.ConnectionInfo;
import isebase.cognito.tourpilot.Data.Settings.Option;
import isebase.cognito.tourpilot.Data.Settings.OptionManager;
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
import android.widget.TextView;

public class OptionsActivity extends BaseActivity {

	public Dialog dialogNoConnection;
	public Dialog dialogNoIPEntered;

	private Option option;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		StaticResources.setBaseContext(getBaseContext());
		reloadData();
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

	public void startSync(View view) {
		if (((TextView) findViewById(R.id.etServer)).getText().toString()
				.equals("")) {
			showDialog(0);
			return;
		}
		if (ConnectionInfo.Instance().getNetWorkInfo() == null
				|| !ConnectionInfo.Instance().getNetWorkInfo().isConnected()) {
			showDialog(1);
			return;
		}
		// MyAsyncTask m = new MyAsyncTask();
		// m.execute();
		Intent workersActivity = new Intent(getApplicationContext(),
				WorkersActivity.class);
		saveOptions();
		startActivity(workersActivity);
	}

	private void initOptions() {
		if (option == null)
			return;
		((TextView) findViewById(R.id.etServer)).setText(option.getServerIP());
		((TextView) findViewById(R.id.etPort)).setText(String.valueOf(option
				.getServerPort()));
		TelephonyManager tMgr = (TelephonyManager) getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		String phoneNumber = (String) (tMgr.getLine1Number().equals("") ? string.unknown_number
				: tMgr.getLine1Number());
		((TextView) findViewById(R.id.etPhoneNumber)).setText(phoneNumber);
	}

	private void saveOptions() {
		if (option == null)
			option = new Option();
		String serverIP = ((TextView) findViewById(R.id.etServer)).getText()
				.toString();
		option.setServerIP(serverIP);
		int serverPort = Integer
				.parseInt(((TextView) findViewById(R.id.etPort)).getText()
						.toString());
		option.setServerPort(serverPort);
		if (option.getId() == option.emptyID)
			OptionManager.Instance().add(option);
		else
			OptionManager.Instance().save(option);
	}

	private void reloadData() {
		option = OptionManager.Instance().loadOptions();
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
