package isebase.cognito.tourpilot.Activity.QuestionActivities;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Activity.BaseActivities.BaseActivity;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Question.Answer.Answer;
import isebase.cognito.tourpilot.Data.Question.Answer.AnswerManager;
import isebase.cognito.tourpilot.Data.Question.AnsweredCategory.AnsweredCategory;
import isebase.cognito.tourpilot.Data.Question.AnsweredCategory.AnsweredCategoryManager;
import isebase.cognito.tourpilot.Data.Question.Category.Category;
import isebase.cognito.tourpilot.Data.Question.Category.CategoryManager;
import isebase.cognito.tourpilot.DataBase.HelperFactory;
import isebase.cognito.tourpilot.Dialogs.BaseDialogListener;
import isebase.cognito.tourpilot.Dialogs.BradenDialog;
import isebase.cognito.tourpilot.NewData.NewEmployment.NewEmployment;
import isebase.cognito.tourpilot.Templates.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class BradenSkalaActivity extends BaseActivity implements BaseDialogListener  {

	List<String> questionsName = new ArrayList<String>();
	Map<String, List<String>> answersNameMap = new LinkedHashMap<String, List<String>>();
	List<Answer> answers;
	List<String> answersName = new ArrayList<String>();
	Map<String, List<String>> subAnswersNameMap = new LinkedHashMap<String, List<String>>();
	ExpandableListView expListView;
    BradenDialog bradenDialog;
    int questionNumber;
    int answerNumber;
    Employment employment;
    Category category;
    ExpandableListAdapter expListAdapter;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_braden_skala);
        setTitle();
        reloadData();
        setQuestions();
        setAnswers();
        setSubAnswers();
        fillUpList();
    }
    
    private void setTitle() {
    	setTitle(R.string.braden);
    }
    
    private void fillUpList() {
        expListView = (ExpandableListView) findViewById(R.id.elvBraden);
        expListAdapter = new ExpandableListAdapter(
                this, questionsName, answersNameMap, answers);
        expListView.setAdapter(expListAdapter);
 
        expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				if (bradenDialog != null && bradenDialog.getFragmentManager() != null)
					return false;
				createBradenDialog(groupPosition, childPosition);
                return true;
			}
        });
    }
    
    private void createBradenDialog(int groupPosition, int childPosition) {
		bradenDialog = new BradenDialog(answersNameMap.get(questionsName.get(groupPosition)).get(childPosition), 
				subAnswersNameMap, AnswerManager.Instance().loadByQuestionIDAndType(groupPosition, 
						Category.type.braden.ordinal()), childPosition);
		bradenDialog.setCancelable(false);
		bradenDialog.show(getSupportFragmentManager(), "bradenDialog");
		questionNumber = groupPosition;
		answerNumber = childPosition;
    }
    
    public void setQuestions() {
        questionsName.add(getString(R.string.braden_question_0));
        questionsName.add(getString(R.string.braden_question_1));
        questionsName.add(getString(R.string.braden_question_2));
        questionsName.add(getString(R.string.braden_question_3));
        questionsName.add(getString(R.string.braden_question_4));
        questionsName.add(getString(R.string.braden_question_5));
    }
    
    public void setAnswers() {
    	List<String> list;
    	for (String question : questionsName) {
    		list = new ArrayList<String>();
    		if (question.equals(getString(R.string.braden_question_0)))	{    			
    			list.add(getString(R.string.braden_answer_0_0));
    			list.add(getString(R.string.braden_answer_0_1));
    			list.add(getString(R.string.braden_answer_0_2));
    			list.add(getString(R.string.braden_answer_0_3));
    			answersNameMap.put(question, list);
    		}
    		else if (question.equals(getString(R.string.braden_question_1))) {
    			list.add(getString(R.string.braden_answer_1_0));
    			list.add(getString(R.string.braden_answer_1_1));
    			list.add(getString(R.string.braden_answer_1_2));
    			list.add(getString(R.string.braden_answer_1_3));
    			answersNameMap.put(question, list);
			}
    		else if (question.equals(getString(R.string.braden_question_2))) {
    			list.add(getString(R.string.braden_answer_2_0));
    			list.add(getString(R.string.braden_answer_2_1));
    			list.add(getString(R.string.braden_answer_2_2));
    			list.add(getString(R.string.braden_answer_2_3));
    			answersNameMap.put(question, list);
			}
    		else if (question.equals(getString(R.string.braden_question_3))) {
    			list.add(getString(R.string.braden_answer_3_0));
    			list.add(getString(R.string.braden_answer_3_1));
    			list.add(getString(R.string.braden_answer_3_2));
    			list.add(getString(R.string.braden_answer_3_3));
    			answersNameMap.put(question, list);
			}
    		else if (question.equals(getString(R.string.braden_question_4))) {
    			list.add(getString(R.string.braden_answer_4_0));
    			list.add(getString(R.string.braden_answer_4_1));
    			list.add(getString(R.string.braden_answer_4_2));
    			list.add(getString(R.string.braden_answer_4_3));
    			answersNameMap.put(question, list);
			}
    		else {
    			list.add(getString(R.string.braden_answer_5_0));
    			list.add(getString(R.string.braden_answer_5_1));
    			list.add(getString(R.string.braden_answer_5_2));
    			answersNameMap.put(question, list);
    		}    		
    	}
    	answersName.add(getString(R.string.braden_answer_0_0));
		answersName.add(getString(R.string.braden_answer_0_1));
		answersName.add(getString(R.string.braden_answer_0_2));
		answersName.add(getString(R.string.braden_answer_0_3));
		answersName.add(getString(R.string.braden_answer_1_0));
		answersName.add(getString(R.string.braden_answer_1_1));
		answersName.add(getString(R.string.braden_answer_1_2));
		answersName.add(getString(R.string.braden_answer_1_3));
		answersName.add(getString(R.string.braden_answer_2_0));
		answersName.add(getString(R.string.braden_answer_2_1));
		answersName.add(getString(R.string.braden_answer_2_2));
		answersName.add(getString(R.string.braden_answer_2_3));
		answersName.add(getString(R.string.braden_answer_3_0));
		answersName.add(getString(R.string.braden_answer_3_1));
		answersName.add(getString(R.string.braden_answer_3_2));
		answersName.add(getString(R.string.braden_answer_3_3));
		answersName.add(getString(R.string.braden_answer_4_0));
		answersName.add(getString(R.string.braden_answer_4_1));
		answersName.add(getString(R.string.braden_answer_4_2));
		answersName.add(getString(R.string.braden_answer_4_3));
		answersName.add(getString(R.string.braden_answer_5_0));
		answersName.add(getString(R.string.braden_answer_5_1));
		answersName.add(getString(R.string.braden_answer_5_2));
    }
    
    private void setSubAnswers() {	
    	List<String> list;
    	for (String answer : answersName) {
    		list = new ArrayList<String>();
            if (answer.equals(getString(R.string.braden_answer_0_0))) {
            	list.add(getString(R.string.braden_subanswer_0_0_0));
            	list.add(getString(R.string.braden_subanswer_0_0_1));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_0_1))) {
            	list.add(getString(R.string.braden_subanswer_0_1_0));
            	list.add(getString(R.string.braden_subanswer_0_1_1));
            	list.add(getString(R.string.braden_subanswer_0_1_2));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_0_2))) {
            	list.add(getString(R.string.braden_subanswer_0_2_0));
            	list.add(getString(R.string.braden_subanswer_0_2_1));
            	list.add(getString(R.string.braden_subanswer_0_2_2));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_0_3))) {
            	list.add(getString(R.string.braden_subanswer_0_3_0));
            	list.add(getString(R.string.braden_subanswer_0_3_1));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_1_0))) {
            	list.add(getString(R.string.braden_subanswer_1_0_0));
            	list.add(getString(R.string.braden_subanswer_1_0_1));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_1_1))) {
            	list.add(getString(R.string.braden_subanswer_1_1_0));
            	list.add(getString(R.string.braden_subanswer_1_1_1));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_1_2))) {
            	list.add(getString(R.string.braden_subanswer_1_2_0));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_1_3))) {
            	list.add(getString(R.string.braden_subanswer_1_3_0));
            	list.add(getString(R.string.braden_subanswer_1_3_1));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_2_0))) {
            	list.add(getString(R.string.braden_subanswer_2_0_0));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_2_1))) {
            	list.add(getString(R.string.braden_subanswer_2_1_0));
            	list.add(getString(R.string.braden_subanswer_2_1_1));
            	list.add(getString(R.string.braden_subanswer_2_1_2));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_2_2))) {
            	list.add(getString(R.string.braden_subanswer_2_2_0));
            	list.add(getString(R.string.braden_subanswer_2_2_1));
            	list.add(getString(R.string.braden_subanswer_2_2_2));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_2_3))) {
            	list.add(getString(R.string.braden_subanswer_2_3_0));
            	list.add(getString(R.string.braden_subanswer_2_3_1));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_3_0))) {
            	list.add(getString(R.string.braden_subanswer_3_0_0));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_3_1))) {
            	list.add(getString(R.string.braden_subanswer_3_1_0));
            	list.add(getString(R.string.braden_subanswer_3_1_1));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_3_2))) {
            	list.add(getString(R.string.braden_subanswer_3_2_0));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_3_3))) {
            	list.add(getString(R.string.braden_subanswer_3_3_0));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_4_0))) {
            	list.add(getString(R.string.braden_subanswer_4_0_0));
            	list.add(getString(R.string.braden_subanswer_4_0_1));
            	list.add(getString(R.string.braden_subanswer_4_0_2));
            	list.add(getString(R.string.braden_subanswer_4_0_3));
            	list.add(getString(R.string.braden_subanswer_4_0_4));
            	list.add(getString(R.string.braden_subanswer_4_0_5));
            	list.add(getString(R.string.braden_subanswer_4_0_6));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_4_1))) {
             	list.add(getString(R.string.braden_subanswer_4_1_0));
            	list.add(getString(R.string.braden_subanswer_4_1_1));
            	list.add(getString(R.string.braden_subanswer_4_1_2));
            	list.add(getString(R.string.braden_subanswer_4_1_3));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_4_2))) {
             	list.add(getString(R.string.braden_subanswer_4_2_0));
            	list.add(getString(R.string.braden_subanswer_4_2_1));
            	list.add(getString(R.string.braden_subanswer_4_2_2));
            	list.add(getString(R.string.braden_subanswer_4_2_3));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_4_3))) {
             	list.add(getString(R.string.braden_subanswer_4_3_0));
            	list.add(getString(R.string.braden_subanswer_4_3_1));
            	list.add(getString(R.string.braden_subanswer_4_3_2));
            	list.add(getString(R.string.braden_subanswer_4_3_3));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_5_0))) {
             	list.add(getString(R.string.braden_subanswer_5_0_0));
            	list.add(getString(R.string.braden_subanswer_5_0_1));
            	list.add(getString(R.string.braden_subanswer_5_0_2));
            	list.add(getString(R.string.braden_subanswer_5_0_3));
            	list.add(getString(R.string.braden_subanswer_5_0_4));
            	subAnswersNameMap.put(answer, list);
            }
            else if (answer.equals(getString(R.string.braden_answer_5_1))) {
             	list.add(getString(R.string.braden_subanswer_5_1_0));
            	list.add(getString(R.string.braden_subanswer_5_1_1));
            	list.add(getString(R.string.braden_subanswer_5_1_2));
            	list.add(getString(R.string.braden_subanswer_5_1_3));
            	subAnswersNameMap.put(answer, list);
            }
            else {
             	list.add(getString(R.string.braden_subanswer_5_2_0));
            	list.add(getString(R.string.braden_subanswer_5_2_1));
            	list.add(getString(R.string.braden_subanswer_5_2_2));
            	subAnswersNameMap.put(answer, list);
            }
    	}
    }
 
    private void setGroupIndicatorToRight() {
        /* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
 
        expListView.setIndicatorBounds(width - getDipsFromPixel(35), width
                - getDipsFromPixel(5));
    }
    
    private void reloadData() {
   		employment = EmploymentManager.Instance().loadAll(Option.Instance().getEmploymentID());
    	category = CategoryManager.Instance().loadByCategoryName(getString(R.string.braden));
    	answers = AnswerManager.Instance().loadByCategoryID(category.getID());

    }
 
    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		if (dialog.getTag() == "bradenDialog" && ((BradenDialog)dialog).getCurrentLayout() != null)	{
			String answerKey = getAnswerKey(getSelectedCheckBoxes(((BradenDialog)dialog).getCurrentLayout()), 
					getCurrentLevel(((BradenDialog)dialog).getCurrentLayout()));
			saveAnswer(AnswerManager.Instance().loadByQuestionIDAndType(questionNumber, Category.type.braden.ordinal()), answerKey);
			updateList();
			lastQuestionCheck();
		}
		
	}
	
	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub		
	}
	
	private String getSelectedCheckBoxes(LinearLayout linearLayout) {
		int checkBoxIndex = 0;
		String selectedCheckBoxes = "";
		for (int i = 0; i < linearLayout.getChildCount(); i++)
			if (linearLayout.getChildAt(i) instanceof CheckBox){
				if (((CheckBox)linearLayout.getChildAt(i)).isChecked())
					selectedCheckBoxes += checkBoxIndex + "/";
				checkBoxIndex++;
			}
		return selectedCheckBoxes;
	}
	
	private int getCurrentLevel(LinearLayout linearLayout) {
		for (int i = 0; i < ((RelativeLayout)(linearLayout).getParent()).getChildCount(); i++)
			if (((RelativeLayout)(linearLayout).getParent()).getChildAt(i).equals(linearLayout))
				return i;
		return -1;
	}
	
	private String getAnswerKey(String selectedCheckBoxes, int level) {
		return String.format("%d#%s%%%d", answerNumber, selectedCheckBoxes, level);
	}
	
	private void saveAnswer(Answer answer, String answerKey) {
		if (answer == null)
			answer = new Answer(employment.getPatient(), questionNumber, 
					questionsName.get(questionNumber), answerNumber, category.getID(), answerKey, Category.type.braden.ordinal());
		else
			answer.setAnswerKey(answerKey);
		AnswerManager.Instance().save(answer);
	}
	
	private void updateList() {
		expListAdapter.answers = answers = AnswerManager.Instance().loadByCategoryID(category.getID());
		expListAdapter.notifyDataSetChanged();
	}
	
	private void lastQuestionCheck() {
		if (AnsweredCategoryManager.Instance().loadByCategoryID(category.getID()) == null && answers.size() == 6)
		{
			AnsweredCategoryManager.Instance().save(new AnsweredCategory(category.getID(), employment.getID()));
			onBackPressed();
		}
	}


}
