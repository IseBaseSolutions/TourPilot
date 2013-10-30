package isebase.cognito.tourpilot.Data.BaseObject;

import isebase.cognito.tourpilot.DataBase.MapField;

public class BaseObject {

	private int emptyID = -1;
	private String stringEmpty = "";
	
	private int id;

	public int getId() {
		return id;
	}
	
	@MapField(DatabaseField = "_id", Number = 0)
	public void setId(int id) {
		this.id = id;
	}
	
	private String name;

	public String getName() {
		return name;
	}

	@MapField(DatabaseField = "name", Number = 1)
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	private long checkSum;
	
	public long getCheckSum() {
		return checkSum;
	}
	
	@MapField(DatabaseField = "checkSum", Number = 2)
	public void setCheckSum(long checkSum) {
		this.checkSum = checkSum;  
	}
	
	public void Clear()
	{
		id = emptyID;
		name = stringEmpty;
		checkSum = 0;
	}
	
}
