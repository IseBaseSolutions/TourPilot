package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StaticResources.setBaseContext(getBaseContext());
        Intent optionsActivity = new Intent(getApplicationContext(), OptionsActivity.class);
        startActivity(optionsActivity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}

}
