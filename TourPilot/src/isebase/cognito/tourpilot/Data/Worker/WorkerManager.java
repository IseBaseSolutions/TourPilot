package isebase.cognito.tourpilot.Data.Worker;

import android.database.sqlite.SQLiteDatabase;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Tour.Tour;
import isebase.cognito.tourpilot.Data.Tour.TourManager;
import isebase.cognito.tourpilot.DataBase.DataBaseWrapper;

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
	public void onUpgrade(SQLiteDatabase db){
		addColumn(db, Worker.IsUseGPSField, DataBaseWrapper.TYPE_INTEGER);		
		addColumn(db, Worker.ActualDateField, DataBaseWrapper.TYPE_INTEGER);	
	}
		
	@Override
	public void afterLoad(Worker worker){
		worker.tours = TourManager.Instance().load(Tour.WorkerIDField, worker.getId()+"");
	}
	
}
