package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ToursActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tours);
		fillUpTours();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}
	
	 public void fillUpTours() {
		 ListView listView = (ListView) findViewById(R.id.lvTours);
		 
         String[] tours = new String[] { 
    		 "First tour", 
             "Second tour",
             "Third tour",             
             "Fourth tour", 
             "Fifth tour", 
             "Sixth tour", 
             "Seventh tour", 
             "Eight tour" 
		 };         
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        		 android.R.layout.simple_list_item_1, android.R.id.text1, tours);
         listView.setAdapter(adapter);
	 }
	 
	 public void logOut(View view){
		 Intent workersActivity = new Intent(getApplicationContext(), WorkersActivity.class);
		 startActivity(workersActivity);
	 }

}