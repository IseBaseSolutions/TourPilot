package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Doctor.Doctor;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTour;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTourManager;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Templates.EmploymentAdapter;
import isebase.cognito.tourpilot.Utils.DateUtils;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class PatientsActivity extends BaseActivity {

	private List<Employment> employments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_patients);
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
		getMenuInflater().inflate(R.menu.patients_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add_additional_work:
			startAdditionalWorksActivity();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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
	
	private void startAdditionalWorksActivity() {
		Intent additionalWorksActivity = new Intent(getApplicationContext(), AdditionalWorksActivity.class);
		startActivity(additionalWorksActivity);
	}

	public void fillUp() {
		EmploymentAdapter adapter = new EmploymentAdapter(this,R.layout.row_employment_template, employments);
		ListView lvEmployments = (ListView) findViewById(R.id.lvEmployments);
		lvEmployments.setAdapter(adapter);
		lvEmployments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				saveSelectedEmploymentID(employments.get(position).getId());
				startTasksActivity();
			}
		});
	}
	
	public void reloadData() {
		employments = EmploymentManager.Instance().loadWhereOrder(Employment.PilotTourIDField
				, String.valueOf(Option.Instance().getPilotTourID()), Employment.DateField);
	}
	
	private void fillUpTitle() {
		PilotTour pt = PilotTourManager.Instance().loadPilotTour(Option.Instance().getPilotTourID());
		Worker worker = Option.Instance().getWorker();
		setTitle(String.format("%1$s, %2$s - %3$s"
				, worker.getName()
				, pt.getName()
				, DateUtils.WeekDateFormat.format(pt.getPlanDate())));
	}
	
	private void saveSelectedEmploymentID(int emplID) {
		Option.Instance().setEmploymentID(emplID);
		Option.Instance().save();
	}	
}
