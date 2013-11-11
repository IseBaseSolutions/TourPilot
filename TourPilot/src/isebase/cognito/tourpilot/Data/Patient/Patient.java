package isebase.cognito.tourpilot.Data.Patient;

import isebase.cognito.tourpilot.Connection.ServerCommandParser;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class Patient extends BaseObject {

	public static final String AddressField = "address";
	public static final String IsDoneField = "is_done";
	public static final String IsAdditionalField = "is_additional";
	public static final String SurnameField = "surname";
	public static final String StreetField = "street";
	public static final String ZipField = "zip";
	public static final String CityField = "city";
	public static final String PhoneField = "phone";
	public static final String SexField = "sex";
	public static final String DoctorIDsField = "doctor_ids";
	public static final String RelativeIDsField = "relative_ids";
	public static final String CatalogKKTypeField = "catalog_kk_type";
	public static final String CatalogPKTypeField = "catalog_pk_type";
	public static final String CatalogSATypeField = "catalog_sa_type";
	public static final String CatalogPRTypeField = "catalog_pr_type";

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

	@MapField(DatabaseField = SurnameField)
	public String getSurname() {
		return surname;
	}

	@MapField(DatabaseField = SurnameField)
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

	@MapField(DatabaseField = IsAdditionalField)
	public boolean getIsAdditional() {
		return isAdditional;
	}

	@MapField(DatabaseField = IsAdditionalField)
	public void setIsAdditional(boolean isAdditional) {
		this.isAdditional = isAdditional;
	}

	@MapField(DatabaseField = StreetField)
	public String getStreet() {
		return street;
	}

	@MapField(DatabaseField = StreetField)
	public void setStreet(String street) {
		this.street = street;
	}

	@MapField(DatabaseField = CityField)
	public String getCity() {
		return city;
	}

	@MapField(DatabaseField = CityField)
	public void setCity(String city) {
		this.city = city;
	}

	@MapField(DatabaseField = ZipField)
	public String getZip() {
		return zip;
	}

	@MapField(DatabaseField = ZipField)
	public void setZip(String zip) {
		this.zip = zip;
	}

	@MapField(DatabaseField = PhoneField)
	public String getPhone() {
		return phone;
	}

	@MapField(DatabaseField = PhoneField)
	public void setPhone(String phone) {
		this.phone = phone;
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

	}

	public Patient(String initString) {
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
		if (x.length() == 0)
			x = "0";
		setKK(Integer.parseInt(x));
		x = parsingString.next("+");
		if (x.length() == 0)
			x = "0";
		setPK(Integer.parseInt(x));
		x = parsingString.next("+");
		if (x.length() == 0)
			x = "0";
		setSA(Integer.parseInt(x));
		x = parsingString.next(";");
		if (x.length() == 0)
			x = "0";
		setPR(Integer.parseInt(x));
		setStrDoctorsIDs(parsingString.next(";"));
		setStrRelativeIDs(parsingString.next("~"));
		setCheckSum(ncryptor.NcodeToL(parsingString.next()));
	}

	public String getFullName() {
		return String.format("%s %s", getSurname(), getName());
	}

	public String forServer() {
		if (getIsAdditional())
			return "";
		NCryptor ncryptor = new NCryptor();
		String strValue = new String(ServerCommandParser.PATIENT + ";");
		strValue += ncryptor.LToNcode(getId()) + ";";
		strValue += ncryptor.LToNcode(getCheckSum());
		return strValue;
	}

}
