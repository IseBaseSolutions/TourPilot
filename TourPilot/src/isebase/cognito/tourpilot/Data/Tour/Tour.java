package isebase.cognito.tourpilot.Data.Tour;

import android.os.Parcel;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;

public class Tour extends BaseObject{

	public Tour(String name) {
		super(name);
	}
	
	//���������� �������� ������ ����� ��������
	public void writeToParcel(Parcel out, int flags){
		out.writeInt(getId());
		out.writeString(getName());
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

}
