package isebase.cognito.tourpilot.Data.AdditionalTask;

import android.database.sqlite.SQLiteDatabase;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

public class AdditionalTaskManager extends BaseObjectManager<AdditionalTask>{
	
	public static final String TableName = "AdditionalTasks";
	
	private static AdditionalTaskManager instance;

	public static AdditionalTaskManager Instance() {
		if (instance != null)
			return instance;
		instance = new AdditionalTaskManager();
		instance.open();
		return instance;
	}
	
	public AdditionalTaskManager() {
		super(AdditionalTask.class);
	}

	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
				
	}

}
