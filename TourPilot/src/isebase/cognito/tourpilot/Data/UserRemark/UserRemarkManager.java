package isebase.cognito.tourpilot.Data.UserRemark;

import java.util.List;

import isebase.cognito.tourpilot.Activity.UserRemarksActivity;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import android.database.sqlite.SQLiteDatabase;

public class UserRemarkManager extends BaseObjectManager<UserRemark> {

	public static final String TableName = "UserRemarks";
	
	public UserRemarkManager(Class<UserRemark> entityClass) {
		super(entityClass);
	}
	private static UserRemarkManager instance;

	public static UserRemarkManager Instance() {
		if (instance != null)
			return instance;
		instance = new UserRemarkManager();
		instance.open();
		return instance;
	}
		
	public UserRemarkManager() {
		super(UserRemark.class);
	}
	
	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {

	}
	
	public List<UserRemark> loadByEmploymentID(int employmentID) {
		String strSQL = String.format("SELECT * FROM %1$s " +
				"WHERE %2$s = %3$d"
				, UserRemarkManager.TableName
				, Employment.PilotTourIDField
				, employmentID);
		return load(strSQL);
	}
	
}
