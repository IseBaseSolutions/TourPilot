package isebase.cognito.tourpilot.Data.Task;

import java.util.List;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import android.database.sqlite.SQLiteDatabase;

public class TaskManager extends BaseObjectManager<Task> {

	public static String TableName = "Tasks";

	private static TaskManager instance;

	public static TaskManager Instance() {
		if (instance != null)
			return instance;
		instance = new TaskManager();
		instance.open();
		return instance;
	}

	public TaskManager() {
		super(Task.class);
	}

	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {

	}
	
	public List<Task> loadByPatientID(int patientID) {
		return load(String.format(" SELECT t2.* FROM %1$s AS t1 " +
				" INNER JOIN %2$s AS t2 ON t1.%3$s = t2.%4$s " +
				" WHERE t1.%3$s = %5$d ORDER BY t2.%3$s", 
				PatientManager.TableName,
				TaskManager.TableName,
				BaseObject.IDField,
				Task.PatientIDField,
				patientID
				));
	}

}
