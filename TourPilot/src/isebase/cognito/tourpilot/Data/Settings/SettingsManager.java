package isebase.cognito.tourpilot.Data.Settings;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

public class SettingsManager extends BaseObjectManager<Settings> {

	private static SettingsManager instance;

	public static SettingsManager Instance() {
		if (instance != null)
			return instance;
		instance = new SettingsManager();
		instance.open();
		return instance;
	}

	public SettingsManager() {
		super(Settings.class);
	}

	@Override
	public String getRecTableName() {
		return dbHelper.SETTINGS;
	}

}
