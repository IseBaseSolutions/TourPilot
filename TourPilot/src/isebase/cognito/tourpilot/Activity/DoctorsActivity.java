package isebase.cognito.tourpilot.Activity;

import java.util.ArrayList;
import java.util.List;
import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Doctor.Doctor;
import isebase.cognito.tourpilot.Templates.AddressAdapter;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class DoctorsActivity extends BaseActivity {

	List<Doctor> listDoctors = new ArrayList<Doctor>();
	AddressAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctors);
		InitListDoctor(listDoctors.size());
		InitForm();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.doctors, menu);
		return true;
	}
	private void InitListDoctor(int iTableSize){
		if(iTableSize > 0)
			return;
		for(int i = 0;i < 20;i++){
			Doctor doctor = new Doctor();
			doctor.address.setPhone("87654321" + i);
			doctor.setName("DOCTOR #" + i);
			listDoctors.add(doctor);
		}
	}
	private void InitForm(){
		adapter = new AddressAdapter(this, R.layout.row_address_template,listDoctors);
		ListView doctorsListView = (ListView) findViewById(R.id.lvListDoctors);
		doctorsListView.setAdapter(adapter);
	}
	public void onCallToDoctor(View view){
		Doctor doctor = (Doctor) view.getTag();
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + doctor.address.getPhone()));
		startActivity(callIntent);
	}
}
