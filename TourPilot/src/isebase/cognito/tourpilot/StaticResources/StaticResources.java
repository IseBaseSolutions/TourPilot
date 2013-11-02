package isebase.cognito.tourpilot.StaticResources;

import android.content.Context;

public class StaticResources {

	private static Context baseContext;

	public static void setBaseContext(Context context) {
		baseContext = context;
	}

	public static Context getBaseContext() {
		return baseContext;
	}
	
}
