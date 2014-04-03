package isebase.cognito.tourpilot.Activity.AdditionalTasks;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.BaseActivities.BaseActivity;
import isebase.cognito.tourpilot.Data.AdditionalTask.Catalog;
import isebase.cognito.tourpilot.Data.AdditionalTask.Catalog.eCatalogType;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.DataBase.HelperFactory;
import isebase.cognito.tourpilot.NewData.NewEmployment.NewEmployment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CatalogsActivity extends BaseActivity {

	private List<Catalog> catalogs = new ArrayList<Catalog>();
	private Employment employment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_add_tasks_category);
			reloadData();
			fillUpTitle();
			fillUp();
		}
		catch(Exception e){
			e.printStackTrace();
			criticalClose();
		}
	}

	private void reloadData(){
		employment = EmploymentManager.Instance().load(Option.Instance().getEmploymentID());

		Patient patient = PatientManager.Instance().load(employment.getPatientID());
		if(patient.getKK() != BaseObject.EMPTY_ID)
			catalogs.add(new Catalog(eCatalogType.btyp_kk));
		if(patient.getPK() != BaseObject.EMPTY_ID)
			catalogs.add(new Catalog(eCatalogType.btyp_pk));
		if(patient.getPR() != BaseObject.EMPTY_ID)
			catalogs.add(new Catalog(eCatalogType.btyp_pr));
		if(patient.getSA() != BaseObject.EMPTY_ID)
			catalogs.add(new Catalog(eCatalogType.btyp_sa));
	}
	
	@Override
	public void onBackPressed() {
		Intent intentFlege = getIntent();
		Bundle bundle = intentFlege.getExtras();
		if(bundle == null)
			super.onBackPressed();
		
	}
		
	private void fillUpTitle(){
		setTitle(employment.getName());
	}
	
	private void fillUp(){
		ListView lvAddTasksCategories = (ListView)findViewById(R.id.lvAddTasksCategory);
		ArrayAdapter<Catalog> adapter = new ArrayAdapter<Catalog>(this, android.R.layout.simple_list_item_1, catalogs);
		lvAddTasksCategories.setAdapter(adapter);		
		lvAddTasksCategories.setOnItemClickListener(catalogOnItemClickListener);
	}
	
	private AdapterView.OnItemClickListener catalogOnItemClickListener 
			= new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			Catalog catalog = catalogs.get(position);
			Intent addTasksActivity = new Intent(getApplicationContext(), AdditionalTasksActivity.class);
			addTasksActivity.putExtra("catalog_type", catalog.getCatalogType().ordinal());
			startActivity(addTasksActivity);
		}
	};
	
}
