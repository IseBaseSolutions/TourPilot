package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTour;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTourComparer;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTourManager;
import isebase.cognito.tourpilot.DataBase.DataBaseWrapper;
import isebase.cognito.tourpilot.Dialogs.BaseDialog;
import isebase.cognito.tourpilot.Dialogs.BaseDialogListener;
import isebase.cognito.tourpilot.Templates.PilotToursAdapter;
import isebase.cognito.tourpilot.Utils.DataBaseUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ToursActivity extends BaseActivity implements BaseDialogListener{

	private List<PilotTour> pilotTours = new ArrayList<PilotTour>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_tours);
			reloadData();		
			fillUpTitle();
			fillUp();			
		} catch(Exception e) {
			e.printStackTrace();
			criticalClose();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.tour_info, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.action_db_backup:
				try {
					DataBaseUtils.backup();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
				return true;
			case R.id.action_clear_darabase:
				clearDatabase();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		showDialogLogout();
	}
	
	public void fillUp() {
		ListView listView = (ListView) findViewById(R.id.lvTours);
		PilotToursAdapter adapter = new PilotToursAdapter(this,
				R.layout.row_tour_template, pilotTours);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				saveSelectedTour(pilotTours.get(position));
				startPatientsActivity();
			}
		});
	}

	public void btlogOutClick(View view) {
		showDialogLogout();
	}

	public void btStartSyncClick(View view) {
		startSyncActivity();
	}

	private void showDialogLogout(){
		BaseDialog dialog = new BaseDialog(
				getString(R.string.attention)
				,getString(R.string.dialog_proof_logout));
		dialog.show(getSupportFragmentManager(), "dialogBack");
		getSupportFragmentManager().executePendingTransactions();
	}
	
	private void logOut() {
		clearPersonalOptions();
		startWorkersActivity();
	}

	private void fillUpTitle() {
		setTitle(Option.Instance().getWorker().getName());
	}

	private void reloadData(){
		pilotTours = PilotTourManager.Instance().loadPilotTours();
		Collections.sort(pilotTours, new PilotTourComparer());
	}
	
	private void saveSelectedTour(PilotTour pilotTour) {
		Option.Instance().setPilotTourID(pilotTour.getID());
		Option.Instance().save();
	}

	private void clearPersonalOptions() {
		Option.Instance().setWorkerID(BaseObject.EMPTY_ID);
		Option.Instance().setPilotTourID(BaseObject.EMPTY_ID);
		Option.Instance().save();
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		if(dialog.getTag().equals("dialogBack"))
			logOut();
		else if (dialog.getTag().equals("clearDatabase") && DataBaseWrapper.Instance().clearWorkerData()) {
			reloadData();
			fillUp();
		}
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		
	}

	private void clearDatabase(){
		BaseDialog dialog = new BaseDialog(getString(R.string.attention), 
				getString(R.string.dialog_clear_database));
		dialog.show(getSupportFragmentManager(), "clearDatabase");
		getSupportFragmentManager().executePendingTransactions();
	}
}
