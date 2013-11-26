package isebase.cognito.tourpilot.Data.Work;

import isebase.cognito.tourpilot.Connection.ServerCommandParser;
import isebase.cognito.tourpilot.Data.AdditionalWork.AdditionalWork;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.DataInterfaces.Job.IJob;
import isebase.cognito.tourpilot.Utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Work extends BaseObject implements IJob {
	
	public static final String AdditionalWorkIDField = "add_work_id";
	public static final String PilotTourIDField = "pilot_tour_id";
	public static final String StartTimeField = "start_time";
	public static final String StopTimeField = "stop_time";
	public static final String ManualTimeField = "manual_time";
	public static final String PatientIDsField = "patient_ids";
	public static final String IsDoneField = "is_done";
	
	private int additionalWorkID;
	private int pilotTourID;
	
    private Date startTime;
    private Date stopTime;
    private Date manualTime;

	private String patientIDs;
	
	private boolean isDone;
	
	private AdditionalWork additionalWork;
	
	private List<Patient> patients = new ArrayList<Patient>(); 
	
	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public AdditionalWork getAdditionalWork() {
		return additionalWork;
	}
	
	public void setAdditionalWork(AdditionalWork additionalWork) {
		this.additionalWork = additionalWork;
	}
	@MapField(DatabaseField = AdditionalWorkIDField)
	public int getAdditionalWorkID() {
		return additionalWorkID;
	}

	@MapField(DatabaseField = AdditionalWorkIDField)
	public void setAdditionalWorkID(int additionalWorkID) {
		this.additionalWorkID = additionalWorkID;
	}

	@MapField(DatabaseField = PilotTourIDField)
	public int getPilotTourID() {
		return pilotTourID;
	}

	@MapField(DatabaseField = PilotTourIDField)
	public void setPilotTourID(int pilotTourID) {
		this.pilotTourID = pilotTourID;
	}

	@MapField(DatabaseField = StartTimeField)
	public Date getStartTime() {
		return startTime;
	}

	@MapField(DatabaseField = StartTimeField)
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@MapField(DatabaseField = StopTimeField)
	public Date getStopTime() {
		return stopTime;
	}

	@MapField(DatabaseField = StopTimeField)
	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	@MapField(DatabaseField = PatientIDsField)
	public String getPatientIDs() {
		return patientIDs;
	}

	@MapField(DatabaseField = PatientIDsField)
	public void setPatientIDs(String patientIDs) {
		this.patientIDs = patientIDs;
	}
	
	@MapField(DatabaseField = ManualTimeField)
    public Date getManualTime() {
		return manualTime;
	}

	@MapField(DatabaseField = ManualTimeField)
	public void setManualTime(Date manualTime) {
		this.manualTime = manualTime;
	}
	
	@MapField(DatabaseField = IsDoneField)
	public boolean getIsDone() {
		return isDone;
	}
	
	@MapField(DatabaseField = IsDoneField)
	public void setIsDone(boolean isDone) {
		this.isDone = isDone;
	}

    public Work()
    {
    	clear();
    }
	
    public Work(Date startDate, int addWorkID, int pilotTourID, String name)
    {
        setStartTime(startDate);
        setAdditionalWorkID(addWorkID);
        setPilotTourID(pilotTourID);
        setName(name);
    }
    
    public Work(Date startDate, Date stopDate, Date manualtime, int addWorkID, int pilotTourID, String name)
    {
        setStartTime(startDate);
        setStopTime(stopDate);
        setManualTime(manualtime);
        setAdditionalWorkID(addWorkID);
        setPilotTourID(pilotTourID);
        setName(name);
    }
        
    //W;358;1385194841000;1385196041000;2;1002012;;1385227538953
	@Override
	public String forServer() {
        String strValue = new String(ServerCommandParser.WORK + ";");
        strValue += Option.Instance().getWorkerID() + ";";
        strValue += getStartTime().getTime() + ";";
        strValue += getStopTime().getTime() + ";";
        strValue += getAdditionalWorkID() + ";";
        strValue += getPilotTourID() + ";";
        strValue += getPatientIDs() + ";";
        strValue += !getManualTime().equals(DateUtils.EmptyDate) ? getManualTime() : ""; 
        return strValue;
	}
	
	public String getDone() {
		return forServer();
	}
	
	@Override
	protected void clear() {
		setManualTime(DateUtils.EmptyDate);
		setPatientIDs("");
		super.clear();
	}
	
	public String getTime() {
		return DateUtils.HourMinutesFormat.format(getStartTime());
	}

	@Override
	public String timeInterval() {
		return String.format("%s-%s", DateUtils.HourMinutesFormat.format(getStartTime()), 
				DateUtils.HourMinutesFormat.format(getStopTime()));
	}

	@Override
	public boolean isDone() {
		return getIsDone();
	}

	@Override
	public String text() {
		return getName();
	}

	@Override
	public String time() {
		return getTime();
	}

	@Override
	public boolean wasSent() {
		return getWasSent();
	}

	@Override
	public Date startTime() {
		return getStartTime();
	}

	@Override
	public Date stopTime() {
		return getStopTime();
	}

}
