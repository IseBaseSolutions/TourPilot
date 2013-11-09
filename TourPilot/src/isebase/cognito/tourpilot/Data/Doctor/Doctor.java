package isebase.cognito.tourpilot.Data.Doctor;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class Doctor extends BaseObject {

	private String surname;
	private String zip;
	private String city;
	private String street;
	private String phone;

	public Doctor(String initString) {
		initString = initString.substring(0, 2);
		StringParser parsingString = new StringParser(initString);
		setId(Integer.parseInt(parsingString.next(";")));
		setSurname(parsingString.next(";"));
		setName(parsingString.next(";"));
		setStreet(parsingString.next(";"));
		setZip(parsingString.next(";"));
		setCity(parsingString.next(";"));
		setPhone(parsingString.next("~"));
		setCheckSum(Long.parseLong(parsingString.next(";")));
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getZip() {
		return zip;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getFullName() {
		return String.format("%s %s", getSurname(), getName());
	}

	public String forServer() {
		NCryptor ncryptor = new NCryptor();
		String strValue = new String("M;");
		strValue += ncryptor.LToNcode(getId()) + ";";
		strValue += ncryptor.LToNcode(getCheckSum());
		return strValue;
	}
}
