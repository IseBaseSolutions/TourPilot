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

	public void updateWasSent(){
		String strSQL = "UPDATE WayPoints SET was_sent = 0 WHERE was_sent = 1";
		execSQL(strSQL);
	}
	
	public void updateTourID(){
		String strSQL = "UPDATE WayPoints SET pilot_tour_id = 390753 WHERE pilot_tour_id = 390701";
		execSQL(strSQL);
	}

}
