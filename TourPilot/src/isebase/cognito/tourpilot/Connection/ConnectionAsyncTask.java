package isebase.cognito.tourpilot.Connection;

import isebase.cognito.tourpilot.Data.Option.OptionManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.zip.GZIPOutputStream;

import android.os.AsyncTask;

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
			if (strInvitation.substring(0, 2).compareTo("OK") != 0)
				return false;
			writeToStream(os, "GETTIME");
			os.flush();
			String strMsg = readFromStream(is);
			writePack(os, "U;-1;-1;:359704043511397;+380636173121@1033"
					+ "\0.\0");
			// writeToStream(os, "U;-1;-1;:359704043511397;+380636173121@1033"
			// + "\0.\0");
			strMsg = readFromStream(is);
			int a = 2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private String readFromStream(InputStream is) throws InterruptedException, IOException {
		String retVal = "";
		int timeoutCount = 30000;
		long startTime = new Date().getTime();
		long currentTime = startTime;
		while (currentTime - startTime < timeoutCount) {
			int av = 0;
			try {
				av = is.available();
			} finally {
				if (av == 0) {
					Thread.sleep(100);
					currentTime = new Date().getTime();
					continue;
				}
			}
			while (av != 0) {
				byte[] charI = { (byte) is.read() };
				retVal += new String(charI, "cp1252");
				av = is.available();
			}
			return retVal;
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
		// String strMsg = new String("U;");
		// int userID = Option.Instance().getId();
		// if (userID != 0)
		// strMsg += userID;
		// else
		// strMsg += "-1"; // userID
		// strMsg += ";";
		// strMsg += CSettings.Instance().PrevUserID(); // beforeUser.ID()
		// strMsg += ";:";
		// strMsg += CSettings.Instance().DeviceID() + ";";
		// strMsg += CSettings.Instance().PhoneNumber() + "@";
		// strMsg += String.valueOf(GetVersion()) + "\0R;";
		// if (User != null)
		// strMsg += CSettings.Instance().TroubleFlags();
		// else
		// strMsg += "0";
		// strMsg += "\0x1\0";
		// String strDone = new String();
		// strDone += CEmployments.Instance().GetDone();
		//
		// strDone += CUserWorks.Instance().GetDone();
		//
		// if (User != null)
		// strDone += CInformations.Instance().GetDone();
		//
		// strDone += CLogs.Instance().GetDone();
		//
		// strDone += CUserRemarks.Instance().GetDone();
		//
		// strDone += CMergedEmploymentTimes.Instance().GetDone(); // Andrew
		//
		// strDone += CCoordinates.Instance().GetDone(); // Andrew
		//
		// String patAnswers[] =
		// CPatientAnswers.Instance().GetDone().split("\0");
		// ArrayList<String> patAnsw = new ArrayList<String>();
		// for (int i = 0; i < patAnswers.length; i++) {
		// if (!patAnsw.contains(patAnswers[i]))
		// patAnsw.add(patAnswers[i]);
		// }
		// for (String str : patAnsw) {
		// strDone += str + "\0";
		// }
		//
		// strDone += CFreeAnswers.Instance().GetDone();

		return "";
	}

	public void writePack(OutputStream os, String strToWrite) throws IOException {
		GZIPOutputStream zos = new GZIPOutputStream(os);
		try {
			zos.write(strToWrite.getBytes());
			zos.finish();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
