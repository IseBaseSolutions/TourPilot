package isebase.cognito.tourpilot.Activity.AdditionalTasks;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.BaseActivities.BaseTimeSyncActivity;
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
import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.Data.Task.TaskManager;
import isebase.cognito.tourpilot.Templates.AdditionalTaskAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AdditionalTasksActivity extends BaseTimeSyncActivity {

	private ListView lvAddTasks;
	public Button btSaveAddTasks;
	private AdditionalTaskAdapter adapter;
	
	private List<AdditionalTask> additionalTasks;
	private List<Task> tasks;
	private Catalog catalog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_add_tasks);
			initControls();
			reloadData();
			deleteExistedAdditionalyTasks();
			fillUp();
			fillUpTitle();
			setTimeSync(true);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			criticalClose();
		}
	}
	
	private void initControls() {
		lvAddTasks = (ListView) findViewById(R.id.lvAddTasks);
		btSaveAddTasks = (Button) findViewById(R.id.btSaveAddTask);
		btSaveAddTasks.setEnabled(false);
		initFilter();
	}
	
	private void initFilter() {
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
	
	private void reloadData() {
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
	
	private void deleteExistedAdditionalyTasks() {
		tasks = TaskManager.Instance().load(Task.EmploymentIDField, String.valueOf(Option.Instance().getEmploymentID()));
		List<AdditionalTask> copyAddTasks = new ArrayList<AdditionalTask>(additionalTasks);
		tasks.remove(0);
		Task endTask = null;
		int i = 1;
		while(endTask == null) {
			Task task = tasks.get(tasks.size() - i++);
			if(!task.getIsAdditionalTask())
				endTask = task;
		}
		tasks.remove(endTask);
		for (Task task : tasks) {
			for (AdditionalTask addTask : copyAddTasks) {
				if (task.getAditionalTaskID() == addTask.getID()) {
					try {
						additionalTasks.remove(addTask);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}

	private void fillUp() {
		adapter = new AdditionalTaskAdapter(this, R.layout.row_additional_task_template, additionalTasks);
		lvAddTasks.setAdapter(adapter);
	}
	
	private void fillUpTitle() {
		setTitle(catalog.getName());
	}

	public void onSaveAddTasks(View view) {
		TaskManager.Instance().createTasks(adapter.getSelectedTasks());
		startTasksActivity();
	}

	private void sortAdditinalTasks() {
		Collections.sort(additionalTasks, new BaseObjectCompare());
	}
}
