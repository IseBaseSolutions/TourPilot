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
import android.widget.EditText;
import android.widget.ListView;

public class AdditionalTasksActivity extends BaseActivity {

	List<AdditionalTask> listAddTasks;
	AddTaskAdapter adapter;
			

//	List<AdditionalTask> listAddTasksFilter;
//	List<AdditionalTask> listAddTasksShow;
//	List<AdditionalTask> listAddTasksSelected;

	ListView lvAddTasks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_tasks);
		initComponents();
		reloadData();
		InitTable(listAddTasks.size());
		initAddTasksTotalList();
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
		listAddTasks.clear();
		for (int i = 0; i < 20; i++) {
			AdditionalTask additionaTask = new AdditionalTask();
			additionaTask.setName("Add task #" + i);
			listAddTasks.add(additionaTask);
			// AddTasksManager.Instance().add(new AddTasks("Add Task #" + i));
		}
//		listAddTasksShow = listAddTasksTotal;
		// reloadData();
	}

	private void initAddTasksTotalList() {
//		listAddTasksShow = listAddTasksTotal;
		lvAddTasks.setAdapter(adapter);
	}

	private void FilterAdditionalTasks(String sFilter) {
//		listAddTasksFilter.clear();
		for (AdditionalTask tempAddTask : listAddTasks){
			if (tempAddTask.getName().contains(sFilter)){
				//	listAddTasksFilter.add(tempAddTask);
			}
		}
//		AddTaskAdapter adapter = new AddTaskAdapter(this,
//				R.layout.row_add_task_template, listAddTasksShow);
//		lvAddTasks.setAdapter(adapter);
	}

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
//		listAddTasksFilter = new ArrayList<AdditionalTask>();
		adapter = new AddTaskAdapter(this,R.layout.row_add_task_template, listAddTasks);
				
		lvAddTasks = (ListView) findViewById(R.id.lvAddTasks);
		EditText etFilter = (EditText) findViewById(R.id.etAddTasksFilter);
		etFilter.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable text) {
				// TODO Auto-generated method stub
				if (text.length() == 0)
					initAddTasksTotalList();
				else
					FilterAdditionalTasks(text.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
			}
		});
	}
}
