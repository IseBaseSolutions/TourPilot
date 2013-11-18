package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import android.os.Bundle;
import android.view.Menu;

public class DoctorsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctors);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.doctors, menu);
		return true;
	}

}
