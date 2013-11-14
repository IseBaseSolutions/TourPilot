package isebase.cognito.tourpilot.Data.Task;

import isebase.cognito.tourpilot.Connection.ServerCommandParser;
import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTaskManager;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.DateUtils;
import isebase.cognito.tourpilot.Utils.StringParser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task extends BaseObject {

	public static final String StateField = "task_state";
	public static final String WorkerIDField = "worker_id";
	public static final String PlanDateField = "plan_date";
	public static final String LeistungsField = "leistungs";
	public static final String MinutePriceField = "minute_price";
	public static final String TourIDField = "tour_id";
	public static final String PatientIDField = "patient_id";
	public static final String IsAdditionalTaskField = "additional_task";
	public static final String AdditionalTaskIDField = "additional_task_id";
	public static final String EmploymentIDField = "employment_id";

	public enum eTaskState {
		Empty, Done, UnDone
	}

	private eTaskState taskState = eTaskState.Empty;

	private Date planDate;

	private String leistungs;

	
	private int workerID;
	private int minutePrice;
	private int additionalTaskID;

	private long employmentID;
	private long tourID;
	private long patientID;

	private boolean isAdditionaltask;
	
	@MapField(DatabaseField = AdditionalTaskIDField)
	public int getAditionalTaskID () {
		return additionalTaskID;
	}

	@MapField(DatabaseField = AdditionalTaskIDField)
	public void setAditionalTaskID(int additionalTaskID) {
		this.additionalTaskID = additionalTaskID;
	}		
	
	@MapField(DatabaseField = EmploymentIDField)
	public long getEmploymentID() {
		return employmentID;
	}

	@MapField(DatabaseField = EmploymentIDField)
	public void setEmploymentID(long employmentID) {
		this.employmentID = employmentID;
	}	

	@MapField(DatabaseField = WorkerIDField)
	public int getWorkerID() {
		return workerID;
	}

	@MapField(DatabaseField = WorkerIDField)
	public void setWorkerID(int workerID) {
		this.workerID = workerID;
	}

	@MapField(DatabaseField = StateField)
	public int getTaskStateIndex() {
		return taskState.ordinal();
	}

	@MapField(DatabaseField = StateField)
	public void setTaskState(int taskStateIndex) {
		this.taskState = eTaskState.values()[taskStateIndex];
	}

	public eTaskState getTaskState() {
		return taskState;
	}

	public void setTaskState(eTaskState taskState) {
		this.taskState = taskState;
	}

	@MapField(DatabaseField = PlanDateField)
	public Date getPlanDate() {
		return planDate;
	}

	@MapField(DatabaseField = PlanDateField)
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	@MapField(DatabaseField = LeistungsField)
	public String getLeistungs() {
		return leistungs;
	}

	@MapField(DatabaseField = LeistungsField)
	public void setLeistungs(String leistungs) {
		this.leistungs = leistungs;
	}

	@MapField(DatabaseField = MinutePriceField)
	public int getMinutePrice() {
		return minutePrice;
	}

	@MapField(DatabaseField = MinutePriceField)
	public void setMinutePrice(int minutePrice) {
		this.minutePrice = minutePrice;
	}

	@MapField(DatabaseField = TourIDField)
	public long getTourID() {
		return tourID;
	}

	@MapField(DatabaseField = TourIDField)
	public void setTourID(long tourID) {
		this.tourID = tourID;
	}

	@MapField(DatabaseField = PatientIDField)
	public long getPatientID() {
		return patientID;
	}

	@MapField(DatabaseField = PatientIDField)
	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}

	@MapField(DatabaseField = IsAdditionalTaskField)
	public boolean getIsAdditionalTask() {
		return isAdditionaltask;
	}

	@MapField(DatabaseField = IsAdditionalTaskField)
	public void setIsAdditionalTask(boolean isAdditionaltask) {
		this.isAdditionaltask = isAdditionaltask;
	}

	public Task() {
		clear();
	}

	public Task(String initString) {
		StringParser parsingString = new StringParser(initString);
		parsingString.next(";");
		setWorkerID(Integer.parseInt(parsingString.next(";")));
		String strDate = parsingString.next(";");
		String strTime = parsingString.next(";");
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmm");
		try {
			setPlanDate(format.parse(strDate + strTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		setPatientID(Integer.parseInt(parsingString.next(";")));
		setLeistungs(parsingString.next(";"));
		String str = parsingString.next(";");
		if (str.contains("@")) {
			setMinutePrice(Integer.parseInt(str.substring(1)));
			str = parsingString.next(";");
		} else
			setMinutePrice(-1);
		if (str.startsWith("[")) {
			if (!str.contains("Einsatz")) {
				if (getLeistungs().contains("Anfang"))
					str = "[Einsatzbeginn " + str.substring(1);
				if (getLeistungs().contains("Ende"))
					str = "[Einsatzende " + str.substring(1);
			}
			setName(str);
			parsingString.next(";");
			setTaskState(eTaskState.Empty);
		} else {
			setName(str);
			setTaskState(eTaskState.Empty);
			setName(AdditionalTaskManager.Instance().load(GetAddTaskIDFromLeist(getLeistungs())).getName());
//			else 
//			{
//				int zIndex = getLeistungs().indexOf("Z");
//				String adsd = Integer.valueOf(getLeistungs().substring(zIndex + 1, zIndex + 3)) + ";"
//				+ Integer.valueOf(getLeistungs().substring(zIndex + 3,
//				zIndex + 6));
//				int b = 3;
//			}
		}
		setTourID(Long.parseLong(parsingString.next(";")));
		setEmploymentID(Long.parseLong(parsingString.next("~")));
		setCheckSum(Long.parseLong(parsingString.next()));
	}

	@Override
	protected void clear() {
		super.clear();
		setTaskState(eTaskState.Empty);
		setPlanDate(DateUtils.EmptyDate);
		setLeistungs("");
		setTourID(0);
		setPatientID(EMPTY_ID);
		setIsAdditionalTask(false);
	}

	@Override
	public String forServer() {
		if (getWasSent())
			return new String();
		String strValue = new String(ServerCommandParser.TASK + ";");
		strValue += getId() + ";";
		strValue += getCheckSum();
		return strValue;
	}
	
    private int GetAddTaskIDFromLeist(String leist) {
        return Integer.valueOf(leist.split("\\+")[3]);
    }

}
