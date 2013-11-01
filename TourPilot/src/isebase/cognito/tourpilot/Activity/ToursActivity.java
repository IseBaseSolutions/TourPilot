package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Tour.Tour;
import isebase.cognito.tourpilot.Data.Tour.TourManager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ToursActivity extends Activity {

	List<Tour> tours = new ArrayList<Tour>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tours);
		reloadData();
		initTable(tours.size());
		initListTours();
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

			}

		});
	}

	private void initTable(int tableSize) {
		if (tableSize > 0)
			return;
		TourManager.Instance().add("First tour");
		TourManager.Instance().add("Second tour");
		TourManager.Instance().add("Third tour");
		TourManager.Instance().add("Fourth tour");
		TourManager.Instance().add("Fifth tour");
		TourManager.Instance().add("Sixth tour");
		TourManager.Instance().add("Seventh tour");
	}

	public void logOut(View view) {
		Intent workersActivity = new Intent(getApplicationContext(),
				WorkersActivity.class);
		startActivity(workersActivity);
	}

	public void reloadData() {
		tours = TourManager.Instance().load();
	}

}