package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.R.string;
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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class OptionsActivity extends BaseActivity {

	public Dialog dialogNoConnection;
	public Dialog dialogNoIPEntered;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		StaticResources.setBaseContext(getBaseContext());
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
		((TextView) findViewById(R.id.etPhoneNumber)).setText(phoneNumber);
		((TextView) findViewById(R.id.etServer)).setText(Option.Instance().getServerIP());
		((TextView) findViewById(R.id.etPort)).setText(String.valueOf(Option.Instance()
				.getServerPort()));
	}

	private void saveOptions() {
		String serverIP = ((TextView) findViewById(R.id.etServer)).getText()
				.toString();
		Option.Instance().setServerIP(serverIP);
		int serverPort = Integer
				.parseInt(((TextView) findViewById(R.id.etPort)).getText()
						.toString());
		Option.Instance().setServerPort(serverPort);
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
