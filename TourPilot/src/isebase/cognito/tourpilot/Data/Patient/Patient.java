package isebase.cognito.tourpilot.Data.Patient;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;

public class Patient extends BaseObject {

	public Patient() {

	}

	public Patient(String name, boolean isDone) {
		super(name);
		this.isDone = isDone;
	}

	private String address;
	private boolean isDone;

	@MapField(DatabaseField = "address")
	public void setAddress(String address) {
		this.address = address;
	}

	@MapField(DatabaseField = "address")
	public String getAddress() {
		return this.address;
	}

	@MapField(DatabaseField = "is_done")
	public void setIsDone(boolean isDone) {
		this.isDone = isDone;
	}

	@MapField(DatabaseField = "is_done")
	public boolean getIsDone() {
		return isDone;
	}
}
