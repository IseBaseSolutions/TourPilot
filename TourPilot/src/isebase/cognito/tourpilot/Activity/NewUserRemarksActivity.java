package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.BaseActivities.BaseActivity;
import isebase.cognito.tourpilot.Data.CustomRemark.CustomRemark;
import isebase.cognito.tourpilot.Data.CustomRemark.CustomRemarkManager;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.UserRemark.RemarksComparer;
import isebase.cognito.tourpilot.Data.UserRemark.UserRemark;
import isebase.cognito.tourpilot.Data.UserRemark.UserRemarkManager;
import isebase.cognito.tourpilot.DataBase.HelperFactory;
import isebase.cognito.tourpilot.NewData.NewEmployment.NewEmployment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewUserRemarksActivity extends BaseActivity {

	private Employment employment;
	private UserRemark userRemark;	
	private List<CustomRemark> customRemarks;
	
	private LinearLayout llCustomRemarks;
	private boolean viewMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_new_user_remarks);
			reloadData();
			initComponents();
			fillUp();
		} catch (Exception e) {
			e.printStackTrace();
			criticalClose();
		}
	}

	private void initComponents() {
		Intent intentSave = getIntent();
		Bundle bundle = intentSave.getExtras();
		viewMode = false;
		if(bundle != null){
			viewMode = bundle.getBoolean("ViewMode");
		}
		llCustomRemarks = (LinearLayout) findViewById(R.id.llCustomRemarks);
	}

	private void reloadData() {
		employment = EmploymentManager.Instance().load(Option.Instance().getEmploymentID());

		customRemarks = CustomRemarkManager.Instance().load();
		userRemark = UserRemarkManager.Instance().load(Option.Instance().getEmploymentID());
		if (userRemark == null)
			userRemark = new UserRemark(employment.getID(), Option.Instance().getWorkerID(), employment.getPatientID(), 
						false, false, false, false, "");
		Collections.sort(customRemarks, new RemarksComparer());
	}

	private void fillUp() {
		for (CustomRemark customRemark : customRemarks)
			if (customRemark.getID() != 1)
				addCheckBox(customRemark);
		TextView textView = new TextView(this);
		textView.setText(getString(R.string.other));
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		llCustomRemarks.addView(textView);
		EditText editText = new EditText(this);
		editText.setText(userRemark.getName());
		editText.setEnabled(!viewMode);
		llCustomRemarks.addView(editText);
	}
	
	private void addCheckBox(CustomRemark customRemark) {
		CheckBox checkBox = new CheckBox(this);
		checkBox.setText(customRemark.getName());
		checkBox.setTag(customRemark.getID());
		if (!userRemark.getCheckedIDs().equals(""))
		{
			List<String> s = Arrays.asList(userRemark.getCheckedIDsArr());
			checkBox.setChecked(s.contains(String.valueOf(customRemark.getID())));
		}
		checkBox.setEnabled(!viewMode);
		llCustomRemarks.addView(checkBox);
	}

	private void pickUp() {
		String checkedIDs = "";
		for (int i = 0; i < llCustomRemarks.getChildCount(); i++) {
			if (llCustomRemarks.getChildAt(i) instanceof CheckBox) {
				CheckBox checkBox = (CheckBox) llCustomRemarks.getChildAt(i);
				checkedIDs += (checkBox.isChecked() ? ((checkedIDs.equals("") ? "" : ",") + String.valueOf(checkBox.getTag())) : "");
			}
			else if (llCustomRemarks.getChildAt(i) instanceof EditText) {
				EditText editText = (EditText) llCustomRemarks.getChildAt(i);
				userRemark.setName(editText.getText().toString());
			}
		}
		String chid = userRemark.getName().equals("") ? checkedIDs : (checkedIDs.equals("") ? "1" : (checkedIDs + ",1"));
		userRemark.setCheckedIDs(chid);
	}

	private void save() {
		try {
			UserRemarkManager.Instance().save(userRemark);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void btSaveClick(View view) {
		pickUp();
		save();
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		if (bundle == null)
			return;
		
		setResult(NewUserRemarksActivity.RESULT_OK, intent);
		finish();
	}

	@Override
	public void onBackPressed() {
		setResult(NewUserRemarksActivity.RESULT_CANCELED);
		finish();
	}

}
