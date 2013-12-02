package isebase.cognito.tourpilot.Activity.AdditionalTasks;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.BaseActivity;
import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTask;
import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTaskManager;
import isebase.cognito.tourpilot.Data.AdditionalTask.Catalog;
import isebase.cognito.tourpilot.Data.AdditionalTask.Catalog.eCatalogType;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectCompare;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Data.Task.TaskManager;
import isebase.cognito.tourpilot.Templates.AdditionalTaskAdapter;

import java.util.Collections;
import java.util.List;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class AdditionalTasksActivity extends BaseActivity {

	private ListView lvAddTasks;
	private AdditionalTaskAdapter adapter;
	
	private List<AdditionalTask> additionalTasks;
	private Catalog catalog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_add_tasks);
			init();
			initFilter();
			reloadData();
			fillUp();
			fillUpTitle();
		}
		catch(Exception ex){
			ex.printStackTrace();
			criticalClose();
		}
	}
	
	private void init(){
		lvAddTasks = (ListView) findViewById(R.id.lvAddTasks);		
	}
	
	private void initFilter(){
		EditText etFilter = (EditText) findViewById(R.id.etAddTasksFilter);
		etFilter.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable text) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {
				adapter.getFilter().filter(arg0);
			}
		});
	}
	
	private void reloadData(){
		int catalogType = getIntent().getIntExtra("catalog_type", BaseObject.EMPTY_ID);
		catalog = new Catalog(eCatalogType.values()[catalogType]);	
		
		Employment empl = EmploymentManager.Instance().load(Option.Instance().getEmploymentID());
		Patient patient = PatientManager.Instance().load(empl.getPatientID());
		
		switch (catalog.getCatalogType()) {
			case btyp_kk:
				additionalTasks = AdditionalTaskManager.Instance().loadByCatalog(patient.getKK());
				break;
			case btyp_pk:		
				additionalTasks = AdditionalTaskManager.Instance().loadByCatalog(patient.getPK());	
				break;
			case btyp_pr:			
				additionalTasks = AdditionalTaskManager.Instance().loadByCatalog(patient.getPR());
				break;
			case btyp_sa:			
				additionalTasks = AdditionalTaskManager.Instance().loadByCatalog(patient.getSA());
				break;
		}
		sortAdditinalTasks();
	}

	private void fillUp(){
		adapter = new AdditionalTaskAdapter(this
				,R.layout.row_additional_task_template, additionalTasks);
		lvAddTasks.setAdapter(adapter);
	}
	
	private void fillUpTitle(){
		setTitle(catalog.getName());
	}

	public void onSaveAddTasks(View view) {
		TaskManager.Instance().createTasks(adapter.getSelectedTasks());
		startTasksActivity();
	}

	private void sortAdditinalTasks() {

		Collections.sort(additionalTasks,new BaseObjectCompare());
	}
}
