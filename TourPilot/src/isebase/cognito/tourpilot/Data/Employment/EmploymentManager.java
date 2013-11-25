package isebase.cognito.tourpilot.Data.Employment;

import java.util.List;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
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
		String strSQL = String.format("INSERT INTO %4$s" +
				"(_id, patient_id, name, was_sent, checksum, is_server_time" +
				", pilot_tour_id, date, tour_id, is_done) SELECT " +
				"t1.employment_id as _id, " +
				"t1.patient_id as patient_id, " +
				"(t2.surname || ', ' || t2.name) as name, " +
				"t1.was_sent as was_sent, " +
				"t1.checksum as checksum, " +
				"t1.is_server_time as is_server_time, " +
				"t1.pilot_tour_id as pilot_tour_id, " +
				"t1.plan_date as date, " +
				"t1.tour_id as tour_id, " +
				"t1.task_state as is_done " +
				"FROM %1$s t1 " +
				"INNER JOIN %2$s t2 on t1.patient_id = t2._id " +
				"GROUP BY t1.employment_id"
				, TaskManager.TableName
				, PatientManager.TableName
				, getRecTableName()
				, EmploymentManager.TableName);
		execSQL(strSQL);
	}
	
	@Override

	public void afterLoad(Employment item) {
		item.setPatient(PatientManager.Instance().load(item.getPatientID()));		
	}

	public void afterLoad(List<Employment> items) {
		for (Employment employment : items)
		{
			employment.setTasks(TaskManager.Instance().load(Task.EmploymentIDField, String.valueOf(employment.getId())));
			employment.setPatient(PatientManager.Instance().load(employment.getPatientID()));
			employment.setPilotTour(PilotTourManager.Instance().load(employment.getPilotTourID()));
		}
	}
	
    public String getDone()
    {
    	List<Employment> employments = loadAll();
    	if (employments.size() == 0)
    		return "";  
    	String strEmpls = "";
    	for (Employment employment : employments)
    		if (employment.getIsDone() && !employment.getWasSent())
    		{
    			strEmpls += employment.getDone();
    			employment.setWasSent(true);
    			save(employment);
    		}
    	return strEmpls;
    }
}
