package isebase.cognito.tourpilot.Connection;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.PatientsActivity;
import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTaskManager;
import isebase.cognito.tourpilot.Data.AdditionalWork.AdditionalWorkManager;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Diagnose.DiagnoseManager;
import isebase.cognito.tourpilot.Data.Doctor.DoctorManager;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.EmploymentVerification.EmploymentVerificationManager;
import isebase.cognito.tourpilot.Data.Information.InformationManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Data.PatientRemark.PatientRemarkManager;
import isebase.cognito.tourpilot.Data.Relative.RelativeManager;
import isebase.cognito.tourpilot.Data.Task.TaskManager;
import isebase.cognito.tourpilot.Data.Tour.TourManager;
import isebase.cognito.tourpilot.Data.UserRemark.UserRemarkManager;
import isebase.cognito.tourpilot.Data.WayPoint.WayPointManager;
import isebase.cognito.tourpilot.Data.Work.WorkManager;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;
import isebase.cognito.tourpilot.Dialogs.BaseInfoDialog;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import isebase.cognito.tourpilot.Utils.DateUtils;
import isebase.cognito.tourpilot.Utils.StringParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import android.content.Intent;
import android.os.AsyncTask;

public class ConnectionAsyncTask extends AsyncTask<Void, Boolean, Void> {

	private ConnectionStatus conStatus;
	
	private boolean isTerminated;

	public ConnectionAsyncTask(ConnectionStatus cs) {
		isTerminated = false;
		conStatus = cs;
	}

	public void terminate(){
		isTerminated = true;
	}
	
	@Override
	protected void onProgressUpdate(Boolean... values) {
		super.onProgressUpdate(values);
		if(values.length > 0 && values[0]){
			conStatus.UISynchHandler.onProgressUpdate("Start");
		}
		else
			conStatus.UISynchHandler.onProgressUpdate(
				conStatus.getProgressMessage(), conStatus.getCurrentProgress());
	}
	
	@Override
	protected void onPostExecute(Void result) {
		if (!conStatus.lastExecuteOK 
				|| conStatus.isFinished
				|| isTerminated) {
			if (!conStatus.lastExecuteOK || isTerminated) {
				conStatus.UISynchHandler.onSynchronizedFinished(false,
						conStatus.getMessage());
				closeConnection();
			}
			if (conStatus.isFinished){
				conStatus.UISynchHandler.onSynchronizedFinished(
						conStatus.isFinished, conStatus.getMessage());	
			}				
			return;
		}
		conStatus.UISynchHandler.onItemSynchronized(conStatus.getMessage());
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected Void doInBackground(Void... params) {
		switch (conStatus.CurrentState) {
		case ConnectionStatus.INIT:
			conStatus.setMessage(String.format(
					"%1$s %2$s : %3$s ...",
					StaticResources.getBaseContext().getString(
							R.string.connection_try), 
							Option.Instance().getServerIP(),
							Option.Instance().getServerPort()));
			break;
		case ConnectionStatus.CONNECTION:
			conStatus.lastExecuteOK = initializeConnection();
			break;
		case ConnectionStatus.INVINTATION:
			conStatus.lastExecuteOK = recievingInvitation();
			break;
		case ConnectionStatus.DATE_SYNC:
			conStatus.lastExecuteOK = sendDateSycnhronizationRequest();
			break;
		case ConnectionStatus.SEND_DATA:
			conStatus.lastExecuteOK = sendHelloRequest();
			break;
		case ConnectionStatus.COMPARE_CHECKSUMS:
			conStatus.lastExecuteOK = compareCkeckSums();
			break;
		case ConnectionStatus.PARSE_DATA:
			conStatus.lastExecuteOK = parseRecievedData();
			break;
		case ConnectionStatus.CLOSE_CONNECTION:
			conStatus.isFinished = true;
			conStatus.lastExecuteOK = closeConnection();
			break;
		case ConnectionStatus.ADDITONAL_PATIENTS_SYNC:
			conStatus.lastExecuteOK = additionalPatientsSync();
			break;
		case ConnectionStatus.TIME_SYNC:
			conStatus.lastExecuteOK = getTimeSync();
			break;
		default:
			conStatus.isFinished = true;
			break;
		}

		return null;

	}

	private boolean initializeConnection() {
		try {
			conStatus.socket = new Socket(Option.Instance().getServerIP(),
					Option.Instance().getServerPort());

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
					&& strInvitation.substring(0, 2).compareTo("OK") == 0)
				retVal = true;
			else
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
			Option.Instance().setTimeSynchronised(true);
			correctTime();
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
			Option.Instance().setIsAuto(false);
			writePack(conStatus.OS, getDataToSend() + "\0.\0");
			String recievedStatus = readFromStream(conStatus.IS);
			if (recievedStatus.startsWith("OVER") 
					|| recievedStatus.equals("")) {
				conStatus.setAnswerFromServer("OVER");
				retVal = false;
			}
			else if(recievedStatus.startsWith("OK")) {
				SentObjectVerification.Instance().setWasSent();
				StringParser stringParser = new StringParser(recievedStatus);
				String msg = stringParser.next("\0");
				Option.Instance().setIsAuto(msg.contains("1"));
				msg.contains("skippflegeok");
				if (stringParser.contains(ServerCommandParser.SERVER_CURRENT_VERSION))
					conStatus.serverCommandParser.parseElement(stringParser.next("\0"), false);
				else
					Option.Instance().setPalmVersion(null);
				if (stringParser.contains(ServerCommandParser.SERVER_VERSION_LINK))
					conStatus.serverCommandParser.parseElement(stringParser.next("\0"), false);
				else
					Option.Instance().setVersionLink(null);
			}
						
		} catch (Exception ex) {
			ex.printStackTrace();
			retVal = false;
		} finally {
			Option.Instance().save();
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
			if(dataFromServer == "")
				retVal = false;
			else
				conStatus.setDataFromServer(dataFromServer.split("\0"));
		} catch (Exception ex) {
			ex.printStackTrace();
			retVal = false;
		} finally {
			if (retVal)
				conStatus.setMessage(String.format(
						"%1$s \n %2$s: %3$s",
						StaticResources.getBaseContext().getString(
								R.string.checksum_ok),
						StaticResources.getBaseContext().getString(
								R.string.data_to_download),
						conStatus.getTotalProgress()));
			else
				conStatus.setMessage(StaticResources.getBaseContext()
						.getString(R.string.checksum_fail));
		}
		return retVal;
	}

	private boolean parseRecievedData() {
		boolean retVal = true;
		try {
			publishProgress(true);
			for (String data : conStatus.getDataFromServer()){
				if(isTerminated)
				{
					conStatus.isFinished = true;
					break;
				}
				conStatus.serverCommandParser.parseElement(data, false);
				publishProgress();
			}
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
			if(isTerminated)
				return "";
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
		try {
			os.write(text.getBytes());
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getDataToSend() {
		String strMsg = new String("U;");
		strMsg += Option.Instance().getWorkerID();
		strMsg += ";";
		strMsg += Option.Instance().getPrevWorkerID();
		strMsg += ";:";
		strMsg += Option.Instance().getDeviceID() + ";";
		strMsg += Option.Instance().getPhoneNumber() + "@";
		strMsg += Option.Instance().getVersion() + "\0R;";
		if (Option.Instance().getWorkerID() != BaseObject.EMPTY_ID)
			strMsg += "0";//TorubleFlags
		else
			strMsg += "0";
		strMsg += "\0x1\0";
		String strDone = "";
		strDone += EmploymentManager.Instance().getDone();
		strDone += WorkManager.Instance().getDone();
		//
		// if (User != null)
		// strDone += CInformations.Instance().GetDone();
		//
		// strDone += CLogs.Instance().GetDone();
		//
		strDone += UserRemarkManager.Instance().getDone();
		strDone += EmploymentVerificationManager.Instance().getDone();
		strDone += WayPointManager.Instance().getDone();
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
		return strMsg + strDone;
	}

	private String getStrChecksums() {
		String strMsg = "";
		strMsg += "z" + AdditionalTaskManager.Instance().getCheckSumByRequest() + "\0.\0";
		strMsg += "a" + WorkerManager.Instance().getCheckSumByRequest() + "\0.\0";
		strMsg += "u" + AdditionalWorkManager.Instance().getCheckSumByRequest() + "\0.\0";
//		strMsg += "q" + CQuestions.Instance().getCheckSums() + "\0.\0";
//		strMsg += "y" + CTopics.Instance().getCheckSums() + "\0.\0";
//		strMsg += "j" + CLinks.Instance().getCheckSums() + "\0.\0";
//		strMsg += ">" + CFreeQuestions.Instance().getCheckSums() + "\0.\0";
//		strMsg += "<" + CFreeTopics.Instance().getCheckSums() + "\0.\0";
		boolean userIsPresent = Option.Instance().getWorkerID() != -1;
		strMsg += "d" + (userIsPresent ? DiagnoseManager.Instance().getCheckSumByRequest() : 0) + "\0.\0";
		strMsg += "b" + (userIsPresent ? PatientRemarkManager.Instance().getCheckSumByRequest() : 0) + "\0.\0";
		strMsg += "v" + (userIsPresent ? RelativeManager.Instance().getCheckSumByRequest() : 0) + "\0.\0";
		strMsg += "m" + (userIsPresent ? DoctorManager.Instance().getCheckSumByRequest() : 0) + "\0.\0";
		strMsg += "p" + (userIsPresent ? PatientManager.Instance().getCheckSumByRequest() : 0) + "\0.\0";
		strMsg += "i" + (userIsPresent ? InformationManager.Instance().getCheckSumByRequest() : 0) + "\0.\0";
		strMsg += "r" + (userIsPresent ? TourManager.Instance().getCheckSumByRequest() : 0) + "\0.\0";
		strMsg += "t" + (userIsPresent ? TaskManager.Instance().getCheckSumByRequest() : 0) + "\0.\0";
//		strMsg += "x" + (userIsPresent ? CQuestionSettings.Instance().getCheckSums() : 0) + "\0.\0";
//		strMsg += "*" + (userIsPresent ? CFreeQuestionSettings.Instance().getCheckSums() : 0) + "\0.\0";
//		strMsg += "^" + (userIsPresent ? CAutoQuestionSettings.Instance().getCheckSums() : 0) + "\0.\0";
		return strMsg;
	}

	private String get_SRV_msgStoredData(String strNeedSend) {
		String strMsg = "";
//		if (strNeedSend.length() > 18 && strNeedSend.charAt(18) == '1')
//			strMsg += CAutoQuestionSettings.Instance().forServer();
//		if (strNeedSend.length() > 17 && strNeedSend.charAt(17) == '1')
//			strMsg += CFreeQuestionSettings.Instance().forServer();
//		if (strNeedSend.length() > 16 && strNeedSend.charAt(16) == '1')
//			strMsg += CFreeTopics.Instance().forServer();
//		if (strNeedSend.length() > 15 && strNeedSend.charAt(15) == '1')
//			strMsg += CFreeQuestions.Instance().forServer();
//		if (strNeedSend.length() > 14 && strNeedSend.charAt(14) == '1')
//			strMsg += CQuestionSettings.Instance().forServer();
//		if (strNeedSend.length() > 13 && strNeedSend.charAt(13) == '1')
//			strMsg += CLinks.Instance().forServer();
//		if (strNeedSend.length() > 12 && strNeedSend.charAt(12) == '1')
//			strMsg += CTopics.Instance().forServer();
//		if (strNeedSend.length() > 11 && strNeedSend.charAt(11) == '1')
//			strMsg += CQuestions.Instance().forServer();
		if (strNeedSend.length() > 10 && strNeedSend.charAt(10) == '1')
			strMsg += TaskManager.Instance().forServer();
		if (strNeedSend.length() > 9 && strNeedSend.charAt(9) == '1')
			strMsg += AdditionalWorkManager.Instance().forServer();
		if (strNeedSend.length() > 8 && strNeedSend.charAt(8) == '1')
			strMsg += TourManager.Instance().forServer();
		if (strNeedSend.length() > 7 && strNeedSend.charAt(7) == '1')
			strMsg += InformationManager.Instance().forServer();
		if (strNeedSend.length() > 6 && strNeedSend.charAt(6) == '1')
			strMsg += PatientRemarkManager.Instance().forServer();
		if (strNeedSend.length() > 5 && strNeedSend.charAt(5) == '1')
			strMsg += PatientManager.Instance().forServer();
		if (strNeedSend.length() > 4 && strNeedSend.charAt(4) == '1')
			strMsg += DoctorManager.Instance().forServer();
		if (strNeedSend.length() > 3 && strNeedSend.charAt(3) == '1')
			strMsg += RelativeManager.Instance().forServer();
		if (strNeedSend.length() > 2 && strNeedSend.charAt(2) == '1')
			strMsg += DiagnoseManager.Instance().forServer();
		if (strNeedSend.length() > 1 && strNeedSend.charAt(1) == '1')
			strMsg += WorkerManager.Instance().forServer();
		if (strNeedSend.length() > 0 && strNeedSend.charAt(0) == '1')
			strMsg += AdditionalTaskManager.Instance().forServer();
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
		int counter = 0;
		int timeoutSeconds = 120;
		while (is.available() == 0)
			try {
				if(isTerminated || counter >= timeoutSeconds)
					return "";
				Thread.sleep(1000);
				counter++;
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

		StringBuffer stringBuffer = new StringBuffer();
		GZIPInputStream zis = new GZIPInputStream(is);

		while (zis.available() != 0){
			if(isTerminated)
				return "";
			stringBuffer.append((char) zis.read());
		}
		return stringBuffer.toString();
	}
	
	private boolean additionalPatientsSync() {
		writeToStream(conStatus.OS, conStatus.getRequestMessage());
		try {
			conStatus.OS.flush();
		} catch(Exception e) {
			e.printStackTrace();
		}
		if (!conStatus.getRequestMessage().toLowerCase().startsWith("get") 
				&& !conStatus.getRequestMessage().toLowerCase().startsWith("sel"))
			return true;
		String answerFromServer = "";
		try {
			answerFromServer = readPack(conStatus.IS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		conStatus.setAnswerFromServer(answerFromServer);
		return true;
	}
	
	private boolean getTimeSync() {
		boolean retVal = true;

		try {
			writeToStream(conStatus.OS, "GETTIME_CLOSE");
			conStatus.OS.flush();
			String recievedDate = readFromStream(conStatus.IS);
			conStatus.serverCommandParser.parseElement(recievedDate, true);
			Option.Instance().setTimeSynchronised(true);		
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
	
	private void correctTime() {
		
	}

}
