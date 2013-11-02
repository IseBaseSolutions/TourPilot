package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;
import isebase.cognito.tourpilot.StaticResources.StaticResources;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class WorkersActivity extends BaseActivity {

	List<Worker> workers = new ArrayList<Worker>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workers);
		reloadData();
		initTable(workers.size());
		initListWorkers();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}

	public void initListWorkers() {
		final ListView listView = (ListView) findViewById(R.id.lvWorkers);
		ArrayAdapter<Worker> adapter = new ArrayAdapter<Worker>(this,
				android.R.layout.simple_list_item_1, workers);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				showDialog(0);
				((TextView) pinDialog.findViewById(R.id.tvWorkerName)).
					setText(((Worker)listView.getItemAtPosition(position)).getName());
				((EditText) pinDialog.findViewById(R.id.evPin)).
					setText(StaticResources.stringEmpty);
			}

		});
	}
	
	private void initTable(int tableSize) {
		if (tableSize > 0)
			return;
		for (int i = 0; i < 500; i++)
			WorkerManager.Instance().add(new Worker("Worker " + i));
	}	

	public void switchToOptions(View view) {
		Intent optionsActivity = new Intent(getApplicationContext(), OptionsActivity.class);
		startActivity(optionsActivity);
	}
	
	public void reloadData() {
		workers = WorkerManager.Instance().load();
	}

}
