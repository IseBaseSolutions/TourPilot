package isebase.cognito.tourpilot.Activity.TasksAssessmentsActivity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.AdditionalEmploymentsActivity;
import isebase.cognito.tourpilot.Activity.QuestionsActivity;
import isebase.cognito.tourpilot.Data.Question.Category.Category;
import isebase.cognito.tourpilot.Data.Question.Category.Category.type;
import isebase.cognito.tourpilot.Data.Question.Category.CategoryManager;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AssessmentsFragment extends Fragment {

	TasksAssessementsActivity activity;
	
	View rootView;
	
	ListView lvAssessments;
	List<Category> categories = new ArrayList<Category>();	
	
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
			reloadData();	
			fillUpAssessments();	
		} catch(Exception e){
			e.printStackTrace();
			//criticalClose();
		}	
		return rootView;
	}
	
	private void initControls() {
		lvAssessments = (ListView) rootView.findViewById(R.id.lvAssessments);
	}
	
	private void reloadData() {
		categories = CategoryManager.Instance().load();
		List<Category> filteredCategories = new ArrayList<Category>();
		for(Category category : categories)
			if (category.getCategoryType() == type.normal)
				filteredCategories.add(category);
		categories = filteredCategories;
	}
	
	private void fillUpAssessments() {
		ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(activity,
				android.R.layout.simple_list_item_1, categories);
		lvAssessments.setAdapter(adapter);
		lvAssessments.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				Category category = (Category) lvAssessments.getItemAtPosition(position);
				startQuestionActivity(category.getID());
			}
		});
	}
	
	private void startQuestionActivity(int categoryID) {
		Intent questionsActivity = new Intent(activity.getApplicationContext(), QuestionsActivity.class);
		questionsActivity.putExtra("categoryID", categoryID);
		startActivity(questionsActivity);
	}

}

