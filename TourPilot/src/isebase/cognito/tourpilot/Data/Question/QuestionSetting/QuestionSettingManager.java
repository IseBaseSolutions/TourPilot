package isebase.cognito.tourpilot.Data.Question.QuestionSetting;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import android.database.sqlite.SQLiteDatabase;

public class QuestionSettingManager extends BaseObjectManager<QuestionSetting> {

	public static final String TableName = "QuestionSettings";
	
	private static QuestionSettingManager instance;

	public static QuestionSettingManager Instance() {
		if (instance != null)
			return instance;
		instance = new QuestionSettingManager();
		instance.open();
		return instance;
	}
	
	public QuestionSettingManager() {
		super(QuestionSetting.class);
	}

	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
		// TODO Auto-generated method stub		
	}
	
}
