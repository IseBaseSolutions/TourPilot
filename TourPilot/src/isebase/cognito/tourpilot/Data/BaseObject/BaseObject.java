package isebase.cognito.tourpilot.Data.BaseObject;

import isebase.cognito.tourpilot.DataBase.MapField;

public abstract class BaseObject {

	public int emptyID = -1;
	public String stringEmpty = "";
	
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

	public void Clear() {
		id = emptyID;
		name = stringEmpty;
		checkSum = 0;
	}

}
