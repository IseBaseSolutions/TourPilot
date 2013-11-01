package isebase.cognito.tourpilot.StaticResources;

import android.content.Context;

public class StaticResources {

	private static Context baseContext;

	public final static String stringEmpty = new String();

	public static void setBaseContext(Context context) {
		baseContext = context;
	}

	public static Context getBaseContext() {
		return baseContext;
	}

	public static boolean checkWorkerPIN(String workerName, String strPin) {
		if (strPin.equals(stringEmpty))
			return false;
		Long pin = Long.parseLong(strPin);
		long num = 0;
		int numArray[] = new int[] { 1, 3, 5, 7, 13, 0x11 };
		try {
			byte byteText[] = workerName.getBytes("latin1");
			for (int i = 0; i < byteText.length; i++)
				num += (byteText[i]) * numArray[i % 6];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num == pin;
	}

}
