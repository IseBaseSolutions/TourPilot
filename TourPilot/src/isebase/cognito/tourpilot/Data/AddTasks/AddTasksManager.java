package isebase.cognito.tourpilot.Data.AddTasks;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

public class AddTasksManager  extends BaseObjectManager<AddTasks> {

	private static AddTasksManager instance;
	
	public static AddTasksManager Instance(){
		if (instance != null)
			return instance;
		instance = new AddTasksManager();
		instance.open();
		return instance;
	}
	public AddTasksManager() {
		super(AddTasks.class);
	}
	
	@Override
	public String getRecTableName() {
//		return dbHelper.ADD_TASKS;
		return null;
	}

}
