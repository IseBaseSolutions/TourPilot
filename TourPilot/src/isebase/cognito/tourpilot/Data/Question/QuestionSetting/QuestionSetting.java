package isebase.cognito.tourpilot.Data.Question.QuestionSetting;

import java.text.ParseException;
import java.util.Date;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.DateUtils;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class QuestionSetting extends BaseObject {

	public static final String PatientIDField = "patient_id";
	public static final String WorkerIDField = "worker_id";
	public static final String PilotTourIDField = "pilot_tour_id";
	public static final String DateField = "date";
	public static final String QuestionIDsStringField = "question_ids_string";
	public static final String CategoryIDsStringField = "category_ids_string";
	
	private long patientID;
	private long workerID;
	private long pilotTourID;
	private Date date;
	private String questionIDsString;
	private String categoryIDsString;
	
	@MapField(DatabaseField = PatientIDField)
	public long getPatientID() {
		return patientID;
	}
	
	@MapField(DatabaseField = PatientIDField)
	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}
	
	@MapField(DatabaseField = WorkerIDField)
	public long getWorkerID() {
		return workerID;
	}
	
	@MapField(DatabaseField = WorkerIDField)
	public void setWorkerID(long workerID) {
		this.workerID = workerID;
	}
	
	@MapField(DatabaseField = PilotTourIDField)
	public long getPilotTourID() {
		return pilotTourID;
	}
	
	@MapField(DatabaseField = PilotTourIDField)
	public void setPilotTourID(long pilotTourID) {
		this.pilotTourID = pilotTourID;
	}
	
	@MapField(DatabaseField = DateField)
	public Date getDate() {
		return date;
	}
	
	@MapField(DatabaseField = DateField)
	public void setDate(Date date) {
		this.date = date;
	}
	
	@MapField(DatabaseField = QuestionIDsStringField)
	public String getQuestionIDsString() {
		return questionIDsString;
	}
	
	@MapField(DatabaseField = QuestionIDsStringField)
	public void setQuestionIDsString(String questionIDsString) {
		this.questionIDsString = questionIDsString;
	}
	
	@MapField(DatabaseField = CategoryIDsStringField)
	public String getCategoryIDsString() {
		return categoryIDsString;
	}
	
	@MapField(DatabaseField = CategoryIDsStringField)
	public void setCategoryIDsString(String categoryIDsString) {
		this.categoryIDsString = categoryIDsString;
	}
	
	public QuestionSetting() {
		clear();
	}
	
    public QuestionSetting(String initString)
    {
    	StringParser parsingString = new StringParser(initString);
    	parsingString.next(";");
        setPatientID(Integer.parseInt(parsingString.next(";")));
        setWorkerID(Integer.parseInt(parsingString.next(";")));
        setID(Integer.parseInt(parsingString.next(";"))); //employmentID
        setPilotTourID(Integer.parseInt(parsingString.next(";")));
        try {
			setDate(DateUtils.DateFormat.parse(parsingString.next(";")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        setQuestionIDsString(parsingString.next(";"));
        setCategoryIDsString(parsingString.next("~"));
       	setCheckSum(Long.parseLong(parsingString.next()));
    }

    public String forServer()
    {
    	NCryptor ncryptor = new NCryptor();
    	String strValue = new String("X;");
        strValue += ncryptor.LToNcode(getID()) + ";";
        strValue += ncryptor.LToNcode(getCheckSum());
        return strValue;
    }

}
