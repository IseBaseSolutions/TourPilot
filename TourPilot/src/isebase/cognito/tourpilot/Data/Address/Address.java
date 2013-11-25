package isebase.cognito.tourpilot.Data.Address;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.StaticResources.StaticResources;

public class Address {

	public static final String StreetField = "street";
	public static final String ZipField = "zip";
	public static final String CityField = "city";
	public static final String PhoneField = "phone";
	public static final String PrivatePhoneField = "private_phone";
	public static final String MobilePhoneField = "mobile_phone";

	private String street;
	private String zip;
	private String city;
	private String phone;
	private String privatePhone;
	private String mobilePhone;
	private int id;

	@MapField(DatabaseField = BaseObject.IDField)
	public int getID() {
		return id;}
		
	@MapField(DatabaseField = BaseObject.IDField)
	public void setID(int id) {
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
	
	@MapField(DatabaseField = PrivatePhoneField)
	public String getPrivatePhone() {
		return privatePhone;
	}
	
	@MapField(DatabaseField = PrivatePhoneField)
	public void setPrivatePhone(String privatePhone) {
		this.privatePhone = privatePhone;
	}
	
	@MapField(DatabaseField = MobilePhoneField)
	public String getMobilePhone() {
		return mobilePhone;
	}
	
	@MapField(DatabaseField = MobilePhoneField)
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
		
	public Address(){
		clear();
	}
	
	private void clear(){
		setID(BaseObject.EMPTY_ID);
		setStreet("");
		setCity("");
		setPhone("");
		setPrivatePhone("");
		setMobilePhone("");
		setZip("");
	}
	
	public String getRealPhone()
	{
		return getOnlyNumbers(phone);
	}
	
	public String getRealPrivatePhone()
	{
		return getOnlyNumbers(privatePhone);
	}
	
	public String getRealMobilePhone()
	{
		return getOnlyNumbers(mobilePhone);
	}
	
	private String getOnlyNumbers(String val){
		String retVal = "";
		for(char c : val.toCharArray())
			if(Character.isDigit(c))
				retVal += c;
		return retVal;
	}
	
	public String getAddressData()
	{
		String address = "";
		if(getStreet().length() > 0)
			address += getStreet();
		if(getZip().length() > 0)
			address += ", " + getZip();
		if(getCity().length() > 0)
			address += ", " + getCity();
		if(address.length() == 0 )
			address = StaticResources.getBaseContext().getString(R.string.err_no_address);
		return address;
	}
}
