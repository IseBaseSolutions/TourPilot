package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Option.OptionManager;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;

import java.util.ArrayList;
import java.util.List;

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

public class WorkersActivity extends BaseActivity {

	List<Worker> workers = new ArrayList<Worker>();

	Worker selectedWorker;
	Option option;

	private Dialog dialogPin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workers);
		reloadData();
		initTable(workers.size());
		initListWorkers();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case 0:
			return getDialogPin();
		default:
			return null;
		}
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
				if (Option.testMode)
				{
					switchToTours();
					return;
				}

				showDialog(0);
				selectedWorker = (Worker) listView.getItemAtPosition(position);
				dialogPin.setTitle((selectedWorker).getName());
				((EditText) dialogPin.findViewById(R.id.evPin)).setText("");
			}
		});
	}

	private void initTable(int tableSize) {
		if (tableSize > 0)
			return;
		for (int i = 0; i < 10; i++){
			Worker w = new Worker();
			w.setName("Worker " + i);
			WorkerManager.Instance().save(w);			
		}
		reloadData();
	}

	public void switchToOptions(View view) {
		Intent optionsActivity = new Intent(getApplicationContext(),
				OptionsActivity.class);
		startActivity(optionsActivity);
	}

	public void switchToTours() {
		Intent toursActivity = new Intent(getApplicationContext(),
				ToursActivity.class);
		startActivity(toursActivity);
	}

	public void reloadData() {
		workers = WorkerManager.Instance().load();
		option = OptionManager.Instance().loadOption();
	}

	private Dialog getDialogPin() {
		if (dialogPin != null)
			return dialogPin;
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.AppBaseTheme));
		LayoutInflater inflater = getLayoutInflater();
		builder.setView(inflater.inflate(R.layout.dialog_pin, null));

		builder.setPositiveButton(getString(R.string.ok),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int buttonId) {
						String name = selectedWorker.getName();
						String pinStr = ((EditText) dialogPin
								.findViewById(R.id.evPin)).getText().toString();
						if (!checkWorkerPIN(name, pinStr))
							return;
						if (option != null) {
							option.setWorkerID(selectedWorker.getId());
							OptionManager.Instance().save(option);
						}
						switchToTours();
					}
				});

		builder.setNegativeButton(getString(R.string.cancel),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int buttonId) {
						return;
					}
				});
		builder.setTitle(R.string.some_text);
		builder.setMessage(R.string.enter_pin);
		builder.setIcon(R.drawable.dialog_password);
		dialogPin = builder.create();
		return dialogPin;
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

}
