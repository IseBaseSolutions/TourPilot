package isebase.cognito.tourpilot.Data.Relative;

import isebase.cognito.tourpilot.Connection.ServerCommandParser;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class Relative extends BaseObject {

	public static final String SurnameField = "surname";
	public static final String StreetField = "street";
	public static final String ZipField = "zip";
	public static final String CityField = "city";
	public static final String PhoneField = "phone";
	public static final String ShipField = "ship";
	
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

	@MapField(DatabaseField = SurnameField)
	public String getSurname() {
		return surname;
	}

	@MapField(DatabaseField = SurnameField)
	public void setSurname(String surname) {
		this.surname = surname;
	}

	@MapField(DatabaseField = StreetField)
	public String getStreet() {
		return street;
	}

	@MapField(DatabaseField = StreetField)
	public void setStreet(String street) {
		this.street = street;
	}

	@MapField(DatabaseField = ZipField)
	public String getZip() {
		return zip;
	}

	@MapField(DatabaseField = ZipField)
	public void setZip(String zip) {
		this.zip = zip;
	}

	@MapField(DatabaseField = CityField)
	public String getCity() {
		return city;
	}

	@MapField(DatabaseField = CityField)
	public void setCity(String city) {
		this.city = city;
	}

	@MapField(DatabaseField = PhoneField)
	public String getPhone() {
		return phone;
	}

	@MapField(DatabaseField = PhoneField)
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@MapField(DatabaseField = ShipField)
	public String getShip() {
		return ship;
	}

	@MapField(DatabaseField = ShipField)
	public void setShip(String ship) {
		this.ship = ship;
	}

	public String getFullName() {
		return String.format("%s %s", getSurname(), getName());
	}

	public String forServer() {
		NCryptor ncryptor = new NCryptor();
		String strValue = new String(ServerCommandParser.RELATIVE + ";");
		strValue += ncryptor.LToNcode(getId()) + ";";
		strValue += ncryptor.LToNcode(getCheckSum());
		return strValue;
	}
}
