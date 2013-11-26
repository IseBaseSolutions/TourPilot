package isebase.cognito.tourpilot.Dialogs;

import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PinDialog extends BaseDialog {

	private EditText etPin;
	private Worker worker;

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public String getPin() {
		return etPin.getText().toString();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
		etPin = new EditText(StaticResources.getBaseContext());
		etPin.setTextColor(Color.BLACK);
		etPin.setHint(isebase.cognito.tourpilot.R.string.enter_pin);
		etPin.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_VARIATION_PASSWORD);
		adb.setView(etPin);
		adb.setIcon(isebase.cognito.tourpilot.R.drawable.ic_action_screen_locked_to_landscape);
		adb.setTitle(isebase.cognito.tourpilot.R.string.pin_code);
		adb.setPositiveButton(isebase.cognito.tourpilot.R.string.ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						listener.onDialogPositiveClick(PinDialog.this);
					}
				});
		adb.setNegativeButton(isebase.cognito.tourpilot.R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						listener.onDialogNegativeClick(PinDialog.this);
					}
				});
		return adb.create();
	}

	@Override
	public void onStart() {
		super.onStart();
		AlertDialog dialog = (AlertDialog) getDialog();
		if (dialog != null) {
			Button positiveButton = (Button) dialog
					.getButton(Dialog.BUTTON_POSITIVE);
			positiveButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (worker.checkPIN(getPin())) {
						dismiss();
						listener.onDialogPositiveClick(PinDialog.this);
					} else {
						etPin.setText("");
						etPin.setHintTextColor(Color.RED);
					}
				}
			});
		}
	}
}