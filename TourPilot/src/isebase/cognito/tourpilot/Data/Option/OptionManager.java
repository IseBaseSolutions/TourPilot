package isebase.cognito.tourpilot.Data.Option;

import android.database.sqlite.SQLiteDatabase;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

public class OptionManager extends BaseObjectManager<Option> {

	private static OptionManager instance;

	public static OptionManager Instance() {
		if (instance != null)
			return instance;
		instance = new OptionManager();
		instance.open();
		return instance;
	}

	public OptionManager() {
		super(Option.class);
	}

	@Override
	public String getRecTableName() {
		return "Options";
	}

	@Override
	public void save(Option object) {
		if (load().size() <= 0)
			super.save(object);
	}

	public Option loadOption() {
		return super.load().size() > 0 ? super.load().get(0) : null;
	}

	@Override
	public void onUpdate(SQLiteDatabase db) {
			
	}

}
