package isebase.cognito.tourpilot.Data.Question.AnsweredCategory;

import java.util.List;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Question.Category.Category;
import isebase.cognito.tourpilot.Data.Question.Category.CategoryManager;
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

	public List<AnsweredCategory> LoadByEmploymentID(int employmentID) {
		return load(String.format("SELECT * FROM %s WHERE employment_id = %d", TableName, employmentID));
	}
	
	public AnsweredCategory loadByCategoryID(int categoryID) {
		String strSQL = String.format("SELECT * FROM %1$s " +
				" WHERE category_id = '%2$d' && employment_id = '%3$d'"
				, AnsweredCategoryManager.Instance().getRecTableName()
				, categoryID
				, Option.Instance().getEmploymentID());
		List<AnsweredCategory> list = load(strSQL);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db) {
		// TODO Auto-generated method stub		
	}
	
}
