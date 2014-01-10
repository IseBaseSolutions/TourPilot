package isebase.cognito.tourpilot.Data.EmploymentVerification;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import android.database.sqlite.SQLiteDatabase;

public class EmploymentVerificationManager extends BaseObjectManager<EmploymentVerification> {

	public static final String TableName = "EmploymentVerifications";
	
	private static EmploymentVerificationManager instance;

	public static EmploymentVerificationManager Instance() {
		if (instance != null)
			return instance;
		instance = new EmploymentVerificationManager();
		instance.open();
		return instance;
	}
	
	public EmploymentVerificationManager() {
		super(EmploymentVerification.class);
	}

	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
		
	}
}
