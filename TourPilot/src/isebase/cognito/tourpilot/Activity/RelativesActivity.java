package isebase.cognito.tourpilot.Activity;

import java.util.List;
import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Relative.Relative;
import isebase.cognito.tourpilot.Data.Relative.RelativeManager;
import isebase.cognito.tourpilot.Templates.AddressAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class RelativesActivity extends BaseActivity {
	
	private List<Relative> relatives;
	private Employment employment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_relatives);
			reloadData();
			init();
			fillUpTitle();
		}
		catch(Exception ex){
			ex.printStackTrace();
			criticalClose();
		}
	}
	
	private void fillUpTitle(){
		setTitle(getString(R.string.menu_relatives) + ", " + employment.getName());
	}
	
	private void init() {
		AddressAdapter<Relative> adapter = new AddressAdapter<Relative>(this
				, R.layout.row_address_template, relatives);
		ListView doctorsListView = (ListView) findViewById(R.id.lvListRelatives);
		doctorsListView.setAdapter(adapter);
	}
	
	private void reloadData()
	{
		employment = EmploymentManager.Instance().loadAll(Option.Instance().getEmploymentID());
		relatives =  RelativeManager.Instance().loadAllByIDs(employment.getPatient().getStrRelativeIDs());
	}
	
	public void onCallPhone(View view) {		
		Relative relative = (Relative) view.getTag();
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + relative.address.getRealPhone()));
		startActivity(callIntent);
	}
	
	public void onCallPrivatePhone(View view){
		Relative relative = (Relative) view.getTag();
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + relative.address.getRealPrivatePhone()));
		startActivity(callIntent);
	}
	
	public void onCallMobilePhone(View view){
		Relative relative = (Relative) view.getTag();
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + relative.address.getRealMobilePhone()));
		startActivity(callIntent);
	}
}