package isebase.cognito.tourpilot.Data.CustomRemark;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import android.database.sqlite.SQLiteDatabase;

public class CustomRemarkManager extends BaseObjectManager<CustomRemark> {

	public static final String TableName = "CustomRemarks";
	
	public CustomRemarkManager(Class<CustomRemark> entityClass) {
		super(entityClass);
	}
	private static CustomRemarkManager instance;

	public static CustomRemarkManager Instance() {
		if (instance != null)
			return instance;
		instance = new CustomRemarkManager();
		instance.open();
		return instance;
	}
		
	public CustomRemarkManager() {
		super(CustomRemark.class);
	}
	
	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
		
	}
	
}
