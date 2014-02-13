package isebase.cognito.tourpilot.Data.Question.Link;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import android.database.sqlite.SQLiteDatabase;

public class LinkManager extends BaseObjectManager<Link> {

	public static final String TableName = "Links";
	
	private static LinkManager instance;

	public static LinkManager Instance() {
		if (instance != null)
			return instance;
		instance = new LinkManager();
		instance.open();
		return instance;
	}
	
	public LinkManager() {
		super(Link.class);
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
