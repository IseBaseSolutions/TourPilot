package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Tour.Tour;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ToursActivity extends Activity {

	List<Tour> tours = new ArrayList<Tour>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tours);
		tours = Option.Instance().getWorker().tours;
		initListTours();
		initComnponents();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}

	public void initListTours() {
		ListView listView = (ListView) findViewById(R.id.lvTours);
		ArrayAdapter<Tour> adapter = new ArrayAdapter<Tour>(this,
				android.R.layout.simple_list_item_1, tours);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				saveSelectedTour(tours.get(position).getId());
				Intent patientsActivity = new Intent(getApplicationContext(),
						PatientsActivity.class);
				startActivity(patientsActivity);
			}

		});
	}

	public void logOut(View view) {
		clearPersonalOptions();
		Intent workersActivity = new Intent(getApplicationContext(),
				WorkersActivity.class);
		startActivity(workersActivity);
	}

	private void initComnponents() {
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("EE MM.dd");
		String dayOfTheWeek = simpleDateformat.format(new Date());
		((TextView) findViewById(R.id.tvCurrentInfo)).setText(String.format(
				"%s - %s", dayOfTheWeek, Option.Instance().getWorker().getName()));
	}
	
	private void saveSelectedTour(int tourID) {
		Option.Instance().setTourID(tourID);
		Option.Instance().save();
	}
	
	private void clearPersonalOptions() {
		Option.Instance().setWorkerID(-1);
		Option.Instance().setTourID(-1);
		Option.Instance().save();
	}
	
}