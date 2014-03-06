package isebase.cognito.tourpilot.Activity.WorkersOptionActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.SynchronizationActivity;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectComparer;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;
import isebase.cognito.tourpilot.DataBase.DataBaseWrapper;
import isebase.cognito.tourpilot.Dialogs.BaseDialogListener;
import isebase.cognito.tourpilot.Dialogs.PinDialog;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

public class WorkersFragment extends Fragment implements BaseDialogListener {

	private List<Worker> workers = new ArrayList<Worker>();
	private PinDialog pinDialog;
	private Worker selectedWorker;	
	
	private View rootView;
	
	private ListView listView;
	
	public WorkersFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(
				R.layout.new_activity_workers, container, false);
		reloadData();
		initControls();
		initDialogs();
		initListWorkers();		
		return rootView;
	}
	
	private void initControls() {	
		listView = (ListView) rootView.findViewById(R.id.lvWorkers);
	}
	
	public void initListWorkers() {	
		ArrayAdapter<Worker> adapter = new ArrayAdapter<Worker>(StaticResources.getBaseContext(),
				android.R.layout.simple_list_item_1, workers);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				selectedWorker = (Worker) listView.getItemAtPosition(position);
				if (selectedWorker.checkPIN(String.valueOf(Option.Instance().getPin())))
					logIn();
				else if (pinDialog.getFragmentManager() == null)
					showPinDialog();
			}
		});
	}
	

	
	public void reloadData() {
		workers = WorkerManager.Instance().load(null, null, BaseObject.NameField);
		Collections.sort(workers,new BaseObjectComparer());
		Option.Instance().setWorkerActivity(true);
		Option.Instance().save();
	}
	
	private void initDialogs() {
		pinDialog = new PinDialog();
	}
	
	private void showPinDialog() {
		pinDialog.setWorker(selectedWorker);
		pinDialog.show(getFragmentManager(), "dialogPin");
		getFragmentManager().executePendingTransactions();
		pinDialog.getDialog().setTitle(selectedWorker.getName());
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		if (dialog == pinDialog)
			logIn();		
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		return;
	}
	
	public void logIn() {
		DataBaseWrapper.Instance().clearWorkerData();
		saveSelectedWorkerID();
		startSyncActivity();
	}
	
	private void saveSelectedWorkerID() {
		Option.Instance().setPrevWorkerID(Option.Instance().getWorkerID());
		Option.Instance().setWorkerID(selectedWorker.getID());
		Option.Instance().save();
	}
	
	private void startSyncActivity() {
		Intent synchActivity = new Intent(getActivity().getApplicationContext(),
				SynchronizationActivity.class);
		startActivity(synchActivity);
	}

}
