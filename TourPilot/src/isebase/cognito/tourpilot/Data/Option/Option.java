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
	public static final String PreviousWorkerIDField = "prev_worker_id";
	public static final String ServerIPField = "server_ip";
	public static final String ServerPortField = "server_port";
	public static final String IsAutoField = "is_auto";

	public static boolean testMode = false;
	private Worker worker;
	private TelephonyManager phoneManager = StaticResources.phoneManager;
	private OptionManager optionManager;
	private static Option instance;

	private String serverIP;
	
	private int prevWorkerID;
	private int workerID;
	private int employmentID;
	private int pilotTourID;
	private int serverPort;
	private int id;

	private boolean isAuto;

	@MapField(DatabaseField = PilotTourIDField)
	public void setPilotTourID(int id){
		this.pilotTourID = id;
	}

	@MapField(DatabaseField = PilotTourIDField)
	public int getPilotTourID(){
		return pilotTourID;
	}
	
	@MapField(DatabaseField = PreviousWorkerIDField)
	public void setPrevWorkerID(int id){
		this.prevWorkerID = id;
	}

	@MapField(DatabaseField = PreviousWorkerIDField)
	public int getPrevWorkerID(){
		return prevWorkerID;
	}
	
	@MapField(DatabaseField = IsAutoField)
	public void setIsAuto(boolean isAuto) {
		this.isAuto = isAuto;
	}

	@MapField(DatabaseField = IsAutoField)
	public boolean getIsAuto() {
		return isAuto;
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
		return id;
	}

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
		clearSelected();
	}

	public void clearSelected() {
		prevWorkerID = BaseObject.EMPTY_ID;
		workerID = BaseObject.EMPTY_ID;
		pilotTourID = BaseObject.EMPTY_ID;
		employmentID = BaseObject.EMPTY_ID;
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
		return phoneManager.getLine1Number() == null ? "" : phoneManager
				.getLine1Number().toString();
	}

	public String getDeviceID() {
		return phoneManager.getDeviceId() == null ? "" : phoneManager
				.getDeviceId().toString();
	}

	public static Option Instance() {
		if (instance == null) {
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

	public void save() {
		optionManager.save(Option.Instance());
	}

}
