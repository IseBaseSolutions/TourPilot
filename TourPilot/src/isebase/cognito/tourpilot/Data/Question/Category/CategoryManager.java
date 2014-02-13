package isebase.cognito.tourpilot.Data.Question.Category;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import android.database.sqlite.SQLiteDatabase;

public class CategoryManager extends BaseObjectManager<Category> {

	public static final String TableName = "Categories";
	
	private static CategoryManager instance;

	public static CategoryManager Instance() {
		if (instance != null)
			return instance;
		instance = new CategoryManager();
		instance.open();
		return instance;
	}
	
	public CategoryManager() {
		super(Category.class);
	}

	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
		// TODO Auto-generated method stub		
	}
	
}
