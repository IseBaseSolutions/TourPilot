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
	
	public List<Patient> loadByOption() {
//		return load(String.format(" SELECT t3.* FROM %1$s AS t1 " +
//					" INNER JOIN %2$s AS t2 ON t1.%3$s = t2.%4$s " +
//					" INNER JOIN %5$s AS t3 ON t2.%6$s = t3.%3$s" +
//					" WHERE t1.%3$s = %7$d AND t2.%8$s / %9$d = %10$d / %9$d GROUP BY t3.%3$s, t2.%11$s", 
//					TourManager.TableName,
//					TaskManager.TableName, 
//					BaseObject.IDField,
//					Task.TourIDField,
//					PatientManager.TableName,
//					Task.PatientIDField,
//					Option.Instance().getTourID(),
//					Task.PlanDateField,
//					DateUtils.DayMillisec,
//					Option.Instance().getPilotTourID().getTime(),
//					Task.EmploymentIDField));
		return null;
	}
	
}
