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
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;

public class BlutdruckTaskDialog extends StandardTaskDialog{
	
	private boolean isViewMode;
		
	@Override
	public String getValue(){
		return etMinValue.getText().toString() + "$" + etMaxValue.getText().toString();
	}
	
	private EditText etMinValue;
	private EditText etMaxValue;
	private BaseDialogListener listener;
		
	public BlutdruckTaskDialog(Task task){
		this();
		this.task = task;
		this.isViewMode = false;
	}
	
	public BlutdruckTaskDialog(Task task, String value){
		this();
		this.task = task;
		this.isViewMode = true;
		String[] parsedData = value.split("\\$");
		String minValue = parsedData.length > 0 ? parsedData[0] : "";
		String maxValue = parsedData.length > 1 ? parsedData[1] : "";
		etMinValue.setText(minValue);
		etMaxValue.setText(maxValue);
	}
	
	private BlutdruckTaskDialog(){
		etMinValue = new EditText(StaticResources.getBaseContext());
		etMaxValue = new EditText(StaticResources.getBaseContext());
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
		adb.setTitle("blutdruck...");
				
		etMinValue.setTextColor(Color.BLACK);
		etMinValue.setHint("min...");
		etMinValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
		etMinValue.setEnabled(!isViewMode);
		
		etMaxValue.setTextColor(Color.BLACK);
		etMaxValue.setHint("max...");
		etMaxValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
		etMaxValue.setEnabled(!isViewMode);
		
		LinearLayout ll = new LinearLayout(StaticResources.getBaseContext());
		ll.addView(etMinValue);
		ll.addView(etMaxValue);
		
		adb.setView(ll);
		if(!isViewMode)
			adb.setPositiveButton(isebase.cognito.tourpilot.R.string.ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							listener.onDialogPositiveClick(BlutdruckTaskDialog.this);
						}
					});
		adb.setNegativeButton(isebase.cognito.tourpilot.R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						listener.onDialogNegativeClick(BlutdruckTaskDialog.this);
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
			throw new ClassCastException(activity.toString() + " must implement BaseDialogListener");
		}
	}
}
