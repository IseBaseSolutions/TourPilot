package isebase.cognito.tourpilot.Activity.TasksAssessmentsActivity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.BaseActivities.BaseActivity;
import isebase.cognito.tourpilot.Activity.WorkersOptionActivity.OptionsFragment;
import isebase.cognito.tourpilot.Activity.WorkersOptionActivity.WorkersFragment;
import isebase.cognito.tourpilot.Activity.WorkersOptionActivity.WorkerOptionActivity.SectionsPagerAdapter;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Question.QuestionSetting.QuestionSettingManager;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;
import isebase.cognito.tourpilot.Dialogs.BaseDialog;
import isebase.cognito.tourpilot.Dialogs.BaseDialogListener;
import isebase.cognito.tourpilot.R.layout;
import isebase.cognito.tourpilot.R.menu;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

public class TasksAssessementsActivity extends BaseActivity implements BaseDialogListener{

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
	
	TasksFragment tasksFragment;
	AssessmentsFragment assessmentsFragment;
	
	TasksAssessementsActivity instance;
	
	boolean hasQuestions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_worker_option);
		StaticResources.setBaseContext(getBaseContext());
		instance = this;
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		reloadData();
		//switchToLastActivity();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
//		case R.id.action_show_program_info:
//			optionsFragment.versionFragmentDialog.show(getSupportFragmentManager(), "dialogVersion");
//			return true;
//		case R.id.action_clear_database:
//			optionsFragment.busy(optionsFragment.getClearMode());
//			return true;
//		case R.id.action_db_backup:		
//			optionsFragment.busy(optionsFragment.getBackupMode());
//			return true;
//		case R.id.action_db_restore:		
//			optionsFragment.chooseFile();
//			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onBackPressed() {
		if(!tasksFragment.employment.isDone() && tasksFragment.isClickable()) {
			BaseDialog dialog = new BaseDialog(
					getString(R.string.attention),
					getString(R.string.dialog_task_proof_back));
			dialog.show(getSupportFragmentManager(), "dialogBack");
			getSupportFragmentManager().executePendingTransactions();
		}
		else
		{
			tasksFragment.clearEmployment();
			startPatientsActivity();
		}
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
				return tasksFragment = new TasksFragment(instance);
			else
				return assessmentsFragment = new AssessmentsFragment(instance);
		}

		@Override
		public int getCount() {
			if (hasQuestions)
				return 2;
			return 1;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0:
					return "Tasks";
				case 1:
					return "Assessments";
			}
			return null;
		}
	}
	
	public void btSyncClick(View view) {
		//saveSettings();
		startSyncActivity();
	}
	
	
	private void switchToLastActivity() {
		
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		if (dialog.getTag().equals("dialogPin"))
		{
			//assessmentsFragment.logIn();
		}
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		return;		
	}
	
	private void reloadData() {
		hasQuestions = (QuestionSettingManager.Instance().load(Option.Instance().getEmploymentID()) != null);
	}

}
