package isebase.cognito.tourpilot.Data.Address;

import android.database.sqlite.SQLiteDatabase;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Relative.Relative;

public class AddressManager extends BaseObjectManager<Address>{

	public static final String TableName = "Address";
	
	private static AddressManager instance;

	public static AddressManager Instance() {
		if (instance != null)
			return instance;
		instance = new AddressManager();
		instance.open();
		return instance;
	}
	
	
	public AddressManager() {
		super(Address.class);
	}

	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
		
	}

}
