package isebase.cognito.tourpilot.Data.Task;

import isebase.cognito.tourpilot.Connection.ServerCommandParser;
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
	public static final String TourCodeField = "tour_code";
	public static final String EmploymentIDField = "employment_id";
	public static final String IsAdditionalTaskField = "additional_task";
	public static final String TaskIDField = "task_id";

	public enum eTaskState {
		Empty, Done, UnDone
	}

	private eTaskState taskState = eTaskState.Empty;

	private Date planDate;

	private String leistungs;

	private int taskID;
	private int workerID;
	private int minutePrice;

	private long tourId;
	private long employmentId;

	private boolean isAdditionaltask;
	
	@MapField(DatabaseField = TaskIDField)
	public int getTaskID() {
		return taskID;
	}

	@MapField(DatabaseField = TaskIDField)
	public void setTaskID(int taskID) {
		this.taskID = taskID;
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

	@MapField(DatabaseField = TourCodeField)
	public long getTourCode() {
		return tourId;
	}

	@MapField(DatabaseField = TourCodeField)
	public void setTourCode(long tourId) {
		this.tourId = tourId;
	}

	@MapField(DatabaseField = EmploymentIDField)
	public long getEmploymentId() {
		return employmentId;
	}

	@MapField(DatabaseField = EmploymentIDField)
	public void setEmploymentId(long employmentId) {
		this.employmentId = employmentId;
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
		setTaskID(Integer.parseInt(parsingString.next(";")));
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
			setName(parsingString.next(";"));
			setTaskState(eTaskState.Empty);
		} else {
			setName(str);
			setTaskState(eTaskState.Empty);
			// setTaskState(eTaskState.Empty);
			// if (getLeistungs().contains("+"))
			// fld_addTaskIdentID = GetAddTaskIDFromLeist(getLeistungs());
			// else {
			// int zIndex = getLeistungs().indexOf("Z");
			// fld_addTaskIdentID = Integer.valueOf(fld_Leistungs.substring(
			// zIndex + 1, zIndex + 3))
			// + ";"
			// + Integer.valueOf(getLeistungs().substring(zIndex + 3,
			// zIndex + 6));
			// }
		}
		setTourCode(Long.parseLong(parsingString.next(";")));
		setEmploymentId(Long.parseLong(parsingString.next("~")));
		setCheckSum(Long.parseLong(parsingString.next()));
	}

	@Override
	protected void clear() {
		super.clear();
		setTaskState(eTaskState.Empty);
		setPlanDate(DateUtils.EmptyDate);
		setLeistungs("");
		setTourCode(0);
		setEmploymentId(EMPTY_ID);
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

}
