package isebase.cognito.tourpilot.Data.Task;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;

public class Task extends BaseObject {

	public static final String StateField = "task_state";
	
	public enum eTaskState {
		Empty, Done, UnDone
	}

	private eTaskState taskState = eTaskState.Empty;

	public Task() {

	}

	public Task(String string) {
		super(string);
	}

	@MapField(DatabaseField = StateField)
	public void setTaskState(int taskStateIndex) {
		this.taskState = eTaskState.values()[taskStateIndex];
	}
	
	@MapField(DatabaseField = StateField)
	public int getTaskStateIndex() {
		return taskState.ordinal();
	}

	public void setTaskState(eTaskState taskState) {
		this.taskState = taskState;
	}

	public eTaskState getTaskState() {
		return taskState;
	}

	@Override
	protected void Clear() {
		super.Clear();
		setTaskState(eTaskState.Empty);
	}
}
