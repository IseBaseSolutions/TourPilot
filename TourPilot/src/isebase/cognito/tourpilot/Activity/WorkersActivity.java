package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;
import isebase.cognito.tourpilot.DataBase.DataBaseWrapper;
import isebase.cognito.tourpilot.Dialogs.BaseDialogListener;
import isebase.cognito.tourpilot.Dialogs.PinDialog;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WorkersActivity extends BaseActivity implements BaseDialogListener {

	private List<Worker> workers = new ArrayList<Worker>();
	private PinDialog pinDialog;
	private Worker selectedWorker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_workers);
			reloadData();
			initDialogs();
			initListWorkers();
		}catch(Exception ex){
			ex.printStackTrace();
			criticalClose();
		}
	}
		
	@Override
	public void onBackPressed() {
		toOptionActivity();
	}
	
	public void btOptionsClick(View view) {
		toOptionActivity();
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
				showPinDialog();
			}
		});
	}

	public void reloadData() {
		workers = WorkerManager.Instance().load(null, null, BaseObject.NameField);
		Option.Instance().setWorkerActivity(true);
		Option.Instance().save();
	}

	private void initDialogs() {
		pinDialog = new PinDialog();
	}
	
	private void showPinDialog() {
		pinDialog.setWorker(selectedWorker);
		pinDialog.show(getSupportFragmentManager(), "dialogPin");
		getSupportFragmentManager().executePendingTransactions();
		pinDialog.getDialog().setTitle(selectedWorker.getName());
	}
	
	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		if (dialog != pinDialog)
			return;
		logIn();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		return;
	}
	
	private void logIn(){
		DataBaseWrapper.Instance().clearWorkerData();
		saveSelectedWorkerID();
		startSyncActivity();
	}
	
	private void saveSelectedWorkerID() {
		Option.Instance().setPrevWorkerID(Option.Instance().getWorkerID());
		Option.Instance().setWorkerID(selectedWorker.getID());
		Option.Instance().save();
	}

	private void toOptionActivity(){
		Option.Instance().setWorkerActivity(false);
		Option.Instance().save();
		startOptionsActivity();
	}
	
}
