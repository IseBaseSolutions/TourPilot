package isebase.cognito.tourpilot.Data.Tour;

import android.database.sqlite.SQLiteDatabase;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

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
		addColumn(db, Tour.WorkerIDField, "INTEGER");
	}

}
