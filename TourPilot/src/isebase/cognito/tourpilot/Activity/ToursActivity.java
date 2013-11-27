package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Information.Information;
import isebase.cognito.tourpilot.Data.Information.InformationManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTourComparer;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTourManager;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTour;
import isebase.cognito.tourpilot.Dialogs.BaseDialog;
import isebase.cognito.tourpilot.Dialogs.BaseDialogListener;
import isebase.cognito.tourpilot.Dialogs.InfoBaseDialog;
import isebase.cognito.tourpilot.Templates.PilotToursAdapter;
import isebase.cognito.tourpilot.Utils.DataBaseUtils;
import isebase.cognito.tourpilot.Utils.DateUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
	private List<Information> infos = new ArrayList<Information>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_tours);
			reloadData();		
			fillUpTitle();
			fillUp();
			loadTourInfos(false);
		}catch(Exception ex){
			ex.printStackTrace();
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
			case R.id.tour_info:
				loadTourInfos(true);
				return true;
			case R.id.action_db_backup:
				try{
					DataBaseUtils.backup();
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		showDialogLogout();
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu){
		if(infos.size() == 0){
			MenuItem item = menu.findItem(R.id.tour_info);
			item.setEnabled(false);
		}
		return true;
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
		logOut();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
	}

	private void loadTourInfos(boolean is_from_menu){
		infos = InformationManager.Instance().load(Information.EmploymentCodeField, BaseObject.EMPTY_ID);
		String strInfos = "";
		Date today = new Date();
		for(Information info : infos){
			if(!DateUtils.isToday(info.getReadTime()) || is_from_menu)
				if((today.getTime() >= info.getFromDate().getTime()) && (today.getTime() <= info.getTillDate().getTime())){
					if(strInfos != "" )
						strInfos += "\n";
					strInfos += info.getName();
					info.setReadTime(today);
				}
		}
		if(strInfos.length() > 0){
			InformationManager.Instance().save(infos);
			InfoBaseDialog dialog = new InfoBaseDialog(getString(R.string.menu_info),strInfos);
			dialog.show(getSupportFragmentManager(), "");
		}
	}
}
