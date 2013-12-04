package isebase.cognito.tourpilot.Data.Information;

import isebase.cognito.tourpilot.Connection.ServerCommandParser;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.DateUtils;
import isebase.cognito.tourpilot.Utils.StringParser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Information extends BaseObject {

	public static final String EmploymentIDField = "employment_id";
	public static final String PatientIDField = "patient_id";
	public static final String FromDateField = "from_date";
	public static final String TilldateField = "till_date";
	public static final String ReadTimeField = "read_time";
	public static final String IsFromServerField = "is_from_server";

	private long employmentID;
	private int patientID;
	private Date fromDate;
	private Date tillDate;
	private Date readTime;
	private boolean isFromServer;
	
	@MapField(DatabaseField = EmploymentIDField)
	public long getEmploymentID() {
		return employmentID;
	}

	@MapField(DatabaseField = EmploymentIDField)
	public void setEmploymentID(long employmentID) {
		this.employmentID = employmentID;
	}

	@MapField(DatabaseField = PatientIDField)
	public int getPatientID() {
		return patientID;
	}

	@MapField(DatabaseField = PatientIDField)
	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}

	@MapField(DatabaseField = FromDateField)
	public Date getFromDate() {
		return fromDate;
	}

	@MapField(DatabaseField = FromDateField)
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	@MapField(DatabaseField = TilldateField)
	public Date getTillDate() {
		return tillDate;
	}

	@MapField(DatabaseField = TilldateField)
	public void setTillDate(Date tillDate) {
		this.tillDate = tillDate;
	}

	@MapField(DatabaseField = ReadTimeField)
	public Date getReadTime() {
		return readTime;
	}

	@MapField(DatabaseField = ReadTimeField)
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	@MapField(DatabaseField = IsFromServerField)
	public boolean getIsFromServer() {
		return isFromServer;
	}

	@MapField(DatabaseField = IsFromServerField)
	public void setIsFromServer(boolean isFromServer) {
		this.isFromServer = isFromServer;
	}

	public Information(){
		clear();
	}
	
	public Information(String initString) {
		StringParser parsingString = new StringParser(initString);
		parsingString.next(";");
		setIsFromServer(true);
		setID(Integer.parseInt(parsingString.next(";")));
		setEmploymentID(Long.parseLong(parsingString.next(";")));
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmm");
		try {
			setFromDate(format.parse(parsingString.next(";") + "0000"));
			setTillDate(format.parse(parsingString.next(";") + "2359"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		setName(parsingString.next("~"));
		setCheckSum(Long.parseLong(parsingString.next()));
	}

	@Override
	public String forServer() {
		String strValue = new String(ServerCommandParser.INFORMATION + ";");
		strValue += String.format("%d;%d", getID(), getEmploymentID()) + ";";
		strValue += getCheckSum();
		return strValue;
	}

	@Override
	protected void clear() {
		super.clear();
		setPatientID(EMPTY_ID);
		setFromDate(DateUtils.EmptyDate);
		setTillDate(DateUtils.EmptyDate);
		setReadTime(DateUtils.EmptyDate);
		setIsFromServer(false);
		
	}
	
	public boolean isActualInfo(Date date) {
		return date.getTime() >= getFromDate().getTime() 
				&& date.getTime() <= getTillDate().getTime();		
	}
	
}
