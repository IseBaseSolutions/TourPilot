package isebase.cognito.tourpilot.Data.Option;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.telephony.TelephonyManager;

public class Option {

	public static final String TableName = "Options";
	
	public static final String WorkerIDField = "worker_id";
	public static final String PilotTourIDField = "pilot_tour_id";
	public static final String EmploymentIDField = "employment_id";
	public static final String ServerIPField = "server_ip";
	public static final String ServerPortField = "server_port";

	private String serverIP;
	public static boolean testMode = false;
	private Worker worker;
	private TelephonyManager phoneManager = StaticResources.phoneManager;;
	private OptionManager optionManager;	
	private static Option instance;
		
	private int workerID;
	private int employmentID;
	private int serverPort;
	private int id;	
	private int pilotTourID;
	
	@MapField(DatabaseField = PilotTourIDField)
	public int getPilotTourID() {
		return pilotTourID;
	}
	
	@MapField(DatabaseField = PilotTourIDField)
	public void setPilotTourID(int tourDate) {
		this.pilotTourID = tourDate;
	}
	
	@MapField(DatabaseField = WorkerIDField)
	public int getWorkerID() {
		return workerID;
	}

	@MapField(DatabaseField = WorkerIDField)
	public void setWorkerID(int workerID) {
		this.workerID = workerID;
	}

	@MapField(DatabaseField = EmploymentIDField)
	public int getEmploymentID() {
		return employmentID;
	}

	@MapField(DatabaseField = EmploymentIDField)
	public void setEmploymentID(int id) {
		this.employmentID = id;
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

	@MapField(DatabaseField = BaseObject.IDField)
	public int getId() {
		return id;}
		
	@MapField(DatabaseField = BaseObject.IDField)
	public void setId(int id) {
		this.id = id;
	}
		
	public Worker getWorker() {
		if (worker != null && worker.getId() == getWorkerID())
			return worker;
		worker = WorkerManager.Instance().load(workerID);
		return worker;
	}

	protected void clear() {
		setId(BaseObject.EMPTY_ID);
		setWorkerID(BaseObject.EMPTY_ID);
		setPilotTourID(BaseObject.EMPTY_ID);
		setEmploymentID(BaseObject.EMPTY_ID);	
		setServerPort(4448);		
		setServerIP("192.168.0.138");
	}

	public String getVersion() {
		try {
			return StaticResources
					.getBaseContext()
					.getPackageManager()
					.getPackageInfo(
							StaticResources.getBaseContext().getPackageName(),
							0).versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getPhoneNumber() {
		return phoneManager.getLine1Number() == null ? "" : phoneManager.getLine1Number().toString();
	}

	public String getDeviceID() {
		return phoneManager.getDeviceId() == null ? "" : phoneManager.getDeviceId().toString();
	}
	
	public static Option Instance() {
		if (instance == null){
			OptionManager optionManager = new OptionManager();
			optionManager.open();
			instance = optionManager.loadOption();
			instance.optionManager = optionManager;
		}			
		return instance;
	}

	public Option() {
		clear();
	}

	public void save(){
		optionManager.save(Option.Instance());
	}

}
