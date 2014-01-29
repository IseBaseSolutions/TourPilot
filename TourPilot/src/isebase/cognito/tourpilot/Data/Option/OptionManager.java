package isebase.cognito.tourpilot.Data.Option;

import java.util.List;
import android.database.sqlite.SQLiteDatabase;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

class OptionManager extends BaseObjectManager<Option> {
	
	protected OptionManager() {
		super(Option.class);
	}

	@Override
	public String getRecTableName() {
		return Option.TableName;
	}
	
	public Option loadOption() {
		List<Option> options = load();
		if(options.size() > 0)
			return options.get(0);
		Option newOption = new Option();
		save(newOption);
		return newOption;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
		db.execSQL("ALTER TABLE " + Option.TableName + " ADD COLUMN + " + Option.IsLockOptionsField + " INTEGER DEFAULT 0");
	}
}
