package isebase.cognito.tourpilot.Data.Settings;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;

public class Option extends BaseObject {

	private int workerID;
	private int tourID;
	private int employmentID;
	private String serverIP;
	private int serverPort;
	
	public Option() {
		
	}
	
	@MapField(DatabaseField = "worker_id")
	public int getWorkerID() {
		return workerID;
	}
	
	@MapField(DatabaseField = "worker_id")
	public void setWorkerID(int workerID) {
		this.workerID = workerID;
	}
	
	@MapField(DatabaseField = "tour_id")
	public int getTourID() {
		return tourID;
	}
	
	@MapField(DatabaseField = "tour_id")
	public void setTourID(int tourID) {
		this.tourID = tourID;
	}
	
	@MapField(DatabaseField = "employment_id")
	public int getEmploymentID() {
		return employmentID;
	}
	
	@MapField(DatabaseField = "employment_id")
	public void setEmploymentID(int employmentID) {
		this.employmentID = employmentID;
	}
	
	@MapField(DatabaseField = "server_ip")
	public String getServerIP() {
		return serverIP;
	}
	
	@MapField(DatabaseField = "server_ip")
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	
	@MapField(DatabaseField = "server_port")
	public int getServerPort() {
		return serverPort;
	}
	
	@MapField(DatabaseField = "server_port")
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
}
