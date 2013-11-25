package isebase.cognito.tourpilot.Data.Task;

import isebase.cognito.tourpilot.Connection.ServerCommandParser;
import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTask;
import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTaskManager;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTour;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTourManager;
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
	public static final String PilotTourIDField = "pilot_tour_id";
	public static final String RealDateField = "real_date";
	public static final String ManualDateField = "manual_date";
	public static final String QualityField = "quality";
	public static final String QualityResultField = "quality_result";
	public static final String CatalogField = "catalog";

	public String getDayPart(){
		return getName().substring(15,getName().length()-1);
	}
	
	public enum eTaskState {
		Empty, Done, UnDone
	}

	private eTaskState taskState = eTaskState.Empty;

	private Date planDate;

	private Date realDate;
	private Date manualDate;

	private String leistungs;
	private String qualityResult;
	
	private int workerID;
	private int minutePrice;
	private int additionalTaskID;
	private int pilotTourID;
	private int quality;
	private int catalog;
		
	private long employmentID;
	private long tourID;
	private long patientID;

	private boolean isAdditionaltask;

	@MapField(DatabaseField = CatalogField)
	public void setCatalog(int catalog){
		this.catalog = catalog;
	}

	@MapField(DatabaseField = CatalogField)
	public int getCatalog(){
		return this.catalog;
	}
	
	@MapField(DatabaseField = QualityResultField)
	public void setQualityResult(String qualityResult){
		this.qualityResult = qualityResult;
	}
	
	@MapField(DatabaseField = QualityResultField)
	public String getQualityResult(){
		return qualityResult;
	}
	
	@MapField(DatabaseField = QualityField)
	public void setQuality(int quality){
		this.quality = quality;
	}

	@MapField(DatabaseField = QualityField)
	public int getQuality(){
		return quality;
	}
	
	@MapField(DatabaseField = RealDateField)
	public Date getRealDate() {
		return realDate;
	}

	@MapField(DatabaseField = RealDateField)
	public void setRealDate(Date realDate) {
		this.realDate = realDate;
	}

	@MapField(DatabaseField = ManualDateField)
	public Date getManualDate() {
		return manualDate;
	}

	@MapField(DatabaseField = ManualDateField)
	public void setManualDate(Date manualDate) {
		this.manualDate = manualDate;
	}
	
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
	public int getStateIndex() {
		return taskState.ordinal();
	}

	@MapField(DatabaseField = StateField)
	public void setStateIndex(int taskStateIndex) {
		this.taskState = eTaskState.values()[taskStateIndex];
	}

	public eTaskState getState() {
		return taskState;
	}

	public void setState(eTaskState taskState) {
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
	
	@MapField(DatabaseField = PilotTourIDField)
	public int getPilotTourID() {
		return pilotTourID;
	}

	@MapField(DatabaseField = PilotTourIDField)
	public void setPilotTourID(int pilotTourID) {
		this.pilotTourID = pilotTourID;
	}

	public Task() {
		clear();
	}

	public Task(AdditionalTask additionalTask){
		clear();
		setAditionalTaskID(additionalTask.getID());
		setIsAdditionalTask(true);
		setName(additionalTask.getName());
		setPlanDate(new Date());
		setWorkerID(Option.Instance().getWorkerID());
		setPilotTourID(Option.Instance().getPilotTourID());
		setEmploymentID(Option.Instance().getEmploymentID());
		PilotTour pilotTour = PilotTourManager.Instance().loadPilotTour(getPilotTourID());
		setTourID(pilotTour.getTourID());
		Employment employment = EmploymentManager.Instance().load(getEmploymentID());
		setPatientID(employment.getPatientID());
		setQuality(additionalTask.getQuality());
		setQualityResult("");
		SimpleDateFormat ddMMyyyyFormat = new SimpleDateFormat("ddMMyyyy");
		String lstStr = TaskManager.Instance().getFirstSymbol(employment.getID()) + "";
		lstStr += additionalTask.getCatalogType();
		lstStr += "Z";
		if ( additionalTask.getCatalogType() < 10 ) lstStr += "0";
		lstStr += additionalTask.getCatalogType();
        if ( additionalTask.getID() < 100 ) lstStr += "0";
        if ( additionalTask.getID() < 10 ) lstStr += "0";
        lstStr += additionalTask.getID();
        lstStr += ddMMyyyyFormat.format(getPlanDate());
		setLeistungs(lstStr);
	}
	
	public Task(String initString) {
		StringParser parsingString = new StringParser(initString);
		setQuality(0);
		setQualityResult("");
		setManualDate(DateUtils.EmptyDate);
		setRealDate(DateUtils.EmptyDate);
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
			setState(eTaskState.Empty);
			setPilotTourID(getPilotTourIDFromLeist());
		} else {
			setName(str);
			setState(eTaskState.Empty);
			setAditionalTaskID(getAddTaskIDFromLeist());
			setQuality(getQualityFromLeist());
			setCatalog(getCatalogFromLeist());
			setName(AdditionalTaskManager.Instance().load(getAddTaskIDFromLeist()).getName());
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
		setQuality(0);
		setQualityResult("");
		setState(eTaskState.Empty);
		setPlanDate(DateUtils.EmptyDate);
		setLeistungs("");
		setTourID(0);
		setPatientID(EMPTY_ID);
		setIsAdditionalTask(false);
		setRealDate(DateUtils.EmptyDate);
		setManualDate(DateUtils.EmptyDate);
	}

	@Override
	public String forServer() {
		if (getWasSent())
			return new String();
		String strValue = new String(ServerCommandParser.TASK + ";");
		strValue += getID() + ";";
		strValue += getCheckSum();
		return strValue;
	}
	
    private int getAddTaskIDFromLeist() {
        return Integer.valueOf(leistungs.split("\\+")[3]);
    }
    
    private int getCatalogFromLeist() {
        return Integer.valueOf(leistungs.split("\\+")[1]);
    }
    
    private int getQualityFromLeist() {
    	String quality = leistungs.split("\\+")[4];
    	int retVal = 0;
    	try{
    		retVal = Integer.valueOf(quality);;
    	}
    	catch(Exception ex){
    		retVal = 0;
    	}
		return retVal;
    }
    
    public int getPilotTourIDFromLeist()
    {
    	int pilotTourID = 0;
    	String[] strArr = {""};
    	try {
    		strArr = leistungs.split("\\+");
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	if (strArr.length == 0 && !leistungs.contains("Anfang") && !leistungs.contains("Ende"))
    		return pilotTourID;
    	return Integer.parseInt(strArr[1]);
    }    
}
