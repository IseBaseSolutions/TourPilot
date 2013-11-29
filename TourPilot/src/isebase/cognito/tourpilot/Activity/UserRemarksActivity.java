package isebase.cognito.tourpilot.Activity;

import java.util.Date;
import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.UserRemark.UserRemark;
import isebase.cognito.tourpilot.Data.UserRemark.UserRemarkManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class UserRemarksActivity extends BaseActivity {
		
	private CheckBox chbConnect;
	private CheckBox chbMedchanges;
	private CheckBox chbPflege;
	private EditText etOther;
	
	private UserRemark userRemark;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_notes);
			initElements();
			setWriteAble();
			reloadData();
			fillUp();
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			criticalClose();
		}
	}
	
	private void initElements(){		
		chbConnect = (CheckBox)findViewById(R.id.chbConnect);
		chbMedchanges = (CheckBox)findViewById(R.id.chbMedchanges);
		chbPflege = (CheckBox)findViewById(R.id.chbPflege);
		etOther = (EditText)findViewById(R.id.etOther);
	}
	
	private void reloadData(){
		Employment empl = EmploymentManager.Instance().load(Option.Instance().getEmploymentID());
		userRemark = UserRemarkManager.Instance().loadByWorkerPatient(
				Option.Instance().getWorkerID(), empl.getPatientID());		
	}
	
	private void fillUp(){
		chbConnect.setChecked((userRemark.getCheckboxes() & 1) == 1);
		chbMedchanges.setChecked((userRemark.getCheckboxes() & 2) == 2);
		chbPflege.setChecked((userRemark.getCheckboxes() & 4) == 4);
		etOther.setText(userRemark.getName());
	}
	
	private void pickUp(){
		userRemark.setName(etOther.getText().toString());
		userRemark.setCheckboxes(chbConnect.isChecked()
				, chbMedchanges.isChecked()
				, chbPflege.isChecked()
				, !etOther.getText().toString().isEmpty());
		userRemark.setDate(new Date());
		userRemark.setWasSent(false);
	}
	
	private void save(){
		UserRemarkManager.Instance().save(userRemark);		
	}
	
	public void btUserRemarkSaveClick(View view){
		pickUp();
		save();
		
		Integer type_save = -1;
		Intent intentSave = getIntent();
		Bundle b = intentSave.getExtras();
		 if(b!=null){
			 type_save = (Integer)b.get("mode");
			 switch(type_save){
			 case 0:
			//	 startTasksActivity();
				 finish();
				 break;
			 case 1:
				 clearEmployment();
				 startSyncActivity();
				 break;
			 case 2:
				 clearEmployment();
				 startPatientsActivity();
				 break;
			 }
		 }
		
	}
	
	private void clearEmployment() {
		Option.Instance().setEmploymentID(BaseObject.EMPTY_ID);
		Option.Instance().save();
	}
	
	private void setWriteAble(){
		Intent intentSave = getIntent();
		Bundle bundle = intentSave.getExtras();
		Boolean viewMode = false;
		if(bundle != null){
			viewMode = !bundle.getBoolean("viewMode");
			CheckBox chbConnect = (CheckBox)findViewById(R.id.chbConnect);
			CheckBox chbMedchanges = (CheckBox)findViewById(R.id.chbMedchanges);
			CheckBox chbPflege = (CheckBox)findViewById(R.id.chbPflege);
			TextView tvOther = (TextView)findViewById(R.id.tvOther);
			EditText etOther = (EditText)findViewById(R.id.etOther);
			Button btUserRemarkSave = (Button)findViewById(R.id.btUserRemarkSave);
			
			chbConnect.setClickable(viewMode);
			chbMedchanges.setClickable(viewMode);
			chbPflege.setClickable(viewMode);
			tvOther.setClickable(viewMode);
			etOther.setFocusable(viewMode);
			btUserRemarkSave.setEnabled(viewMode);
		}
	}
}



