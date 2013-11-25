package isebase.cognito.tourpilot.Data.Employment;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTour;
import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.Data.Task.Task.eTaskState;
import isebase.cognito.tourpilot.Data.Task.TaskManager;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.DataInterfaces.Job.IJob;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import isebase.cognito.tourpilot.Utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Employment extends BaseObject implements IJob {
	
	public static final String PatientIDField = "patient_id";
	public static final String PilotTourIDField = "pilot_tour_id";
	public static final String TourIDField = "tour_id"; 
	public static final String DateField = "date";
	public static final String IsDoneField = "is_done";
	public static final String StartTimeField = "start_time";
	public static final String StopTimeField = "stop_time";
	
	private boolean isDone;
	
	private int patientID;	
	private int pilotTourID;
	
	private Date date;
	private Date startTime;
	private Date stopTime;

	private int tourID;
		
	private List<Task> tasks =  new ArrayList<Task>();
	
	private Patient patient;
	private PilotTour pilotTour;

	@MapField(DatabaseField = IsDoneField)
	public boolean getIsDone() {
		return isDone;
	}

	@MapField(DatabaseField = IsDoneField)
	public void setIsDone(boolean isDone) {
		this.isDone = isDone;
	}
	
	@MapField(DatabaseField = DateField)
	public Date getDate() {
		return date;
	}

	@MapField(DatabaseField = DateField)
	public void setDate(Date date) {
		this.date = date;
	}
	
	@MapField(DatabaseField = TourIDField)
	public int getTourID() {
		return tourID;
	}

	@MapField(DatabaseField = TourIDField)
	public void setTourID(int id) {
		this.tourID = id;
	}
	
	@MapField(DatabaseField = PatientIDField)
	public int getPatientID() {
		return patientID;
	}

	@MapField(DatabaseField = PatientIDField)
	public void setPatientID(int patientID) {
		this.patientID = patientID;
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


	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public PilotTour getPilotTour() {
		return pilotTour;
	}

	public void setPilotTour(PilotTour pilotTour) {
		this.pilotTour = pilotTour;
	}	
	
	public Employment() {
		
	}
	
	@Override
	public String toString() {
		return getName() + "  " + DateUtils.HourMinutesFormat.format(getDate());
	}

	public String getTime() {
		return DateUtils.HourMinutesFormat.format(startTime());
	}
		
	@Override
	public String forServer() {
		return "";
	}
	
    public String getDone()
    {
        if (!getIsDone())
        	return "";
        String strValue = "";
        String strEmpl = "E;";
        strEmpl += Option.Instance().getWorkerID() + ";";
        strEmpl += getPatientID() + ";";
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        for (Task task : tasks)
        {
        	String strTask = strEmpl;
            strTask += format.format(task.getPlanDate()) + ";"; // date only
            strTask += task.getPlanDate().toString().substring(11,13); // time hours
            strTask += task.getPlanDate().toString().substring(14,16) + ";"; // time minutes
            strTask += task.getLeistungs() + ";";
            strTask += (task.getState().equals(eTaskState.Done) ? "ja" : "nein"); 
            String qulityResult = task.getQualityResult().isEmpty() ? "" : "$" + task.getQualityResult();
            strTask += qulityResult;
            strTask += ";";
            if (!DateUtils.EmptyDate.equals(task.getManualDate()))
                strTask += DateUtils.toDateTime(task.getManualDate()) + ";";
            strTask += DateUtils.toDateTime(task.getRealDate());
            strTask += "\0";
            strValue += strTask;
            task.setWasSent(true);            
        }
        TaskManager.Instance().save(tasks);
        return strValue;
    }
    
    public Task getFirstTask() {
    	if (tasks.size() == 0)
    		return null;
    	if (tasks.get(0).getLeistungs().contains("Anfang"))
    		return tasks.get(0);
    	for (Task task : tasks)
    		if (task.getLeistungs().contains("Anfang"))
    			return task;
    	return null;
    }
    
    public Task getLastTask() {
    	if (tasks.size() == 0)
    		return null;
    	if (tasks.get(tasks.size()-1).getLeistungs().contains("Ende"))
    		return tasks.get(tasks.size()-1);
    	for (Task task : tasks)
    		if (task.getLeistungs().contains("Ende"))
    			return task;
    	return null;
    }

	@Override
	public String timeInterval() {
		return String.format("%s-%s", (getStartTime().equals(DateUtils.EmptyDate) 
				? StaticResources.getBaseContext().getString(R.string.empty_time) 
						: DateUtils.HourMinutesFormat.format(getStartTime())), 
				(getStopTime().equals(DateUtils.EmptyDate) 
						? StaticResources.getBaseContext().getString(R.string.empty_time)
								: DateUtils.HourMinutesFormat.format(getStopTime())));
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
		return getStartTime().equals(DateUtils.EmptyDate) ? getDate() : getStartTime();
	}

	@Override
	public Date stopTime() {
		return getStopTime().equals(DateUtils.EmptyDate) ? DateUtils.EmptyDate : getStopTime();
	}
	
}
