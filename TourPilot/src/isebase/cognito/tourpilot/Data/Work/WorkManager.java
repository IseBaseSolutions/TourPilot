package isebase.cognito.tourpilot.Data.Work;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;

public class WorkManager extends BaseObjectManager<Work> {

	public static final String TableName = "Works";

	private static WorkManager instance;

	public static WorkManager Instance() {
		if (instance != null)
			return instance;
		instance = new WorkManager();
		instance.open();
		return instance;
	}

	public WorkManager() {
		super(Work.class);
	}

	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
	}
	
	@Override
	public void afterLoad(List<Work> items) {
		for (Work work : items)
		{
			//work.setAdditionalWork(AdditionalWorkManager.Instance().load(work.getAdditionalWorkID()));
			work.setPatients(PatientManager.Instance().loadByIDs(work.getPatientIDs()));
		}
	}
	
	public List<Work> loadDoneByPilotTourID(int tourPilotID) {

		String strSQL = String.format("SELECT * " +
				"FROM %1$s " +
				"WHERE %2$s = %3$d AND is_done = 1"
				, WorkManager.TableName
				, Work.PilotTourIDField
				, tourPilotID);
		return load(strSQL);
	}
}
