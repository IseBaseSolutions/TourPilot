package isebase.cognito.tourpilot.Data.Worker;

import android.database.sqlite.SQLiteDatabase;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

public class WorkerManager extends BaseObjectManager<Worker> {

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

	public static String tableName() {
		return "Workers";
	}
	
	@Override
	public String getRecTableName() {
		return tableName();
	}
	
	@Override
	public void onUpdate(SQLiteDatabase db){
		addColumn(db, Worker.IsUseGPSField, "INTEGER");		
		addColumn(db, Worker.ActualDateField, "INTEGER");	
	}
}
