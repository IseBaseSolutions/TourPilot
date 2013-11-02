package isebase.cognito.tourpilot.Data.BaseObject;

import isebase.cognito.tourpilot.DataBase.MapField;
import android.os.Parcelable;

public abstract class BaseObject implements Parcelable  {

	public int emptyID = -1;
	
	public BaseObject() {
		
	}
	
	public BaseObject(String name) {
		setName(name);
	}

	private int id;

	public int getId() {
		return id;
	}

	@MapField(DatabaseField = "_id")
	public void setId(int id) {
		this.id = id;
	}

	private String name;

	@MapField(DatabaseField = "name")
	public String getName() {
		return name;
	}

	@MapField(DatabaseField = "name")
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	private long checkSum;

	@MapField(DatabaseField = "checksum")
	public long getCheckSum() {
		return checkSum;
	}

	@MapField(DatabaseField = "checksum")
	public void setCheckSum(long checkSum) {
		this.checkSum = checkSum;
	}

	protected void Clear() {
		id = emptyID;
		name = "";
		checkSum = 0;
	}
}
