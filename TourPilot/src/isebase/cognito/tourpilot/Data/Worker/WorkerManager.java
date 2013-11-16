package isebase.cognito.tourpilot.Data.Worker;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Tour.TourManager;
import android.database.sqlite.SQLiteDatabase;

public class WorkerManager extends BaseObjectManager<Worker> {

	public static final String TableName = "Workers";

	private static WorkerManager instance;

	public static WorkerManager Instance() {
		if (instance != null)
			return instance;
		instance = new WorkerManager();
		instance.open();
		return instance;
	}

	public WorkerManager() {
		super(Worker.class);
	}

	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
	}

	@Override
	public void afterLoad(Worker worker) {
		worker.tours = TourManager.Instance().load();
	}

}
