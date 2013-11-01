package isebase.cognito.tourpilot.Data.Patient;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;

public class Patient  extends BaseObject{
	
	private String address;
	
	@MapField(DatabaseField = "address")
	public void setAddress(String address) {
		this.address = address;
	}
}
