package isebase.cognito.tourpilot.Data.Question.Link;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class Link extends BaseObject {

	public static final String QuestionIDField = "question_id";
	public static final String CategoryIDField = "category_id";
	
	private int questionID;
	private int categoryID;
	
	@MapField(DatabaseField = QuestionIDField)
    public int getQuestionID() {
		return questionID;
	}

	@MapField(DatabaseField = QuestionIDField)
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	@MapField(DatabaseField = CategoryIDField)
	public int getCategoryID() {
		return categoryID;
	}

	@MapField(DatabaseField = CategoryIDField)
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public Link() {
		clear();
	}
	
    public Link(String initString)
    {
    	StringParser parsingString = new StringParser(initString);
    	parsingString.next(";");
        setQuestionID(Integer.parseInt(parsingString.next(";")));
        setCategoryID(Integer.parseInt(parsingString.next("~")));
        setCheckSum(Integer.parseInt(parsingString.next()));
    }

    public String forServer()
    {
    	NCryptor ncryptor = new NCryptor();
    	String strValue = new String("J;");
        strValue += getQuestionID() + "~" + getCategoryID() + ";";
        strValue += ncryptor.LToNcode(getCheckSum());
        return strValue;
    }

}
