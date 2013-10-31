package isebase.cognito.tourpilot.Data.Tour;

import android.content.Context;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;

public class TourManager extends BaseObjectManager<Tour>{

	public TourManager(Context context) {
		super(context, Tour.class);
	}

	@Override
	public String getRecTableName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
