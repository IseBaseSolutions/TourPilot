package isebase.cognito.tourpilot.Connection;

import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTask;
import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTaskManager;
import isebase.cognito.tourpilot.Data.AdditionalWork.AdditionalWork;
import isebase.cognito.tourpilot.Data.AdditionalWork.AdditionalWorkManager;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Diagnose.Diagnose;
import isebase.cognito.tourpilot.Data.Diagnose.DiagnoseManager;
import isebase.cognito.tourpilot.Data.Doctor.Doctor;
import isebase.cognito.tourpilot.Data.Doctor.DoctorManager;
import isebase.cognito.tourpilot.Data.Information.Information;
import isebase.cognito.tourpilot.Data.Information.InformationManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Data.PatientRemark.PatientRemark;
import isebase.cognito.tourpilot.Data.PatientRemark.PatientRemarkManager;
import isebase.cognito.tourpilot.Data.Relative.Relative;
import isebase.cognito.tourpilot.Data.Relative.RelativeManager;
import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.Data.Task.TaskManager;
import isebase.cognito.tourpilot.Data.Tour.Tour;
import isebase.cognito.tourpilot.Data.Tour.TourManager;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;
import isebase.cognito.tourpilot.EventHandle.SynchronizationHandler;

public class ServerCommandParser {

	public static final String SERVER_CURRENT_VERSION = "[CURVER]=";
	public static final String SERVER_SET_TIME_KEY = "[SETTIME]=";
	public static final String SERVER_VERSION_LINK = "[VERLINK]=";

	public static final char END_OF_COMMAND = '\0';
	public static final char NEED_TO_ADD = ';';
	public static final char END = '.';
	public static final char TIME = '[';

	public static final char WORKER = 'A';
	public static final char PATIENT_REMARK = 'B';
	public static final char DIAGNOSE = 'D';
	public static final char INFORMATION = 'I';
	public static final char ADDITIONAL_TASK_L = 'L';
	public static final char ADDITIONAL_TASK_Z = 'Z';
	public static final char DOCTOR = 'M';
	public static final char PATIENT = 'P';
	public static final char TASK = 'T';
	public static final char ADDITIONAL_WORK = 'U';
	public static final char TOUR = 'R';
	public static final char RELATIVE = 'V';
	public static final char QUESTION = 'Q';
	public static final char TOPIC = 'Y';
	public static final char LINK = 'J';
	public static final char QUESTION_SETTING = 'X';
	public static final char FREE_TOPIC = '<';
	public static final char FREE_QUESTION = '>';
	public static final char FREE_QUESTION_SETTING = '*';
	public static final char AUTO_QUESTION_SETTING = '^';

	private SynchronizationHandler syncHandler;

	private String strVerlink;
	private boolean needShowVersion;
	private static long timeDiff = 0;

	public ServerCommandParser(SynchronizationHandler sh) {
		syncHandler = sh;
	}

	public boolean parseElement(String commandLine, boolean isAutomaticSync) {

		char commandActionType = END_OF_COMMAND;
		char commandType = commandLine.charAt(0);

		boolean blnRes = true;
		if (commandLine.length() > 1)
			commandActionType = commandLine.charAt(1);
		if (commandLine.equals(END)) // pos_start = strData.length();
			blnRes = false;
		if (commandLine.equals(""))
			return false;

		switch (commandType) {
		case END:
			syncHandler.onProgressUpdate("Done");
			break;
		case TIME:
			if (commandLine.indexOf(SERVER_CURRENT_VERSION) == 0)
				// TODO Check version
				// CheckVersion(commandLine.substring(SERVER_CURRENT_VERSION.length()));
			if (commandLine.indexOf(SERVER_VERSION_LINK) == 0)
				strVerlink = commandLine.substring(SERVER_VERSION_LINK
						.length());
			if (commandLine.indexOf(SERVER_SET_TIME_KEY) == 0)
				// TODO Set time
				// SetTime(commandLine.substring(SERVER_SET_TIME_KEY.length()));
				break;
		case WORKER:
			if (commandActionType == NEED_TO_ADD) {
				Worker item = new Worker(commandLine);
				WorkerManager.Instance().save(item);
				syncHandler.onProgressUpdate(item.getName() + " OK");
			} else 
				removeByID(commandLine, WorkerManager.Instance());
			break;
		case PATIENT_REMARK:
			if (commandActionType == NEED_TO_ADD) {
				PatientRemark item = new PatientRemark(commandLine);
				PatientRemarkManager.Instance().save(item);
				syncHandler.onProgressUpdate(item.getName() + " OK");
			} else 
				removeByID(commandLine, PatientRemarkManager.Instance());
			break;
		case DIAGNOSE:
			if (commandActionType == NEED_TO_ADD) {
				Diagnose item = new Diagnose(commandLine);
				DiagnoseManager.Instance().save(item);
				syncHandler.onProgressUpdate(item.getName() + " OK");
			} else 
				removeByID(commandLine, DiagnoseManager.Instance());
			break;
		case INFORMATION:
			if (commandActionType == NEED_TO_ADD) {
				Information item = new Information(commandLine);
				InformationManager.Instance().save(item);
				syncHandler.onProgressUpdate(item.getName() + " OK");
			} else 
				removeByID(commandLine, InformationManager.Instance());
			break;
		case 'F':
			break;
		case ADDITIONAL_TASK_L:
		case ADDITIONAL_TASK_Z:
			if (commandLine.startsWith("" + ADDITIONAL_TASK_Z))
			commandLine = ADDITIONAL_TASK_L + commandLine.substring(1);
			if (commandActionType == NEED_TO_ADD) {
				AdditionalTask item = new AdditionalTask(commandLine);
				AdditionalTaskManager.Instance().save(item);
				syncHandler.onProgressUpdate(item.getName() + " OK");
			} else 
				removeByID(commandLine, AdditionalTaskManager.Instance());
			break;
		case DOCTOR:
			if (commandActionType == NEED_TO_ADD) {
				Doctor item = new Doctor(commandLine);
				DoctorManager.Instance().save(item);
				syncHandler.onProgressUpdate(item.getFullName() + " OK");
			} else 
				removeByID(commandLine, DoctorManager.Instance());
			break;
		case PATIENT:
			if (commandActionType == NEED_TO_ADD) {
				Patient item = new Patient(commandLine);
				PatientManager.Instance().save(item);
				syncHandler.onProgressUpdate(item.getFullName() + " OK");
			} else 
				removeByID(commandLine, PatientManager.Instance());
			break;
		case TASK:
			if (commandActionType == NEED_TO_ADD) {
				Task item = new Task(commandLine);
				TaskManager.Instance().save(item);
				syncHandler.onProgressUpdate(item.getName() + " OK");
			} else
				removeByID(commandLine, TaskManager.Instance());
			break;
		case ADDITIONAL_WORK:
			if (commandActionType == NEED_TO_ADD) {
				AdditionalWork item = new AdditionalWork(commandLine);
				AdditionalWorkManager.Instance().save(item);
				syncHandler.onProgressUpdate(item.getName() + " OK");
			} else 
				removeByID(commandLine, AdditionalWorkManager.Instance());
			break;
		case TOUR:
			if (commandActionType == NEED_TO_ADD) {
				Tour item = new Tour(commandLine);
				TourManager.Instance().save(item);
				syncHandler.onProgressUpdate(item.getName() + " OK");
			} else
				removeByID(commandLine, TourManager.Instance());
			break;
		case RELATIVE:
			if (commandActionType == NEED_TO_ADD) {
				Relative item = new Relative(commandLine);
				RelativeManager.Instance().save(item);
				syncHandler.onProgressUpdate(item.getFullName() + " OK");
			} else 
				removeByID(commandLine, RelativeManager.Instance());
			break;
		/*
		 * case QUESTION: // ANDREW if
		 * (GetSyncStatus().indexOf(context.getString
		 * (org.microemu.android.R.string.questions)) == -1)
		 * setSyncStatus(context
		 * .getString(org.microemu.android.R.string.questions), 5,
		 * isAutomaticSync); if (commandActionType == NEED_TO_ADD) { CQuestion
		 * question = new CQuestion(commandLine);
		 * CQuestions.Instance().RemoveByIdentID(question.IdentID());
		 * CQuestions.Instance().addElement(question); } else
		 * CQuestions.Instance().removeElement(commandLine); break; case TOPIC:
		 * // ANDREW if
		 * (GetSyncStatus().indexOf(context.getString(org.microemu.android
		 * .R.string.categories)) == -1)
		 * setSyncStatus(context.getString(org.microemu
		 * .android.R.string.categories), 5, isAutomaticSync); if
		 * (commandActionType == NEED_TO_ADD) { CTopic category = new
		 * CTopic(commandLine);
		 * CTopics.Instance().RemoveByIdentID(category.IdentID());
		 * CTopics.Instance().addElement(category); } else
		 * CTopics.Instance().removeElement(commandLine); break; case LINK: //
		 * ANDREW if
		 * (GetSyncStatus().indexOf(context.getString(org.microemu.android
		 * .R.string.links)) == -1)
		 * setSyncStatus(context.getString(org.microemu.android.R.string.links),
		 * 5, isAutomaticSync); if (commandActionType == NEED_TO_ADD) { CLink
		 * link = new CLink(commandLine);
		 * CLinks.Instance().RemoveByIdentID(link.IdentID());
		 * CLinks.Instance().addElement(link); } else
		 * CLinks.Instance().removeElement(commandLine); break; case
		 * QUESTION_SETTING: // ANDREW if
		 * (GetSyncStatus().indexOf(context.getString
		 * (org.microemu.android.R.string.settings)) == -1)
		 * setSyncStatus(context
		 * .getString(org.microemu.android.R.string.settings), 5,
		 * isAutomaticSync); if (commandActionType == NEED_TO_ADD) {
		 * CQuestionSetting setting = new CQuestionSetting(commandLine);
		 * CQuestionSettings.Instance().RemoveByIdentID(setting.IdentID());
		 * CQuestionSettings.Instance().addElement(setting); } else
		 * CQuestionSettings.Instance().removeElement(commandLine); break; case
		 * FREE_QUESTION: // ANDREW if
		 * (GetSyncStatus().indexOf(context.getString
		 * (org.microemu.android.R.string.free_questions)) == -1)
		 * setSyncStatus(context
		 * .getString(org.microemu.android.R.string.free_questions), 5,
		 * isAutomaticSync); if (commandActionType == NEED_TO_ADD) {
		 * CFreeQuestion freeQuestion = new CFreeQuestion(commandLine);
		 * CFreeQuestions.Instance().RemoveByIdentID(freeQuestion.IdentID());
		 * CFreeQuestions.Instance().addElement(freeQuestion); } else
		 * CFreeQuestions.Instance().removeElement(commandLine); break; case
		 * FREE_TOPIC: // ANDREW if
		 * (GetSyncStatus().indexOf(context.getString(org
		 * .microemu.android.R.string.free_topics)) == -1)
		 * setSyncStatus(context.
		 * getString(org.microemu.android.R.string.free_topics), 5,
		 * isAutomaticSync); if (commandActionType == NEED_TO_ADD) { CFreeTopic
		 * freeTopic = new CFreeTopic(commandLine);
		 * CFreeTopics.Instance().RemoveByIdentID(freeTopic.IdentID());
		 * CFreeTopics.Instance().addElement(freeTopic); } else
		 * CFreeTopics.Instance().removeElement(commandLine); break; case
		 * FREE_QUESTION_SETTING: // ANDREW if
		 * (GetSyncStatus().indexOf(context.getString
		 * (org.microemu.android.R.string.free_settings)) == -1)
		 * setSyncStatus(context
		 * .getString(org.microemu.android.R.string.free_settings), 5,
		 * isAutomaticSync); if (commandActionType == NEED_TO_ADD) {
		 * CFreeQuestionSetting freeSettings = new
		 * CFreeQuestionSetting(commandLine);
		 * CFreeQuestionSettings.Instance().RemoveByIdentID
		 * (freeSettings.IdentID());
		 * CFreeQuestionSettings.Instance().addElement(freeSettings); } else
		 * CFreeQuestionSettings.Instance().removeElement(commandLine); break;
		 * case AUTO_QUESTION_SETTING: // ANDREW if
		 * (GetSyncStatus().indexOf(context
		 * .getString(org.microemu.android.R.string.item_links)) == -1)
		 * setSyncStatus
		 * (context.getString(org.microemu.android.R.string.item_links), 5,
		 * isAutomaticSync); if (commandActionType == NEED_TO_ADD) {
		 * CAutoQuestionSetting itemLinks = new
		 * CAutoQuestionSetting(commandLine);
		 * CAutoQuestionSettings.Instance().RemoveByIdentID
		 * (itemLinks.IdentID());
		 * CAutoQuestionSettings.Instance().addElement(itemLinks); } else
		 * CAutoQuestionSettings.Instance().removeElement(commandLine); break;
		 */

		default:
			// setSyncStatus(context.getString(org.microemu.android.R.string.dirt)
			// + commandLine.charAt(0) + "= " + count, 0, isAutomaticSync);
			break;
		}
		return blnRes;
	}
	
	private void removeByID(String commandLine, BaseObjectManager<? extends BaseObject> manager){
		int idToDelete = getIDFromStr(commandLine);
		if(idToDelete != BaseObject.EMPTY_ID)
			manager.delete(idToDelete);
		syncHandler.onProgressUpdate(idToDelete + " Deleted");
	}
	
	private int getIDFromStr(String str) {
		int retVal = BaseObject.EMPTY_ID;
		try{
			str = str.substring(1,str.length()-1);
			String firstNumber = str.split(";")[0];
			retVal = Integer.parseInt(firstNumber);
		}
		catch(Exception e){
			retVal = BaseObject.EMPTY_ID;
		}
		return retVal;
	}
	
}
