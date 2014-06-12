package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.BaseActivities.BaseActivity;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTour;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTourComparer;
import isebase.cognito.tourpilot.DataBase.HelperFactory;
import isebase.cognito.tourpilot.Dialogs.BaseDialog;
import isebase.cognito.tourpilot.Dialogs.BaseDialogListener;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import isebase.cognito.tourpilot.Templates.PilotToursAdapter;
import isebase.cognito.tourpilot.Utils.DataBaseUtils;

import java.util.Collections;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ToursActivity extends BaseActivity implements BaseDialogListener{
	
	private List<PilotTour> pilotTours;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_tours);
			saveTourActivity(true);
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
					Toast.makeText(StaticResources.getBaseContext()
							, StaticResources.getBaseContext().getString(R.string.db_backup_created)
							, Toast.LENGTH_SHORT).show();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
				return true;
			case R.id.action_clear_darabase:
				clearDatabase();
				return true;
			case R.id.action_close_program:
				close();
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
				saveTourActivity(false);
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
		pilotTours = HelperFactory.getHelper().getPilotTourDAO().loadPilotTours();
		Collections.sort(pilotTours, new PilotTourComparer());
	}
	
	private void saveSelectedTour(PilotTour pilotTour) {
		Option.Instance().setPilotTourID(pilotTour.getId());
		Option.Instance().save();
	}

	private void clearPersonalOptions() {
		Option.Instance().setWorkerID(BaseObject.EMPTY_ID);
		Option.Instance().setPilotTourID(BaseObject.EMPTY_ID);
		Option.Instance().save();
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		if(dialog.getTag().equals("dialogBack")) {
			saveTourActivity(false);
			logOut();
		}
		else if (dialog.getTag().equals("clearDatabase")) {
			clearDB();
			saveTourActivity(false);
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
	
	private void clearDB() {
//		pbClearDB.setVisibility(View.VISIBLE);
//		syncButton.setEnabled(false);
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try{
					HelperFactory.getHelper().clearWorkerData();
				}
				catch(Exception e){
					e.printStackTrace();
				}
				return null;
			}
							
			@Override
			protected void onPostExecute(Void result) {
//				pbClearDB.setVisibility(View.INVISIBLE);
//				syncButton.setEnabled(true);
			}
		}.execute();
		startWorkersActivity();
	}
	
	private void saveTourActivity(boolean state) {
		Option.Instance().setTourActivity(state);
		Option.Instance().save();
	}
	
}
