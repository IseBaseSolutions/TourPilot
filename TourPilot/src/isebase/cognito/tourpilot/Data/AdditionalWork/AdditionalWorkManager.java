package isebase.cognito.tourpilot.Data.AdditionalWork;

import android.database.sqlite.SQLiteDatabase;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

public class AdditionalWorkManager extends BaseObjectManager<AdditionalWork>{

	private static AdditionalWorkManager instance;

	public static AdditionalWorkManager Instance() {
		if (instance != null)
			return instance;
		instance = new AdditionalWorkManager();
		instance.open();
		return instance;
	}
	
	public AdditionalWorkManager() {
		super(AdditionalWork.class);
	}

	@Override
	public String getRecTableName() {		
		return "AdditionalWorks";
	}

	@Override
	public void onUpdate(SQLiteDatabase db) {
			
	}

}
