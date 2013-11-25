package isebase.cognito.tourpilot.Data.Patient;

import isebase.cognito.tourpilot.Connection.ServerCommandParser;
import isebase.cognito.tourpilot.Data.Address.Address;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class Patient extends BaseObject {

	public static final String IsAdditionalField = "is_additional";
	public static final String SurnameField = "surname";
	public static final String AddressIDField = "address_id";
		
	public static final String SexField = "sex";
	public static final String DoctorIDsField = "doctor_ids";
	public static final String RelativeIDsField = "relative_ids";
	
	public static final String CatalogKKTypeField = "catalog_kk_type";
	public static final String CatalogPKTypeField = "catalog_pk_type";
	public static final String CatalogSATypeField = "catalog_sa_type";
	public static final String CatalogPRTypeField = "catalog_pr_type";

	public Address address;
	private int addressID;
	
	private String surname;
	private String sex;
	private String strDoctorIDs;
	private String strRelativeIDs;

	private boolean isAdditional;

	private int btyp_kk;
	private int btyp_pk;
	private int btyp_sa;
	private int btyp_pr;

	@MapField(DatabaseField = AddressIDField)
	public int getAddressID() {
		return addressID;
	}

	@MapField(DatabaseField = AddressIDField)
	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}

	@MapField(DatabaseField = SurnameField)
	public String getSurname() {
		return surname;
	}

	@MapField(DatabaseField = SurnameField)
	public void setSurname(String surname) {
		this.surname = surname;
	}

	@MapField(DatabaseField = IsAdditionalField)
	public boolean getIsAdditional() {
		return isAdditional;
	}

	@MapField(DatabaseField = IsAdditionalField)
	public void setIsAdditional(boolean isAdditional) {
		this.isAdditional = isAdditional;
	}

	@MapField(DatabaseField = SexField)
	public String getSex() {
		return sex;
	}

	@MapField(DatabaseField = SexField)
	public void setSex(String sex) {
		this.sex = sex;
	}

	@MapField(DatabaseField = DoctorIDsField)
	public String getStrDoctorsIDs() {
		return strDoctorIDs;
	}

	@MapField(DatabaseField = DoctorIDsField)
	public void setStrDoctorsIDs(String doctorIDs) {
		this.strDoctorIDs = doctorIDs;
	}

	@MapField(DatabaseField = RelativeIDsField)
	public String getStrRelativeIDs() {
		return strRelativeIDs;
	}

	@MapField(DatabaseField = RelativeIDsField)
	public void setStrRelativeIDs(String relativeIDs) {
		this.strRelativeIDs = relativeIDs;
	}

	@MapField(DatabaseField = CatalogKKTypeField)
	public int getKK() {
		return btyp_kk;
	}

	@MapField(DatabaseField = CatalogKKTypeField)
	public void setKK(int btyp_kk) {
		this.btyp_kk = btyp_kk;
	}

	@MapField(DatabaseField = CatalogPKTypeField)
	public int getPK() {
		return btyp_pk;
	}

	@MapField(DatabaseField = CatalogPKTypeField)
	public void setPK(int btyp_pk) {
		this.btyp_pk = btyp_pk;
	}

	@MapField(DatabaseField = CatalogSATypeField)
	public int getSA() {
		return btyp_sa;
	}

	@MapField(DatabaseField = CatalogSATypeField)
	public void setSA(int btyp_sa) {
		this.btyp_sa = btyp_sa;
	}

	@MapField(DatabaseField = CatalogPRTypeField)
	public int getPR() {
		return btyp_pr;
	}

	@MapField(DatabaseField = CatalogPRTypeField)
	public void setPR(int btyp_pr) {
		this.btyp_pr = btyp_pr;
	}
	
	public Patient() {
		clear();
	}
	
	public Patient(String initString) {
		address = new Address();
		NCryptor ncryptor = new NCryptor();
		setIsAdditional(initString.contains("^"));
		initString = initString.replace("^", "");
		StringParser parsingString = new StringParser(initString);
		parsingString.next(";");
		setID(Integer.parseInt(parsingString.next(";")));
		setSurname(parsingString.next(";"));
		setName(parsingString.next(";"));
		String sexStr = parsingString.next(";");
		setSex(sexStr.substring(1, sexStr.length()));
		address.setStreet(parsingString.next(";"));
		address.setZip(parsingString.next(";"));
		address.setCity(parsingString.next(";"));
		address.setPhone(parsingString.next(";"));
		address.setPrivatePhone(parsingString.next(";"));
		address.setMobilePhone(parsingString.next(";"));
		
		setKK(parseInt(parsingString.next("+")));
		setPK(parseInt(parsingString.next("+")));
		setSA(parseInt(parsingString.next("+")));
		setPR(parseInt(parsingString.next(";")));
				
		setStrDoctorsIDs(parsingString.next(";"));
		setStrRelativeIDs(parsingString.next("~"));
		setCheckSum(ncryptor.NcodeToL(parsingString.next()));
	}

	@Override
	public String toString() {
		return getFullName();
	}
	
	public String getFullName() {
		return String.format("%s %s", getSurname(), getName());
	}

	@Override
	public String forServer() {
		if (getIsAdditional())
			return "";
		NCryptor ncryptor = new NCryptor();
		String strValue = new String(ServerCommandParser.PATIENT + ";");
		strValue += ncryptor.LToNcode(getID()) + ";";
		strValue += ncryptor.LToNcode(getCheckSum());
		return strValue;
	}

	private int parseInt(String strVal){
		if (strVal.length() == 0)
			return EMPTY_ID;
		return Integer.parseInt(strVal);	
	}

	@Override
	protected void clear() {
		super.clear();
		address = new Address();
		setIsAdditional(false);
		setSurname("");
		setAddressID(EMPTY_ID);
		setSex("");
		setStrDoctorsIDs("");
		setStrRelativeIDs("");
		setKK(EMPTY_ID);
		setPK(EMPTY_ID);
		setSA(EMPTY_ID);
		setPR(EMPTY_ID);
		
	}
}
