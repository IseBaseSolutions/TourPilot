package isebase.cognito.tourpilot.Data.Address;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;

public class Address {

	public static final String StreetField = "street";
	public static final String ZipField = "zip";
	public static final String CityField = "city";
	public static final String PhoneField = "phone";

	private String street;
	private String zip;
	private String city;
	private String phone;
	private int id;

	@MapField(DatabaseField = BaseObject.IDField)
	public int getId() {
		return id;}
		
	@MapField(DatabaseField = BaseObject.IDField)
	public void setId(int id) {
		this.id = id;
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
		
	public Address(){
		clear();
	}
	
	private void clear(){
		setId(BaseObject.EMPTY_ID);
		setStreet("");
		setCity("");
		setPhone("");
		setZip("");
	}
	public String getRealPhone()
	{
		
		String realPhone = "";
		for(char c : this.phone.toCharArray())
		{
			if(Character.isDigit(c))
				realPhone += c;
		}
		return realPhone;
	}
	
}
