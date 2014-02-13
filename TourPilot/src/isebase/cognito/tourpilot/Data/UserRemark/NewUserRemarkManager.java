package isebase.cognito.tourpilot.Data.UserRemark;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Employment.Employment;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;

public class NewUserRemarkManager extends BaseObjectManager<NewUserRemark> {

	public static final String TableName = "UserRemarks";
	
	public NewUserRemarkManager(Class<NewUserRemark> entityClass) {
		super(entityClass);
	}
	private static NewUserRemarkManager instance;

	public static NewUserRemarkManager Instance() {
		if (instance != null)
			return instance;
		instance = new NewUserRemarkManager();
		instance.open();
		return instance;
	}
		
	public NewUserRemarkManager() {
		super(NewUserRemark.class);
	}
	
	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {

	}
	
	public List<NewUserRemark> loadByEmploymentID(int employmentID) {
		String strSQL = String.format("SELECT * FROM %1$s " +
				"WHERE %2$s = %3$d"
				, NewUserRemarkManager.TableName
				, Employment.PilotTourIDField
				, employmentID);
		return load(strSQL);
	}
	
}
