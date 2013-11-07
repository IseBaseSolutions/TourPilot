package isebase.cognito.tourpilot.Data.Doctor;

import android.database.sqlite.SQLiteDatabase;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

public class DoctorManager extends BaseObjectManager<Doctor>{

	private static DoctorManager instance;

	public static DoctorManager Instance() {
		if (instance != null)
			return instance;
		instance = new DoctorManager();
		instance.open();
		return instance;
	}
	
	public DoctorManager() {
		super(Doctor.class);
	}

	@Override
	public String getRecTableName() {		
		return "Doctors";
	}

	@Override
	public void onUpdate(SQLiteDatabase db) {
			
	}

}
