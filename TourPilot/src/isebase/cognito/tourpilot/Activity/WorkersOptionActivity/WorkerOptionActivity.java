package isebase.cognito.tourpilot.Activity.WorkersOptionActivity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.SynchronizationActivity;
import isebase.cognito.tourpilot.Activity.BaseActivities.BaseActivity;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;
import isebase.cognito.tourpilot.Dialogs.BaseDialogListener;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class WorkerOptionActivity extends BaseActivity implements BaseDialogListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	
	OptionsFragment optionsFragment;
	WorkersFragment workersFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_worker_option);
		StaticResources.setBaseContext(getBaseContext());

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo("org.microemu.android", PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        if (app_installed){
        	Uri uri = Uri.fromParts("package", "org.microemu.android", null);
        	Intent it = new Intent(Intent.ACTION_DELETE, uri);
        	it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        	StaticResources.getBaseContext().startActivity(it);
        }
		switchToLastActivity();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_show_program_info:
			optionsFragment.versionFragmentDialog.show(getSupportFragmentManager(), "dialogVersion");
			return true;
		case R.id.action_clear_database:
			optionsFragment.busy(optionsFragment.getClearMode());
			return true;
		case R.id.action_db_backup:		
			optionsFragment.busy(optionsFragment.getBackupMode());
			return true;
		case R.id.action_db_restore:		
			optionsFragment.chooseFile();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onBackPressed() {

	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			if(position == 0)
				return optionsFragment = new OptionsFragment();
			else
				return workersFragment = new WorkersFragment();
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0:
					return "Optionen";
				case 1:
					return "Mitarbeiter";
			}
			return null;
		}
	}
	
	public void onLockOptions(View view) {
		optionsFragment.onLockOptionsChecked();
	}
	
	public void btSyncClick(View view) {
		saveSettings();
		startSyncActivity();
	}
	
	public void saveSettings() {
		Option.Instance().setServerIP(optionsFragment.etServerIP.getText().toString());
		Option.Instance().setServerPort(Integer.parseInt(optionsFragment.etServerPort.getText().toString()));
		Option.Instance().setPrevWorkerID(BaseObject.EMPTY_ID);
		Option.Instance().setWorkerID(BaseObject.EMPTY_ID);
	}
	
	private void switchToLastActivity() {
		if (Option.Instance().getWorkID() != -1)
			startAdditionalWorksActivity();
		else if (Option.Instance().getEmploymentID() != -1)
			startTasksActivity();
		else if (Option.Instance().getPilotTourID() != -1)
			startPatientsActivity();
		else if (WorkerManager.Instance().load(null, null, BaseObject.NameField).size() > 0)
			mViewPager.setCurrentItem(1);
		else 
			mViewPager.setCurrentItem(0);
		return;
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		if (dialog.getTag().equals("dialogPin"))
		{
			workersFragment.logIn();
		}
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		return;		
	}
	
	
	
}
