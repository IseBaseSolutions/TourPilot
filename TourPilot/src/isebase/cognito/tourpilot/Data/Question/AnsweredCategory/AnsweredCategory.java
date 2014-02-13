package isebase.cognito.tourpilot.Data.Question.AnsweredCategory;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;

public class AnsweredCategory extends BaseObject {

	public static final String CategoryIDField = "category_id";
	public static final String EmploymentIDField = "employment_id";
	public static final String IsAnsweredField = "is_answered";
	
	int categoryID;
	int employmentID;
	boolean isAnswered;
	
	@MapField(DatabaseField = CategoryIDField)
	public int getCategoryID() {
		return categoryID;
	}

	@MapField(DatabaseField = CategoryIDField)
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	@MapField(DatabaseField = EmploymentIDField)
	public int getEmploymentID() {
		return employmentID;
	}

	@MapField(DatabaseField = EmploymentIDField)
	public void setEmploymentID(int employmentID) {
		this.employmentID = employmentID;
	}

	@MapField(DatabaseField = IsAnsweredField)
	public boolean isAnswered() {
		return isAnswered;
	}

	@MapField(DatabaseField = IsAnsweredField)
	public void setAnswered(boolean isAnswered) {
		this.isAnswered = isAnswered;
	}
	
	
	public AnsweredCategory() {
		clear();
	}

	@Override
	protected void clear() {
		super.clear();
		setCategoryID(BaseObject.EMPTY_ID);
		setEmploymentID(BaseObject.EMPTY_ID);
		setAnswered(false);
	}
	
}
