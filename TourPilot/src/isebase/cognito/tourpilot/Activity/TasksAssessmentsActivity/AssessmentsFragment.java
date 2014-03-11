package isebase.cognito.tourpilot.Activity.TasksAssessmentsActivity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.QuestionActivities.BradenSkalaActivity;
import isebase.cognito.tourpilot.Activity.QuestionActivities.FallenFactorSkalaActivity;
import isebase.cognito.tourpilot.Activity.QuestionActivities.NortonSkalaActivity;
import isebase.cognito.tourpilot.Activity.QuestionActivities.PainAnalyseSkalaActivity;
import isebase.cognito.tourpilot.Activity.QuestionActivities.QuestionsActivity;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Question.AnsweredCategory.AnsweredCategory;
import isebase.cognito.tourpilot.Data.Question.AnsweredCategory.AnsweredCategoryManager;
import isebase.cognito.tourpilot.Data.Question.Category.Category;
import isebase.cognito.tourpilot.Data.Question.Category.CategoryManager;
import isebase.cognito.tourpilot.Data.Question.ExtraCategory.ExtraCategory;
import isebase.cognito.tourpilot.Data.Question.ExtraCategory.ExtraCategoryManager;
import isebase.cognito.tourpilot.Data.Question.QuestionSetting.QuestionSetting;
import isebase.cognito.tourpilot.Data.Question.QuestionSetting.QuestionSettingManager;
import isebase.cognito.tourpilot.Dialogs.ExtraCategoriesDialog;
import isebase.cognito.tourpilot.Templates.EmploymentCategoryAdapter;
import isebase.cognito.tourpilot.Utils.DateUtils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class AssessmentsFragment extends Fragment {

	TasksAssessementsActivity activity;
	
	View rootView;
	public QuestionSetting questionSetting;
	ListView lvAssessments;
	int allCategoriesCount;
	List<Category> categories = new ArrayList<Category>();
	public List<AnsweredCategory> answeredCategories = new ArrayList<AnsweredCategory>();
	List<EmploymentCategory> employmentCategories = new ArrayList<EmploymentCategory>();
	EmploymentCategoryAdapter adapter;
	public AssessmentsFragment(TasksAssessementsActivity instance) {
		activity = instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(
				R.layout.activity_assessments, container, false);
		try{
			super.onCreate(savedInstanceState);
			initControls();
			//reloadData();	
			fillUpAssessments();	
		} catch(Exception e){
			e.printStackTrace();
			//criticalClose();
		}	
		return rootView;
	}
	
	@Override
	public void onResume() {
		reloadData();
		adapter.notifyDataSetChanged();
		super.onResume();
	}
	
	private void initControls() {
		lvAssessments = (ListView) rootView.findViewById(R.id.lvAssessments);
	}
	
	public void reloadData() {
		questionSetting = QuestionSettingManager.Instance().loadAll(Option.Instance().getEmploymentID());
		reloadCategories();
	}
	
	public void reloadCategories() {
		if (questionSetting == null)
			return;
		allCategoriesCount = CategoryManager.Instance().load().size();
		categories = CategoryManager.Instance().loadByQuestionSettings(questionSetting);		
		answeredCategories = AnsweredCategoryManager.Instance().LoadByEmploymentID(Option.Instance().getEmploymentID());
		employmentCategories.clear();
		for(Category category : categories)
			employmentCategories.add(new EmploymentCategory(category.getName(), category.getID(), category.getCategoryType(), isAnswered(category)));
		Collections.sort(employmentCategories, new EmploymentCategoryComparer());
	}
	
	private boolean isAnswered(Category category) {
		for(AnsweredCategory answeredCategory : answeredCategories)
			if (answeredCategory.getCategoryID() == category.getID())
				return true;
		return false;
	}
	
	private void fillUpAssessments() {
		adapter = new EmploymentCategoryAdapter(activity, 
				R.layout.row_employment_category_template, employmentCategories);
		lvAssessments.setAdapter(adapter);
		lvAssessments.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				
				if (activity.tasksFragment.getStartTask().getRealDate().getTime() == DateUtils.EmptyDate.getTime())
				{
					if (activity.tasksFragment.startEmploymentDialog.getFragmentManager() == null)
						activity.tasksFragment.startEmploymentDialog.show(getFragmentManager(), "");
					return;
				}
				EmploymentCategory employmentCategory = (EmploymentCategory) lvAssessments.getItemAtPosition(position);
				switch(employmentCategory.type)
				{
					case normal:
						startQuestionActivity(employmentCategory.categoryID);
						break;
					case braden:
						startBradenSkalaActivity();
						break;
					case norton:
						startNortonSkalaActivity();
						break;
					case schmerzermittlung:
						startPainAnalyseSkalaActivity();
						break;
					case sturzrisiko:
						startFallenFactorSkalaActivity();
						break;
				}
				
			}
		});
	}
	
	public void showExtraAssessmentsDialog() {
		List<Category> ExtraCategories = CategoryManager.Instance().loadExtraCategoriesByQuestionSettings(questionSetting);
		ExtraCategoriesDialog extraCategoriesDialog = new ExtraCategoriesDialog(ExtraCategories, getString(R.string.extra_assessments));
		extraCategoriesDialog.show(getFragmentManager(), "extraAssessmentsDialog");
	}
	
	public void saveExtraAssessments(DialogFragment dialog) {
		questionSetting.setExtraCategoryIDsString(questionSetting.getExtraCategoryIDsString() + (questionSetting.getExtraCategoryIDsString().equals("") ? "" : ",") +((ExtraCategoriesDialog)dialog).getSelectedCategoriesID());
		ExtraCategory extraCategory = ExtraCategoryManager.Instance().load(Option.Instance().getEmploymentID());
		if (extraCategory == null)
			extraCategory = new ExtraCategory(Option.Instance().getEmploymentID()); 
		extraCategory.setExtraCategoryIDsString(questionSetting.getExtraCategoryIDsString());
		ExtraCategoryManager.Instance().save(extraCategory);
		reloadCategories();
		adapter.notifyDataSetChanged();
	}
	
	private void startQuestionActivity(int categoryID) {
		Intent questionsActivity = new Intent(activity.getApplicationContext(), QuestionsActivity.class);
		questionsActivity.putExtra("categoryID", categoryID);
		startActivity(questionsActivity);
	}
	
	private void startBradenSkalaActivity() {
		Intent bradenSkalaActivity = new Intent(activity.getApplicationContext(), BradenSkalaActivity.class);
		startActivity(bradenSkalaActivity);
	}
	
	private void startPainAnalyseSkalaActivity() {
		Intent painAnalyseSkalaActivity = new Intent(activity.getApplicationContext(), PainAnalyseSkalaActivity.class);
		startActivity(painAnalyseSkalaActivity);
	}
	
	private void startFallenFactorSkalaActivity() {
		Intent fallenFactorSkalaActivity = new Intent(activity.getApplicationContext(), FallenFactorSkalaActivity.class);
		startActivity(fallenFactorSkalaActivity);
	}
	
	private void startNortonSkalaActivity() {
		Intent nortonSkalaActivity = new Intent(activity.getApplicationContext(), NortonSkalaActivity.class);
		startActivity(nortonSkalaActivity);
	}
	
	public class EmploymentCategory {
		public String name;
		public int categoryID;
		public boolean isAnswered;
		public Category.type type;
		
		public EmploymentCategory(String name, int categoryID, Category.type type, boolean isAnswered) {
			this.name = name;
			this.categoryID = categoryID;
			this.type = type;
			this.isAnswered = isAnswered;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	public class EmploymentCategoryComparer implements Comparator<EmploymentCategory> {

		@Override
		public int compare(EmploymentCategory lhs, EmploymentCategory rhs) {
			
			Collator deCollator = Collator.getInstance(Locale.GERMANY);
			// TODO Auto-generated method stub
			if (lhs.isAnswered == rhs.isAnswered)
				return deCollator.compare(lhs.name, rhs.name);
			return lhs.isAnswered ? 1 : -1;
		}
		
	}

}

