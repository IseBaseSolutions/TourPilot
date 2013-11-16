package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class BaseActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.base, menu);
		return true;
	}
    
}
