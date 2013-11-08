package isebase.cognito.tourpilot.Data.BaseObject;

import isebase.cognito.tourpilot.DataBase.MapField;

public abstract class BaseObject {

	public static final String IDField = "_id";
	public static final String NameField = "name";
	public static final String CheckSumField = "checksum";
	
	public static final int EMPTY_ID = -1;

	public BaseObject() {
		clear();
	}

	public BaseObject(String name) {
		clear();
		setName(name);
	}

	private int id;

	public int getId() {
		return id;
	}

	@MapField(DatabaseField = IDField)
	public void setId(int id) {
		this.id = id;
	}

	private String name;

	@MapField(DatabaseField = NameField)
	public String getName() {
		return name;
	}

	@MapField(DatabaseField = NameField)
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	private long checkSum;

	@MapField(DatabaseField = CheckSumField)
	public long getCheckSum() {
		return checkSum;
	}

	@MapField(DatabaseField = CheckSumField)
	public void setCheckSum(long checkSum) {
		this.checkSum = checkSum;
	}

	protected void clear() {
		id = EMPTY_ID;
		name = "";
		checkSum = 0;
	}
}
