package isebase.cognito.tourpilot.Activity;

import java.util.ArrayList;
import java.util.List;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Address.Address;
import isebase.cognito.tourpilot.Data.Doctor.Doctor;
import isebase.cognito.tourpilot.Data.Doctor.DoctorManager;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Templates.AddressAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class AddressPatientActivity extends BaseActivity {

	List<Patient> patients;
	AddressAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address);
		reloadData();
		InitForm();
	}
	
	private void InitForm(){
		adapter = new AddressAdapter(this, R.layout.row_address_template,patients);
		ListView doctorsListView = (ListView) findViewById(R.id.lvListPatientAdrress);
		doctorsListView.setAdapter(adapter);
	}
	public void reloadData() {
		Employment employment = EmploymentManager.Instance().loadAll(Option.Instance().getEmploymentID());
		patients = new ArrayList<Patient>();
		patients.add(PatientManager.Instance().loadAll(employment.getPatientID()));
	}
	
	
	public void onCallPhone(View view) {		
		Patient patinet = (Patient) view.getTag();
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + patinet.address.getRealPhone()));
		startActivity(callIntent);
	}
	public void onCallPrivatePhone(View view){
		Patient patient = (Patient) view.getTag();
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + patient.address.getRealPrivatePhone()));
		startActivity(callIntent);
	}
	public void onCallMobilePhone(View view){
		Patient patient = (Patient) view.getTag();
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + patient.address.getRealMobilePhone()));
		startActivity(callIntent);
	}
}
