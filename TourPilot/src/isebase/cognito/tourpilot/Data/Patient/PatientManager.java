package isebase.cognito.tourpilot.Data.Patient;

import isebase.cognito.tourpilot.Data.Address.Address;
import isebase.cognito.tourpilot.Data.Address.AddressManager;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;

public class PatientManager extends BaseObjectManager<Patient> {

	public static final String TableName = "Patients";
	
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
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
			
	}
	
	@Override
	public void afterLoad(Patient item) {
		item.address = AddressManager.Instance().load(item.getAddressID());
	}
	
	@Override
	public void afterLoad(List<Patient> items) {
		int[] addressIDs = new int[items.size()];
		for(int i = 0;i< items.size(); i++)
			addressIDs[i] = items.get(i).getAddressID();

		List<Address> addresses = AddressManager.Instance().loadByIDs(addressIDs);
		for(Patient pat : items){
			for(Address address : addresses){
				if(address.getID() == pat.getAddressID()){
					pat.address = address;
					break;
				}
			}
		}		
	}
	
	@Override
	public void beforeSave(Patient item) {
		AddressManager.Instance().save(item.address);
		item.setAddressID(item.address.getID());
	}
	
	public List<Patient> loadByPilotTourID(int tourPilotID) {
		String strSQL = String.format("SELECT t1.* " +
				"FROM %1$s as t1 " +
				"INNER JOIN %2$s as t2 ON t1._id = t2.patient_id " +
				"WHERE t2.%3$s = %4$d GROUP BY t2._id "
				, PatientManager.TableName
				, EmploymentManager.TableName
				, Employment.PilotTourIDField
				, tourPilotID);
		return load(strSQL);
	}
	
}
