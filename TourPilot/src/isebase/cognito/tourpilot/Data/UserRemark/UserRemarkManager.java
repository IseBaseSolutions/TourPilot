package isebase.cognito.tourpilot.Data.UserRemark;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import android.database.sqlite.SQLiteDatabase;

public class UserRemarkManager extends BaseObjectManager<UserRemark>{

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
	
}
