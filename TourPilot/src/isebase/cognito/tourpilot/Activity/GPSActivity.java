package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Data.WayPoint.WayPoint;
import isebase.cognito.tourpilot.Gps.GpsNavigator;
import isebase.cognito.tourpilot.Gps.Service.GPSLogger;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class GPSActivity extends Activity {

	private List<Employment> employments = new ArrayList<Employment>();
	private ListView listView;
	private Employment employment; 
	private Button btStartGPS;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);
		listView = (ListView) findViewById(R.id.lvEmployments);
		btStartGPS = (Button) findViewById(R.id.btStartGPS);
		btStartGPS.setEnabled(false);
		List<Employment> empls = new ArrayList<Employment>();
		empls = EmploymentManager.Instance().load(Employment.PilotTourIDField, Option.Instance().getPilotTourID());
		for (Employment empl : empls)
			if (!empl.getIsDone() && !isAlreadyInList(empl))
				employments.add(empl);
		ArrayAdapter<Employment> adapter = new ArrayAdapter<Employment>(this,
				android.R.layout.simple_list_item_single_choice, employments);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				employment = employments.get(position);
				btStartGPS.setEnabled(employment != null);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gps, menu);
		return true;
	}
	
	public void btStartGPSClick(View view) {
		Intent patientsActivity = new Intent(getApplicationContext(),
				PatientsActivity.class);
		startActivity(patientsActivity);
		
		GpsNavigator.startGpsNavigation(PatientManager.Instance().loadAll(employment.getPatientID()).getAddress());
//		WayPointManager.Instance().updateWasSent();
//		WayPointManager.Instance().updateTourID();
		Intent gpsLoggerServiceIntent = new Intent(this, GPSLogger.class);
		gpsLoggerServiceIntent.putExtra(WayPoint.TrackIDField, 0);
		startService(gpsLoggerServiceIntent);
		ServiceConnection gpsLoggerConnection = new ServiceConnection() {
			
			@Override
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub					
			}
			

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				// TODO Auto-generated method stub				
			}
			
		};
		bindService(gpsLoggerServiceIntent, gpsLoggerConnection, 0);
	}
	
	private boolean isAlreadyInList(Employment employment) {
		for (Employment empl : employments)
			if (empl.getName().equals(employment.getName()))
				return true;
		return false;
	}

}
