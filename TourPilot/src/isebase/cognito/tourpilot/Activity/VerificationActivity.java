package isebase.cognito.tourpilot.Activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.EmploymentVerification.EmploymentVerification;
import isebase.cognito.tourpilot.Data.EmploymentVerification.EmploymentVerificationManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.Data.Task.TaskManager;
import isebase.cognito.tourpilot.Data.Task.Task.eTaskState;
import isebase.cognito.tourpilot.Data.UserRemark.UserRemark;
import isebase.cognito.tourpilot.Data.UserRemark.UserRemarkManager;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Utils.DateUtils;
import android.os.Bundle;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class VerificationActivity extends BaseActivity {

	private Intent answerInent;

	
	private List<Task> tasks;
	private TextView tvVerification;

	private String taskVerification;

	private Employment employment;
	
	private UserRemark userRemark;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verification);
		tvVerification = (TextView) findViewById(R.id.tvVerificationText);
		
		employment = EmploymentManager.Instance().load(Option.Instance().getEmploymentID());
		tasks = TaskManager.Instance().load(Task.EmploymentIDField, String.valueOf(Option.Instance().getEmploymentID()));
		
		taskVerification = "";

		taskVerification += "<b>" + getWorker(Option.Instance().getWorker()) + "</b>";
		taskVerification += "hat am ";
		
		taskVerification += "<b>" + getDate(DateUtils.DateFormat.format(employment.getDate())) + "</b> ";
		taskVerification += "bei Patient ";
		taskVerification += "<b>" + getPatient(employment.getName()) + "</b>";
		taskVerification += "in der Zeit von: ";
		taskVerification += "<b>" + getTime( DateUtils.HourMinutesFormat.format(tasks.get(0).getManualDate().equals(DateUtils.EmptyDate) ? tasks.get(0).getRealDate() : tasks.get(0).getManualDate())) + "</b>";
		taskVerification += "bis ";
		taskVerification += "<b>" + getTime(DateUtils.HourMinutesFormat.format(tasks.get(tasks.size()-1).getManualDate().equals(DateUtils.EmptyDate) ? tasks.get(tasks.size()-1).getRealDate() : tasks.get(tasks.size()-1).getManualDate())) + "</b>";
		taskVerification += "bei einer Dauer von ";
		taskVerification += "<b>" + getInterval(tasks.get(0).getManualDate().equals(DateUtils.EmptyDate) ? tasks.get(0).getRealDate() : tasks.get(0).getManualDate(),tasks.get(tasks.size()-1).getManualDate().equals(DateUtils.EmptyDate) ? tasks.get(tasks.size()-1).getRealDate() : tasks.get(tasks.size()-1).getManualDate()) + "</b>";
		taskVerification += getString(R.string.minuten_einen) + "<br /><br />";
		
		String[] arrayResultTask = getTasks();
		int doneTasks = 0;
		int undoneTasks = 1;
		taskVerification += "<b>" + getString(R.string.done_tasks) + ":</b> <br />" + ((arrayResultTask[doneTasks].equals("")) ? getString(R.string.there_are_no_done_tasks) + "<br />" : arrayResultTask[doneTasks]) + "<br />";
		taskVerification += "<b>" + getString(R.string.undone_tasks) + ":</b> <br />" + ((arrayResultTask[undoneTasks].equals("")) ? getString(R.string.there_are_no_undone_tasks) + "<br />" : arrayResultTask[undoneTasks]) + "<br />";
		taskVerification += getFlege();
		
		tvVerification.setText(Html.fromHtml(taskVerification));
		answerInent = new Intent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.verification, menu);
		return true;
	}

	public void onClickButtonOk(View view) {
		
		saveVerification();
		setResult(VerificationActivity.RESULT_OK, answerInent);
		finish();
	}

	public void onClickButtonCancel(View view) {
		setResult(VerificationActivity.RESULT_CANCELED, answerInent);
		finish();
	}

	private String getWorker(Worker worker) {
		String[] workerName = worker.getName().split(" ");
		return String.format("%s %s ",workerName[0], workerName[1]);
	}
	
	private String getDate(String date) {
		String times = date + " ";
		return times;
	}

	private String getPatient(String patient) {
		String[] patientName = patient.split(" ") ;
		return String.format("%s %s ", patientName[0],patientName[1]); 
	}

	private String getTime(String time) {
		return time + " ";
	}

	private String getInterval(Date timeStart, Date timeStop) {
		SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		long difference = (timeStop.getTime() - timeStart.getTime()) / 1000 / 60;
		return String.valueOf(difference) + " ";
	}
	
	private String[] getTasks() {
		String[] sTasks = new String[]{ "", "" };
		List<Task> tasksExceptFirstAndLast = new ArrayList<Task>(tasks);
		tasksExceptFirstAndLast.remove(0);
		tasksExceptFirstAndLast.remove(tasksExceptFirstAndLast.size() - 1);
		for(Task task : tasksExceptFirstAndLast) { 
			if(task.getState() == eTaskState.Done) {
				sTasks[0] +=  " - " + task.getName() + (task.getQualityResult().equals("") ? "" : (" (" + task.getQualityResult()) + ")") + "<br />";
			}
			else
				sTasks[1] += " - " + task.getName() + "<br />";
		}
		return sTasks;
	}
	
	private String getFlege() {
		String flege = "";
		Intent intentFlege = getIntent();
		Bundle bundle = intentFlege.getExtras();
		boolean isFlegeOK = false;
		if(bundle != null){
			isFlegeOK = bundle.getBoolean("isFlegeOK");
			if(isFlegeOK){
				flege += "Flege is OK <br />";
			}else {
				userRemark = UserRemarkManager.Instance().loadByWorkerPatient(Option.Instance().getWorkerID(), employment.getPatientID());
				String connect = new Boolean((userRemark.getCheckboxes() & 1) == 1).toString();
				String medChanges = new Boolean((userRemark.getCheckboxes() & 2) == 2).toString();
				String pflege = new Boolean((userRemark.getCheckboxes() & 4) == 4).toString();
				flege += "<b>" + getString(R.string.note) + ":</b> <br />";
				if(!connect.equals("false"))
					flege += getString(R.string.enter_interval) + ": + <br />";
				else
					flege += getString(R.string.enter_interval) + ": - <br />";
				if(!medChanges.equals("false"))
					flege += getString(R.string.medications_changed) + ": + <br />";
				else
					flege += getString(R.string.medications_changed) + ": - <br />";
				if(!pflege.equals("false"))
					flege += getString(R.string.aubrplanmabige_pflege) + ": + <br />";
				else
					flege += getString(R.string.aubrplanmabige_pflege) + ": - <br />";
				if(!userRemark.getName().equals(""))
					flege += "<b>" + getString(R.string.other) + ":</b> " + userRemark.getName() + "<br />";
			}
		}
		return flege + "<br />";
	}
	
	private void saveVerification() {
		long workerID = Option.Instance().getWorkerID();
		
		long patientID = PatientManager.Instance().load(employment.getPatientID()).getID();
		
		Date dateBegin = tasks.get(0).getManualDate().equals(DateUtils.EmptyDate) ? tasks.get(0).getRealDate() : tasks.get(0).getManualDate();
		Date dateEnd = tasks.get(tasks.size()-1).getManualDate().equals(DateUtils.EmptyDate) ? tasks.get(tasks.size()-1).getRealDate() : tasks.get(tasks.size()-1).getManualDate();
		
		String doneTasksIDs = "", undoneTasksIDs = "";
		
		for(int i = 1;i < tasks.size() - 1;i++) {
			if(tasks.get(i).getState().equals(eTaskState.Done))
				doneTasksIDs += (doneTasksIDs.equals("") ? "" : ",") + tasks.get(i).getID();
			else
				undoneTasksIDs += (undoneTasksIDs.equals("") ? "" : ",") + tasks.get(i).getID();
		}
		String userRemarks = getFlegeMarks();
		
<<<<<<< HEAD
		EmploymentVerificationManager.Instance().save(new EmploymentVerification(workerID, patientID, dateBegin, 
				dateEnd, additionalyTasksIDs, doneTasksIDs, undoneTasksIDs, userRemarks));
=======
		EmploymentVerificationManager.Instance().save(new EmploymentVerification(workerID, patientID, dateBegin, dateEnd, doneTasksIDs, undoneTasksIDs, userRemarks));
>>>>>>> refs/heads/VladimirsBranch2
	}

	private String getFlegeMarks() {
		String flegeMarks = "";
		if(userRemark == null)
				flegeMarks += "";				
		else {
			UserRemark userRemark = UserRemarkManager.Instance().loadByWorkerPatient(Option.Instance().getWorkerID(), employment.getPatientID());
			String connect = new Boolean((userRemark.getCheckboxes() & 1) == 1).toString();
			String medChanges = new Boolean((userRemark.getCheckboxes() & 2) == 2).toString();
			String pflege = new Boolean((userRemark.getCheckboxes() & 4) == 4).toString();
			if(!connect.equals("false"))
				flegeMarks += "+,";
			else
				flegeMarks += "-,";
			if(!medChanges.equals("false"))
				flegeMarks += "+,";
			else
				flegeMarks += "-,";
			if(!pflege.equals("false"))
				flegeMarks += "+,";
			else
				flegeMarks += "-,";
			flegeMarks += userRemark.getName();
		}
		
		return flegeMarks;
	}
}
