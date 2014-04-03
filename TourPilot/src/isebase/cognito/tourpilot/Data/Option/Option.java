package isebase.cognito.tourpilot.Data.Option;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;
import isebase.cognito.tourpilot.DataBase.HelperFactory;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.NewData.NewWorker.NewWorker;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.telephony.TelephonyManager;

public class Option {

	public static final String TableName = "Options";

	public static final String WorkerIDField = "worker_id";
	public static final String PilotTourIDField = "pilot_tour_id";
	public static final String EmploymentIDField = "employment_id";
	public static final String PreviousWorkerIDField = "prev_worker_id";
	public static final String WorkIDField = "work_id";
	public static final String ServerIPField = "server_ip";
	public static final String ServerPortField = "server_port";
	public static final String IsAutoField = "is_auto";
	public static final String IsWorkerActivityField = "is_worker_activity";
	public static final String PinField = "pin";
	public static final String ServerTimeDifferenceField = "server_time_difference";
	public static final String IsLockOptionsField = "lock_options";
	public static boolean testMode = false;
	private Worker worker;
	private NewWorker newWorker;
	private TelephonyManager phoneManager = StaticResources.phoneManager;
	private OptionManager optionManager;
	private static Option instance;

	private String serverIP;
	
	private int prevWorkerID;
	private int workerID;
	private int employmentID;
	private int pilotTourID;
	private int workID;
	private int serverPort;
	private int id;
	private long serverTimeDifference;
	private String pin;
	
	private String palmVersion;
	private String versionLink;

	private boolean isAuto;
	private boolean isWorkerActivity;
	private boolean isTimeSynchronised;
	private boolean isLockOptions;

	@MapField(DatabaseField = IsLockOptionsField)
	public boolean isLockOptions() {
		return isLockOptions;
	}

	@MapField(DatabaseField = IsLockOptionsField)
	public void setLockOptions(boolean isLockOptions) {
		this.isLockOptions = isLockOptions;
	}

	@MapField(DatabaseField = PinField)
	public String getPin() {
		return pin;
	}

	@MapField(DatabaseField = PinField)
	public void setPin(String pin) {
		this.pin = pin;
	}
	
	@MapField(DatabaseField = IsWorkerActivityField)
	public boolean isWorkerActivity() {
		return isWorkerActivity;
	}

	@MapField(DatabaseField = IsWorkerActivityField)
	public void setWorkerActivity(boolean isWorkerActivity) {
		this.isWorkerActivity = isWorkerActivity;
	}

	@MapField(DatabaseField = WorkIDField)
	public int getWorkID() {
		return workID;
	}

	@MapField(DatabaseField = WorkIDField)
	public void setWorkID(int workID) {
		this.workID = workID;
	}
	
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
	public int getID() {
		return id;
	}

	@MapField(DatabaseField = BaseObject.IDField)
	public void setID(int id) {
		this.id = id;
	}
	
	@MapField(DatabaseField = ServerTimeDifferenceField)
	public long getServerTimeDifference() {
		return serverTimeDifference;
	}
	
	@MapField(DatabaseField = ServerTimeDifferenceField)
	public void setServerTimeDifference(long serverTimeDifference) {
		this.serverTimeDifference = serverTimeDifference;
	}
	
	public String getPalmVersion() {
		return palmVersion;
	}

	public void setPalmVersion(String palmVersion) {
		this.palmVersion = palmVersion;
	}

	public String getVersionLink() {
		return versionLink;
	}

	public void setVersionLink(String versionLink) {
		this.versionLink = versionLink;
	}
	
	public boolean isTimeSynchronised() {
		return isTimeSynchronised;
	}

	public void setTimeSynchronised(boolean isTimeSynchronised) {
		this.isTimeSynchronised = isTimeSynchronised;
	}

	public Worker getWorker() {
		if (worker != null && worker.getID() == getWorkerID())
			return worker;
		worker = WorkerManager.Instance().load(workerID);
		return worker;
	}
	
	public NewWorker getNewWorker() {
		if (newWorker != null && newWorker.getId() == getWorkerID())
			return newWorker;
		newWorker = HelperFactory.getHelper().getWorkerDAO().load(workerID);
		return newWorker;
	}

	protected void clear() {
		setID(BaseObject.EMPTY_ID);	
		setServerPort(4448);		
		setServerIP("");
		setPin("");
		clearSelected();
	}

	public void clearSelected() {
		prevWorkerID = BaseObject.EMPTY_ID;
		workerID = BaseObject.EMPTY_ID;
		workID = BaseObject.EMPTY_ID;
		pilotTourID = BaseObject.EMPTY_ID;
		employmentID = BaseObject.EMPTY_ID;
	}

	public String getVersion() {
		try {
			return StaticResources.getBaseContext().getPackageManager().
					getPackageInfo(StaticResources.getBaseContext().getPackageName(), 0).versionName;
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
