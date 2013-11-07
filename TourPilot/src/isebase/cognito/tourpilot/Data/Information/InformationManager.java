package isebase.cognito.tourpilot.Data.Information;

import android.database.sqlite.SQLiteDatabase;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

public class InformationManager extends BaseObjectManager<Information>{
	
	private static InformationManager instance;

	public static InformationManager Instance() {
		if (instance != null)
			return instance;
		instance = new InformationManager();
		instance.open();
		return instance;
	}
	
	public InformationManager() {
		super(Information.class);
	}

	@Override
	public String getRecTableName() {
		return "Informations";
	}

	@Override
	public void onUpdate(SQLiteDatabase db) {
			
	}

}
