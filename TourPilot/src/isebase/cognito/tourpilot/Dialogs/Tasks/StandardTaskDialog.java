package isebase.cognito.tourpilot.Dialogs.Tasks;

import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.Dialogs.BaseDialogListener;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.widget.EditText;

public class StandardTaskDialog extends DialogFragment{
	
	private String title;
	private boolean isViewMode;
	
	protected Task task;
	public Task getTask(){
		return task;
	}
	
	public String getValue(){
		return etValue.getText().toString();
	}
	
	private EditText etValue;
	private BaseDialogListener listener;
	
	
	public StandardTaskDialog(Task task, String title){
		this();
		this.task = task;
		this.title = title;
		this.isViewMode = false;
	}
	
	public StandardTaskDialog(Task task, String title, String value){
		this(task, title);
		this.isViewMode = true;
		etValue.setText(value);
	}
	
	protected StandardTaskDialog(){
		etValue = new EditText(StaticResources.getBaseContext());
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
		adb.setTitle(title);
		etValue.setTextColor(Color.BLACK);
		etValue.setHint(title);
		etValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
		etValue.setEnabled(!isViewMode);
		adb.setView(etValue);
		if(!isViewMode)
			adb.setPositiveButton(isebase.cognito.tourpilot.R.string.ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							listener.onDialogPositiveClick(StandardTaskDialog.this);
						}
					});
			adb.setNegativeButton(isebase.cognito.tourpilot.R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						listener.onDialogNegativeClick(StandardTaskDialog.this);
					}
				});
		return adb.create();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (BaseDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement BaseDialogListener");
		}
	}
}
