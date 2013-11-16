package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Intent;
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

	List<Employment> employments ;
	List<Employment> donePatients = new ArrayList<Employment>();
	List<Employment> unDonePatients = new ArrayList<Employment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patients);
		reloadData();
		initComnponents();
		initListUndonePatients();
		initListDonePatients();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	@Override
	public void onBackPressed() {
		startToursActivity();
	}
	
	private void startToursActivity() {
		Intent toursActivity = new Intent(getApplicationContext(), ToursActivity.class);
		startActivity(toursActivity);
	}
	
	private void startTasksActivity() {
		Intent tasksActivity = new Intent(getApplicationContext(), TasksActivity.class);
		startActivity(tasksActivity);
	}

	public void initListUndonePatients() {
		final ArrayAdapter<Employment> adapter = new ArrayAdapter<Employment>(this,
				android.R.layout.simple_list_item_1, unDonePatients);
		final ListView lvListUndoneTasks = (ListView) findViewById(R.id.lvUndonePatients);

		lvListUndoneTasks.setAdapter(adapter);
		lvListUndoneTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				saveSelectedEmploymentID(((Employment)employments.get(position)).getId());
				startTasksActivity();
			}
		});
	}
	
	public void initListDonePatients() {
		final ArrayAdapter<Employment> adapter = new ArrayAdapter<Employment>(this,
				android.R.layout.simple_list_item_1, donePatients);
		final ListView lvListDoneTasks = (ListView) findViewById(R.id.lvDonePatients);
		lvListDoneTasks.setAdapter(adapter);

		final SlidingDrawer slidingDonePatients = (SlidingDrawer) findViewById(R.id.sdDonePatients);
		final Button bOpened = (Button) findViewById(R.id.btHandle);
		slidingDonePatients.setOnDrawerOpenListener(new OnDrawerOpenListener() {

			@Override
			public void onDrawerOpened() {
				bOpened.setText(R.string.hide_done_patients);
				bOpened.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						android.R.drawable.arrow_down_float, 0);
			}

		});
		slidingDonePatients
				.setOnDrawerCloseListener(new OnDrawerCloseListener() {
					@Override
					public void onDrawerClosed() {
						bOpened.setText(R.string.show_done_patients);
						bOpened.setCompoundDrawablesWithIntrinsicBounds(0, 0,
								android.R.drawable.arrow_up_float, 0);
					}
				});
	}

	public void reloadData() {
		employments = EmploymentManager.Instance().load(Employment.PilotTourIDField
				, Option.Instance().getPilotTourID()+"");
		for (Employment empl : employments) {
			donePatients.add(empl);
			unDonePatients.add(empl);
		}
	}
	
	private void initComnponents() {
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("EE MM.dd");
		String dayOfTheWeek = simpleDateformat.format(new Date());
		((TextView) findViewById(R.id.tvCurrentInfo)).setText(String.format(
				"%s - %s", dayOfTheWeek, Option.Instance().getWorker().getName()));
	}
	
	private void saveSelectedEmploymentID(int emplID) {
		Option.Instance().setEmploymentID(emplID);
		Option.Instance().save();
	}
	
}
