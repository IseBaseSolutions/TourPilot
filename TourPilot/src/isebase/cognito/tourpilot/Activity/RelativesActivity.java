package isebase.cognito.tourpilot.Activity;

import java.util.ArrayList;
import java.util.List;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Relative.Relative;
import isebase.cognito.tourpilot.Templates.AddressAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class RelativesActivity extends BaseActivity {
	
	List<Relative> listRelatives = new ArrayList<Relative>();
	AddressAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_relatives);
		InitListRelatives(listRelatives.size());
		InitForm();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.relatives, menu);
		return true;
	}
	private void InitListRelatives(int sizeTable) {
		if(sizeTable > 0)
			return;
		for(int i = 0;i < 19;i++){
			Relative relative = new Relative();
			
			relative.address.setCity("CITY #" + i);
			relative.address.setZip("ZIP #" + i);
			relative.address.setStreet("ADDRESS # " + i);
			relative.address.setPhone("0987654321_" + i);
			relative.setSurname("SURNAME #" + i);
			relative.setName("NAME #" + i);
			listRelatives.add(relative);
		}
	}
	private void InitForm() {
		adapter = new AddressAdapter(this, R.layout.row_address_template, listRelatives);
		ListView doctorsListView = (ListView) findViewById(R.id.lvListRelatives);
		doctorsListView.setAdapter(adapter);
	}
	public void onCallToDoctor(View view) {		
		Relative relative = (Relative) view.getTag();
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + relative.address.getPhone()));
		startActivity(callIntent);
	}
}