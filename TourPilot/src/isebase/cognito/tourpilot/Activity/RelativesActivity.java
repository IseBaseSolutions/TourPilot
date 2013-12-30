package isebase.cognito.tourpilot.Activity;

import java.util.List;
import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.BaseActivities.BaseActivity;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Relative.Relative;
import isebase.cognito.tourpilot.Data.Relative.RelativeManager;
import isebase.cognito.tourpilot.Templates.AddressAdapter;
import android.os.Bundle;
import android.widget.ListView;

public class RelativesActivity extends BaseActivity {
	
	private List<Relative> relatives;
	private Employment employment;
	private AddressAdapter<Relative> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_relatives);
			reloadData();
			fillUp();
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
	
	private void fillUp() {
		adapter = new AddressAdapter<Relative>(this
				, R.layout.row_address_template, relatives);
		ListView relativesListView = (ListView) findViewById(R.id.lvListRelatives);
		relativesListView.setAdapter(adapter);
	}
	
	private void reloadData()
	{
		employment = EmploymentManager.Instance().loadAll(Option.Instance().getEmploymentID());
		relatives =  RelativeManager.Instance().sortByStrIDs(RelativeManager.Instance().loadAllByIDs(employment.getPatient().getStrRelativeIDs()), employment.getPatient().getStrRelativeIDs());
	}

}