package isebase.cognito.tourpilot.Data.Question.Answer;

import java.util.ArrayList;
import java.util.List;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Question.Category.CategoryManager;
import isebase.cognito.tourpilot.Data.Question.Link.LinkManager;
import isebase.cognito.tourpilot.Data.Question.Question.Question;
import isebase.cognito.tourpilot.Data.Question.Question.QuestionManager;
import isebase.cognito.tourpilot.Data.Question.QuestionSetting.QuestionSetting;
import isebase.cognito.tourpilot.Data.Question.QuestionSetting.QuestionSettingManager;
import android.database.sqlite.SQLiteDatabase;

public class AnswerManager extends BaseObjectManager<Answer> {

	public static final String TableName = "Answers";
	
	private static AnswerManager instance;

	public static AnswerManager Instance() {
		if (instance != null)
			return instance;
		instance = new AnswerManager();
		instance.open();
		return instance;
	}
	
	public AnswerManager() {
		super(Answer.class);
	}

	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}
	
	public List<Answer> loadByCategory(int categoryID) {
		String strSQL = String.format("SELECT * FROM %1$s " +
				" WHERE employment_id = %2$d AND category_id = %3$d "
				, AnswerManager.Instance().getRecTableName()
				, Option.Instance().getEmploymentID()
				, categoryID);
		return load(strSQL);
	}
	
	public boolean isSubQuestionAnswered(Question subQuestion) {
		String strSQL = String.format("SELECT * FROM %1$s " +
				" WHERE employment_id = %2$d AND question_id = %3$d "
				, AnswerManager.Instance().getRecTableName()
				, Option.Instance().getEmploymentID()
				, subQuestion.getID());
		return load(strSQL).size() > 0;
	}


}
