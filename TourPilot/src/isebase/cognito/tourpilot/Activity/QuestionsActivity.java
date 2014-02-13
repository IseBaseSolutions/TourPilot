package isebase.cognito.tourpilot.Activity;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Question.Answer.Answer;
import isebase.cognito.tourpilot.Data.Question.Answer.AnswerManager;
import isebase.cognito.tourpilot.Data.Question.Category.Category;
import isebase.cognito.tourpilot.Data.Question.Category.CategoryManager;
import isebase.cognito.tourpilot.Data.Question.Interfaces.IQuestionable;
import isebase.cognito.tourpilot.Data.Question.Question.Question;
import isebase.cognito.tourpilot.Data.Question.Question.QuestionManager;
import isebase.cognito.tourpilot.Templates.QuestionAdapter;
import isebase.cognito.tourpilot.Templates.QuestionAdapter.QuestionHolder;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class QuestionsActivity extends Activity {

	ListView lvQuestions;
	ListView lvAnswers;
	List<IQuestionable> questions = new ArrayList<IQuestionable>();
	List<IQuestionable> answers = new ArrayList<IQuestionable>();
	List<Question> subQuestions = new ArrayList<Question>();
	Category category;
	QuestionAdapter questionAdapter;
	QuestionAdapter answerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questions);

		initControls();
		reloadData();
		fillUpTitle();
		fillUp();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.simple_questions, menu);
		return true;
	}
	
	private void initControls() {
		lvQuestions = (ListView) findViewById(R.id.lvQuestions);
		lvAnswers = (ListView) findViewById(R.id.lvAnswers);
	}
	
	private void reloadData() {
		int categoryID = -1;
		Intent intentFlege = getIntent();
		Bundle bundle = intentFlege.getExtras();
		if(bundle != null)
			categoryID = bundle.getInt("categoryID");		
		category = CategoryManager.Instance().load(categoryID);
		answers.addAll(AnswerManager.Instance().loadByCategory(categoryID));
		initQuestions(QuestionManager.Instance().loadActualsByCategory(categoryID));

	}
	
	private void fillUpTitle() {
		setTitle(category.getName());
	}
	
	private void fillUp() {		
		questionAdapter = new QuestionAdapter(this,
				R.layout.row_question, questions);
		lvQuestions.setAdapter(questionAdapter);
		lvQuestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

			}
		});
		answerAdapter = new QuestionAdapter(this,
				R.layout.row_question, answers);
		lvAnswers.setAdapter(answerAdapter);
		lvAnswers.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

			}
		});		
	}
	
	public void onRadioButtonClicked(View view) {
		QuestionHolder questionHolder = (QuestionHolder) ((RadioGroup) view.getParent()).getTag();
		Answer answer;
		int selectedIndex = questionHolder.rgAnswers.indexOfChild((RadioButton) view);
		if (questionHolder.item  instanceof Question)
		{
			questionHolder.tvQuestionName.setVisibility(View.GONE);
			questionHolder.rgAnswers.setVisibility(View.GONE);
			Question question = (Question) questionHolder.item;
			answer = new Answer(questionAdapter.patient, question, selectedIndex, category.getID());
			answers.add(answer);
			questions.remove(question);
			for (Question subQuestionItem : subQuestions)
			{
				if (isOwnerAnswered(subQuestionItem))
					questions.add(subQuestionItem);
			}
			questionAdapter.notifyDataSetChanged();
		}
		else
		{
			answer = (Answer) questionHolder.item;
			answer.setAnswerID(selectedIndex);
		}
		AnswerManager.Instance().save(answer);
	}
	
	public void onTgSwitchClicked(View view) {
		lvQuestions.setVisibility(lvQuestions.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
		lvAnswers.setVisibility(lvQuestions.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
	}
	
	private void initQuestions(List<Question> allQuestions) {
		for (Question question : allQuestions)
		{
			if (question.isSubQuestion() && !isOwnerAnswered(question))
			{
				if (isSubQuestionIsAnswered(question))
					continue;
				else
					subQuestions.add(question);
			}
			else
				questions.add(question);
		}
	}
	
	private boolean isOwnerAnswered(Question question) {
		for (IQuestionable answer : answers)
			for (int i = 0; i < question.getOwnerIDs().length; i++)
				if (Integer.parseInt(question.getOwnerIDs()[i]) == ((Answer)answer).getQuestionID() && question.getKeyAnswer() == ((Answer)answer).getAnswerID())
					return true;
		return false;
	}
	
	private boolean isSubQuestionIsAnswered(Question subQuestion) {		
		return AnswerManager.Instance().isSubQuestionAnswered(subQuestion);
	}

}
