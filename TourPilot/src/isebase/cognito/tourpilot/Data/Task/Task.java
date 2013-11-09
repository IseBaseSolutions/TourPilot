package isebase.cognito.tourpilot.Data.Task;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.StringParser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task extends BaseObject {

	public static final String StateField = "task_state";

	public enum eTaskState {
		Empty, Done, UnDone
	}

	private eTaskState taskState = eTaskState.Empty;

	private Date planDate;

	private String leistungs;

	private int minutePrice;
	
	private long tourId;
	private long employmentId;

	public Task() {

	}

	public Task(String initString) {
		initString = initString.substring(0, 2);
		StringParser parsingString = new StringParser(initString);
		setId(Integer.parseInt(parsingString.next(";")));
		String strDate = parsingString.next(";");
		String strTime = parsingString.next(";");
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyHHmm");
		try {
			setPlanDate(format.parse(strDate + strTime));
		} catch(Exception e) {
			e.printStackTrace();
		}
		setId(Integer.parseInt(parsingString.next(";")));
		setLeistungs(parsingString.next(";"));
		String str = parsingString.next(";");
		if (str.startsWith("@")) {
			setMinutePrice(Integer.parseInt(str.substring(1)));
			str = parsingString.next(";");
		} else
			setMinutePrice(-1);
		if (str.startsWith("[")) {
			if (str.indexOf("Einsatz") != 1) {
				if (getLeistungs().indexOf("Anfang") != -1)
					str = "[Einsatzbeginn " + str.substring(1);
				if (getLeistungs().indexOf("Ende") != -1)
					str = "[Einsatzende " + str.substring(1);
			}
			setName(str);
			String taskState = parsingString.next(";");
			setTaskState(eTaskState.Empty);
		} else {
//			fld_answer = str;
//			if (getLeistungs().indexOf("+") != -1)
//				fld_addTaskIdentID = GetAddTaskIDFromLeist(fld_Leistungs);
//			else {
//				int zIndex = fld_Leistungs.indexOf("Z");
//				fld_addTaskIdentID = Integer.valueOf(fld_Leistungs.substring(
//						zIndex + 1, zIndex + 3))
//						+ ";"
//						+ Integer.valueOf(fld_Leistungs.substring(zIndex + 3,
//								zIndex + 6));
//			}
		}
		setTourCode(Long.parseLong(parsingString.next(";")));
		setEmploymentId(Long.parseLong(parsingString.next("~")));
		setCheckSum(Long.parseLong(parsingString.next()));
	}

	@MapField(DatabaseField = StateField)
	public void setTaskState(int taskStateIndex) {
		this.taskState = eTaskState.values()[taskStateIndex];
	}

	@MapField(DatabaseField = StateField)
	public int getTaskStateIndex() {
		return taskState.ordinal();
	}

	public void setTaskState(eTaskState taskState) {
		this.taskState = taskState;
	}

	public eTaskState getTaskState() {
		return taskState;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public String getLeistungs() {
		return leistungs;
	}

	public void setLeistungs(String leistungs) {
		this.leistungs = leistungs;
	}

	public int getMinutePrice() {
		return minutePrice;
	}

	public void setMinutePrice(int minutePrice) {
		this.minutePrice = minutePrice;
	}

	public long setTourCode() {
		return tourId;
	}
	
	public void setTourCode(long tourId) {
		this.tourId = tourId;
	}
	
	public long getEmploymentId() {
		return employmentId;
	}
	
	public void setEmploymentId(long employmentId) {
		this.employmentId = employmentId;
	}
		
	@Override
	protected void clear() {
		super.clear();
		setTaskState(eTaskState.Empty);
	}
	
    public String forServer()
    {
        if (getWasSent())
            return new String();
        String strValue = new String("T;");
        strValue += getId() + ";";
        strValue += getCheckSum();
        return strValue;
    }
}
