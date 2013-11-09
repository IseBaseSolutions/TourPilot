package isebase.cognito.tourpilot.Activity.AddTasks;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.BaseActivity;
import isebase.cognito.tourpilot.Data.AddTasks.AddTasks;
import isebase.cognito.tourpilot.Data.AddTasks.AddTasksManager;
import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.Templates.AddTaskAdapter;
import isebase.cognito.tourpilot.Templates.TaskAdapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddTasksActivity extends BaseActivity  {

//	List<AddTasks> listAddTasks = new ArrayList<AddTasks>();
//	List<String> listAddTasksTotal = new ArrayList<String>();
//	List<String> listAddTasksFilter = new ArrayList<String>();
//	List<String> listAddTasksShow = new ArrayList<String>();
	
	

	List<AddTasks> listAddTasksTotal;
	List<AddTasks> listAddTasksFilter;
	List<AddTasks> listAddTasksShow;
	
	List<AddTasks> listAddTasksSelected;
	
	ListView lvAddTasks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_tasks);
		
		
		listAddTasksFilter = new ArrayList<AddTasks>();
		lvAddTasks = (ListView) findViewById(R.id.lvAddTasks);
		
		
		reloadData();
		InitTable(listAddTasksTotal.size());
		initAddTasksTotalList();
		
		EditText etFilter = (EditText)findViewById(R.id.etAddTasksFilter);
		etFilter.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable text) {
				// TODO Auto-generated method stub
				if(text.length() == 0){
					initAddTasksTotalList();
				}else{
					InitFilter(text.toString());
				}
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.add_tasks, menu);
		return true;
	}
	
	private void InitTable(int tableSize) {
		if (tableSize > 0)
			return;
		listAddTasksTotal.clear();
		for (int i = 0; i < 20; i++){
			listAddTasksTotal.add(new AddTasks("Add task #" + i));
//			AddTasksManager.Instance().add(new AddTasks("Add Task #" + i));
		}
		listAddTasksShow = listAddTasksTotal;
	//	reloadData();
	}
	private void initAddTasksTotalList(){
		listAddTasksShow = listAddTasksTotal;
		AddTaskAdapter adapter = new AddTaskAdapter(this,R.layout.row_add_task_template,listAddTasksShow);
		
		lvAddTasks.setAdapter(adapter);
	}
	private void InitFilter(String sFilter){
		listAddTasksFilter.clear();
		for(AddTasks tempAddTask : listAddTasksTotal){
			if(tempAddTask.getName().contains(sFilter)){
				listAddTasksFilter.add(tempAddTask);
			}
		}
		listAddTasksShow = listAddTasksFilter;
		AddTaskAdapter adapter = new AddTaskAdapter(this,R.layout.row_add_task_template,listAddTasksShow);
		
		lvAddTasks.setAdapter(adapter);
	}
	
	private void reloadData() {
		listAddTasksTotal = AddTasksManager.Instance().load();
	}
	public void onSaveAddTasks(View view)
	{
		listAddTasksSelected = new ArrayList<AddTasks>();
		for(AddTasks tempTask : listAddTasksShow){
			if(tempTask.getCheck())
				listAddTasksSelected.add(tempTask);
		}
		String s = "";
		for(AddTasks tempTask : listAddTasksSelected){
			s += tempTask.getName() + " - " + Boolean.toString(tempTask.getCheck()) + "\n";
		}
		Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();
	//	finish();
	}
	public void onSelectAddTask(View view)
	{
		AddTasks addTask = (AddTasks) view.getTag();
		addTask.setCheck(((CheckBox)view).isChecked()); 
	}
}
