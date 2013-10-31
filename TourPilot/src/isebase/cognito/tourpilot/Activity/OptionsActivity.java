package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class OptionsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		getBaseContext();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}
	
	 public void startSync(View view) {		 
         Intent workersActivity = new Intent(getApplicationContext(), WorkersActivity.class);
//         
//         //Sending data to another Activity
//         nextScreen.putExtra("name", inputName.getText().toString());
//         nextScreen.putExtra("email", inputEmail.getText().toString());
//
         startActivity(workersActivity);
	 }
}
