package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class WorkersActivity extends Activity {

	WorkerManager workerManager;
	String workerName = new String();
	Dialog pinDialog;
	TextView tvWorkerName;
	EditText evPin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workers);
		initActivityComponents();
		workerManager.open();
		List<Worker> workers = workerManager.loadAll();
		InitTable(workers.size());
		final ListView listView = (ListView) findViewById(R.id.lvWorkers);
		ArrayAdapter<Worker> adapter = new ArrayAdapter<Worker>(this,
				android.R.layout.simple_list_item_1, workers);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				workerName = ((Worker) listView.getItemAtPosition(position))
						.getName();
				showDialog(0);
				if (pinDialog != null) {
					initDialogComponents();
					tvWorkerName.setText(workerName);
					evPin.setText("");
				}
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}

	private void initActivityComponents() {
		workerManager = new WorkerManager(this);
	}

	private void InitTable(int tableSize) {
		if(tableSize > 0)
			return;
		workerManager.addWorker("Goncharenko, Andrew");
		workerManager.addWorker("Begov, Bogdan");
		workerManager.addWorker("Kiryanov, Igor");
		workerManager.addWorker("Goenko, Nikolai");
		workerManager.addWorker("Parker, Peter");
		workerManager.addWorker("Wayne, Bruce");
		workerManager.addWorker("Kent, Clark");
	}

	private void initDialogComponents() {
		tvWorkerName = (TextView) pinDialog.findViewById(R.id.tvWorkerName);
		evPin = (EditText) pinDialog.findViewById(R.id.evPin);
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case 0:
			return getPinDialog();
		default:
			return null;
		}
	}

	public void addUser(View view) {

		// EditText text = (EditText) findViewById(R.id.editText1);
		// Worker worker = workerOperation.addWorker(text.getText().toString());
		//
		// adapter.add(worker);

	}

	public void deleteFirstUser(View view) {

		// Worker worker = null;
		//
		// if (getListAdapter().getCount() > 0) {
		// worker = (Worker) getListAdapter().getItem(0);
		// workerOperation.deleteWorker(worker);
		// adapter.remove(worker);
		// }

	}

	private Dialog getPinDialog() {
		if (pinDialog != null)
			return pinDialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.AppBaseTheme));
		LayoutInflater inflater = getLayoutInflater();
		builder.setView(inflater.inflate(R.layout.dialog_pin, null));

		builder.setPositiveButton(getString(R.string.ok),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int buttonId) {
						if (!checkPIN())
							return;
						Intent toursActivity = new Intent(
								getApplicationContext(), ToursActivity.class);
						startActivity(toursActivity);
					}
				});

		builder.setNegativeButton(getString(R.string.cancel),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int buttonId) {
						return;
					}
				});
		pinDialog = builder.create();
		return pinDialog;
	}

	public boolean checkPIN() {
		String name = tvWorkerName.getText().toString();
		String pinStr = evPin.getText().toString();
		if (pinStr.equals(""))
			return false;
		Long pin = Long.parseLong(pinStr);
		long num = 0;
		int numArray[] = new int[] { 1, 3, 5, 7, 13, 0x11 };
		try {
			byte byteText[] = name.getBytes("latin1");
			for (int i = 0; i < byteText.length; i++)
				num += (byteText[i]) * numArray[i % 6];
		} catch (Exception ex) {
		}
		return num == pin;
	}

	public void switchToOptions(View view) {
		Intent optionsActivity = new Intent(getApplicationContext(),
				OptionsActivity.class);
		startActivity(optionsActivity);
	}
}
