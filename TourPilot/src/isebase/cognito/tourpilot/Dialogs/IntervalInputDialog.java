package isebase.cognito.tourpilot.Dialogs;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.SelectionPeriod.SelectionPeriod;

import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class IntervalInputDialog extends BaseDialog  {

	private SelectionPeriod selectedPeriod = new SelectionPeriod();
	private TimePicker tpStartTime;
	private TimePicker tpStopTime;
	
	private int minHour;
    private int minMinute;
    private int maxHour;
    private int maxMinute;
    
    PatientsDialog patientsDialog;
    
    String title;
    
	public IntervalInputDialog(String title) {
		this.title = title;
	}

	public void setSelectedPeriod(SelectionPeriod selectedPeriod) {
		this.selectedPeriod = selectedPeriod;
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		setMinMaxTime();
	    View customView = getActivity().getLayoutInflater().inflate(R.layout.custom_time_picker, null);

	    tpStartTime = (TimePicker) customView.findViewById(R.id.tpStartTime);
	    tpStartTime.setIs24HourView(true);
	    tpStartTime.setCurrentHour(selectedPeriod.startTime().getHours());
	    tpStartTime.setCurrentMinute(selectedPeriod.startTime().getMinutes());
	    
	    tpStartTime.setOnTimeChangedListener(new OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				if (hourOfDay < minHour || hourOfDay > maxHour || hourOfDay > tpStopTime.getCurrentHour())
					tpStartTime.setCurrentHour(minHour);
				if (hourOfDay == minHour && minute < minMinute || hourOfDay == tpStopTime.getCurrentHour() && minute > tpStopTime.getCurrentMinute())
					tpStartTime.setCurrentMinute(minMinute);
			}
		});
	    
	    tpStopTime = (TimePicker) customView.findViewById(R.id.tpStopTime);
	    tpStopTime.setIs24HourView(true);
	    tpStopTime.setCurrentHour(selectedPeriod.stopTime().getHours());
	    tpStopTime.setCurrentMinute(selectedPeriod.stopTime().getMinutes());
	    tpStopTime.setOnTimeChangedListener(new OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				if (hourOfDay < minHour || hourOfDay > maxHour || hourOfDay < tpStartTime.getCurrentHour())
					tpStopTime.setCurrentHour(maxHour);
				if (hourOfDay == maxHour && minute > maxMinute || hourOfDay == tpStartTime.getCurrentHour() && minute < tpStartTime.getCurrentMinute())
					tpStopTime.setCurrentMinute(maxMinute);
			}
		});
	    
		AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
				.setView(customView)
				.setTitle(title)
				.setPositiveButton(isebase.cognito.tourpilot.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								listener.onDialogPositiveClick(IntervalInputDialog.this);
							}
						});
		return adb.create();
	}
	
	private void setMinMaxTime() {
		minMinute = selectedPeriod.getStartTime().getMinutes();
		minHour = selectedPeriod.getStartTime().getHours();
		
		maxMinute = selectedPeriod.getStopTime().getMinutes();
		maxHour = selectedPeriod.getStopTime().getHours();
	}
	
    public Date getStartDate() {
    	return getDateFromTicker(tpStartTime.getCurrentHour(), tpStartTime.getCurrentMinute());
    }
    
    public Date getStopDate() {
    	return getDateFromTicker(tpStopTime.getCurrentHour(), tpStopTime.getCurrentMinute());
    }
	
	private Date getDateFromTicker(int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
	}

}
