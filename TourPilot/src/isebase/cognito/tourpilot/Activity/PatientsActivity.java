package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Data.Tour.Tour;
import isebase.cognito.tourpilot.Data.Worker.Worker;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;

public class PatientsActivity extends BaseActivity {

	List<Patient> patients = new ArrayList<Patient>();
	List<Patient> donePatients = new ArrayList<Patient>();
	List<Patient> unDonePatients = new ArrayList<Patient>();

	private Tour tour;
	private Worker worker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patients);
		reloadData();
		initPatients(patients.size());
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
		final ArrayAdapter<Patient> adapter = new ArrayAdapter<Patient>(this,
				android.R.layout.simple_list_item_1, unDonePatients);
		final ListView lvListUndoneTasks = (ListView) findViewById(R.id.lvUndonePatients);

		lvListUndoneTasks.setAdapter(adapter);
		lvListUndoneTasks
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
					}
				});

	}

	public void initListDonePatients() {
		final ArrayAdapter<Patient> adapter = new ArrayAdapter<Patient>(this,
				android.R.layout.simple_list_item_1, donePatients);
		final ListView lvListDoneTasks = (ListView) findViewById(R.id.content);
		lvListDoneTasks.setAdapter(adapter);
		
		final SlidingDrawer slidingDonePatients = (SlidingDrawer) findViewById(R.id.slidingDonePatients);
		final Button bOpened = (Button) findViewById(R.id.handle);
		slidingDonePatients.setOnDrawerOpenListener(new OnDrawerOpenListener() {

			@Override
			public void onDrawerOpened() {
				// TODO Auto-generated method stub
				bOpened.setText("OPENED");
			}

		});
		slidingDonePatients
				.setOnDrawerCloseListener(new OnDrawerCloseListener() {
					@Override
					public void onDrawerClosed() {
						// TODO Auto-generated method stub
						bOpened.setText("CLOSED");
					}
				});
	}

	private void initPatients(int tableSize) {
		if (tableSize > 0)
			return;
		boolean bb = false;
		for (int i = 0; i < 10; i++) {
			bb = !bb;
			PatientManager.Instance().add(new Patient("Patient " + i, bb));
		}
		reloadData();
	}

	public void reloadData() {
		patients = PatientManager.Instance().load();
		for (Patient patient : patients) {
			if (patient.getIsDone())
				donePatients.add(patient);
			else
				unDonePatients.add(patient);
		}
		TextView tvTourName = (TextView) findViewById(R.id.tvPatientsWorkerName);
		// tour = (Tour) getIntent().getParcelableExtra("patientData");
		// tvTourName.setText(tour.getName());
	}
}

