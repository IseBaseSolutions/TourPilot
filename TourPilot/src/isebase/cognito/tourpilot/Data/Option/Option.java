package isebase.cognito.tourpilot.Data.Option;

import android.content.pm.PackageManager.NameNotFoundException;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.StaticResources.StaticResources;

public class Option extends BaseObject {

	private int workerID;
	private int tourID;
	private int employmentID;	
	private int serverPort;	
	
	private String serverIP;
	
	public static boolean testMode = true;
	
	private Worker worker; 
	
	private static Option instance;
	
	public static Option Instance() {
		if (instance != null)
			return instance;
		instance = OptionManager.Instance().loadOption();
		if (instance != null)
			return instance;
		return instance = new Option();
	}
	
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
	
	public Worker getWorker() {
		if (worker != null)
			return worker;
		worker = WorkerManager.Instance().load(workerID);
		return worker;
	}	

	@Override
	protected void Clear() {
		super.Clear();
		workerID = emptyID;
		tourID = emptyID;
		employmentID = emptyID;	
		serverPort = 4448;		
		serverIP = "192.168.1.8";
	}
	
	public String getVersion() throws NameNotFoundException {
		return StaticResources.getBaseContext().getPackageManager()
			    .getPackageInfo(StaticResources.getBaseContext().getPackageName(), 0).versionName;
	}
	
}
