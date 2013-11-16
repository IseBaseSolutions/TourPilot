package isebase.cognito.tourpilot.Dialogs;

import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TimePicker;

public class DialogManualInput extends DialogFragment {
	
	public TextView tvTextMissionBegin;
	public TextView tvBeginTime;
	public TextView tvSetInterval;
	public Button btShowTimePicker;
	public LinearLayout linearMain;
	public LinearLayout linearSetTimeBegin;
	public EditText etInterval;

	public DialogManualInputListener manualListener;
	
	static final int DATE_DIALOG_ID = 0;

    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
    		new TimePickerDialog.OnTimeSetListener()
    		{
    			public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour)
    			{
    				tvBeginTime.setText(hourOfDay + ":" + minuteOfHour);
    			}
    		};
	
	
	private int textSize = 30;

	public interface DialogManualInputListener {
		
		public void onDialogBackClick(DialogFragment dialog);
		public void onDialogServisesClick(DialogFragment dialog);
	}
	
	
	
	public Dialog onCreateDialog(Bundle savedInstanceState){
		
		AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
		InitDialog(adb);
		return adb.create();
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			manualListener = (DialogManualInputListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}
	private void InitDialog(AlertDialog.Builder adb){
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, 1.0f);
		params.leftMargin = params.rightMargin =  10;

		linearMain = new LinearLayout(StaticResources.getBaseContext());
		linearMain.setOrientation(LinearLayout.VERTICAL);
		
		linearSetTimeBegin = new LinearLayout(StaticResources.getBaseContext());
		linearSetTimeBegin.setOrientation(LinearLayout.HORIZONTAL);

		tvTextMissionBegin = new TextView(StaticResources.getBaseContext()); 
		tvTextMissionBegin.setTextColor(Color.BLACK);
		tvTextMissionBegin.setText("MISSION BEGIN");
		tvTextMissionBegin.setTextSize(textSize);
		tvTextMissionBegin.setGravity(Gravity.LEFT);
		tvTextMissionBegin.setLayoutParams(params);
		linearMain.addView(tvTextMissionBegin);
		
		tvBeginTime = new TextView(StaticResources.getBaseContext());
		tvBeginTime.setTextColor(Color.BLACK);
		tvBeginTime.setText("00:00");
		tvBeginTime.setTextSize(textSize);
		tvBeginTime.setGravity(Gravity.CENTER);
		tvBeginTime.setLayoutParams(params);
		linearSetTimeBegin.addView(tvBeginTime);

		btShowTimePicker = new Button(StaticResources.getBaseContext());
		btShowTimePicker.setTextColor(Color.BLACK);
		btShowTimePicker.setText("CHANGE TIME");
		btShowTimePicker.setTextSize(textSize);
		btShowTimePicker.setGravity(Gravity.CENTER);
		btShowTimePicker.setLayoutParams(params);
		linearSetTimeBegin.addView(btShowTimePicker);
		linearMain.addView(linearSetTimeBegin);
		
		tvSetInterval = new TextView(StaticResources.getBaseContext());
		tvSetInterval.setTextColor(Color.BLACK);
		tvSetInterval.setText("USING INTERVAL (minute)");
		tvSetInterval.setTextSize(textSize-10);
		tvSetInterval.setGravity(Gravity.LEFT);
		tvSetInterval.setLayoutParams(params);
		linearMain.addView(tvSetInterval);
		
		etInterval = new EditText(StaticResources.getBaseContext());
		etInterval.setTextColor(Color.BLACK);
		etInterval.setHint(isebase.cognito.tourpilot.R.string.enter_interval);
		etInterval.setInputType(InputType.TYPE_CLASS_NUMBER);
		linearMain.addView(etInterval);
		
		adb.setView(linearMain);
		
		adb.setIcon(isebase.cognito.tourpilot.R.drawable.ic_action_screen_locked_to_landscape);
		adb.setTitle(isebase.cognito.tourpilot.R.string.some_text);
	//	adb.setMessage("MISSION BEGIN");
		
		adb.setPositiveButton(isebase.cognito.tourpilot.R.string.BUTTON_SERVISES,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						manualListener.onDialogBackClick(DialogManualInput.this);
					}
				});
		adb.setNegativeButton(isebase.cognito.tourpilot.R.string.button_back,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						manualListener.onDialogServisesClick(DialogManualInput.this);
					}
				});

		btShowTimePicker.setOnClickListener(new View.OnClickListener(){
			 public void onClick(View v) {
				 new TimePickerDialog(getActivity(), textSize, mTimeSetListener, textSize, textSize, true ).show();
			 }
		});

	}
}
