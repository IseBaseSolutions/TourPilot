package isebase.cognito.tourpilot.Data.UserRemark;

import java.util.List;
import android.database.sqlite.SQLiteDatabase;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

public class UserRemarkManager extends BaseObjectManager<UserRemark>{

	public static final String TableName = "UserRemarks";
	
	public UserRemarkManager(Class<UserRemark> entityClass) {
		super(entityClass);
	}
	private static UserRemarkManager instance;

	public static UserRemarkManager Instance() {
		if (instance != null)
			return instance;
		instance = new UserRemarkManager();
		instance.open();
		return instance;
	}
		
	public UserRemarkManager() {
		super(UserRemark.class);
	}
	
	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
		
	}
	
	public UserRemark loadByWorkerPatient(int workerID, int patientID){
		List<UserRemark> items = loadAll(UserRemark.IDField, workerID + " AND " 
					+ UserRemark.PatientIDField + " = " + patientID);
		if(items.size() > 0)
			return items.get(0);
		return new UserRemark(workerID, patientID, false, false, false, false, "");
	}
	
}
