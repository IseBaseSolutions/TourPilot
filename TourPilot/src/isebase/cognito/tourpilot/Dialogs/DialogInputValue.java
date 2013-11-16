package isebase.cognito.tourpilot.Dialogs;

import isebase.cognito.tourpilot.StaticResources.StaticResources;
import isebase.cognito.tourpilot.Templates.TaskFormatDataResultInput;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.LinearLayout;

public class DialogInputValue extends DialogFragment {

	public interface DialoglInputValueListener {

		public void onDialogOkInputValuesClick(DialogFragment dialog);

		public void onDialogCanceInputValuelClick(DialogFragment dialog);
	}

	DialoglInputValueListener inputValueListener;
	AlertDialog.Builder adb;

	// public TextView tvValueName;
	// public EditText tvValueDatum;
	public LinearLayout linearHor;
	public LinearLayout linearVer;
	public List<TaskFormatDataResultInput> listInputValues;

	public Dialog onCreateDialog(Bundle savedInstanceState) {

		adb = new AlertDialog.Builder(getActivity());

		InitDialog();
		ShowDialog();
		return adb.create();
	}

	public DialogInputValue(List<TaskFormatDataResultInput> listInputValues) {
		this.listInputValues = listInputValues;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			inputValueListener = (DialoglInputValueListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

	private void InitDialog() {
		linearVer = new LinearLayout(StaticResources.getBaseContext());
		linearVer.setOrientation(LinearLayout.VERTICAL);

		for (TaskFormatDataResultInput temp : listInputValues) {
			
			linearVer.addView(temp.getRowInputData());
		}
		adb.setView(linearVer);
	}

	private void ShowDialog() {
		adb.setIcon(isebase.cognito.tourpilot.R.drawable.ic_action_screen_locked_to_landscape);
		adb.setTitle(isebase.cognito.tourpilot.R.string.some_text);
		// adb.setMessage("MISSION BEGIN");

		adb.setPositiveButton(isebase.cognito.tourpilot.R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						inputValueListener
								.onDialogOkInputValuesClick(DialogInputValue.this);
					}
				});
		adb.setNegativeButton(isebase.cognito.tourpilot.R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						inputValueListener
								.onDialogCanceInputValuelClick(DialogInputValue.this);
					}
				});
	}
}
