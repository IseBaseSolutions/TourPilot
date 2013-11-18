package isebase.cognito.tourpilot.Data.Employment;

import java.util.List;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Option.Option;
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
		String strEmplIDs = "";
		List<Employment> currentEmpls = load();
		for (Employment currentEmpl : currentEmpls)
			strEmplIDs += (strEmplIDs.equals("") ? "" : ",") + currentEmpl.getId(); 
		String strSQL = String.format("SELECT " +
				"t1.employment_id as _id, " +
				"t1.patient_id as patient_id, " +
				"t1.pilot_tour_id as pilot_tour_id, " +
				"t1.was_sent, " +
				"t1.is_server_time, " +
				"t1.checksum, " +
				"(t2.surname || ', ' || t2.name) as name, " +
				"t1.plan_date as date, " +
				"t1.tour_id as tour_id, " +
				"0 as is_done " +
				"FROM %1$s t1 " +
				"INNER JOIN %2$s t2 on t1.patient_id = t2._id " +
				"WHERE t1.employment_id NOT IN (%4$s) GROUP BY t1.employment_id"
				, TaskManager.TableName
				, PatientManager.TableName
				, getRecTableName()
				, strEmplIDs); 
		
		List<Employment> createdEmpls = load(strSQL);
		if (createdEmpls.size() > 0)
			save(createdEmpls);
	}
	
	@Override
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
