package isebase.cognito.tourpilot.Data.EmploymentInterval;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import android.database.sqlite.SQLiteDatabase;

public class EmploymentIntervalManager extends BaseObjectManager<EmploymentInterval> {

	public static final String TableName = "EmploymentIntervals";
	
	private static EmploymentIntervalManager instance;

	public static EmploymentIntervalManager Instance() {
		if (instance != null)
			return instance;
		instance = new EmploymentIntervalManager();
		instance.open();
		return instance;
	}
	
	public EmploymentIntervalManager() {
		super(EmploymentInterval.class);
	}

	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
		
	}


}
