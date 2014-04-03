package isebase.cognito.tourpilot.Data.WayPoint;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import android.database.sqlite.SQLiteDatabase;

public class WayPointManager extends BaseObjectManager<WayPoint>{

	public static final String TableName = "WayPoints";
	
	private static WayPointManager instance;

	public static WayPointManager Instance() {
		if (instance != null)
			return instance;
		instance = new WayPointManager();
		instance.open();
		return instance;
	}
	
	public WayPointManager() {
		super(WayPoint.class);
	}

	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
		
	}


}
