package isebase.cognito.tourpilot.Data.UserRemark;

import java.util.Date;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.DateUtils;
import isebase.cognito.tourpilot.Utils.StringParser;

public class UserRemark extends BaseObject {

	public static final String PatientIDField = "patient_id";
	public static final String DateField = "date";
	public static final String CheckboxField = "checkboxes";

	private int patientID;
	private Date date;
	private int checkboxes;
	
	@MapField(DatabaseField = PatientIDField)
	public int getPatientID() {
		return patientID;
	}
	@MapField(DatabaseField = PatientIDField)
	public void setPatientID(int patientID) {
		this.patientID = patientID;
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
	
	public UserRemark(){
		clear();
	}
	
    public UserRemark(int workerID, int patientID
    		, boolean chkContact, boolean chkMed, boolean chkVisit
            , boolean chkOther, String strRemark)
    {
        setId(workerID);
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
        setId(Integer.parseInt(initString.next(";")));
        setPatientID(Integer.parseInt(initString.next(";")));
        setDate(new Date(Long.parseLong(initString.next(";"))));
        setCheckboxes(Integer.parseInt(initString.next(";")));
        setName(initString.next(";"));
        setIsServerTime(initString.next(";").equals("True"));
        setWasSent(initString.next().equals("True"));
    }  
    
    public String toString(){
        String strValue = new String("O;");
        strValue += getId() + ";";
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
        //O;358;4926;2008-08-19 22:14:59;0;ferting
        String strValue = new String("O;");
        strValue += getId() + ";";
        strValue += getPatientID() + ";";
        strValue += DateUtils.getLocalTime(getDate()) + ";";
        strValue += getCheckboxes() + ";";
        strValue += getName();
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
	}
	
}
