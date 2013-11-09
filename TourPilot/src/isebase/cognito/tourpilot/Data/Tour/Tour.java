package isebase.cognito.tourpilot.Data.Tour;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;

public class Tour extends BaseObject {

	public static final String WorkerIDField = "worker_id";
	
	private int workerID;
	
	@MapField(DatabaseField = WorkerIDField)
	public int getWorkerID() {
		return workerID;
	}

	@MapField(DatabaseField = WorkerIDField)
	public void setWorkerID(int workerID) {
		this.workerID = workerID;
	}

	public Tour() {

	}

	public Tour(String name) {
		super(name);
	}

}
