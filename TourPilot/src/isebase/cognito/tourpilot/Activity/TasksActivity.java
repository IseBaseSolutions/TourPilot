package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.Data.Task.Task.eTaskState;
import isebase.cognito.tourpilot.Data.Task.TaskManager;
import isebase.cognito.tourpilot.Templates.TaskAdapter;

import java.util.List;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class TasksActivity extends BaseActivity {

	TaskAdapter adapter;
	List<Task> tasks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tasks);
		reloadData();
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
		Task task = (Task) view.getTag();
		task.setTaskState((task.getTaskState() == eTaskState.Done) ? eTaskState.UnDone
				: eTaskState.Done);
		try {
			((Button) view)
					.setBackgroundResource((task.getTaskState() == eTaskState.UnDone) ? android.R.drawable.ic_delete
							: android.R.drawable.checkbox_on_background);
			TaskManager.Instance().save(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		for (int i = 0; i < 10; i++)
			TaskManager.Instance().save(new Task("Task " + i));
		reloadData();
	}
}
