package isebase.cognito.tourpilot.Data.Question.Category;

import java.util.List;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Question.Answer.Answer;
import isebase.cognito.tourpilot.Data.Question.Answer.AnswerManager;
import isebase.cognito.tourpilot.Data.Question.QuestionSetting.QuestionSetting;
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
				" WHERE name LIKE '%2$s' "
				, CategoryManager.Instance().getRecTableName()
				, name);
		List<Category> list = load(strSQL);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}
	
	public List<Category> loadByQuestionSettings(QuestionSetting questionSettings) {
		String strSQL = String.format("SELECT * FROM %1$s " +
				" WHERE _id IN (%2$s) OR _id IN (%3$s)"
				, CategoryManager.Instance().getRecTableName()
				, questionSettings.getCategoryIDsString()
				, questionSettings.getExtraCategoryIDsString());
		return load(strSQL);
	}
	
	public List<Category> loadExtraCategoriesByQuestionSettings(QuestionSetting questionSettings) {
		String strSQL = String.format("SELECT * FROM %1$s " +
				" WHERE _id NOT IN (%2$s) AND _id NOT IN (%3$s)"
				, CategoryManager.Instance().getRecTableName()
				, questionSettings.getCategoryIDsString()
				, questionSettings.getExtraCategoryIDsString());
		return load(strSQL);
	}
	
}
