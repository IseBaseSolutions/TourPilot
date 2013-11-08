package isebase.cognito.tourpilot.Data.Tour;

import android.database.sqlite.SQLiteDatabase;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

public class TourManager extends BaseObjectManager<Tour> {

	private static TourManager instance;

	public static TourManager Instance() {
		if (instance != null)
			return instance;
		instance = new TourManager();
		instance.open();
		return instance;
	}

	public TourManager() {
		super(Tour.class);
	}

	public static String tableName() {
		return "Tours";
	}
	
	@Override
	public String getRecTableName() {
		return tableName();
	}

	@Override
	public void onUpdate(SQLiteDatabase db) {
				
	}

}
