package isebase.cognito.tourpilot.Data.EmploymentInterval;

import java.util.Date;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;

public class EmploymentInterval extends BaseObject {

	public static final String EmploymentIDField = "employment_id";
	public static final String StartTimeField = "start_time";
	public static final String StopTimeField = "stop_time";

	private int employmentID;
	private Date startTime;
	private Date stopTime;
	
	public EmploymentInterval() {
		
	}
	
	public EmploymentInterval(int employmentID, Date startTime, Date stopTime) {
		this.employmentID = employmentID;
		this.startTime = startTime;
		this.stopTime = stopTime;
	}
	
	@MapField(DatabaseField = EmploymentIDField)
	public int getEmploymentID() {
		return employmentID;
	}
	
	@MapField(DatabaseField = EmploymentIDField)
	public void setEmploymentID(int employmentID) {
		this.employmentID = employmentID;
	}

	@MapField(DatabaseField = StartTimeField)
	public Date getStartTime() {
		return startTime;
	}

	@MapField(DatabaseField = StartTimeField)
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@MapField(DatabaseField = StopTimeField)
	public Date getStopTime() {
		return stopTime;
	}

	@MapField(DatabaseField = StopTimeField)
	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

}
