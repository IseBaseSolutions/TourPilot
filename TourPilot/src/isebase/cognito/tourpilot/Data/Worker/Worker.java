package isebase.cognito.tourpilot.Data.Worker;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import android.os.Parcel;

public class Worker extends BaseObject {

	public Worker() {

	}

	public Worker(String name) {
		super(name);
	}

	public Worker(Parcel in) {
		String[] data = new String[2];

		in.readStringArray(data);
		setId(Integer.parseInt(data[0]));
		setName(data[1]);
	}

}
