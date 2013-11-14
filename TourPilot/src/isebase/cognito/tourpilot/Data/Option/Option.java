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
	public static final String TourIDField = "tour_id";
	public static final String PatientIDField = "patient_id";
	public static final String ServerIPField = "server_ip";
	public static final String ServerPortField = "server_port";

	private String serverIP;
	public static boolean testMode = false;
	private Worker worker;
	private TelephonyManager phoneManager = StaticResources.phoneManager;;
	private OptionManager optionManager;	
	private static Option instance;
		
	private int workerID;
	private int tourID;
	private int patientID;
	private int serverPort;
	private int id;
	
	public int count;
	
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

	@MapField(DatabaseField = PatientIDField)
	public int getPatientID() {
		return patientID;
	}

	@MapField(DatabaseField = PatientIDField)
	public void setPatientID(int patientID) {
		this.patientID = patientID;
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
		worker = WorkerManager.Instance().loadAll(workerID);
		return worker;
	}

	protected void clear() {
		id = BaseObject.EMPTY_ID;
		workerID = BaseObject.EMPTY_ID;
		tourID = BaseObject.EMPTY_ID;
		patientID = BaseObject.EMPTY_ID;	
		serverPort = 4448;		
		serverIP = "192.168.1.8";
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
