package isebase.cognito.tourpilot.Data.AddTasks;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;

public class AddTasks extends BaseObject  {

	private boolean isCheck = false;
	
	public AddTasks(String name) {
		super(name);
	}

	public AddTasks() {
	}
	
	public void setCheck(boolean isCheck){
		this.isCheck = isCheck;
	}
	
	public boolean getCheck(){
		return this.isCheck;
	}
}
