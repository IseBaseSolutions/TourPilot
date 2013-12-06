package isebase.cognito.tourpilot.Data.Relative;

import isebase.cognito.tourpilot.Connection.ServerCommandParser;
import isebase.cognito.tourpilot.Data.Address.Address;
import isebase.cognito.tourpilot.Data.Address.IAddressable;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.NCryptor;
import isebase.cognito.tourpilot.Utils.StringParser;

public class Relative extends BaseObject implements IAddressable {

	public static final String SurnameField = "surname";
	public static final String ShipField = "ship";
	public static final String AddressIDField = "address_id";
	public static final String FamilyStateField = "family_state";
	
	public Address address;
	private int addressID;
	
	private String surname;
	private String ship;
	private String familyState;

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

	@MapField(DatabaseField = ShipField)
	public String getShip() {
		return ship;
	}

	@MapField(DatabaseField = ShipField)
	public void setShip(String ship) {
		this.ship = ship;
	}
	
	@MapField(DatabaseField = FamilyStateField)
	public String getFamilyState() {
		return familyState;
	}

	@MapField(DatabaseField = FamilyStateField)
	public void setFamilyState(String familyState) {
		this.familyState = familyState;
	}
	
	public Relative(){
		clear();
	}
	
	public Relative(String initString) {
		address = new Address();
		StringParser parsingString = new StringParser(initString);
		parsingString.next(";");
		setID(Integer.parseInt(parsingString.next(";")));
		setSurname(parsingString.next(";"));
		setName(parsingString.next(";"));
		address.setStreet(parsingString.next(";"));
		address.setZip(parsingString.next(";"));
		address.setCity(parsingString.next(";"));
		address.setPhone(parsingString.next(";"));
		address.setPrivatePhone(parsingString.next(";"));
		address.setMobilePhone(parsingString.next(";"));
		setShip(parsingString.next(";"));
		setFamilyState(parsingString.next("~"));
		setCheckSum(Long.parseLong(parsingString.next()));
	}

	public String getFullName() {
		return String.format("%s %s", getSurname(), getName());
	}
	public Address getAddress(){
		return address;
	}

	@Override
	public String forServer() {
		NCryptor ncryptor = new NCryptor();
		String strValue = new String(ServerCommandParser.RELATIVE + ";");
		strValue += ncryptor.LToNcode(getID()) + ";";
		strValue += ncryptor.LToNcode(getCheckSum());
		return strValue;
	}
	
	@Override
	protected void clear() {
		super.clear();
		address = new Address();
		setSurname("");
		setShip("");
		setAddressID(EMPTY_ID);
	}
	
	@Override
	public String toString(){
		return String.format("%s\n%s\n%s,%s\n",getFullName(),address.getStreet(),address.getZip(),address.getCity());
	}
	
}
