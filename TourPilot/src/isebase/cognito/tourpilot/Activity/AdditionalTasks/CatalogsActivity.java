package isebase.cognito.tourpilot.Activity.AdditionalTasks;

import java.util.ArrayList;
import java.util.List;
import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.BaseActivity;
import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTask;
import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTaskManager;
import isebase.cognito.tourpilot.Data.AdditionalTask.Catalog;
import isebase.cognito.tourpilot.Data.AdditionalTask.Catalog.eCatalogType;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CatalogsActivity extends BaseActivity {

	private List<Catalog>  listCatalogs = new ArrayList<Catalog>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_tasks_category);
//		reloadData();
//		initListCategories();
//		initTable(listAddTasksCategories.size());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
// 		Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.add_tasks_category, menu);
		return true;
	}
	
	private void initListCategories(){
		ListView lvAddTasksCategories = (ListView)findViewById(R.id.lvAddTasksCategory);
		
		
		for(int i = 0; i < 4; i++)
			listCatalogs.add(new Catalog(Catalog.eCatalogType.values()[i]));
		ArrayAdapter<Catalog> adapter = new ArrayAdapter<Catalog>(this, android.R.layout.simple_list_item_1, listCatalogs);
		lvAddTasksCategories.setAdapter(adapter);
		
		lvAddTasksCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent addTasksActivity = new Intent(getApplicationContext(), AdditionalTasksActivity.class);
				startActivity(addTasksActivity);
			}

		});
	}
}
