package isebase.cognito.tourpilot.Data.Patient;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;

public class Patient extends BaseObject {

	public static final String AddressField = "address";
	public static final String IsDoneField = "is_done";
	
	private String address;
	private boolean isDone;
	
	public Patient() {

	}

	public Patient(String intString){
		
	}
	
	public Patient(String name, boolean isDone) {
		super(name);
		this.isDone = isDone;
	}

	@MapField(DatabaseField = AddressField)
	public void setAddress(String address) {
		this.address = address;
	}

	@MapField(DatabaseField = AddressField)
	public String getAddress() {
		return this.address;
	}

	@MapField(DatabaseField = IsDoneField)
	public void setIsDone(boolean isDone) {
		this.isDone = isDone;
	}

	@MapField(DatabaseField = IsDoneField)
	public boolean getIsDone() {
		return isDone;
	}
}
