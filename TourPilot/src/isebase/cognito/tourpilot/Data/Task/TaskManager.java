package isebase.cognito.tourpilot.Data.Task;

import java.util.List;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
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

}
