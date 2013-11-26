package isebase.cognito.tourpilot.Activity;

import java.util.List;
import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Doctor.Doctor;
import isebase.cognito.tourpilot.Data.Doctor.DoctorManager;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Templates.AddressAdapter;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;

public class DoctorsActivity extends BaseActivity {

	private List<Doctor> addressable;
	private Employment employment;
	AddressAdapter<Doctor> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_doctors);
			reloadData();
			fillUp();
			fillUpTitle();
		}
		catch(Exception ex){
			ex.printStackTrace();
			criticalClose();
		}
	}

	private void fillUp(){
		adapter = new AddressAdapter<Doctor>(this
				, R.layout.row_address_template, addressable);
		ListView doctorsListView = (ListView) findViewById(R.id.lvListDoctors);
		doctorsListView.setAdapter(adapter);
	}
	
	public void reloadData() {
		employment = EmploymentManager.Instance().loadAll(Option.Instance().getEmploymentID());
		addressable = DoctorManager.Instance().loadAllByIDs(employment.getPatient().getStrDoctorsIDs());
	}

	private void fillUpTitle(){
		setTitle(getString(R.string.menu_doctors) + ", " + employment.getName());
	}
	

}
