package isebase.cognito.tourpilot.Activity;

import java.util.ArrayList;
import java.util.List;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Doctor.DoctorManager;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Relative.Relative;
import isebase.cognito.tourpilot.Data.Relative.RelativeManager;
import isebase.cognito.tourpilot.Templates.AddressAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class RelativesActivity extends BaseActivity {
	
	List<Relative> listRelatives;
	AddressAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_relatives);
		InitList();
		InitForm();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.relatives, menu);
		return true;
	}
	private void InitForm() {
		adapter = new AddressAdapter(this, R.layout.row_address_template, listRelatives);
		ListView doctorsListView = (ListView) findViewById(R.id.lvListRelatives);
		doctorsListView.setAdapter(adapter);
	}
	public void onCallMobPhone(View view) {		
		Relative relative = (Relative) view.getTag();
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + relative.address.getRealPhone()));
		startActivity(callIntent);
	}
	private void InitList()
	{
		Employment employment = EmploymentManager.Instance().loadAll(Option.Instance().getEmploymentID());
		listRelatives =  RelativeManager.Instance().loadAllByIDs(employment.getPatient().getStrRelativeIDs());
	}
}