package isebase.cognito.tourpilot.Data.Information;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Utils.DateUtils;

import java.util.Date;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;

public class InformationManager extends BaseObjectManager<Information>{
	
	public static final String TableName = "Informations";
	
	private static InformationManager instance;

	public static InformationManager Instance() {
		if (instance != null)
			return instance;
		instance = new InformationManager();
		instance.open();
		return instance;
	}
	
	public InformationManager() {
		super(Information.class);
	}

	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
			
	}
	
	public static String getInfoStr(List<Information> infos, Date date, boolean isFromMenu) {
		String strInfos = "";
		for(Information info : infos) {
			if(DateUtils.isToday(info.getReadTime()) && !isFromMenu || !info.isActualInfo(date))
				continue;
			strInfos += (strInfos.equals("") ? "" : "\n") + info.getName();
			info.setReadTime(new Date());
			info.setIsServerTime(Option.Instance().isTimeSynchronised());
		}
		if(strInfos.equals(""))
			return "";
		InformationManager.Instance().save(infos);
		return strInfos;
	}

}
