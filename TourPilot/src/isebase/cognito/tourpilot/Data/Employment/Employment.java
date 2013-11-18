package isebase.cognito.tourpilot.Data.Employment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTour;
import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.Data.Task.TaskManager;
import isebase.cognito.tourpilot.Data.Task.Task.eTaskState;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.DateUtils;

public class Employment extends BaseObject {
	
	public static final String PatientIDField = "patient_id";
	public static final String PilotTourIDField = "pilot_tour_id";
	public static final String TourIDField = "tour_id"; 
	public static final String DateField = "date";
	public static final String IsDoneField = "is_done";
	
	private boolean isDone;
	
	private int patientID;	
	private int pilotTourID;
	private Date date;
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
		 return DateUtils.HourMinutesFormat.format(getDate());
	}
		
	@Override
	public String forServer() {
		// TODO Auto-generated method stub
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
            strTask += format.format(task.getPlanDate()) + ";";
            strTask += task.getPlanDate().toString().substring(11,13);
            strTask += task.getPlanDate().toString().substring(14,16) + ";";
            strTask += task.getLeistungs() + ";";
            strTask += (task.getState().equals(eTaskState.Done) ? "ja" : "nein") + ";";
            if (!DateUtils.EmptyDate.equals(task.getManualDate()))
                strTask += DateUtils.toString(task.getManualDate()) + ";";
            strTask += DateUtils.toString(task.getRealDate());
            strTask += "\0";
            strValue += strTask;
            task.setWasSent(true);            
        }
        TaskManager.Instance().save(tasks);
        return strValue;
    }
	
}
