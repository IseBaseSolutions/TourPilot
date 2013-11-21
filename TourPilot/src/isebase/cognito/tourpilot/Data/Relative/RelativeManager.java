package isebase.cognito.tourpilot.Data.Relative;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import isebase.cognito.tourpilot.Data.Address.Address;
import isebase.cognito.tourpilot.Data.Address.AddressManager;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

public class RelativeManager extends BaseObjectManager<Relative>{

	public static final String TableName = "Relatives";
	
	private static RelativeManager instance;

	public static RelativeManager Instance() {
		if (instance != null)
			return instance;
		instance = new RelativeManager();
		instance.open();
		return instance;
	}
	
	public RelativeManager() {
		super(Relative.class);
	}

	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
				
	}
	
	@Override
	public void afterLoad(Relative item) {
		item.address = AddressManager.Instance().load(item.getAddressID());
	}
	
	@Override
	public void afterLoad(List<Relative> items) {
		int[] addressIDs = new int[items.size()];
		for(int i = 0;i< items.size(); i++)
			addressIDs[i] = items.get(i).getAddressID();
		List<Address> addresses = AddressManager.Instance().loadByIDs(addressIDs);
		for(Relative relative : items){
			for(Address address : addresses){
				if(address.getId() == relative.getAddressID()){
					relative.address = address;
					break;
				}
			}
		}		
	}
	
	@Override
	public void beforeSave(Relative item) {
		AddressManager.Instance().save(item.address);
		item.setAddressID(item.address.getId());
	}
}
