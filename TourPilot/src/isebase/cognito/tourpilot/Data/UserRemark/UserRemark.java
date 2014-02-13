package isebase.cognito.tourpilot.Data.UserRemark;

import java.util.Date;

import isebase.cognito.tourpilot.Connection.SentObjectVerification;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.CustomRemark.CustomRemark;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.DateUtils;
import isebase.cognito.tourpilot.Utils.StringParser;

public class UserRemark extends BaseObject {

	public static final String PatientIDField = "patient_id";
	public static final String WorkerIDField = "worker_id";
	public static final String DateField = "date";
	public static final String CheckedIDsField = "checked_ids";
	public static final String CheckboxField = "checkboxes";

	private int patientID;
	private int workerID;
	
	private Date date;
	private int checkboxes;
	private String checkedIDs;
	
	@MapField(DatabaseField = PatientIDField)
	public int getPatientID() {
		return patientID;
	}
	@MapField(DatabaseField = PatientIDField)
	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}
	
	@MapField(DatabaseField = WorkerIDField)
	public int getWorkerID() {
		return workerID;
	}
	
	@MapField(DatabaseField = WorkerIDField)
	public void setWorkerID(int workerID) {
		this.workerID = workerID;
	}

	@MapField(DatabaseField = DateField)
	public Date getDate() {
		return date;
	}
	@MapField(DatabaseField = DateField)
	public void setDate(Date date) {
		this.date = date;
	}

	@MapField(DatabaseField = CheckboxField)
	public int getCheckboxes() {
		return checkboxes;
	}
	@MapField(DatabaseField = CheckboxField)
	public void setCheckboxes(int checkboxes) {
		this.checkboxes = checkboxes;
	}
	
	@MapField(DatabaseField = CheckedIDsField)
	public String getCheckedIDs() {
		return checkedIDs;
	}
	
	@MapField(DatabaseField = CheckedIDsField)
	public void setCheckedIDs(String checkedIDs) {
		this.checkedIDs = checkedIDs;
	}
	
	public String[] getCheckedIDsArr() {
		return getCheckedIDs().split(",");
	}
	
	public UserRemark(){
		clear();
	}
	
    public UserRemark(int EmploymentID, int workerID,  int patientID
    		, boolean chkContact, boolean chkMed, boolean chkVisit
            , boolean chkOther, String strRemark)
    {
        setID(EmploymentID);
        setWorkerID(workerID);
        setPatientID(patientID);
        checkboxes = 0;
        if (chkContact) 
        	checkboxes += 1;
        if (chkMed) 
        	checkboxes += 2;
        if (chkVisit) 
        	checkboxes += 4;
        if (chkOther) 
        	checkboxes += 8;
        setDate(new Date());
        setName(strRemark);
    }

    public UserRemark(String strInitString) {
        StringParser initString = new StringParser(strInitString);
        initString.next(";");
        setID(Integer.parseInt(initString.next(";")));
        setPatientID(Integer.parseInt(initString.next(";")));
        setDate(new Date(Long.parseLong(initString.next(";"))));
        setCheckboxes(Integer.parseInt(initString.next(";")));
        setName(initString.next(";"));
        setIsServerTime(initString.next(";").equals("True"));
        setWasSent(initString.next().equals("True"));
    }  
    
    public String toString(){
		if (!getIsServerTime() && Option.Instance().isTimeSynchronised())
		{
			setDate(DateUtils.getSynchronizedTime(getDate()));
			setIsServerTime(true);
			UserRemarkManager.Instance().save(this);
		}
    	UserRemarkManager.Instance().save(this);
        String strValue = new String("O;");
        strValue += getID() + ";";
        strValue += getPatientID() + ";";
        strValue += String.valueOf(DateUtils.getLocalTime(getDate())) + ";";
        strValue += getCheckboxes() + ";";
        strValue += getName() + ";";
        strValue += (getIsServerTime() ? "True" : "False") + ";";
        strValue += (getWasSent() ? "True" : "False");
        return strValue;
    }    
    
    public String getDone()
    {
		if (!getIsServerTime() && Option.Instance().isTimeSynchronised())
		{
			setDate(DateUtils.getSynchronizedTime(getDate()));
			setIsServerTime(true);
			UserRemarkManager.Instance().save(this);
		}
        String strValue = new String("O;");
        strValue += getWorkerID() + ";";
        strValue += getPatientID() + ";";
        strValue += DateUtils.getLocalTime(getDate()) + ";";
        strValue += getCheckboxes() + ";";
        strValue += getName() + ";";
        strValue += getCheckedIDs();
        SentObjectVerification.Instance().sentUserRemarks.add(this);
        return strValue;
    }
    
	@Override
	public String forServer() {       
	    return "";
	}

	public void setCheckboxes(boolean chkContact
			, boolean chkMed, boolean chkVisit, boolean chkOther ){
        checkboxes = 0;
        if (chkContact) 
        	checkboxes += 1;
        if (chkMed) 
        	checkboxes += 2;
        if (chkVisit) 
        	checkboxes += 4;
        if (chkOther) 
        	checkboxes += 8;
	}

	@Override
	public void clear(){
		super.clear();
		setPatientID(EMPTY_ID);
		setDate(DateUtils.EmptyDate);
		setCheckboxes(0);
		setCheckedIDs("");
	}
	
}
