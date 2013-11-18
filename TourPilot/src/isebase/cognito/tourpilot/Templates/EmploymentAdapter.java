package isebase.cognito.tourpilot.Templates;

import isebase.cognito.tourpilot.R;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.StaticResources.StaticResources;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EmploymentAdapter extends ArrayAdapter<Employment>{
		
		private List<Employment> employments;
		private int layoutResourceId;
		private Context context;

		public EmploymentAdapter(Context context, int layoutResourceId, List<Employment> employments) {
			super(context, layoutResourceId, employments);
			this.layoutResourceId = layoutResourceId;
			this.context = context;
			this.employments = employments;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			EmploymentHolder employmentHolder = new EmploymentHolder();
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			Employment employment = employments.get(position);
			
			employmentHolder.tvEmploymentName = (TextView) row.findViewById(R.id.tvEmploymentName);
			employmentHolder.tvEmploymentName.setText(employment.getName());
			
			employmentHolder.tvEmploymentTime = (TextView) row.findViewById(R.id.tvEmploymentTime);
			employmentHolder.tvEmploymentTime.setText(employment.getTime());
			
			if(employment.isDone()){
				employmentHolder.tvEmploymentName.setTextColor(
						StaticResources.getBaseContext().getResources().getColor(R.color.activeColor));
			}
			if(employment.isAborted()){
				employmentHolder.tvEmploymentName.setTextColor(
						StaticResources.getBaseContext().getResources().getColor(R.color.notActveColor));
			}
		
			row.setTag(employmentHolder);
			return row;
		}

		class EmploymentHolder {
			TextView tvEmploymentName;
			TextView tvEmploymentTime;
		}
}
