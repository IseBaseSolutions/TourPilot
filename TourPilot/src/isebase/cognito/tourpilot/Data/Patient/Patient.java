package isebase.cognito.tourpilot.Data.Patient;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class Patient extends BaseObject {

	
	
	public static final String AddressField = "address";
	public static final String IsDoneField = "is_done";
	
	private String address;
	private String street;
	private String city;
	private String surname;
	private String sex;
	private String zip;
	private String phone;
    private String strDoctorIDs;
    private String strRelativeIDs;
	
	private boolean isDone;
	private boolean isAdditional;
	
    private int btyp_kk;
    private int btyp_pk;
    private int btyp_sa;
    private int btyp_pr;    
	
	public Patient() {

	}

	public Patient(String initString){
        NCryptor ncryptor = new NCryptor();        
        initString = initString.substring(0, 2);
		setIsAdditional(initString.contains("^"));
		initString = initString.replace("^", "");
		StringParser parsingString = new StringParser(initString);
        setId(Integer.parseInt(parsingString.next(";")));
        setSurname(parsingString.next(";"));
        setName(parsingString.next(";"));
        String str = parsingString.next(";");
    	setSex(str.substring(1, str.length()));
    	setAddress(parsingString.next(";"));
        setZip(parsingString.next(";"));
        setCity(parsingString.next(";"));
        setPhone(parsingString.next(";"));
        String x = parsingString.next("+");
        if(x.length()==0) x="0";
        setKK(Integer.parseInt(x));
        x = parsingString.next("+");
        if(x.length()==0) x="0";
        setPK(Integer.parseInt(x));
        x = parsingString.next("+");
        if(x.length()==0) x="0";
        setSA(Integer.parseInt(x));
        x = parsingString.next(";");
        if(x.length()==0) x="0";
        setPR(Integer.parseInt(x));
        setStrDoctorsIDs(parsingString.next(";"));
        setStrRelativeIDs(parsingString.next("~"));
        setCheckSum(ncryptor.NcodeToL(parsingString.next()));
	}
	
	public Patient(String name, boolean isDone) {
		super(name);
		this.isDone = isDone;
	}

	@MapField(DatabaseField = IsDoneField)
	public void setIsDone(boolean isDone) {
		this.isDone = isDone;
	}

	@MapField(DatabaseField = IsDoneField)
	public boolean getIsDone() {
		return isDone;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@MapField(DatabaseField = AddressField)
	public void setAddress(String address) {
		this.address = address;
	}

	@MapField(DatabaseField = AddressField)
	public String getAddress() {
		return this.address;
	}
	
	public boolean getIsAdditional() {
		return isAdditional;
	}
	
	public void setIsAdditional(boolean isAdditional) {
		this.isAdditional = isAdditional;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getZip() {
		return zip;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
		
	public String getStrDoctorsIDs() {
		return strDoctorIDs;
	}
	
	public void setStrDoctorsIDs(String doctorIDs) {
		this.strDoctorIDs = doctorIDs;
	}	
	
	public String getStrRelativeIDs() {
		return strRelativeIDs;
	}
	
	public void setStrRelativeIDs(String relativeIDs) {
		this.strRelativeIDs = relativeIDs;
	}	
    
	public int getKK() {
		return btyp_kk;
	}
	
	public void setKK(int btyp_kk) {
		this.btyp_kk = btyp_kk;
	}
	
	public int getPK() {
		return btyp_pk;
	}
	
	public void setPK(int btyp_pk) {
		this.btyp_pk = btyp_pk;
	}
	
	public int getSA() {
		return btyp_sa;
	}
	
	public void setSA(int btyp_sa) {
		this.btyp_sa = btyp_sa;
	}
	
	public int getPR() {
		return btyp_pr;
	}
	
	public void setPR(int btyp_pr) {
		this.btyp_pr = btyp_pr;
	}
	
	public String getFullName() {
		return String.format("%s %s", getSurname(), getName());
	}
	
    public String forServer()
    {
    	if (getIsAdditional())
    		return "";
        NCryptor ncryptor = new NCryptor();
        String strValue = new String("P;");
        strValue += ncryptor.LToNcode(getId()) + ";";
        strValue += ncryptor.LToNcode(getCheckSum());
        return strValue;
    }
	
}
