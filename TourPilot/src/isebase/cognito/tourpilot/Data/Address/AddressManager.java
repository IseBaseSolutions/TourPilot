package isebase.cognito.tourpilot.Data.Address;

import android.database.sqlite.SQLiteDatabase;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

public class AddressManager extends BaseObjectManager<Address>{

	public static final String TableName = "Address";
	
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
