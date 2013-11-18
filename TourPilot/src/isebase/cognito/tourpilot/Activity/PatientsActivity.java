package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Templates.EmploymentAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class PatientsActivity extends BaseActivity {

	List<Employment> employments ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_patients);
			reloadData();
			initComnponents();
			fillUp();
		}catch(Exception ex){
			ex.printStackTrace();
			criticalClose();
		}
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
		employments = EmploymentManager.Instance().load(Employment.PilotTourIDField
				, String.valueOf(Option.Instance().getPilotTourID()));
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
