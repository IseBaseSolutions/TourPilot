package isebase.cognito.tourpilot.Data.Employment;

import java.util.Date;
import java.util.List;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.EmploymentInterval.EmploymentIntervalManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTourManager;
import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.Data.Task.TaskManager;
import android.database.sqlite.SQLiteDatabase;

public class EmploymentManager extends BaseObjectManager<Employment> {

	public static final String TableName = "Employments";
	
	private static EmploymentManager instance;

	public static EmploymentManager Instance() {
		if (instance != null)
			return instance;
		instance = new EmploymentManager();
		instance.open();
		return instance;
	}
	
	public EmploymentManager() {
		super(Employment.class);
	}

	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
		
	}
	
	public void createEmployments() {
		clearTable();
		String firstStr = "'%beginn%'";
		String strSQL = String.format("INSERT INTO %3$s" +
				"(_id, patient_id, name, was_sent, checksum, is_server_time" +
				", pilot_tour_id, date, tour_id, is_done, start_time, stop_time, day_part) SELECT " +
				"t1.employment_id as _id, " +
				"t1.patient_id as patient_id, " +
				"(t2.surname || ', ' || t2.name) as name, " +
				"t1.was_sent as was_sent, " +
				"t1.checksum as checksum, " +
				"t1.is_server_time as is_server_time, " +
				"t1.pilot_tour_id as pilot_tour_id, " +
				"t1.plan_date as date, " +
				"t1.tour_id as tour_id, " +
				"t1.task_state as is_done, " +
				"t3.start_time as start_time, " +
				"t3.stop_time as stop_time, " +
				"t1.name as day_part " +
				"FROM %1$s t1 " +
				"INNER JOIN %2$s t2 on t1.patient_id = t2._id " +
				"LEFT JOIN %4$s t3 on t1.employment_id = t3.employment_id " +
				"WHERE t1.name like %5$s GROUP BY t1.employment_id"
				, TaskManager.TableName
				, PatientManager.TableName
				, EmploymentManager.TableName
				, EmploymentIntervalManager.TableName
				, firstStr);
		execSQL(strSQL);
	}
	
	public void afterLoad(List<Employment> items) {
		for (Employment employment : items)
			afterLoad(employment);
	}
	
	@Override
	public void afterLoad(Employment employment) {
		employment.setTasks(TaskManager.Instance().load(Task.EmploymentIDField, String.valueOf(employment.getID())));
		employment.setPatient(PatientManager.Instance().load(employment.getPatientID()));
		employment.setPilotTour(PilotTourManager.Instance().load(employment.getPilotTourID()));
	}
	
	public List<Employment> loadDoneByPilotTourID(int tourPilotID) {
		String strSQL = String.format("SELECT * " +
				"FROM %1$s " +
				"WHERE %2$s = %3$d AND is_done = 1"
				, EmploymentManager.TableName
				, Employment.PilotTourIDField
				, tourPilotID);
		return load(strSQL);
	}
	
    public String getDone()
    {
    	List<Employment> employments = loadAll(BaseObject.WasSentField, "0 AND is_done = 1");
    	String strEmpls = "";
    	for (Employment employment : employments)
			strEmpls += employment.getDone();
    	execSQL(String.format("update %1$s set was_sent = 1 where was_sent = 0 and is_done = 1"
    			, getRecTableName()));
    	return strEmpls;
    }
    
    public static Employment createEmployment(Patient patient) {
    	int id = 0;
    	while (EmploymentManager.Instance().load(id) != null)
    		id++;
    	Employment employment = new Employment();
    	employment.setID(id);
    	employment.setPatientID(patient.getID());
    	employment.setName(String.format("%s, %s", patient.getSurname(), patient.getName()));
    	employment.setPilotTourID(Option.Instance().getPilotTourID());
    	employment.setDate(new Date());
    	employment.setTourID(PilotTourManager.Instance().loadPilotTour(Option.Instance().getPilotTourID()).getTourID());
    	employment.setDayPart("");
    	EmploymentManager.Instance().save(employment);
    	Task firstTask = new Task(patient, employment.getID(), employment.getTourID(), true);
    	TaskManager.Instance().save(firstTask);
    	Task endtTask = new Task(patient, employment.getID(), employment.getTourID(), false);
    	TaskManager.Instance().save(endtTask);
    	return employment;
    }
    
}
