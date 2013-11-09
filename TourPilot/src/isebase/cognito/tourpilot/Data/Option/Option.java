package isebase.cognito.tourpilot.Data.Option;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.content.Context;
import android.telephony.TelephonyManager;

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

	public static boolean testMode = false;

	private Worker worker;

	private TelephonyManager tMgr;

	private static Option instance;
	
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

	public Worker getWorker() {
		if (worker != null && worker.getId() == getWorkerID())
			return worker;
		worker = WorkerManager.Instance().loadAll(workerID);
		return worker;
	}

	@Override
	protected void clear() {
		super.clear();
		workerID = EMPTY_ID;
		tourID = EMPTY_ID;
		employmentID = EMPTY_ID;
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
		initPhoneManager();
		return (String) (tMgr.getLine1Number() == null ? "" : tMgr
				.getLine1Number());
	}

	public String getDeviceID() {
		initPhoneManager();
		return (String) (tMgr.getDeviceId() == null ? "" : tMgr.getDeviceId());
	}

	private void initPhoneManager() {
		if (tMgr == null)
			tMgr = (TelephonyManager) StaticResources.getBaseContext()
					.getSystemService(Context.TELEPHONY_SERVICE);
	}

}
