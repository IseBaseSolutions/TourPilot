package isebase.cognito.tourpilot.Data.Relative;

import android.database.sqlite.SQLiteDatabase;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

public class RelativeManager extends BaseObjectManager<Relative>{

	private static RelativeManager instance;

	public static RelativeManager Instance() {
		if (instance != null)
			return instance;
		instance = new RelativeManager();
		instance.open();
		return instance;
	}
	
	public RelativeManager() {
		super(Relative.class);
	}

	@Override
	public String getRecTableName() {
		return "Relatives";
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
				
	}

}
