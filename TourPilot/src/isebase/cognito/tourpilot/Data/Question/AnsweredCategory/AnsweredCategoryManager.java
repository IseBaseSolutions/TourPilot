package isebase.cognito.tourpilot.Data.Question.AnsweredCategory;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import android.database.sqlite.SQLiteDatabase;

public class AnsweredCategoryManager extends BaseObjectManager<AnsweredCategory> {

	public static final String TableName = "AnsweredCategories";
	
	private static AnsweredCategoryManager instance;

	public static AnsweredCategoryManager Instance() {
		if (instance != null)
			return instance;
		instance = new AnsweredCategoryManager();
		instance.open();
		return instance;
	}
	
	public AnsweredCategoryManager() {
		super(AnsweredCategory.class);
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
