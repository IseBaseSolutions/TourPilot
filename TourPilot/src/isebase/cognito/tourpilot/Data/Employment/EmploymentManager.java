package isebase.cognito.tourpilot.Data.Employment;

import java.util.List;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
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
				"'0' as is_done, " +
				"'0' as is_aborted " +
				"FROM %1$s t1 " +
				"INNER JOIN %2$s t2 on t1.patient_id = t2._id " +
				"GROUP BY t1.employment_id"
				, TaskManager.TableName
				, PatientManager.TableName
				, getRecTableName()); 
		List<Employment> items = load(strSQL);
		save(items);
	}
	
	@Override
	public void afterLoad(Employment item) {
		item.setPatient(PatientManager.Instance().load(item.getPatientID()));		
	}

}
