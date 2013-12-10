package isebase.cognito.tourpilot.Data.EmploymentVerification;

import java.util.Date;

import isebase.cognito.tourpilot.Connection.SentObjectVerification;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;

public class EmploymentVerification extends BaseObject {

	public static final String EmploymentIDField = "employment_id";
	public static final String WorkerIDField = "worker_id";
	public static final String PatientIDField = "patient_id";
	public static final String DateBeginField = "date_begin";
	public static final String DateEndField = "date_end";
	public static final String DoneTasksIDsField = "done_tasks_ids";
	public static final String UnDoneTasksIDsField = "undone_tasks_ids";
	public static final String UserRemarksMarksField = "user_remarks_marks";

	private long employmentID;
	private long workerID;
	private long patientID;
	private Date dateBegin;
	private Date dateEnd;
	private String doneTasksIDs;
	private String undoneTasksIDs;
	private String userRemarksMarks;

	@MapField(DatabaseField = EmploymentIDField)
	public long getEmploymentID() {
		return employmentID;
	}

	@MapField(DatabaseField = EmploymentIDField)
	public void setEmploymentID(long employmentID) {
		this.employmentID = employmentID;
	}

	@MapField(DatabaseField = WorkerIDField)
	public long getWorkerID() {
		return workerID;
	}

	@MapField(DatabaseField = WorkerIDField)
	public void setWorkerID(long workerID) {
		this.workerID = workerID;
	}

	@MapField(DatabaseField = PatientIDField)
	public long getPatientID() {
		return patientID;
	}

	@MapField(DatabaseField = PatientIDField)
	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}

	@MapField(DatabaseField = DateBeginField)
	public long getDateBegin() {
		return dateBegin.getTime();
	}

	@MapField(DatabaseField = DateBeginField)
	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}

	@MapField(DatabaseField = DateEndField)
	public long getDateEnd() {
		return dateEnd.getTime();
	}

	@MapField(DatabaseField = DateBeginField)
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	@MapField(DatabaseField = DoneTasksIDsField)
	public String getDoneTasksIDs() {
		return doneTasksIDs;
	}

	@MapField(DatabaseField = DoneTasksIDsField)
	public void setDoneTasksIDs(String doneTasksIDs) {
		this.doneTasksIDs = doneTasksIDs;
	}

	@MapField(DatabaseField = DoneTasksIDsField)
	public String getUnDoneTasksIDs() {
		return undoneTasksIDs;
	}

	@MapField(DatabaseField = DoneTasksIDsField)
	public void setUnDoneTasksIDs(String undoneTasksIDs) {
		this.undoneTasksIDs = undoneTasksIDs;
	}

	@MapField(DatabaseField = UserRemarksMarksField)
	public String getUserRemarksMarks() {
		return userRemarksMarks;
	}

	@MapField(DatabaseField = UserRemarksMarksField)
	public void setUserRemarksMarks(String userRemarksMarks) {
		this.userRemarksMarks = userRemarksMarks;
	}

	public EmploymentVerification(long workerID, long patientID,
			Date dateBegin, Date dateEnd,
			String doneTasksIDs, String undoneTasksIDs, String userRemarksMarks) {
		setWorkerID(workerID);
		setPatientID(patientID);
		setDateBegin(dateBegin);
		setDateEnd(dateEnd);
		setDoneTasksIDs(doneTasksIDs);
		setUnDoneTasksIDs(undoneTasksIDs);
		setUserRemarksMarks(userRemarksMarks);
	}

	public EmploymentVerification() {

	}

	@Override
	public String getDone() {
<<<<<<< HEAD
		SentObjectVerification.Instance().sentEmploymentVerifications.add(this);
		return "S;" + getEmploymentID() + ";" + getVerificationInfo();
=======
		String strValue = "";
		strValue += "S;";
		strValue += workerID + ";";
		strValue += patientID + ";";
		strValue += dateBegin.getTime() + ";";
		strValue += dateEnd.getTime() + ";";
		strValue += doneTasksIDs + ";";
		strValue += undoneTasksIDs + ";";
		strValue += userRemarksMarks;
		setWasSent(true);
		return strValue;

>>>>>>> refs/heads/VladimirsBranch2
	}

}
