package isebase.cognito.tourpilot.Data.Task;

import java.util.List;
import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTask;
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
	
	public void createTasks(List<AdditionalTask> additionalTasks){
		for(AdditionalTask additionalTask : additionalTasks)
			save(new Task(additionalTask));
	}
	
	public int getFirstSymbol(int emplID){
		String strSQL = "select substr(leistungs,1,1) as val from Tasks where employment_id = "
					+ emplID + " limit 1";
		return getIntValue(strSQL);
	}
}
