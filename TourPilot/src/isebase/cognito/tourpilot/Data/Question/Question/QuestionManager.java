package isebase.cognito.tourpilot.Data.Question.Question;

import java.util.ArrayList;
import java.util.List;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Data.Question.Category.CategoryManager;
import isebase.cognito.tourpilot.Data.Question.Link.Link;
import isebase.cognito.tourpilot.Data.Question.Link.LinkManager;
import isebase.cognito.tourpilot.Data.Question.QuestionSetting.QuestionSetting;
import isebase.cognito.tourpilot.Data.Question.QuestionSetting.QuestionSettingManager;
import android.database.sqlite.SQLiteDatabase;

public class QuestionManager extends BaseObjectManager<Question> {

	public static final String TableName = "Questions";
	
	private static QuestionManager instance;

	public static QuestionManager Instance() {
		if (instance != null)
			return instance;
		instance = new QuestionManager();
		instance.open();
		return instance;
	}
	
	public QuestionManager() {
		super(Question.class);
	}

	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
		// TODO Auto-generated method stub		
	}
	
	public List<Question> loadActualsByCategory(int categoryID) {
		QuestionSetting questionSettings = QuestionSettingManager.Instance().load(Option.Instance().getEmploymentID());
		if (questionSettings == null)
			return new ArrayList<Question>();
		String strSQL = String.format("SELECT t3.* " +
				" FROM %1$s AS t1 " + 
				" INNER JOIN %2$s as t2 ON t1._id = t2.category_id " +
				" INNER JOIN %3$s t3 ON t2.question_id = t3._id " +
				" INNER JOIN QuestionSettings t4 ON t4._id = %4$d " +
				" LEFT JOIN Answers t5 ON t3._id = t5.question_id " +  
				" WHERE t1._id = %5$s AND t5._id IS NULL AND t3._id in (%6$s) GROUP BY t3._id "
				, CategoryManager.TableName
				, LinkManager.TableName
				, QuestionManager.TableName
				, Option.Instance().getEmploymentID()
				, categoryID
				, questionSettings.getQuestionIDsString());
		return load(strSQL);
	}
	
}
