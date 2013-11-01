package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Connection.ConnectionInfo;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class OptionsActivity extends BaseActivity {

	private Dialog dialogNoConnection;
	
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

	public void startSync(View view) {
		//if (!ConnectionInfo.Instance().getNetWorkInfo().isConnected())
			
		Intent workersActivity = new Intent(getApplicationContext(),
				WorkersActivity.class);
		//
		// //Sending data to another Activity
		// nextScreen.putExtra("name", inputName.getText().toString());
		// nextScreen.putExtra("email", inputEmail.getText().toString());
		//

		startActivity(workersActivity);
	}
	
	private Dialog getPinDialog() {
		if (dialogNoConnection != null)
			return dialogNoConnection;
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.AppBaseTheme));
		LayoutInflater inflater = getLayoutInflater();
		builder.setView(inflater.inflate(R.layout.dialog_pin, null));

		builder.setPositiveButton(getString(R.string.ok),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int buttonId) {
						String name = ((TextView) dialogNoConnection
								.findViewById(R.id.tvWorkerName)).getText()
								.toString();
						String pinStr = ((EditText) dialogNoConnection
								.findViewById(R.id.evPin)).getText().toString();
						if (!StaticResources.checkWorkerPIN(name, pinStr))
							return;
						Intent toursActivity = new Intent(
								getApplicationContext(), ToursActivity.class);
						startActivity(toursActivity);
					}
				});
		builder.setNegativeButton(getString(R.string.cancel),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int buttonId) {
						return;
					}
					
				});
		dialogNoConnection = builder.create();
		return dialogNoConnection;
	}
}
