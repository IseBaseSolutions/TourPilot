package isebase.cognito.tourpilot.Data.Task;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;

public class Task extends BaseObject {

	public enum eTaskState {
		Empty, Done, UnDone
	}

	private eTaskState taskState = eTaskState.Empty;

	public Task() {

	}

	public Task(String string) {
		super(string);
	}

	@MapField(DatabaseField = "task_state")
	public void setTaskState(int taskStateIndex) {
		this.taskState = eTaskState.values()[taskStateIndex];
	}

	@MapField(DatabaseField = "task_state")
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
