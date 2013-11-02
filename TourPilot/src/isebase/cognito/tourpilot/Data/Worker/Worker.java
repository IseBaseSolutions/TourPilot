package isebase.cognito.tourpilot.Data.Worker;

import android.os.Parcel;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;

public class Worker extends BaseObject{

	public Worker(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeInt(getId());
		out.writeString(getName());
	}
	
}
