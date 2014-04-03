package isebase.cognito.tourpilot.Data.Question.Answer;

import isebase.cognito.tourpilot.Connection.SentObjectVerification;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Question.Question.Question;
import isebase.cognito.tourpilot.DataBase.HelperFactory;
import isebase.cognito.tourpilot.NewData.NewBaseObject.NewBaseObject;
import isebase.cognito.tourpilot.NewData.NewEmployment.NewEmployment;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

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
	
    public String getDone()
    {    	
    	String strEmpls = "";
    	List<Answer> answers;
    	List<Employment> employments = EmploymentManager.Instance().load(BaseObject.WasSentField, "0 AND is_done = 1");
    	for (Employment employment : employments) {
    		answers = loadByEmploymentID(employment.getID());
        	for (Answer answer : answers) {
        		strEmpls += answer.forServer() + "\0";
        		SentObjectVerification.Instance().sentAnswers.add(answer);
        	}
    	}
    	return strEmpls;
    }
	
	public List<Answer> loadByCategoryID(int categoryID) {
		String strSQL = String.format("SELECT * FROM %1$s " +
				" WHERE employment_id = %2$d AND category_id = %3$d "
				, AnswerManager.Instance().getRecTableName()
				, Option.Instance().getEmploymentID()
				, categoryID);
		return load(strSQL);
	}
	
	public List<Answer> loadByEmploymentID(int employmentID) {
		String strSQL = String.format("SELECT * FROM %1$s " +
				" WHERE employment_id = %2$d"
				, AnswerManager.Instance().getRecTableName()
				, employmentID);
		return load(strSQL);
	}
	
	public Answer loadByQuestionIDAndType(int questionID, int type) {
		String strSQL = String.format("SELECT * FROM %1$s " +
				" WHERE question_id = %2$d AND type = %3$d AND employment_id = %4$d" 
				, AnswerManager.Instance().getRecTableName()
				, questionID
				, type
				, Option.Instance().getEmploymentID());
		List<Answer> answers = load(strSQL);
		if (answers.size() > 0)
			return answers.get(0);
		return null;
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
