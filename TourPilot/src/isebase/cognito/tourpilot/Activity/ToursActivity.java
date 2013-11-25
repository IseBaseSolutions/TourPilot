package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTourManager;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTour;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ToursActivity extends BaseActivity {

	List<PilotTour> pilotTours = new ArrayList<PilotTour>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_tours);
			reloadData();		
			fillUpTitle();
			fillUp();
		}catch(Exception ex){
			ex.printStackTrace();
			criticalClose();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		logOut();
	}

	public void fillUp() {
		ListView listView = (ListView) findViewById(R.id.lvTours);
		ArrayAdapter<PilotTour> adapter = new ArrayAdapter<PilotTour>(this,
				android.R.layout.simple_list_item_1, pilotTours);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				saveSelectedTour(pilotTours.get(position));
				startPatientsActivity();
			}

		});
	}

	public void btlogOutClick(View view) {
		logOut();
	}

	public void btStartSyncClick(View view) {
		startSyncActivity();
	}

	private void logOut() {
		clearPersonalOptions();
		startWorkersActivity();
	}

	private void startWorkersActivity() {
		Intent workersActivity = new Intent(getApplicationContext(), WorkersActivity.class);
		startActivity(workersActivity);
	}

	private void startPatientsActivity() {
		Intent patientsActivity = new Intent(getApplicationContext(), PatientsActivity.class);
		startActivity(patientsActivity);
	}

	private void startSyncActivity() {
		Intent synchActivity = new Intent(getApplicationContext(), SynchronizationActivity.class);
		startActivity(synchActivity);
	}

	private void fillUpTitle() {
		setTitle(Option.Instance().getWorker().getName());
	}

	private void reloadData(){
		pilotTours = PilotTourManager.Instance().loadPilotTours();
	}
	
	private void saveSelectedTour(PilotTour pilotTour) {
		Option.Instance().setPilotTourID(pilotTour.getID());
		Option.Instance().save();
	}

	private void clearPersonalOptions() {
		Option.Instance().setWorkerID(BaseObject.EMPTY_ID);
		Option.Instance().setPilotTourID(BaseObject.EMPTY_ID);
		Option.Instance().save();
	}

}
