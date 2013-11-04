package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Connection.ConnectionInfo;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class OptionsActivity extends BaseActivity {

	public Dialog dialogNoConnection;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		StaticResources.setBaseContext(getBaseContext());
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
			return getDialogNoConnection();
		default:
			return null;
		}
	}

	public void startSync(View view) {
		if (ConnectionInfo.Instance().getNetWorkInfo() == null || !ConnectionInfo.Instance().getNetWorkInfo().isConnected())
		{
			showDialog(0);
			return;
		}
		Intent workersActivity = new Intent(getApplicationContext(),
				WorkersActivity.class);

		startActivity(workersActivity);
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
