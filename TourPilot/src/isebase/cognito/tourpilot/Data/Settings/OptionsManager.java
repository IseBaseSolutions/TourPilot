package isebase.cognito.tourpilot.Data.Settings;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

public class OptionsManager extends BaseObjectManager<Option> {

	private static OptionsManager instance;

	public static OptionsManager Instance() {
		if (instance != null)
			return instance;
		instance = new OptionsManager();
		instance.open();
		return instance;
	}

	public OptionsManager() {
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
