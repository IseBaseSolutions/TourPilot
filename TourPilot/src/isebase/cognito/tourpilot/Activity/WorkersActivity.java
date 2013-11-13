package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;
import isebase.cognito.tourpilot.Dialogs.DialogPin;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WorkersActivity extends FragmentActivity implements DialogPin.DialogPinListener {

	private List<Worker> workers = new ArrayList<Worker>();
	private Worker selectedWorker;
	private DialogPin dialogPin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workers);
		reloadData();
		initDialogs();
		initListWorkers();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}

	public void initListWorkers() {
		final ListView listView = (ListView) findViewById(R.id.lvWorkers);
		ArrayAdapter<Worker> adapter = new ArrayAdapter<Worker>(this,
				android.R.layout.simple_list_item_1, workers);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				selectedWorker = (Worker) listView.getItemAtPosition(position);
				showDialogPin();
			}
		});
	}

	public void switchToOptions(View view) {
		Intent optionsActivity = new Intent(getApplicationContext(), OptionsActivity.class);
		startActivity(optionsActivity);
	}

	public void startWorkerSync() {
		Intent synchActivity = new Intent(getApplicationContext(),
				SynchronizationActivity.class);
		startActivity(synchActivity);
	}

	public void reloadData() {
		workers = WorkerManager.Instance().load(null, null, BaseObject.NameField);
	}

	public boolean checkWorkerPIN(String workerName, String strPin) {
		if (strPin.equals(""))
			return false;
		Long pin = Long.parseLong(strPin);
		long num = 0;
		int numArray[] = new int[] { 1, 3, 5, 7, 13, 0x11 };
		try {
			byte byteText[] = workerName.getBytes("latin1");
			for (int i = 0; i < byteText.length; i++)
				num += (byteText[i]) * numArray[i % 6];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num == pin;
	}

	private void initDialogs() {
		dialogPin = new DialogPin();
	}

	private void showDialogPin() {
		dialogPin.show(getSupportFragmentManager(), "dialogPin");
		getSupportFragmentManager().executePendingTransactions();
		dialogPin.getDialog().setTitle(selectedWorker.getName());
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		if (dialog != dialogPin)
			return;
		String name = selectedWorker.getName();
		String pinStr = dialogPin.etPin.getText().toString();
		if (!checkWorkerPIN(name, pinStr))
			return;
		saveWorker();
		startWorkerSync();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		return;
	}
	
	@Override
	public void onBackPressed() {
		switchToOptions(null);
		super.onBackPressed();
	}
	
	private void saveWorker() {
		Option.Instance().setWorkerID(selectedWorker.getId());
		Option.Instance().save();
	}

}
