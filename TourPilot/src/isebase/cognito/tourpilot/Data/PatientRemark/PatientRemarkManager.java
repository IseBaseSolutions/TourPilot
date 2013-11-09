package isebase.cognito.tourpilot.Data.PatientRemark;

import android.database.sqlite.SQLiteDatabase;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

public class PatientRemarkManager extends BaseObjectManager<PatientRemark>{
	
	public static final String TableName = "PatientRemarks";
	
	private static PatientRemarkManager instance;

	public static PatientRemarkManager Instance() {
		if (instance != null)
			return instance;	
		instance = new PatientRemarkManager();
		instance.open();
		return instance;
	}
	
	public PatientRemarkManager() {
		super(PatientRemark.class);
	}

	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
			
	}

}
