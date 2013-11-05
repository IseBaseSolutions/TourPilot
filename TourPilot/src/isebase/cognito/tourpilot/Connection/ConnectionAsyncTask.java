package isebase.cognito.tourpilot.Connection;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Option.OptionManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Build;

public class ConnectionAsyncTask extends AsyncTask<Void, Void, Void> {

	@Override
	protected void onPostExecute(Void result) {
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			Socket socket = new Socket(OptionManager.Instance().loadOption()
					.getServerIP(), OptionManager.Instance().loadOption()
					.getServerPort());

			OutputStream os = socket.getOutputStream();
			InputStream is = socket.getInputStream();
			mainSync(os, is);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean mainSync(OutputStream os, InputStream is) {
		try {
			String strInvitation = readFromStream(is);
//			if (strInvitation != "OK ")
//				return false;
			writeToStream(os, "GETTIME");
			String strMsg = readFromStream(is);
			os.flush();
			writeToStream(os, "U;-1;-1;:359704043511397;+380636173121@1033" + "\0.\0");
			int b = Integer.parseInt("2");
			strMsg = readFromStream(is);
			int s = Integer.parseInt("2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private String readFromStream(InputStream is) {
		String retVal = new String();	
		try {
			int av = is.available();		
			while (av != 0) {
				byte[] charI = { (byte) is.read() };
				retVal += new String(charI, "cp1252");
				av = is.available();
			}
			} catch (Exception e) {
				e.printStackTrace();
		}
		return retVal;
	}
	
	private void writeToStream(OutputStream os, String text) {
		int timeoutCount = 1200;
		for (int i = 0; i < timeoutCount; i++) {
			try {
				os.write(text.getBytes());
				os.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
	}
	
	private String getMsgHello() {
//		String strMsg = new String("U;");
//		int userID = Option.Instance().getId();
//		if (userID != 0)
//			strMsg += userID;
//		else
//			strMsg += "-1"; // userID
//		strMsg += ";";
//		strMsg += CSettings.Instance().PrevUserID(); // beforeUser.ID()
//		strMsg += ";:";
//		strMsg += CSettings.Instance().DeviceID() + ";";
//		strMsg += CSettings.Instance().PhoneNumber() + "@";
//		strMsg += String.valueOf(GetVersion()) + "\0R;";
//		if (User != null)
//			strMsg += CSettings.Instance().TroubleFlags();
//		else
//			strMsg += "0";
//		strMsg += "\0x1\0";
//		String strDone = new String();
//		strDone += CEmployments.Instance().GetDone();
//
//		strDone += CUserWorks.Instance().GetDone();
//
//		if (User != null)
//			strDone += CInformations.Instance().GetDone();
//
//		strDone += CLogs.Instance().GetDone();
//
//		strDone += CUserRemarks.Instance().GetDone();
//
//		strDone += CMergedEmploymentTimes.Instance().GetDone(); // Andrew
//
//		strDone += CCoordinates.Instance().GetDone(); // Andrew
//
//		String patAnswers[] = CPatientAnswers.Instance().GetDone().split("\0");
//		ArrayList<String> patAnsw = new ArrayList<String>();
//		for (int i = 0; i < patAnswers.length; i++) {
//			if (!patAnsw.contains(patAnswers[i]))
//				patAnsw.add(patAnswers[i]);
//		}
//		for (String str : patAnsw) {
//			strDone += str + "\0";
//		}
//
//		strDone += CFreeAnswers.Instance().GetDone();
		
		return "";
	}

}
