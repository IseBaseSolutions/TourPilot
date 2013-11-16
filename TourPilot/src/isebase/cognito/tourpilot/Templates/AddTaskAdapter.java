package isebase.cognito.tourpilot.Templates;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTask;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;

public class AddTaskAdapter extends ArrayAdapter<AdditionalTask> {

	private List<AdditionalTask> listTasks;
	private List<AdditionalTask> listTasksOriginal;
	
	private View row;
	
	private Filter filter;
	
	private List<AdditionalTask> selectedAddTasks = new ArrayList<AdditionalTask>();
	private int layoutResourceId;
	private Context context;
	
	public AddTaskAdapter(Context context, int layoutResourceId, List<AdditionalTask> tasks) {
		super(context, layoutResourceId, tasks);
		
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.listTasks = tasks;
		this.listTasksOriginal = new ArrayList<AdditionalTask>(tasks);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		row = convertView;
		AdditionalTasktHolder additionalTaskHolder = new AdditionalTasktHolder();
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);
		
		additionalTaskHolder.additionalTask = listTasks.get(position);
		additionalTaskHolder.chbAdditionalTask = (CheckBox) row.findViewById(R.id.chbAddTask);
		additionalTaskHolder.chbAdditionalTask.setTag(additionalTaskHolder.additionalTask);
		additionalTaskHolder.chbAdditionalTask.setText(additionalTaskHolder.additionalTask.getName());		
		additionalTaskHolder.chbAdditionalTask.setChecked(selectedAddTasks.contains(additionalTaskHolder.additionalTask));		
//		row.setVisibility(selectedAddTasks.contains(additionalTaskHolder.additionalTask) ? View.VISIBLE : View.INVISIBLE);

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
	///Filter
    @Override
    public Filter getFilter() {
         if (filter == null)
            filter = new TaskFilter();
        return filter;
    }
    private class TaskFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
        	
            FilterResults results = new FilterResults();
            constraint = constraint.toString().toLowerCase();
            
            if (constraint != null && constraint.toString().length() > 0){
            	
            	ArrayList<AdditionalTask> founded = new ArrayList<AdditionalTask>();
            	for (AdditionalTask task : listTasksOriginal)
            	{
            //		String dataNames = allAddTasks.get(i).getName();
            		if (task.getName().toLowerCase().contains(constraint.toString()))
            		{
           // 			AdditionalTask task = new AdditionalTask();
            //			task.setName(dataNames);
            			founded.add(task);
            		}
            	}

            	results.count = founded.size();
            	results.values = founded;
            }else{
            	results.values = listTasksOriginal;
            	results.count = listTasksOriginal.size();
            }

            return results;
        	
        }

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,FilterResults results) {
			// TODO Auto-generated method stub
			clear();
			for (AdditionalTask task : (List<AdditionalTask>) results.values) {
				add(task);
			}
			notifyDataSetChanged();
		}
    }
	
}
