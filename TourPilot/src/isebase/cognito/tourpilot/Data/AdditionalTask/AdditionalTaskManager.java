package isebase.cognito.tourpilot.Data.AdditionalTask;

import android.database.sqlite.SQLiteDatabase;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

public class AdditionalTaskManager extends BaseObjectManager<AdditionalTask>{
	
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
		return "AdditionalTasks";
	}

	@Override
	public void onUpdate(SQLiteDatabase db) {
				
	}

}
