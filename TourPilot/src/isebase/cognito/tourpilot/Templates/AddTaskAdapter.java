package isebase.cognito.tourpilot.Templates;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTask;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

public class AddTaskAdapter extends ArrayAdapter<AdditionalTask>  {

	private List<AdditionalTask> addTasks;
	private int layoutResourceId;
	private Context context;
	
	public AddTaskAdapter(Context context, int layoutResourceId, List<AdditionalTask> tasks) {
		super(context, layoutResourceId, tasks);
		
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.addTasks = tasks;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		AdditionalTasktHolder additionalTaskHolder = new AdditionalTasktHolder();
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);
		
		additionalTaskHolder.additionalTask = addTasks.get(position);
		additionalTaskHolder.chbAdditionalTaskText = (CheckBox) row.findViewById(R.id.chbAddTask);
		additionalTaskHolder.chbAdditionalTaskText.setTag(additionalTaskHolder.additionalTask);
		additionalTaskHolder.chbAdditionalTaskText.setText(additionalTaskHolder.additionalTask.getName());
		
		additionalTaskHolder.chbAdditionalTaskText.setChecked(additionalTaskHolder.additionalTask.getIsChecked());
			
		return row;
	}
	public class AdditionalTasktHolder {
		AdditionalTask additionalTask;
		CheckBox chbAdditionalTaskText;
	}

}
