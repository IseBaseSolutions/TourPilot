package isebase.cognito.tourpilot.Gps;

import isebase.cognito.tourpilot.Data.Address.Address;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.content.Intent;
import android.net.Uri;

public class Gps {

	public static void startGpsNavigation(Address address) {
		String address2 = String.format("google.navigation:q=%s+%s+%s", address.getCity(), address.getStreet().replace(" ", "+"), address.getZip());
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(address2));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		StaticResources.getBaseContext().startActivity(intent);
	}

}
