package isebase.cognito.tourpilot.Data.Option;

import android.content.pm.PackageManager.NameNotFoundException;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.StaticResources.StaticResources;

public class Option extends BaseObject {

	public static final String WorkerIDField = "worker_id";
	public static final String TourIDField = "tour_id";
	public static final String EmploymentIDField = "employment_id";
	public static final String ServerIPField = "server_ip";
	public static final String ServerPortField = "server_port";	
	
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
	
	@MapField(DatabaseField = WorkerIDField)
	public int getWorkerID() {
		return workerID;
	}
	
	@MapField(DatabaseField = WorkerIDField)
	public void setWorkerID(int workerID) {
		this.workerID = workerID;
	}
	
	@MapField(DatabaseField = TourIDField)
	public int getTourID() {
		return tourID;
	}
	
	@MapField(DatabaseField = TourIDField)
	public void setTourID(int tourID) {
		this.tourID = tourID;
	}
	
	@MapField(DatabaseField = EmploymentIDField)
	public int getEmploymentID() {
		return employmentID;
	}
	
	@MapField(DatabaseField = EmploymentIDField)
	public void setEmploymentID(int employmentID) {
		this.employmentID = employmentID;
	}
	
	@MapField(DatabaseField = ServerIPField)
	public String getServerIP() {
		return serverIP;
	}
	
	@MapField(DatabaseField = ServerIPField)
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	
	@MapField(DatabaseField = ServerPortField)
	public int getServerPort() {
		return serverPort;
	}
	
	@MapField(DatabaseField = ServerPortField)
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
		workerID = EMPTY_ID;
		tourID = EMPTY_ID;
		employmentID = EMPTY_ID;	
		serverPort = 4448;		
		serverIP = "";
	}
	
	public String getVersion() throws NameNotFoundException {
		return StaticResources.getBaseContext().getPackageManager()
			    .getPackageInfo(StaticResources.getBaseContext().getPackageName(), 0).versionName;
	}
	
}
