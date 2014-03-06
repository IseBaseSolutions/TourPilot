package isebase.cognito.tourpilot.Data.Question.Answer;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Question.Interfaces.IQuestionable;
import isebase.cognito.tourpilot.Data.Question.Question.Question;
import isebase.cognito.tourpilot.DataBase.MapField;

public class Answer extends BaseObject implements IQuestionable {
	
	public static final String PatientIDField = "patient_id";
	public static final String QuestionIDField = "question_id";
	public static final String CatgoryIDField = "category_id";
	public static final String EmploymentIDField = "employment_id";
	public static final String WorkerIDField = "worker_id";
	public static final String PilotTourIDField = "pilot_tour_id";
	public static final String TypeField = "type";
	public static final String AnswerIDField = "answer_id";
	public static final String AnswerKeyField = "answer_id_key";
	public static final String AddInfoField = "add_info";

	private long patientID;
	private int questionID;
	private long categoryID;
	private long emplID;
	private long workerID;
	private long pilotTourID;
	private int type;
	private int answerID;
	private String answerKey;	
	private String addInfo;
	
	@MapField(DatabaseField = PatientIDField)
	public long getPatientID() {
		return patientID;
	}

	@MapField(DatabaseField = PatientIDField)
	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}

	@MapField(DatabaseField = QuestionIDField)
	public int getQuestionID() {
		return questionID;
	}
	
	@MapField(DatabaseField = QuestionIDField)
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	@MapField(DatabaseField = CatgoryIDField)
	public long getCategoryID() {
		return categoryID;
	}

	@MapField(DatabaseField = CatgoryIDField)
	public void setCategoryID(long categoryID) {
		this.categoryID = categoryID;
	}

	@MapField(DatabaseField = EmploymentIDField)
	public long getEmploymentID() {
		return emplID;
	}

	@MapField(DatabaseField = EmploymentIDField)
	public void setEmploymentID(long emplID) {
		this.emplID = emplID;
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

	@MapField(DatabaseField = TypeField)
	public int getType() {
		return type;
	}

	@MapField(DatabaseField = TypeField)
	public void setType(int type) {
		this.type = type;
	}

	@MapField(DatabaseField = AnswerIDField)
	public int getAnswerID() {
		return answerID;
	}

	@MapField(DatabaseField = AnswerIDField)
	public void setAnswerID(int answerID) {
		this.answerID = answerID;
	}

	@MapField(DatabaseField = AnswerKeyField)
	public String getAnswerKey() {
		return answerKey;
	}

	@MapField(DatabaseField = AnswerKeyField)
	public void setAnswerKey(String answerKey) {
		this.answerKey = answerKey;
	}

	@MapField(DatabaseField = AddInfoField)
	public String getAddInfo() {
		return addInfo;
	}

	@MapField(DatabaseField = AddInfoField)
	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}
	
	public Answer() {
		clear();
	}
	
	public Answer(Patient patient, int questionID, String questionName, int answerID, int categoryID, String answerKey, int type) {
		clear();
		setPatientID(patient.getID());
		setWorkerID(Option.Instance().getWorkerID());
		setEmploymentID(Option.Instance().getEmploymentID());
		setPilotTourID(Option.Instance().getPilotTourID());
		setAnswerID(answerID);
		setType(type);
		setQuestionID(questionID);
		setCategoryID(categoryID);
		setName(questionName);
		setAnswerKey(answerKey);
	}
	
	public Answer(Patient patient, Question question, int answerID, int categoryID, int type) {
		clear();
		setPatientID(patient.getID());
		setWorkerID(Option.Instance().getWorkerID());
		setEmploymentID(Option.Instance().getEmploymentID());
		setPilotTourID(Option.Instance().getPilotTourID());
		setAnswerID(answerID);
		setType(type);
		setQuestionID(question.getID());
		setCategoryID(categoryID);
		setName(question.getNameWithKeyWords(patient));
	}
	
	public int getBradenAnswer() {
		return Integer.parseInt(getAnswerKey().split("#")[0]);
	}
	
	public int getBradenLevel() {
		return getAnswerKey().equals("") ? -1 : Integer.parseInt(getAnswerKey().split("%")[1]);
	}
	
	public int[] getBradenCheckedIndexes() {
		String strArray[] = getAnswerKey().split("%")[0].split("#")[1].split("/");
		int intArray[] = new int[strArray.length];
		for (int i = 0; i < strArray.length; i++)
			intArray[i] = Integer.parseInt(strArray[i]);
		return intArray;
	}
	
	@Override
	public String forServer() {
        String strValue = new String("N;");
        strValue += getPatientID() + ";";
        strValue += getWorkerID() + ";";
        strValue += getEmploymentID() + ";";
        strValue += getPilotTourID() + ";";
        strValue += getType() + ";";
        strValue += getQuestionID() + ";";
        strValue += getAnswerID() + ";";
        strValue += getAnswerKey() + ";";
        strValue += getAddInfo() + ";";
        return strValue;
	}
	
	@Override
	protected void clear() {
		super.clear();
		setAddInfo("");
		setAnswerID(-1);
		setAnswerKey("");
	}

	@Override
	public String name() {
		return getName();
	}

}
