package isebase.cognito.tourpilot.Data.UserRemark;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.CustomRemark.CustomRemark;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.DateUtils;

import java.util.Date;

public class NewUserRemark extends BaseObject {
//	public static final String OwnerIDField = "owner_id";
//	public static final String PatientIDField = "patient_id";
//	public static final String EmploymentIDField = "employment_id";
//	public static final String PosNumberField = "pos_number";
//	public static final String DateField = "date";
//	public static final String ValueField = "value";
//	public static final String TextInputField = "text_input";
//	
//	boolean textInput;
//	private int patientID;	
//	private Date date;
//	private String value;
//	private int employmentID;
//	private int posNumber;
//	private int ownerID;
//	
//	@MapField(DatabaseField = PosNumberField)
//	public int getPosNumber() {
//		return posNumber;
//	}
//	
//	@MapField(DatabaseField = PosNumberField)
//	public void setPosNumber(int posNumber) {
//		this.posNumber = posNumber;
//	}
//	
//	@MapField(DatabaseField = PatientIDField)
//	public int getPatientID() {
//		return patientID;
//	}
//	@MapField(DatabaseField = PatientIDField)
//	public void setPatientID(int patientID) {
//		this.patientID = patientID;
//	}
//
//	@MapField(DatabaseField = DateField)
//	public Date getDate() {
//		return date;
//	}
//	@MapField(DatabaseField = DateField)
//	public void setDate(Date date) {
//		this.date = date;
//	}
//	
//	@MapField(DatabaseField = ValueField)
//	public String getValue() {
//		return value;
//	}
//	
//	@MapField(DatabaseField = ValueField)
//	public void setValue(String value) {
//		this.value = value;
//	}
//
//	@MapField(DatabaseField = EmploymentIDField)
//	public int getEmploymentID() {
//		return employmentID;
//	}
//
//	@MapField(DatabaseField = EmploymentIDField)
//	public void setEmploymentID(int employmentID) {
//		this.employmentID = employmentID;
//	}
//	
//	@MapField(DatabaseField = OwnerIDField)
//	public int getOwnerID() {
//		return ownerID;
//	}
//
//	@MapField(DatabaseField = OwnerIDField)
//	public void setOwnerID(int ownerID) {
//		this.ownerID = ownerID;
//	}
//	
//	@MapField(DatabaseField = TextInputField)
//	public boolean isTextInput() {
//		return textInput;
//	}
//
//	@MapField(DatabaseField = TextInputField)
//	public void setTextInput(boolean textInput) {
//		this.textInput = textInput;
//	}
//	
//	public NewUserRemark(){
//		clear();
//	}
//
//	public NewUserRemark(CustomRemark customRemark){
//		clear();
//		setOwnerID(customRemark.getID());
//		setName(customRemark.getName());
//		setEmploymentID(Option.Instance().getEmploymentID());
//		setPosNumber(customRemark.getPosNumber());
//		setTextInput(customRemark.isTextInput());
//		setDate(new Date());
//	}
//	
//	public NewUserRemark(String name, int posNumber, boolean isTextInput){
//		clear();
//		setOwnerID(-1);
//		setName(name);
//		setEmploymentID(Option.Instance().getEmploymentID());
//		setPosNumber(posNumber);
//		setTextInput(isTextInput);
//		setDate(new Date());
//	}
//
//    public NewUserRemark(String strInitString) {
////        StringParser initString = new StringParser(strInitString);
////        initString.next(";");
////        setID(Integer.parseInt(initString.next(";")));
////        setPatientID(Integer.parseInt(initString.next(";")));
////        setDate(new Date(Long.parseLong(initString.next(";"))));
////        setCheckboxes(Integer.parseInt(initString.next(";")));
////        setName(initString.next(";"));
////        setIsServerTime(initString.next(";").equals("True"));
////        setWasSent(initString.next().equals("True"));
//    }      
//    
//    public String getDone()
//    {
////		if (!getIsServerTime() && Option.Instance().isTimeSynchronised())
////		{
////			setDate(DateUtils.getSynchronizedTime(getDate()));
////			setIsServerTime(true);
////			UserRemarkManager.Instance().save(this);
////		}
////        String strValue = new String("O;");
////        strValue += getID() + ";";
////        strValue += getPatientID() + ";";
////        strValue += DateUtils.getLocalTime(getDate()) + ";";
////        strValue += getCheckboxes() + ";";
////        strValue += getName();
////        SentObjectVerification.Instance().sentUserRemarks.add(this);
////        return strValue;
//    	return "";
//    }
//    
//	@Override
//	public String forServer() {       
//	    return "";
//	}
//
//	@Override
//	public void clear(){
//		super.clear();
//		setPatientID(EMPTY_ID);
//		setEmploymentID(EMPTY_ID);
//		setValue("");
//		setDate(DateUtils.EmptyDate);
//	}
//	
}
