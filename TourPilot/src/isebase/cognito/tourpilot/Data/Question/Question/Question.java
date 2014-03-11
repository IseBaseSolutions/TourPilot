package isebase.cognito.tourpilot.Data.Question.Question;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Question.Interfaces.IQuestionable;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class Question extends BaseObject implements IQuestionable {

	public static final String OwnerIDsStringField = "owner_ids_string";
	public static final String KeyAnswerField = "key_answer";
	
	private String ownerIDsString;
	private int keyAnswer;
	
	@MapField(DatabaseField = OwnerIDsStringField)
	public String getOwnerIDsString() {
		return ownerIDsString;
	}

	@MapField(DatabaseField = OwnerIDsStringField)
	public void setOwnerIDsString(String ownerIDsString) {
		this.ownerIDsString = ownerIDsString;
	}
	
	public String[] getOwnerIDs() {
		return ownerIDsString.split(",");
	}

	@MapField(DatabaseField = KeyAnswerField)
	public int getKeyAnswer() {
		return keyAnswer;
	}

	@MapField(DatabaseField = KeyAnswerField)
	public void setKeyAnswer(int keyAnswer) {
		this.keyAnswer = keyAnswer;
	}
	
	public boolean isSubQuestion() {
		return !getOwnerIDsString().equals("-1");
	}

	public Question() {
		clear();
	}
	
    public Question(String initString)
    {
    	StringParser parsingString = new StringParser(initString);
    	parsingString.next(";");
        setID(Integer.parseInt(parsingString.next(";")));
        setName(parsingString.next(";"));
        setOwnerIDsString(parsingString.next(";"));
        setKeyAnswer(Integer.parseInt(parsingString.next("~")));
        setCheckSum(Long.parseLong(parsingString.next()));
    }
    
    public String forServer()
    {
    	NCryptor ncryptor = new NCryptor();
    	String strValue = new String("Q;");
        strValue += ncryptor.LToNcode(getID()) + ";";
        strValue += ncryptor.LToNcode(getCheckSum());
        return strValue;
    }
    
    public String getNameWithKeyWords(Patient patient) {
    	String name = "";
    	String[] arr = getName().split(" ");
    	for (int i = 0; i < arr.length; i++)
    	{
    		name += (name.equals("") ? "" : " ");
    		if (arr[i].contains("#"))
    		{
    			if (arr[i].contains("@"))
    				name += (patient.getSex().contains("Herr") ? arr[i].split("@")[0].replace("#", "") : arr[i].split("@")[1].replace("#", ""));
    			else
    				name += (arr[i].contains("pat.zuname") ? patient.getSurname() : patient.getName().split(" ")[0]);
    		}
    		else
    			name += arr[i];   
    	}
    	return name;
    }

	@Override
	public String name() {
		return getName();
	}


}
