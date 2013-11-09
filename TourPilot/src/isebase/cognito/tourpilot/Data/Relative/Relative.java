package isebase.cognito.tourpilot.Data.Relative;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class Relative extends BaseObject {

	private String surname;
	private String zip;
	private String city;
	private String street;
	private String phone;
	private String ship;

	public Relative(String initString) {
		StringParser parsingString = new StringParser(initString);
		parsingString.next(";");
		setId(Integer.parseInt(parsingString.next(";")));
		setSurname(parsingString.next(";"));
		setName(parsingString.next(";"));
		setStreet(parsingString.next(";"));
		setZip(parsingString.next(";"));
		setCity(parsingString.next(";"));
		setPhone(parsingString.next(";"));
		setShip(parsingString.next("~"));
		setCheckSum(Long.parseLong(parsingString.next()));
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

	public String getShip() {
		return ship;
	}

	public void setShip(String ship) {
		this.ship = ship;
	}

	public String getFullName() {
		return String.format("%s %s", getSurname(), getName());
	}

	public String forServer() {
		NCryptor ncryptor = new NCryptor();
		String strValue = new String("V;");
		strValue += ncryptor.LToNcode(getId()) + ";";
		strValue += ncryptor.LToNcode(getCheckSum());
		return strValue;
	}
}
