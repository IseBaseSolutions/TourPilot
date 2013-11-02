package isebase.cognito.tourpilot.Data.Tour;

import android.os.Parcel;
import android.os.Parcelable;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;

public class Tour extends BaseObject{

	public Tour() {
		
	}
	
	public Tour(String name) {
		super(name);
	}
	
	//Реализация передачи данных между активити
	public void writeToParcel(Parcel out, int flags){
		out.writeInt(getId());
		out.writeString(getName());
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	public static final Parcelable.Creator<Tour> CREATOR = new Parcelable.Creator<Tour>(){
		 public Tour createFromParcel(Parcel in){
			 return new Tour();
		 }

		@Override
		public Tour[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Tour[size];
		}
	};
}
