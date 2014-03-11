package isebase.cognito.tourpilot.Dialogs;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class PinDialog extends BaseDialog {

	private EditText etPin;
	private CheckBox chbSavePin;
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
//		etPin = new EditText(StaticResources.getBaseContext());
//		etPin.setHint(isebase.cognito.tourpilot.R.string.enter_pin);
//		etPin.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
//		etPin.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });		
	    LayoutInflater inflater = getActivity().getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    View view = inflater.inflate(R.layout.dialog_pin, null);
	    adb.setView(view);
		adb.setIcon(android.R.drawable.ic_partial_secure);
		adb.setTitle(isebase.cognito.tourpilot.R.string.pin_code);
		etPin = (EditText) view.findViewById(R.id.etPin);
		etPin.setTextColor(etPin.getHintTextColors().getDefaultColor());
		chbSavePin = (CheckBox) view.findViewById(R.id.chbSavePin);
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
			Button positiveButton = (Button) dialog.getButton(Dialog.BUTTON_POSITIVE);
			positiveButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (worker.checkPIN(getPin())) {
						dismiss();
						if (chbSavePin.isChecked())
						{
							Option.Instance().setPin(getPin());
							Option.Instance().save();
						}
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
