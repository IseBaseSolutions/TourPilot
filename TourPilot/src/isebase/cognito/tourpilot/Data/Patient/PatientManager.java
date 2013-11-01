package isebase.cognito.tourpilot.Data.Patient;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

public class PatientManager  extends BaseObjectManager<Patient> {
	
	private static PatientManager instance;
	
	public static PatientManager Instance() {
		if (instance != null)
			return instance;
		instance = new PatientManager();
		instance.open();
		return instance;
	}
	
	public PatientManager() {
		super(Patient.class);
	}
	
	@Override
	public String getRecTableName() {
		return dbHelper.PATIENTS;
	}
}
