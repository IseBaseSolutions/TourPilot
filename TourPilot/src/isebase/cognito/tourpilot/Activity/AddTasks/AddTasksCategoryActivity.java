package isebase.cognito.tourpilot.Activity.AddTasks;

import java.util.ArrayList;
import java.util.List;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.R.layout;
import isebase.cognito.tourpilot.R.menu;
import isebase.cognito.tourpilot.Activity.BaseActivity;
import isebase.cognito.tourpilot.Activity.TasksActivity;
import isebase.cognito.tourpilot.Data.AddTasksCategory.AddTasksCategory;
import isebase.cognito.tourpilot.Data.AddTasksCategory.AddTasksCategoryManager;
import isebase.cognito.tourpilot.Data.Tour.Tour;
import isebase.cognito.tourpilot.Data.Tour.TourManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AddTasksCategoryActivity extends BaseActivity {

	private List<AddTasksCategory>  listAddTasksCategories = new ArrayList<AddTasksCategory>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_tasks_category);
//		reloadData();
		initListCategories();
//		initTable(listAddTasksCategories.size());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.add_tasks_category, menu);
		return true;
	}
	private void initTable(int tableSize)
	{
		if (tableSize > 0)
			return;
		for (int i = 0; i < 10; i++)
			AddTasksCategoryManager.Instance().add(new AddTasksCategory("Tour " + i));
		reloadData();
	}
	
	private void initListCategories(){
		ListView lvAddTasksCategories = (ListView)findViewById(R.id.lvAddTasksCategory);
//		ArrayAdapter<AddTasksCategory> adapter = new ArrayAdapter<AddTasksCategory>(this,android.R.layout.simple_list_item_1, listAddTasksCategories);
		
		
		List<String> testListAddTasksCategory = new ArrayList<String>();
		for(int i = 0;i < 30;i++)
		{
			testListAddTasksCategory.add("Aditionaly tasks Categoriy #" + i);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,testListAddTasksCategory);
		lvAddTasksCategories.setAdapter(adapter);
		
		lvAddTasksCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent addTasksActivity = new Intent(getApplicationContext(),AddTasksActivity.class);
				startActivity(addTasksActivity);
			}

		});
	}
	public void reloadData() {
		listAddTasksCategories = AddTasksCategoryManager.Instance().load();
	}
}
