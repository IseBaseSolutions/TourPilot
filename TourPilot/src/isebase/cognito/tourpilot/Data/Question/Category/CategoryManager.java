package isebase.cognito.tourpilot.Data.Question.Category;

import java.util.List;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Question.Answer.Answer;
import isebase.cognito.tourpilot.Data.Question.Answer.AnswerManager;
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
	
	public Category loadByCategoryName(String name) {
		String strSQL = String.format("SELECT * FROM %1$s " +
				" WHERE name like '%2$s' "
				, CategoryManager.Instance().getRecTableName()
				, name);
		List<Category> list = load(strSQL);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}
	
}
