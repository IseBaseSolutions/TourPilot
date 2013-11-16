package isebase.cognito.tourpilot.Data.Tour;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import android.database.sqlite.SQLiteDatabase;

public class TourManager extends BaseObjectManager<Tour> {

	public static final String TableName = "Tours";

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
	
	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
	}

}
