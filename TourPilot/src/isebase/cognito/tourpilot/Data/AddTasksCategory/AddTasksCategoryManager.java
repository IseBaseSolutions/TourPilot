package isebase.cognito.tourpilot.Data.AddTasksCategory;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;



public class AddTasksCategoryManager extends BaseObjectManager<AddTasksCategory> {

	private static AddTasksCategoryManager instance;

	public static AddTasksCategoryManager Instance() {
		if (instance != null)
			return instance;
		instance = new AddTasksCategoryManager();
		instance.open();
		return instance;
	}

	public AddTasksCategoryManager() {
		
		super(AddTasksCategory.class);
	}

	@Override
	public String getRecTableName() {
//		return dbHelper.ADD_TASKS_CATEGORIES;
		return null;
	}
	
}
