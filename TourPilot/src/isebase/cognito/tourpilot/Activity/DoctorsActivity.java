package isebase.cognito.tourpilot.Activity;

import java.util.ArrayList;
import java.util.List;
import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Doctor.Doctor;
import isebase.cognito.tourpilot.Data.Doctor.DoctorManager;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Relative.Relative;
import isebase.cognito.tourpilot.Data.Relative.RelativeManager;
import isebase.cognito.tourpilot.Templates.AddressAdapter;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class DoctorsActivity extends BaseActivity {

	List<Doctor> doctors;
	AddressAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctors);
		reloadData();
		InitForm();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.doctors, menu);
		return true;
	}
	private void InitForm(){
		adapter = new AddressAdapter(this, R.layout.row_address_template,doctors);
		ListView doctorsListView = (ListView) findViewById(R.id.lvListDoctors);
		doctorsListView.setAdapter(adapter);
	}
	public void reloadData() {
		Employment employment = EmploymentManager.Instance().loadAll(Option.Instance().getEmploymentID());
		doctors =  DoctorManager.Instance().loadAllByIDs(employment.getPatient().getStrDoctorsIDs());
	}

	
	public void onCallPhone(View view) {		
		Doctor doctor = (Doctor) view.getTag();
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + doctor.address.getRealPhone()));
		startActivity(callIntent);
	}
	public void onCallPrivatePhone(View view){
		Doctor doctor = (Doctor) view.getTag();
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + doctor.address.getRealPrivatePhone()));
		startActivity(callIntent);
	}
	public void onCallMobilePhone(View view){
		Doctor doctor = (Doctor) view.getTag();
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + doctor.address.getRealMobilePhone()));
		startActivity(callIntent);
	}

}
