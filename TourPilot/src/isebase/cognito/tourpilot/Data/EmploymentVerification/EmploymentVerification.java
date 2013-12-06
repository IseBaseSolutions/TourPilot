package isebase.cognito.tourpilot.Data.EmploymentVerification;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;

public class EmploymentVerification extends BaseObject {

	public static final String EmploymentIDField = "employment_id";
	public static final String VerificationInfoField = "verification_info";
	
	private long employmentID;
	private String verificationInfo;
	
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
	
	public EmploymentVerification() {
		
	}
	
	public EmploymentVerification(long employmentID, String verificationInfo) {
		this.employmentID = employmentID;
		this.verificationInfo = verificationInfo;
	}
	
	@Override
	public String getDone() {
		return "S;" + getEmploymentID() + ";" + getVerificationInfo();
	}

}
