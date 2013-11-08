package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;
import isebase.cognito.tourpilot.Templates.WorkerAdapter;

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
import android.widget.EditText;
import android.widget.ListView;

public class WorkersActivity extends BaseActivity {

	List<Worker> workers;

	Worker selectedWorker;

	private Dialog dialogPin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workers);
		reloadData();
		initPinDialog();
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
			return dialogPin;
		default:
			return null;
		}
	}

	private void initPinDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.AppBaseTheme));
		LayoutInflater inflater = getLayoutInflater();
		builder.setView(inflater.inflate(R.layout.dialog_pin, null));

		builder.setPositiveButton(getString(R.string.ok),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int buttonId) {
						String name = selectedWorker.getName();
						String pinStr = ((EditText) dialogPin.findViewById(R.id.evPin)).getText().toString();
					//	if (!checkWorkerPIN(name, pinStr))
					//		return;

						Option.Instance().setWorkerID(selectedWorker.getId());
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
	}
	
	private void initListWorkers() {
		final ListView listView = (ListView) findViewById(R.id.lvWorkers);
		WorkerAdapter adapter = new WorkerAdapter(this, R.layout.row_worker_template, workers);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				selectedWorker = (Worker)listView.getItemAtPosition(position);
				if (Option.testMode)
				{
					switchToTours();
					return;
				}
				showDialog(0);
				dialogPin.setTitle((selectedWorker).getName());
				((EditText) dialogPin.findViewById(R.id.evPin)).setText("");
			}
		});
	}

	public void switchToOptions(View view) {
		Intent optionsActivity = new Intent(getApplicationContext(),
				OptionsActivity.class);
		startActivity(optionsActivity);
	}

	private void switchToTours() {
		Intent toursActivity = new Intent(getApplicationContext(),
				ToursActivity.class);
		startActivity(toursActivity);
	}

	private void reloadData() {
		workers = WorkerManager.Instance().load();
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
