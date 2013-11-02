package isebase.cognito.tourpilot.Data.Patient;

import android.os.Parcel;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;

public class Patient extends BaseObject {

	public Patient(String name) {
		super(name);
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
}
