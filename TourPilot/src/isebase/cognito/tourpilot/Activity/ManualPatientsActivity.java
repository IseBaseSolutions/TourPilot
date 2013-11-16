package isebase.cognito.tourpilot.Activity;

import java.util.ArrayList;
import java.util.List;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.R.layout;
import isebase.cognito.tourpilot.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ManualPatientsActivity extends BaseActivity {

	List<Patient> patients = new ArrayList<Patient>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manual_patients);
		reloadData();
		initPatients(patients.size());
		ShowPatients();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manual_patients, menu);
		return true;
	}
	private void initPatients(int tableSize) {
		if (tableSize > 0)
			return;
		boolean bb = false;
		for (int i = 0; i < 20; i++) {
			bb = !bb;
			Patient patient = new Patient();
			patient.setName("PATIENT #" + i);
			patient.setIsDone(bb);
			patients.add(patient);
//			PatientManager.Instance().save(new Patient("Patient " + i, bb));
		}
		reloadData();
	}
	public void reloadData() {
	//	patients = PatientManager.Instance().load();
	}
	private void ShowPatients(){
		final ListView lvListPatients = (ListView)findViewById(R.id.lvManualPatientsList);
		final ArrayAdapter<Patient> adapter = new ArrayAdapter<Patient>(this,android.R.layout.simple_list_item_1, patients);
		lvListPatients.setAdapter(adapter);
		lvListPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent manualPatientWorksActivity = new Intent(getApplicationContext(), ManualPatientsWorksActivity.class);
				startActivity(manualPatientWorksActivity);
			}
		});
	}
}
