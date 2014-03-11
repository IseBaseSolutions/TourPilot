package isebase.cognito.tourpilot.Data.Question.QuestionSetting;

import java.util.List;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Question.ExtraCategory.ExtraCategory;
import isebase.cognito.tourpilot.Data.Question.ExtraCategory.ExtraCategoryManager;
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
	
	@Override
	public void afterLoad(List<QuestionSetting> items) {
		for (QuestionSetting questionSetting : items)
			afterLoad(questionSetting);
	}
	
	@Override
	public void afterLoad(QuestionSetting questionSetting) {
		ExtraCategory extraCategoryManager = ExtraCategoryManager.Instance().load(Option.Instance().getEmploymentID());
		if (extraCategoryManager == null)
			return;
		questionSetting.setExtraCategoryIDsString(extraCategoryManager.getExtraCategoryIDsString());
	}
	
}
