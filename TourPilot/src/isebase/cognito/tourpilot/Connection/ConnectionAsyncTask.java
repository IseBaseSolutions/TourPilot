package isebase.cognito.tourpilot.Connection;

import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Option.OptionManager;
import isebase.cognito.tourpilot.EventHandle.SynchronizationHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;
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
			writePack(os, getStrHello() + "\0.\0");
			strMsg = readFromStream(is);
			if (strMsg.startsWith("OVER")) {
				// License is over
			}
			writePack(os, getStrChecksums());
			strMsg = readFromStream(is);
			String strCheckItems = strMsg;
			writePack(os, get_SRV_msgStoredData(strCheckItems));
			strMsg = readPack(is);
			ServerCommandParser serverCommandParser = new ServerCommandParser(new SynchronizationHandler());
			String[] strMsgArr = strMsg.split("\0");
			for (String strMsgLine : strMsgArr)
				serverCommandParser.parseElement(strMsgLine, false);
			writeToStream(os, "OK" + "\0");
			is.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
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
		if (strNeedSend.charAt(18) == '1')
		strMsg += /*CAutoQuestionSettings.Instance().forServer()*/0;
		if (strNeedSend.charAt(17) == '1')
		strMsg += /*CFreeQuestionSettings.Instance().forServer()*/0;
		if (strNeedSend.charAt(16) == '1')
		strMsg += /*CFreeTopics.Instance().forServer()*/0;
		if (strNeedSend.charAt(15) == '1')
		strMsg += /*CFreeQuestions.Instance().forServer()*/0;
		if (strNeedSend.charAt(14) == '1')
		strMsg += /*CQuestionSettings.Instance().forServer()*/0;
		if (strNeedSend.charAt(13) == '1')
		strMsg += /*CLinks.Instance().forServer()*/0;
		if (strNeedSend.charAt(12) == '1')
		strMsg += /*CTopics.Instance().forServer()*/0;
		if (strNeedSend.charAt(11) == '1')
		strMsg += /*CQuestions.Instance().forServer()*/0;
		if (strNeedSend.charAt(10) == '1')
		strMsg += /*CTasks.Instance().forServer()*/0;
		if (strNeedSend.charAt(9) == '1')
		strMsg += /*CAddWorks.Instance().forServer()*/0;
		if (strNeedSend.charAt(8) == '1')
		strMsg += /*CTours.Instance().forServer()*/0;
		if (strNeedSend.charAt(7) == '1') // Informations
		strMsg += /*CInformations.Instance().forServer()*/0;
		if (strNeedSend.charAt(6) == '1') // patient remarks
		strMsg += /*CPatientRemarks.Instance().forServer()*/0;
		if (strNeedSend.charAt(5) == '1') // patients
		strMsg += /*CPatients.Instance().forServer()*/0;
		if (strNeedSend.charAt(4) == '1') // doctors
		strMsg += /*CDoctors.Instance().forServer()*/0;
		if (strNeedSend.charAt(3) == '1') // relatives
		strMsg += /*CRelatives.Instance().forServer()*/0;
		if (strNeedSend.charAt(2) == '1') // diagnoses
		strMsg += /*CDiagnoses.Instance().forServer()*/0;
		if (strNeedSend.charAt(1) == '1') // users
		strMsg += /*CUsers.Instance().forServer()*/0;
		if (strNeedSend.charAt(0) == '1') // add tasks
		strMsg += /*CAddTasks.Instance().forServer()*/0;
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
	
	public String readPack(InputStream is) throws IOException, InterruptedException {
		String retVal = "";
//		while (is.available() == 0)
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException ex) {
//				ex.printStackTrace();
//			}
//		while (is.available() != 0)
//			arr.add((byte) is.read());
//		byte[] buf = new byte[arr.size() - 5];
//		int counter = 0;
//			for (Byte item : arr)
//				if (counter < (arr.size() - 5))
//					buf[counter++] = (Byte) item;
//				else
//					break;
//		String data = new String(GZIP.inflate(buf), codePage);
		GZIPInputStream zis;
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
				zis = new GZIPInputStream(is);
				byte[] charI = { (byte) zis.read() };
				retVal += new String(charI, "cp1252");
				av = zis.available();
			}
			return retVal;
		}
		return retVal;

		/*
		 * while (is.available() == 0) try { Thread.sleep(1000); } catch
		 * (InterruptedException ex) { ex.printStackTrace(); } try { byte[]
		 * resultLength = new byte[4]; is.read(resultLength); int length =
		 * byteArrayToInt(resultLength); byte[] arr = new byte[length - 5]; for
		 * (int i = 0; i < length; i++) if (is.available() == 0) i--; else if (i
		 * < arr.length) arr[i] = (byte)is.read(); byte[] data =
		 * GZIP.inflate(arr); System.gc(); //fruckt //return new String(data);
		 * return new String(data, codePage); } catch(Exception ex) {
		 * ex.printStackTrace(); } return "";//
		 */
	}

}
