package isebase.cognito.tourpilot.Activity.AdditionalTasks;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.BaseActivity;
import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTask;
import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTaskManager;
import isebase.cognito.tourpilot.Templates.AddTaskAdapter;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

public class AdditionalTasksActivity extends BaseActivity {

	List<AdditionalTask> listAddTasks;
	AddTaskAdapter adapter;
	ListView lvAddTasks;
	AutoCompleteTextView etFilter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_tasks);
		
		initComponents();
	//	reloadData();
		
		
	//	initAddTasksTotalList();
	}
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.add_tasks, menu);
		return true;
	}

	private void InitTable(int tableSize) {
		if (tableSize > 0)
			return;
		for(int i = 0;i < 20;i++){
			AdditionalTask additionaTask = new AdditionalTask();
			additionaTask.setName("TASK #" + i);
			listAddTasks.add(additionaTask);
		}
//		AddTasksManager.Instance().add(new AddTasks("Add Task #" + i));
		

	//	lvAddTasks.setTextFilterEnabled(true);
		
		// reloadData();
	}


//	private void FilterAdditionalTasks(String sFilter) {
////		listAddTasksFilter.clear();
//		for (AdditionalTask tempAddTask : listAddTasks){
//			if (tempAddTask.getName().contains(sFilter)){
//				//	listAddTasksFilter.add(tempAddTask);
//			}
//		}
//		AddTaskAdapter adapter = new AddTaskAdapter(this,
//				R.layout.row_add_task_template, listAddTasksShow);
//		lvAddTasks.setAdapter(adapter);
//	}

	private void reloadData() {
		listAddTasks = AdditionalTaskManager.Instance().load();
	}

	public void onSaveAddTasks(View view) {
//		listAddTasksSelected = new ArrayList<AdditionalTask>();
		// for (AdditionalTask tempTask : listAddTasksShow) {
		// if (tempTask.getIsChecked())
		// listAddTasksSelected.add(tempTask);
		// }
		// finish();
	}

	public void onSelectAddTask(View view) {
		AdditionalTask addTask = (AdditionalTask) view.getTag();
		((AddTaskAdapter)lvAddTasks.getAdapter()).toSelectedAddTasks(addTask);
		// addTask.setIsChecked(((CheckBox) view).isChecked());
	}

	private void initComponents() {
		listAddTasks = new ArrayList<AdditionalTask>();
		
		InitTable(listAddTasks.size());
		
		adapter = new AddTaskAdapter(this,R.layout.row_add_task_template, listAddTasks);
		
		lvAddTasks = (ListView) findViewById(R.id.lvAddTasks);
		
		lvAddTasks.setAdapter(adapter);
		
		EditText etFilter = (EditText) findViewById(R.id.etAddTasksFilter);
		etFilter.addTextChangedListener(new TextWatcher() {

		    @Override
		    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
		    		AdditionalTasksActivity.this.adapter.getFilter().filter(cs);		    	
		    }
		    @Override
		    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
		            int arg3) { }
		    @Override
		    public void afterTextChanged(Editable arg0) {}
		});
	}
}
