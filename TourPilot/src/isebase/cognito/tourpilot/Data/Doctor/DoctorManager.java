package isebase.cognito.tourpilot.Data.Doctor;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import isebase.cognito.tourpilot.Data.Address.Address;
import isebase.cognito.tourpilot.Data.Address.AddressManager;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Utils.Utilizer;

public class DoctorManager extends BaseObjectManager<Doctor>{

	public static final String TableName = "Doctors";
	
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
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
			
	}
	
	@Override
	public void afterLoad(Doctor item) {
		item.address = AddressManager.Instance().load(item.getAddressID());
	}
	
	@Override
	public void afterLoad(List<Doctor> items) {
		String IDs = Utilizer.getIDsString(items);
		List<Address> addresses = AddressManager.Instance().loadByIDs(IDs);
		for(Doctor relative : items){
			for(Address address : addresses){
				if(address.getId() == relative.getAddressID()){
					relative.address = address;
					break;
				}
			}
		}		
	}
	
	@Override
	public void afterSave(Doctor item) {
		AddressManager.Instance().save(item.address);
	}
}
