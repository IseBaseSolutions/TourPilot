package isebase.cognito.tourpilot.Templates;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTask;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

public class AdditionalTaskAdapter extends ArrayAdapter<AdditionalTask>  {

	private List<AdditionalTask> allAdditinalTasks;
	private List<AdditionalTask> selectedAddTasks;
	private int layoutResourceId;
	private Context context;
	
	public boolean isVisible = false;
	
	public AdditionalTaskAdapter(Context context, int layoutResourceId, List<AdditionalTask> tasks) {
		super(context, layoutResourceId, tasks);
		
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.allAdditinalTasks = tasks;
		this.selectedAddTasks = new ArrayList<AdditionalTask>();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		AdditionalTasktHolder additionalTaskHolder = new AdditionalTasktHolder();
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);
		
		additionalTaskHolder.additionalTask = allAdditinalTasks.get(position);
		additionalTaskHolder.chbAdditionalTask = (CheckBox) row.findViewById(R.id.chbAddTask);
		additionalTaskHolder.chbAdditionalTask.setTag(additionalTaskHolder.additionalTask);
		additionalTaskHolder.chbAdditionalTask.setText(additionalTaskHolder.additionalTask.getName());		
//		additionalTaskHolder.chbAdditionalTask.setChecked(selectedAddTasks.contains(additionalTaskHolder.additionalTask));		
		return row;
	}
	
	public class AdditionalTasktHolder {
		AdditionalTask additionalTask;
		CheckBox chbAdditionalTask;
	}
	
	public void toSelectedAddTasks(AdditionalTask addTask) {
		if (!selectedAddTasks.contains(addTask))
			selectedAddTasks.add(addTask);
		else
			selectedAddTasks.remove(addTask);
	}

}
