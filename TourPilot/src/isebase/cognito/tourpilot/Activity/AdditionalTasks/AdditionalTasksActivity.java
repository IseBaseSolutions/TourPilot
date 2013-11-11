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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AdditionalTasksActivity extends BaseActivity  {

	List<AdditionalTask> listAddTasksTotal;
	List<AdditionalTask> listAddTasksFilter;
	List<AdditionalTask> listAddTasksShow;	
	List<AdditionalTask> listAddTasksSelected;
	
	ListView lvAddTasks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_tasks);
		
		
		listAddTasksFilter = new ArrayList<AdditionalTask>();
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
			AdditionalTask additionaTask = new AdditionalTask();
			additionaTask.setName("Add task #" + i);
			listAddTasksTotal.add(additionaTask);
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
		for(AdditionalTask tempAddTask : listAddTasksTotal){
			if(tempAddTask.getName().contains(sFilter)){
				listAddTasksFilter.add(tempAddTask);
			}
		}
		listAddTasksShow = listAddTasksFilter;
		AddTaskAdapter adapter = new AddTaskAdapter(this,R.layout.row_add_task_template,listAddTasksShow);
		
		lvAddTasks.setAdapter(adapter);
	}
	
	private void reloadData() {
		listAddTasksTotal = AdditionalTaskManager.Instance().load();
	}
	public void onSaveAddTasks(View view)
	{
		listAddTasksSelected = new ArrayList<AdditionalTask>();
		for(AdditionalTask tempTask : listAddTasksShow){
			if(tempTask.getIsChecked())
				listAddTasksSelected.add(tempTask);
		}
		String s = "";
		for(AdditionalTask tempTask : listAddTasksSelected){
			s += tempTask.getName() + " - " + Boolean.toString(tempTask.getIsChecked()) + "\n";
		}
		Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();
	//	finish();
	}
	public void onSelectAddTask(View view)
	{
		AdditionalTask addTask = (AdditionalTask) view.getTag();
		addTask.setIsChecked(((CheckBox)view).isChecked()); 
	}
}
