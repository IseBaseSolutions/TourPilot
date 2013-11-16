package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.R.string;
import isebase.cognito.tourpilot.Activity.AdditionalTasks.CatalogsActivity;
import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.Data.Task.Task.eTaskState;
import isebase.cognito.tourpilot.Data.Task.TaskManager;
import isebase.cognito.tourpilot.Dialogs.DialogInputValue;
import isebase.cognito.tourpilot.Dialogs.DialogManualInput;
import isebase.cognito.tourpilot.Templates.TaskAdapter;
import isebase.cognito.tourpilot.Templates.TaskFormatDataResultInput;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class TasksActivity extends FragmentActivity implements DialogManualInput.DialogManualInputListener, DialogInputValue.DialoglInputValueListener {

	TaskAdapter adapter;
	
	List<Task> tasks;
	
	View viewInputValues;
	
	List<TaskFormatDataResultInput> listDataInputs;
	
	private DialogManualInput dialogManualInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tasks);
//		reloadData();
		tasks = new ArrayList<Task>();
		InitTable(tasks.size());
		initTaskList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tasks, menu);
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.tasks, menu);
	}

	public void onChangeState(View view) {
		
		viewInputValues = view;
		
		Task task = (Task) viewInputValues.getTag();
		
		if(task.getTaskState() != eTaskState.Done)
			ShowDialogInputedValue(task.iType);
		else
			ChangeState();
		
	}

	private void initTaskList() {
		adapter = new TaskAdapter(this, R.layout.row_task_template, tasks);
		ListView tasksListView = (ListView) findViewById(R.id.lvTasksList);
		tasksListView.setAdapter(adapter);
	}

	public void reloadData() {
		tasks = TaskManager.Instance().load();
	}

	private void InitTable(int tableSize) {
		if (tableSize > 0)
			return;
		int iType = 0;// for test
		for (int i = 0; i < 10; i++){
			iType ++;
			if(iType > 3) iType = 1;
			Task task = new Task();
			task.setName("TASK #" + i);
			task.iType = iType;
			tasks.add(task);
			
		}
	}
	private void ShowDialogInputedValue(int iTaskType)
	{
		
		DialogInputValue dialogInputValues;
		
		listDataInputs = new ArrayList<TaskFormatDataResultInput>();
		
		TaskFormatDataResultInput DataInput;
		int[] arrayInputTypes;
		int iValueLength = 0;
		
		String titleText = "";
		
		switch(iTaskType){
		case 1:
			///BLOOD SUGAR
			arrayInputTypes = new int[] { InputType.TYPE_CLASS_NUMBER, InputType.TYPE_NUMBER_FLAG_DECIMAL };
			iValueLength = 4;
			DataInput = new TaskFormatDataResultInput(R.string.value_input_sugar, iValueLength, arrayInputTypes);

			listDataInputs.add(DataInput);
			
			titleText = "BLOOD SUGAR";
			break;
		case 2:
			//BLOOD PRESSER
			arrayInputTypes = new int[] { InputType.TYPE_CLASS_NUMBER};
			iValueLength = 3;
			DataInput = new TaskFormatDataResultInput(R.string.value_input_presser_bottom, iValueLength, arrayInputTypes);
			listDataInputs.add(DataInput);

			arrayInputTypes = new int[] { InputType.TYPE_CLASS_NUMBER};
			iValueLength = 3;
			DataInput = new TaskFormatDataResultInput(R.string.value_input_presser_top, iValueLength, arrayInputTypes);
			listDataInputs.add(DataInput);

			titleText = "BLOOD PRESSER";
			break;
		case 3:
			///PULSE
			arrayInputTypes = new int[] { InputType.TYPE_CLASS_NUMBER };
			iValueLength = 3;
			DataInput = new TaskFormatDataResultInput(R.string.value_input_pulse, iValueLength, arrayInputTypes);

			listDataInputs.add(DataInput);
			
			titleText = "PULSE";
			break;
		default:
			break;
		}
		
		dialogInputValues = new DialogInputValue(listDataInputs);
		dialogInputValues.show(getSupportFragmentManager(), "InputValues");
		getSupportFragmentManager().executePendingTransactions();
		dialogInputValues.getDialog().setTitle(titleText);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.add_task_category:
	        	Intent addTasksCategoryActivity = new Intent(getApplicationContext(),CatalogsActivity.class);
	        	startActivity(addTasksCategoryActivity);
	            return true;
	        case R.id.cancelAllTasks:
	        	CanceledTasks();
	        	return true;
	        case R.id.notes:
	        	Intent notesActivity = new Intent(getApplicationContext(),NotesActivity.class);
	        	startActivity(notesActivity);
	        	return true;
	        case R.id.info:
	        	Intent infoActivity = new Intent(getApplicationContext(),InfoActivity.class);
	        	startActivity(infoActivity);
	        	return true;
	        case R.id.manualInput:
	        	showDialogManualInput();
	       // 	Intent manualInputActivity = new Intent(getApplicationContext(),ManualInputActivity.class);
	       // 	startActivity(manualInputActivity);
	        	return true;
	        case R.id.address:
	        	Intent addressActivity = new Intent(getApplicationContext(),AddressActivity.class);
	        	startActivity(addressActivity);
	        	return true;
	        case R.id.doctors:
	        	Intent doctorsActivity = new Intent(getApplicationContext(),DoctorsActivity.class);
	        	startActivity(doctorsActivity);
	        	return true;
	        case R.id.relatives:
	        	Intent relativesActivity = new Intent(getApplicationContext(),RelativesActivity.class);
	        	startActivity(relativesActivity);
	        	return true;
	        case R.id.comments:
	        	Intent commentsActivity = new Intent(getApplicationContext(),CommentsActivity.class);
	        	startActivity(commentsActivity);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	private void CanceledTasks(){
		
		for(Task tempTask : tasks){
			tempTask.setTaskState(eTaskState.UnDone);
			try {
				TaskManager.Instance().save(tempTask);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ListView tasksListView = (ListView) findViewById(R.id.lvTasksList);
		tasksListView.setAdapter(adapter);
	}
	private void showDialogManualInput(){
		dialogManualInput = new DialogManualInput();
		dialogManualInput.show(getSupportFragmentManager(), "ManualInput");
		getSupportFragmentManager().executePendingTransactions();
		dialogManualInput.getDialog().setTitle("SET MANUAL INPUT WORK");
	}

	private void ChangeState()
	{
		Task task = (Task) viewInputValues.getTag();
		
		task.setTaskState((task.getTaskState() == eTaskState.Done) ? eTaskState.UnDone
				: eTaskState.Done);
		try {
			((Button) viewInputValues)
					.setBackgroundResource((task.getTaskState() == eTaskState.UnDone) ? android.R.drawable.ic_delete
							: android.R.drawable.checkbox_on_background);
			TaskManager.Instance().save(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onDialogBackClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDialogServisesClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDialogOkInputValuesClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
	//	ChangeState();
		String result = "";
		for(TaskFormatDataResultInput temp : listDataInputs){
			result += temp.getstrLabelName() + " : " + temp.getstrInputValue() + "\n";
		}
		Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDialogCanceInputValuelClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}

}
