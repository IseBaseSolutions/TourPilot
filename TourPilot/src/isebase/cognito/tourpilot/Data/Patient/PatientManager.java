package isebase.cognito.tourpilot.Data.Patient;

import isebase.cognito.tourpilot.Data.Address.Address;
import isebase.cognito.tourpilot.Data.Address.AddressManager;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.Data.Task.TaskManager;
import isebase.cognito.tourpilot.Data.Tour.TourManager;
import isebase.cognito.tourpilot.Utils.DateUtils;
import isebase.cognito.tourpilot.Utils.Utilizer;

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
		String IDs = Utilizer.getIDsString(items);
		List<Address> addresses = AddressManager.Instance().loadByIDs(IDs);
		for(Patient pat : items){
			for(Address address : addresses){
				if(address.getId() == pat.getAddressID()){
					pat.address = address;
					break;
				}
			}
		}		
	}
	
	@Override
	public void beforeSave(Patient item) {
		AddressManager.Instance().save(item.address);
		item.setAddressID(item.address.getId());
	}
	
}
