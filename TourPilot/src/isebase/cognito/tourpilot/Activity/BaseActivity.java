package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.R.layout;
import isebase.cognito.tourpilot.R.menu;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

public class BaseActivity extends Activity {

	public Dialog pinDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.base, menu);
		return true;
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case 0:
			return getPinDialog();
		default:
			return null;
		}
	}
	
	private Dialog getPinDialog() {
		if (pinDialog != null)
			return pinDialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.AppBaseTheme));
		LayoutInflater inflater = getLayoutInflater();
		builder.setView(inflater.inflate(R.layout.dialog_pin, null));

		builder.setPositiveButton(getString(R.string.ok),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int buttonId) {
						String name = ((TextView) pinDialog.findViewById(R.id.tvWorkerName)).getText().toString();
						String pinStr = ((EditText) pinDialog.findViewById(R.id.evPin)).getText().toString();
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
		pinDialog = builder.create();
		return pinDialog;
	}

}
