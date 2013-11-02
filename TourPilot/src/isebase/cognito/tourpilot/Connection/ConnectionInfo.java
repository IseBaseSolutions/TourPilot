package isebase.cognito.tourpilot.Connection;

import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionInfo {

	private NetworkInfo networkInfo;
	
	private static ConnectionInfo instance;
	
	public static ConnectionInfo Instance() {
		if (instance != null)
			return instance;
		instance = new ConnectionInfo();
		return instance;
	}

	public NetworkInfo getNetWorkInfo() {
		return networkInfo;
	}

	public ConnectionInfo() {
		ConnectivityManager connMgr = (ConnectivityManager) StaticResources
				.getBaseContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		networkInfo = connMgr.getActiveNetworkInfo();
	}

}