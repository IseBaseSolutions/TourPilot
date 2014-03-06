package isebase.cognito.tourpilot.Templates;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Question.Answer.Answer;
import isebase.cognito.tourpilot.Data.Question.Interfaces.IQuestionable;
import isebase.cognito.tourpilot.Data.Question.Question.Question;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuestionAdapter extends ArrayAdapter<IQuestionable> {
		
		private List<IQuestionable> items;
		private int layoutResourceId;
		private Context context;
		public Patient patient;
		private Employment employment;

		public QuestionAdapter(Context context, int layoutResourceId, List<IQuestionable> items) {
			super(context, layoutResourceId, items);
			this.layoutResourceId = layoutResourceId;
			this.context = context;
			this.items = items;
			employment = EmploymentManager.Instance().loadAll(Option.Instance().getEmploymentID());
			patient = employment.getPatient();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			QuestionHolder questionHolder = new QuestionHolder();
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			
			questionHolder.tvQuestionName = (TextView) row.findViewById(R.id.tvQuestion);
			questionHolder.tvQuestionName.setText(items.get(position) instanceof Question 
					? ((Question)items.get(position)).getNameWithKeyWords(patient) 
							: items.get(position).name());						
			questionHolder.rgAnswers = (RadioGroup) row.findViewById(R.id.rg_1);
			if (employment.isDone())
				disableRadioButtons(questionHolder.rgAnswers);
			if (items.get(position) instanceof Answer)
			{
				Answer answer = (Answer) items.get(position);
				((RadioButton) questionHolder.rgAnswers.getChildAt(answer.getAnswerID())).setChecked(true);
			}			
			questionHolder.item = items.get(position);
			
			questionHolder.rgAnswers.setTag(questionHolder);
			return row;
		}
		
		private void disableRadioButtons(RadioGroup radioGroup) {
			for (int i = 0; i < radioGroup.getChildCount(); i++)
				((RadioButton)radioGroup.getChildAt(i)).setEnabled(false);
		}

		public class QuestionHolder {
			public TextView tvQuestionName;
			public RadioGroup rgAnswers;
			public IQuestionable item;
		}
}

