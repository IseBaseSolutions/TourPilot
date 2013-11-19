package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Address.Address;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AddressActivity extends BaseActivity {

	private Patient patient;
	private Address address(){ return patient.address; }
	
	private TextView tvStreet;
	private TextView tvZIP;
	private TextView tvCity;
	private TextView tvPhone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address);
		initControls();
		reloadData();
		fillUp();
	}
	
	private void initControls(){
		tvStreet = (TextView) findViewById(R.id.tvStreet);
		tvZIP = (TextView) findViewById(R.id.tvZip);
		tvCity = (TextView) findViewById(R.id.tvCity);
		tvPhone = (TextView) findViewById(R.id.tvPhone);
	}
	
	private void reloadData(){
		Employment eml = EmploymentManager.Instance().load(Option.Instance().getEmploymentID());
		patient = PatientManager.Instance().loadAll(eml.getPatientID());
	}
	
	private void fillUp(){
		tvStreet.setText(address().getStreet());
		tvZIP.setText(address().getZip());
		tvCity.setText(address().getCity());
		tvPhone.setText(address().getPhone());
	}
	public void onCallToPatient(View view ){
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + address().getRealPhone()));
		startActivity(callIntent);

	}
}
