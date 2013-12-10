package isebase.cognito.tourpilot.Data.EmploymentVerification;

import java.util.Date;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;

public class EmploymentVerification extends BaseObject {

	public static final String EmploymentIDField = "employment_id";
	public static final String VerificationInfoField = "verification_info";
	
	private long employmentID;
	private String verificationInfo;
	
	private long workerID;
	private long patientID;
	private String dateBegin;
	private String dateEnd;
	private String additionalWorksIDs;
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

	@MapField(DatabaseField = VerificationInfoField)
	public String getVerificationInfo() {
		return verificationInfo;
	}

	@MapField(DatabaseField = VerificationInfoField)
	public void setVerificationInfo(String verificationInfo) {
		this.verificationInfo = verificationInfo;
	}
	
	public EmploymentVerification(long workerID, long patientID, String dateBegin, String dateEnd, String additionalWorksIDs, String doneTasksIDs, String undoneTasksIDs, String userRemarksMarks) {
		this.workerID = workerID;
		this.patientID = patientID;
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
		this.additionalWorksIDs = additionalWorksIDs;
		this.doneTasksIDs = doneTasksIDs;
		this.undoneTasksIDs = undoneTasksIDs;
		this.userRemarksMarks = userRemarksMarks;
	}
	
	public EmploymentVerification(long employmentID, String verificationInfo) {
		this.employmentID = employmentID;
		this.verificationInfo = verificationInfo;
	}
	
	public EmploymentVerification() {
		
	}
	@Override
	public String getDone() {
		return "S;" + getEmploymentID() + ";" + getVerificationInfo();
	}

}
