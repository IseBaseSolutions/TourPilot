package isebase.cognito.tourpilot.Data.Settings;

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
		return dbHelper.OPTIONS;
	}

	@Override
	public Option add(Option object) {
		if (load().size() > 0)
			return null;
		return super.add(object);
	}

	public Option loadOptions() {
		return super.load().size() > 0 ? super.load().get(0) : null;
	}

}
