package isebase.cognito.tourpilot.Data.Relative;

import isebase.cognito.tourpilot.Data.Address.Address;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectDAO;
import isebase.cognito.tourpilot.DataBase.HelperFactory;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.support.ConnectionSource;

public class RelativeDAO extends BaseObjectDAO<Relative> {

	public RelativeDAO(ConnectionSource connectionSource,
			Class<Relative> dataClass) throws SQLException {
		super(connectionSource, dataClass);
	}
	
	@Override
	public void afterLoad(Relative item) {
		item.address = HelperFactory.getHelper().getAddressDAO().load(item.getAddressID());
	}
	

	@Override
	public void afterLoad(List<Relative> items) {
		int[] addressIDs = new int[items.size()];
		for(int i = 0;i< items.size(); i++)
			addressIDs[i] = items.get(i).getAddressID();
		List<Address> addresses = HelperFactory.getHelper().getAddressDAO().loadByIds(addressIDs);
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
		HelperFactory.getHelper().getAddressDAO().save(item.address);
		item.setAddressID(item.address.getId());
	}	

}
