package isebase.cognito.tourpilot.Templates;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.AddTasks.AddTasks;
import isebase.cognito.tourpilot.Templates.TaskAdapter.TasktHolder;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

public class AddTaskAdapter extends ArrayAdapter<AddTasks>  {

	private List<AddTasks> addTasks;
	private int layoutResourceId;
	private Context context;
	
	public AddTaskAdapter(Context context, int layoutResourceId, List<AddTasks> tasks) {
		super(context, layoutResourceId, tasks);
		
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.addTasks = tasks;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		AddTasktHolder addTaskHolder = new AddTasktHolder();
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);
		
		addTaskHolder.addTask = addTasks.get(position);
		addTaskHolder.chbAddTaskText = (CheckBox) row.findViewById(R.id.chbAddTask);
		addTaskHolder.chbAddTaskText.setTag(addTaskHolder.addTask);
		addTaskHolder.chbAddTaskText.setText(addTaskHolder.addTask.getName());
		
		addTaskHolder.chbAddTaskText.setChecked(addTaskHolder.addTask.getCheck());
			
		return row;
	}
	public class AddTasktHolder {
		AddTasks addTask;
		CheckBox chbAddTaskText;
	}

}
