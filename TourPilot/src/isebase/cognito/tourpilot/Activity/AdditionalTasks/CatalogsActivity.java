package isebase.cognito.tourpilot.Activity.AdditionalTasks;

import java.util.ArrayList;
import java.util.List;
import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.BaseActivity;
import isebase.cognito.tourpilot.Data.AdditionalTask.Catalog;
import isebase.cognito.tourpilot.Data.AdditionalTask.Catalog.eCatalogType;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CatalogsActivity extends BaseActivity {

	private List<Catalog> listCatalogs = new ArrayList<Catalog>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_tasks_category);
		reloadData();
		fillUp();
	}

	private void reloadData(){
		Employment employment = EmploymentManager.Instance().load(Option.Instance().getEmploymentID());
		Patient patient = PatientManager.Instance().load(employment.getPatientID());
		if(patient.getKK() != BaseObject.EMPTY_ID)
			listCatalogs.add(new Catalog(eCatalogType.btyp_kk));
		if(patient.getPK() != BaseObject.EMPTY_ID)
			listCatalogs.add(new Catalog(eCatalogType.btyp_pk));
		if(patient.getPR() != BaseObject.EMPTY_ID)
			listCatalogs.add(new Catalog(eCatalogType.btyp_pr));
		if(patient.getSA() != BaseObject.EMPTY_ID)
			listCatalogs.add(new Catalog(eCatalogType.btyp_sa));
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
// 		Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.add_tasks_category, menu);
		return true;
	}
	
	private void fillUp(){
		ListView lvAddTasksCategories = (ListView)findViewById(R.id.lvAddTasksCategory);
		ArrayAdapter<Catalog> adapter = new ArrayAdapter<Catalog>(this, android.R.layout.simple_list_item_1, listCatalogs);
		lvAddTasksCategories.setAdapter(adapter);		
		lvAddTasksCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Catalog catalog = listCatalogs.get(position);
				Intent addTasksActivity = new Intent(getApplicationContext(), AdditionalTasksActivity.class);
				addTasksActivity.putExtra("catalog_type", catalog.getCatalogType().ordinal());
				startActivity(addTasksActivity);
			}
		});
	}
}
