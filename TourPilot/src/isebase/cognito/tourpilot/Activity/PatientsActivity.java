package isebase.cognito.tourpilot.Activity;

import java.util.ArrayList;
import java.util.List;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

public class PatientsActivity extends BaseActivity {

	List<Patient> patients = new ArrayList<Patient>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patients);
		reloadData();
		initTable(patients.size());
		initListUndonePatients();
		initListDonePatients();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patients, menu);
		return true;
	}
	
	public void initListUndonePatients() {
		final ListView lvListUndoneTasks = (ListView) findViewById(R.id.lvUndonePatients);
		ArrayAdapter<Patient> adapter = new ArrayAdapter<Patient>(this,android.R.layout.simple_list_item_1, patients);
		lvListUndoneTasks.setAdapter(adapter);
		lvListUndoneTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
			}
		});
		
		final ExpandableListView elvListDoneTasks = (ExpandableListView) findViewById(R.id.elvDonePatients);
	}

	public void initListDonePatients() {
		
	}

	private void initTable(int tableSize) {
		if (tableSize > 0)
			return;
		PatientManager.Instance().add("Goncharenko, Andrew");
		PatientManager.Instance().add("Begov, Bogdan");
		PatientManager.Instance().add("Kiryanov, Igor");
		PatientManager.Instance().add("Goenko, Nikolai");
		PatientManager.Instance().add("Parker, Peter");
		PatientManager.Instance().add("Wayne, Bruce");
		PatientManager.Instance().add("Kent, Clark");
		PatientManager.Instance().add("Vladimir, Oleynikov");
	}

	public void reloadData() {
		patients = PatientManager.Instance().load();
	}
}