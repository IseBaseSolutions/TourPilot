package isebase.cognito.tourpilot.Activity;

import java.util.ArrayList;
import java.util.List;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.R.layout;
import isebase.cognito.tourpilot.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ManualPatientsWorksActivity extends BaseActivity {

	List<String> manualWorks = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manual_patients_works);
		
		InitManualWorksList(manualWorks.size());
		reloadData();
		ShowWorks();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manual_patients_works, menu);
		return true;
	}
	private void InitManualWorksList(int listSize){
		if(listSize > 0)
			return;
		for(int i = 0;i < 20;i++){
			manualWorks.add("MANUAL WORK #" + i);
		}
		reloadData();
	}
	private void reloadData(){
		
	}
	private void ShowWorks(){
		final ListView lvlistWorks = (ListView)findViewById(R.id.lvPatientsManualWorksList);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, manualWorks);
		lvlistWorks.setAdapter(adapter);
	}
}
