package isebase.cognito.tourpilot.Activity;

import java.util.ArrayList;
import java.util.List;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.R.layout;
import isebase.cognito.tourpilot.R.menu;
import isebase.cognito.tourpilot.Data.Doctor.Doctor;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DoctorsActivity extends BaseActivity {

	List<Doctor> listDoctors = new ArrayList<Doctor>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctors);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doctors, menu);
		return true;
	}
	private void InitListDoctor(int iTableSize){
		if(iTableSize > 0)
			return;
		for(int i = 0;i < 20;i++)
			listDoctors.add(new Doctor());
	}
}
