package isebase.cognito.tourpilot.Data.Patient;

import android.R.bool;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;

public class Patient extends BaseObject {

	private String address;
	private int isDone;

	@MapField(DatabaseField = "address")
	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	@MapField(DatabaseField = "is_done")
	public void setIsDone(int isDone) {
		this.isDone = isDone;
	}

	public boolean getIsDone() {
		return isDone == 1;
	}
}
