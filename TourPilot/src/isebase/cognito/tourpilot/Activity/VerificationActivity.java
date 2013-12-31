package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.EmploymentVerification.EmploymentVerification;
import isebase.cognito.tourpilot.Data.EmploymentVerification.EmploymentVerificationManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.Data.Task.Task.eTaskState;
import isebase.cognito.tourpilot.Data.Task.TaskManager;
import isebase.cognito.tourpilot.Data.UserRemark.UserRemark;
import isebase.cognito.tourpilot.Data.UserRemark.UserRemarkManager;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Utils.DateUtils;

import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class VerificationActivity extends BaseActivity {

	private List<Task> tasks;
	private TextView tvVerification;

	private String taskVerification;

	private Employment employment;
	
	private UserRemark userRemark;
	
	private Date dateBegin;
	private Date dateEnd;
	private Button btVerificationOK;
	
	private boolean isFlegeOK;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verification);
		tvVerification = (TextView) findViewById(R.id.tvVerificationText);
		btVerificationOK = (Button)findViewById(R.id.btVerificationOK);
		tasks = TaskManager.Instance().load(Task.EmploymentIDField, String.valueOf(Option.Instance().getEmploymentID()));
		
		reloadData();
		
		dateBegin = employment.getFirstTask().getManualDate().equals(DateUtils.EmptyDate) ? employment.getFirstTask().getRealDate() : employment.getFirstTask().getManualDate();
		dateEnd = employment.getLastTask().getManualDate().equals(DateUtils.EmptyDate) ? employment.getLastTask().getRealDate() : employment.getLastTask().getManualDate();;

		taskVerification = "";

		taskVerification += "<b>" + getWorker(Option.Instance().getWorker()) + "</b>";
		taskVerification += "hat am ";
		
		taskVerification += "<b>" + getDate(DateUtils.DateFormat.format(employment.getDate())) + "</b> ";
		taskVerification += "bei Patient ";
		taskVerification += "<b>" + getPatient(employment.getName()) + "</b>";
		taskVerification += "in der Zeit von: ";
		taskVerification += "<b>" + getTime( DateUtils.HourMinutesFormat.format(dateBegin)) + "</b>";
		taskVerification += "bis ";
		taskVerification += "<b>" + getTime(DateUtils.HourMinutesFormat.format(dateEnd)) + "</b> ";
		int interval = getInterval(dateBegin, dateEnd); 
		if( interval > 0) {
			taskVerification += "bei einer Dauer von ";
			taskVerification += "<b>" + interval + "</b> ";
			taskVerification += getString(R.string.minuten_einen);
		} else {
			taskVerification += "die Einsatzdauer <b>ist weniger als 1 Minute</b>. ";
		}
		taskVerification += "<br /><br />";
		
		String[] arrayResultTask = getTasks();
		int doneTasks = 0;
		int undoneTasks = 1;
		taskVerification += "<b>" + getString(R.string.done_tasks) + ":</b> <br />" + ((arrayResultTask[doneTasks].equals("")) ? getString(R.string.there_are_no_done_tasks) + "<br />" : arrayResultTask[doneTasks]) + "<br />";
		taskVerification += "<b>" + getString(R.string.undone_tasks) + ":</b> <br />" + ((arrayResultTask[undoneTasks].equals("")) ? getString(R.string.there_are_no_undone_tasks) + "<br />" : arrayResultTask[undoneTasks]) + "<br />";
		taskVerification += getFlege();
		
		tvVerification.setText(Html.fromHtml(taskVerification));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.verification, menu);
		return true;
	}

	public void onClickButtonOk(View view) {		
		saveVerification();
		setResult(VerificationActivity.RESULT_OK);
		finish();
	}

	public void onClickButtonCancel(View view) {
		setResult(VerificationActivity.RESULT_CANCELED);
		finish();
	}

	public void onClickCheckVerification(View view) {
		CheckBox chbCheckVerification = (CheckBox)findViewById(R.id.chbCheckVerification);
		btVerificationOK.setEnabled(chbCheckVerification.isChecked());
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

	private int getInterval(Date timeStart, Date timeStop) {
		return timeStop.getMinutes() - timeStart.getMinutes();
	}
		
	private String[] getTasks() {
		String[] sTasks = new String[]{ "", "" };
		for(Task task : tasks) {
			if(task.getName().contains(getString(R.string.start_task)) || task.getName().contains(getString(R.string.end_task)))
				continue;
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
		isFlegeOK = false;
		if(bundle != null){
			isFlegeOK = bundle.getBoolean("isAllOK");
			if(isFlegeOK)
				flege += getString(R.string.all_ok) + "<br />";
			userRemark = UserRemarkManager.Instance().load(employment.getID());
			if(userRemark == null)
				return flege;	
			flege += "<b>" + getString(R.string.visit_notes) + ":</b> <br />";
			if((userRemark.getCheckboxes() & 1) == 1)
				flege += getString(R.string.connect_with) + ": " + "<b>" + getString(R.string.yes) + "</b>" + " <br />";
			else
				flege += getString(R.string.connect_with) + ": " + "<b>" + getString(R.string.no) + "</b>" + " <br />";
			if((userRemark.getCheckboxes() & 2) == 2)
				flege += getString(R.string.medications_changed) + ": " + "<b>" + getString(R.string.yes) + "</b>" + " <br />";
			else
				flege += getString(R.string.medications_changed) + ": " + "<b>" + getString(R.string.no) + "</b>" + " <br />";
			if((userRemark.getCheckboxes() & 4) == 4)
				flege += getString(R.string.aubrplanmabige_pflege) + ": " + "<b>" + getString(R.string.yes) + "</b>" + " <br />";
			else
				flege += getString(R.string.aubrplanmabige_pflege) + ": " + "<b>" + getString(R.string.no) + "</b>" + " <br />";
			if(!userRemark.getName().equals(""))
				flege += "<b>" + getString(R.string.other) + "</b> " + userRemark.getName() + "<br />";
		}
		return flege + "<br />";
	}
	
	private void saveVerification() {
		long employmentID = Option.Instance().getEmploymentID();
		
		long workerID = Option.Instance().getWorkerID();
		
		long patientID = PatientManager.Instance().load(employment.getPatientID()).getID();

		String doneTasksIDs = "", undoneTasksIDs = "";
		for(Task task : tasks) {
			if(task.getName().contains(getString(R.string.start_task)) || task.getName().contains(getString(R.string.end_task)))
				continue;
			if(task.getState().equals(eTaskState.Done))
				doneTasksIDs += (doneTasksIDs.equals("") ? "" : ",") + task.getAditionalTaskID();
			else
				undoneTasksIDs += (undoneTasksIDs.equals("") ? "" : ",") + task.getAditionalTaskID();
		}
		String userRemarks = getFlegeMarks();
		EmploymentVerificationManager.Instance().save(new EmploymentVerification(employmentID, workerID, patientID, dateBegin, dateEnd, doneTasksIDs, undoneTasksIDs, userRemarks, isFlegeOK));
	}

	private String getFlegeMarks() {
		String flegeMarks = "";
		if(userRemark == null)
				flegeMarks += "";				
		else {
			if((userRemark.getCheckboxes() & 1) == 1)
				flegeMarks += "+,";
			else
				flegeMarks += "-,";
			if((userRemark.getCheckboxes() & 2) == 2)
				flegeMarks += "+,";
			else
				flegeMarks += "-,";
			if((userRemark.getCheckboxes() & 4) == 4)
				flegeMarks += "+,";
			else
				flegeMarks += "-,";
			flegeMarks += userRemark.getName();
		}
		return flegeMarks;
	}
	
	private void reloadData() {
		employment = EmploymentManager.Instance().loadAll(Option.Instance().getEmploymentID());
	}
	
}
