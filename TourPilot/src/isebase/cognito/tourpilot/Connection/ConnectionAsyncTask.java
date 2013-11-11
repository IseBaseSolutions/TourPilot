package isebase.cognito.tourpilot.Connection;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.StaticResources.StaticResources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import android.os.AsyncTask;

public class ConnectionAsyncTask extends AsyncTask<Void, Void, Void> {

	private ConnectionStatus conStatus;

	public ConnectionAsyncTask(ConnectionStatus cs) {
		conStatus = cs;
	}

	@Override
	protected void onPostExecute(Void result) {
		if (!conStatus.lastExecuteOK || conStatus.isFinished) {
			if (!conStatus.lastExecuteOK){
				conStatus.UISynchHandler.onSynchronizedFinished(false,conStatus.getMessage());
				closeConnection();
			}
			conStatus.UISynchHandler.onSynchronizedFinished(
					conStatus.isFinished, conStatus.getMessage());
			return;
		}
		conStatus.UISynchHandler.onItemSynchronized(conStatus.getMessage());
		conStatus.nextState();
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected Void doInBackground(Void... params) {
		switch (conStatus.CurrentState) {
		case ConnectionStatus.InitState:
			conStatus.setMessage(String.format("%1$s %2$s : %3$s ...",
					StaticResources.getBaseContext().getString(
							R.string.connection_try)
							, Option.Instance().getServerIP()
							, Option.Instance().getServerPort()));
			break;
		case ConnectionStatus.Connection:
			conStatus.lastExecuteOK = initializeConnection();
			break;
		case ConnectionStatus.Invitation:
			conStatus.lastExecuteOK = recievingInvitation();
			break;
		case ConnectionStatus.DateSycnhronizing:
			conStatus.lastExecuteOK = sendDateSycnhronizationRequest();
			break;
		case ConnectionStatus.SendHelloRequest:
			conStatus.lastExecuteOK = sendHelloRequest();
			break;
		case ConnectionStatus.CompareCkeckSums:
			conStatus.lastExecuteOK = compareCkeckSums();
			break;
		case ConnectionStatus.ParseRecievedData:
			conStatus.lastExecuteOK = parseRecievedData();
			break;
		case ConnectionStatus.CloseConnection:
			conStatus.lastExecuteOK = closeConnection();
			break;
		default:
			conStatus.isFinished = true;
			break;
		}

		return null;

	}

	private boolean initializeConnection() {
		try {

			conStatus.socket = new Socket(Option.Instance().getServerIP()
					, Option.Instance().getServerPort());

			conStatus.OS = conStatus.socket.getOutputStream();
			conStatus.IS = conStatus.socket.getInputStream();

		} catch (Exception ex) {
			ex.printStackTrace();
			conStatus.setMessage(String.format("%1$s ...", ex.getMessage()));
			return false;
		}
		conStatus.setMessage(StaticResources.getBaseContext().getString(
				R.string.connection_ok));
		return true;
	}

	private boolean recievingInvitation() {
		String strInvitation = "";
		boolean retVal = true;
		try {

			strInvitation = readFromStream(conStatus.IS);
			if (strInvitation.length() > 2
					&& strInvitation.substring(0, 2).compareTo("OK") != 0)
				retVal = false;
		} catch (Exception ex) {
			ex.printStackTrace();
			retVal = false;
		} finally {
			if (retVal)
				conStatus.setMessage(StaticResources.getBaseContext()
						.getString(R.string.invitation_ok));
			else
				conStatus.setMessage(StaticResources.getBaseContext()
						.getString(R.string.invitation_fail));
		}
		return retVal;
	}

	private boolean sendDateSycnhronizationRequest() {
		boolean retVal = true;

		try {
			writeToStream(conStatus.OS, "GETTIME");
			conStatus.OS.flush();
			String recievedDate = readFromStream(conStatus.IS);
			conStatus.serverCommandParser.parseElement(recievedDate, true);
		} catch (Exception ex) {
			ex.printStackTrace();
			retVal = false;
		} finally {
			if (retVal)
				conStatus.setMessage(StaticResources.getBaseContext()
						.getString(R.string.sycnhronizing_ok));
			else
				conStatus.setMessage(StaticResources.getBaseContext()
						.getString(R.string.sycnhronizing_fail));
		}

		return retVal;
	}

	private boolean sendHelloRequest() {
		boolean retVal = true;
		try {
			writePack(conStatus.OS, getStrHello() + "\0.\0");
			String recievedStatus = readFromStream(conStatus.IS);
			if (recievedStatus.startsWith("OVER")) {
				// License is over
				retVal = false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			retVal = false;
		} finally {
			if (retVal)
				conStatus.setMessage(StaticResources.getBaseContext()
						.getString(R.string.hello_request_ok));
			else
				conStatus.setMessage(StaticResources.getBaseContext()
						.getString(R.string.hello_request_fail));
		}
		return retVal;
	}

	private boolean compareCkeckSums() {
		boolean retVal = true;
		try {
			writePack(conStatus.OS, getStrChecksums());
			String strChecksumRecieve = readFromStream(conStatus.IS);
			String strCheckItems = strChecksumRecieve;

			writePack(conStatus.OS, get_SRV_msgStoredData(strCheckItems));
			String dataFromServer = readPack(conStatus.IS);
			conStatus.dataFromServer = dataFromServer.split("\0");
		} catch (Exception ex) {
			ex.printStackTrace();
			retVal = false;
		} finally {
			if (retVal)
				conStatus.setMessage(String.format(
						"%1$s \n %2$s: %3$s",
						StaticResources.getBaseContext().getString(R.string.checksum_ok),
						StaticResources.getBaseContext().getString(R.string.data_to_download),
						conStatus.dataFromServer.length));
			else
				conStatus.setMessage(StaticResources.getBaseContext()
						.getString(R.string.checksum_fail));
		}
		return retVal;
	}

	private boolean parseRecievedData() {
		boolean retVal = true;
		try {
			for (String data : conStatus.dataFromServer)
				conStatus.serverCommandParser.parseElement(data, false);
		} catch (Exception ex) {
			ex.printStackTrace();
			retVal = false;
		} finally {
			if (retVal)
				conStatus.setMessage(StaticResources.getBaseContext()
						.getString(R.string.processing_data_ok));
			else
				conStatus.setMessage(StaticResources.getBaseContext()
						.getString(R.string.processing_data_fail));
		}
		return retVal;
	}
	
	private boolean closeConnection() {
		boolean retVal = true;
		try {
			writeToStream(conStatus.OS, "OK" + "\0");
		} catch (Exception e) {
			e.printStackTrace();
			retVal = false;
		} finally {
			conStatus.closeConnection();
			if (retVal)
				conStatus.setMessage(StaticResources.getBaseContext()
						.getString(R.string.connection_close_ok));
			else
				conStatus.setMessage(StaticResources.getBaseContext()
						.getString(R.string.connection_close_fail));
		}
		return true;
	}

	private String readFromStream(InputStream is) throws InterruptedException,
			IOException {
		String retVal = "";
		int timeoutCount = 120000;
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

	private String getStrHello() {
		String strMsg = new String("U;");
		strMsg += Option.Instance().getWorkerID();
		strMsg += ";";
		strMsg += "-1"; // beforeUser.ID()
		strMsg += ";:";
		strMsg += Option.Instance().getDeviceID() + ";";
		strMsg += Option.Instance().getPhoneNumber() + "@";
		strMsg += Option.Instance().getVersion() + "\0R;";
		// if (User != null)
		// strMsg += CSettings.Instance().TroubleFlags();
		// else
		// strMsg += "0";
		strMsg += "\0x1\0";
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
		return strMsg;
	}

	private String getStrChecksums() {
		String strMsg = "";
		strMsg += "z" + /* CAddTasks.Instance().Checksum() */0 + "\0.\0";
		strMsg += "a" + /* CUsers.Instance().Checksum() */0 + "\0.\0";
		boolean userPresent = Option.Instance().getWorkerID() != -1;
		strMsg += "d"
				+ (userPresent ? /* CDiagnoses.Instance().Checksum() */0 : 0)
				+ "\0.\0";
		strMsg += "b"
				+ (userPresent ? /* CPatientRemarks.Instance().Checksum() */0
						: 0) + "\0.\0";
		strMsg += "v"
				+ (userPresent ? /* CRelatives.Instance().Checksum() */0 : 0)
				+ "\0.\0";
		strMsg += "m"
				+ (userPresent ? /* CDoctors.Instance().Checksum() */0 : 0)
				+ "\0.\0";
		strMsg += "p"
				+ (userPresent ? /* CPatients.Instance().Checksum() */0 : 0)
				+ "\0.\0";
		strMsg += "i"
				+ (userPresent ? /* CInformations.Instance().Checksum() */0 : 0)
				+ "\0.\0";
		strMsg += "r"
				+ (userPresent ? /* CTours.Instance().Checksum() */0 : 0)
				+ "\0.\0";
		strMsg += "t"
				+ (userPresent ? /* CEmployments.Instance().Checksum() */0 : 0)
				+ "\0.\0";
		strMsg += "u" + /* CAddWorks.Instance().Checksum() */0 + "\0.\0";
		strMsg += "q" + /* CQuestions.Instance().Checksum() */0 + "\0.\0";
		strMsg += "y" + /* CTopics.Instance().Checksum() */0 + "\0.\0";
		strMsg += "j" + /* CLinks.Instance().Checksum() */0 + "\0.\0";
		strMsg += "x" + (userPresent ? /*
										 * CQuestionSettings.Instance().Checksum(
										 * )
										 */0 : 0) + "\0.\0";

		strMsg += ">" + /* CFreeQuestions.Instance().Checksum() */0 + "\0.\0";
		strMsg += "<" + /* CFreeTopics.Instance().Checksum() */0 + "\0.\0";
		strMsg += "*" + (userPresent ? /*
										 * CFreeQuestionSettings.Instance()
										 * .Checksum()
										 */0 : 0) + "\0.\0";
		strMsg += "^" + (userPresent ? /*
										 * CAutoQuestionSettings.Instance()
										 * .Checksum()
										 */0 : 0) + "\0.\0";
		return strMsg;
	}

	private String get_SRV_msgStoredData(String strNeedSend) {
		String strMsg = "";
		if (strNeedSend.length() > 18 && strNeedSend.charAt(18) == '1')
			strMsg += /* CAutoQuestionSettings.Instance().forServer() */"."+'\0';
		if (strNeedSend.length() > 17 && strNeedSend.charAt(17) == '1')
			strMsg += /* CFreeQuestionSettings.Instance().forServer() */"."+'\0';
		if (strNeedSend.length() > 16 && strNeedSend.charAt(16) == '1')
			strMsg += /* CFreeTopics.Instance().forServer() */"."+'\0';
		if (strNeedSend.length() > 15 && strNeedSend.charAt(15) == '1')
			strMsg += /* CFreeQuestions.Instance().forServer() */"."+'\0';
		if (strNeedSend.length() > 14 && strNeedSend.charAt(14) == '1')
			strMsg += /* CQuestionSettings.Instance().forServer() */"."+'\0';
		if (strNeedSend.length() > 13 && strNeedSend.charAt(13) == '1')
			strMsg += /* CLinks.Instance().forServer() */"."+'\0';
		if (strNeedSend.length() > 12 && strNeedSend.charAt(12) == '1')
			strMsg += /* CTopics.Instance().forServer() */"."+'\0';
		if (strNeedSend.length() > 11 && strNeedSend.charAt(11) == '1')
			strMsg += /* CQuestions.Instance().forServer() */"."+'\0';
		if (strNeedSend.length() > 10 && strNeedSend.charAt(10) == '1')
			strMsg += /* CTasks.Instance().forServer() */"."+'\0';
		if (strNeedSend.length() > 9 && strNeedSend.charAt(9) == '1')
			strMsg += /* CAddWorks.Instance().forServer() */"."+'\0';
		if (strNeedSend.length() > 8 && strNeedSend.charAt(8) == '1')
			strMsg += /* CTours.Instance().forServer() */"."+'\0';
		if (strNeedSend.length() > 7 && strNeedSend.charAt(7) == '1') // Informations
			strMsg += /* CInformations.Instance().forServer() */"."+'\0';
		if (strNeedSend.length() > 6 && strNeedSend.charAt(6) == '1') // patient remarks
			strMsg += /* CPatientRemarks.Instance().forServer() */"."+'\0';
		if (strNeedSend.length() > 5 && strNeedSend.charAt(5) == '1') // patients
			strMsg += /* CPatients.Instance().forServer() */"."+'\0';
		if (strNeedSend.length() > 4 && strNeedSend.charAt(4) == '1') // doctors
			strMsg += /* CDoctors.Instance().forServer() */"."+'\0';
		if (strNeedSend.length() > 3 && strNeedSend.charAt(3) == '1') // relatives
			strMsg += /* CRelatives.Instance().forServer() */"."+'\0';
		if (strNeedSend.length() > 2 && strNeedSend.charAt(2) == '1') // diagnoses
			strMsg += /* CDiagnoses.Instance().forServer() */"."+'\0';
		if (strNeedSend.length() > 1 && strNeedSend.charAt(1) == '1') // users
			strMsg += /* CUsers.Instance().forServer() */"."+'\0';
		if (strNeedSend.length() > 0 && strNeedSend.charAt(0) == '1') // add tasks
			strMsg += /* CAddTasks.Instance().forServer() */"."+'\0';
		if (strNeedSend.indexOf("1") == -1)
			strMsg += ".";
		return strMsg;
	}

	public void writePack(OutputStream os, String strToWrite)
			throws IOException {
		GZIPOutputStream zos = new GZIPOutputStream(os);
		try {
			zos.write(strToWrite.getBytes());
			zos.finish();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String readPack(InputStream is) throws IOException,
		InterruptedException {
		String retVal = "";
		while (is.available() == 0)
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
		  	}
		GZIPInputStream zis = new GZIPInputStream(is);
		byte[] buffer = new byte[2048];
		while((zis.read(buffer)) != -1) {
			retVal += new String(buffer, "cp1252");
			int x = 0;
		}
		return retVal.trim();
	}

}
