package isebase.cognito.tourpilot.Connection;

import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTask;
import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTaskManager;
import isebase.cognito.tourpilot.Data.AdditionalWork.AdditionalWork;
import isebase.cognito.tourpilot.Data.AdditionalWork.AdditionalWorkManager;
import isebase.cognito.tourpilot.Data.Diagnose.Diagnose;
import isebase.cognito.tourpilot.Data.Diagnose.DiagnoseManager;
import isebase.cognito.tourpilot.Data.Doctor.Doctor;
import isebase.cognito.tourpilot.Data.Doctor.DoctorManager;
import isebase.cognito.tourpilot.Data.Information.Information;
import isebase.cognito.tourpilot.Data.Information.InformationManager;
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
import isebase.cognito.tourpilot.Utils.StringParser;

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

		boolean blnRes = true; // Andrew
		if (commandLine.length() > 1)
			commandActionType = commandLine.charAt(1);
		if (commandLine.equals(END)) // pos_start = strData.length();
			blnRes = false;
		if (commandLine.equals(""))
			return false;

		switch (commandType) {
		case END:
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
				// TODO Show message
				// if
				// (GetSyncStatus().indexOf(context.getString(org.microemu.android.R.string.employee))
				// == -1)
				// setSyncStatus(context.getString(org.microemu.android.R.string.employee),5,
				// isAutomaticSync);
				syncHandler.onItemSynchronized("Worker synchronization done");
				// TODO Use constructor with viewDate
				Worker worker = new Worker(commandLine);
				WorkerManager.Instance().save(worker);
			} else {
				WorkerManager.Instance().delete(getIDFromStr(commandLine));
			}
			break;
		case PATIENT_REMARK:
			if (commandActionType == NEED_TO_ADD) {
				// if
				// (GetSyncStatus().indexOf(context.getString(org.microemu.android.R.string.comments))
				// == -1)
				// setSyncStatus(context.getString(org.microemu.android.R.string.comments),
				// 5, isAutomaticSync);
				PatientRemark patRem = new PatientRemark(commandLine);
				PatientRemarkManager.Instance().save(patRem);
			} else {
				PatientRemarkManager.Instance().delete(getIDFromStr(commandLine));
			}
			break;
		case DIAGNOSE:
			if (commandActionType == NEED_TO_ADD) {
				// if
				// (GetSyncStatus().indexOf(context.getString(org.microemu.android.R.string.diagnoses))
				// == -1)
				// setSyncStatus(context.getString(org.microemu.android.R.string.diagnoses),
				// 5, isAutomaticSync);
				Diagnose diag = new Diagnose(commandLine);
				DiagnoseManager.Instance().save(diag);
			} else {
				DiagnoseManager.Instance().delete(getIDFromStr(commandLine));
			}
			break;
		case INFORMATION:
			if (commandActionType == NEED_TO_ADD) {
				// if
				// (GetSyncStatus().indexOf(context.getString(org.microemu.android.R.string.information))
				// == -1)
				// setSyncStatus(
				// context.getString(org.microemu.android.R.string.information),
				// 5, isAutomaticSync);
				Information info = new Information(commandLine);
				InformationManager.Instance().save(info);
			} else {
				InformationManager.Instance().delete(getIDFromStr(commandLine));
			}
			break;
		case 'F':
			break;
		case ADDITIONAL_TASK_L:
		case ADDITIONAL_TASK_Z:
			// if
			// (GetSyncStatus().indexOf(context.getString(org.microemu.android.R.string.individual_performance))
			// == -1)
			// setSyncStatus(context.getString(org.microemu.android.R.string.individual_performance),
			// 5, isAutomaticSync);
			if (commandLine.startsWith("" + ADDITIONAL_TASK_Z))
				commandLine = ADDITIONAL_TASK_L + commandLine.substring(1);
			if (commandActionType == NEED_TO_ADD) {
				AdditionalTask addTask = new AdditionalTask(commandLine);
				AdditionalTaskManager.Instance().save(addTask);
			} else {
				AdditionalTaskManager.Instance().delete(getIDFromStr(commandLine));
			}
			break;
		case DOCTOR:
			// if
			// (GetSyncStatus().indexOf(context.getString(org.microemu.android.R.string.physicians))
			// == -1)
			// setSyncStatus(context.getString(org.microemu.android.R.string.physicians),
			// 5, isAutomaticSync);
			if (commandActionType == NEED_TO_ADD) {
				Doctor doc = new Doctor(commandLine);
				DoctorManager.Instance().save(doc);
			} else {
				DoctorManager.Instance().delete(getIDFromStr(commandLine));
			}
			break;
		case PATIENT:
			// if
			// (GetSyncStatus().indexOf(context.getString(org.microemu.android.R.string.patients))
			// == -1)
			// setSyncStatus(context.getString(org.microemu.android.R.string.patients),
			// 5, isAutomaticSync);
			if (commandActionType == NEED_TO_ADD) {
				Patient pat = new Patient(commandLine);
				PatientManager.Instance().save(pat);
			} else {
				PatientManager.Instance().delete(getIDFromStr(commandLine));
			}
			break;
		case TASK:
			if (commandActionType == NEED_TO_ADD) {
				Task task = new Task(commandLine);
				TaskManager.Instance().save(task);
			} else {
				TaskManager.Instance().delete(getIDFromStr(commandLine));
			}
			// if
			// (GetSyncStatus().indexOf(context.getString(org.microemu.android.R.string.task))
			// == -1)
			// setSyncStatus(
			// context.getString(org.microemu.android.R.string.task), 5,
			// isAutomaticSync);
			/*
			 * if (commandActionType == NEED_TO_ADD) { CTask tsk = new
			 * CTask(commandLine); CTasks.Instance().removeElement(tsk); if
			 * (User == null || tsk.UserID() != User.ID()) break;
			 * CTasks.Instance().addElement(tsk); if (tsk.UserTask() == null) {
			 * CUserTask newTsk = new CUserTask(tsk);
			 * CUserTasks.Instance().addElement(newTsk);
			 * CEmployments.Instance().AddTask(newTsk); } } else { CTask tsk =
			 * (CTask) CTasks.Instance().GetByRemoveStr(commandLine); if (tsk ==
			 * null) break; CUserTask updTask = tsk.UserTask(); if (updTask !=
			 * null && !updTask.Result().equals(new String()) &&
			 * !updTask.WasSent()) break; CTasks.Instance().removeElement(tsk);
			 * CUserTasks.Instance().removeElement(tsk);
			 * CEmployments.Instance().RemoveTask(tsk); } break;
			 */
		case ADDITIONAL_WORK:
			// if
			// (GetSyncStatus().indexOf(context.getString(org.microemu.android.R.string.additional_inserts))
			// == -1)
			// setSyncStatus(context.getString(org.microemu.android.R.string.additional_inserts),
			// 5, isAutomaticSync);
			if (commandActionType == NEED_TO_ADD) {
				AdditionalWork addWork = new AdditionalWork(commandLine);
				AdditionalWorkManager.Instance().save(addWork);
			} else {
				AdditionalWorkManager.Instance().delete(getIDFromStr(commandLine));
			}
			break;
		case TOUR:
			// if
			// (GetSyncStatus().indexOf(context.getString(org.microemu.android.R.string.tour))
			// == -1)
			// setSyncStatus(context.getString(org.microemu.android.R.string.tour),
			// 5, isAutomaticSync);
			if (commandActionType == NEED_TO_ADD) {
				Tour tour = new Tour(commandLine);
				TourManager.Instance().save(tour);
			} else {
				TourManager.Instance().delete(getIDFromStr(commandLine));
			}
			break;
		case RELATIVE:
			// if
			// (GetSyncStatus().indexOf(context.getString(org.microemu.android.R.string.member))
			// == -1)
			// setSyncStatus(context.getString(org.microemu.android.R.string.member),
			// 5, isAutomaticSync);
			if (commandActionType == NEED_TO_ADD) {
				Relative relative = new Relative(commandLine);
				RelativeManager.Instance().save(relative);
			} else {
				RelativeManager.Instance().delete(getIDFromStr(commandLine));
			}
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
	
	private int getIDFromStr(String str) {
		str.substring(0,1);
		String strArr = str.split(";")[0];
		return strArr.length() > 0 ? Integer.parseInt(str.split(";")[0]) : -1;
	}
	
}
