package isebase.cognito.tourpilot.Data.Question.AnsweredCategory;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;

public class AnsweredCategory extends BaseObject {

	public static final String CategoryIDField = "category_id";
	public static final String EmploymentIDField = "employment_id";
	
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
	
	public AnsweredCategory() {
		clear();
	}
	
	public AnsweredCategory(int categoryID, int employmentID) {
		clear();
		setCategoryID(categoryID);
		setEmploymentID(employmentID);
	}

	@Override
	protected void clear() {
		super.clear();
		setCategoryID(BaseObject.EMPTY_ID);
		setEmploymentID(BaseObject.EMPTY_ID);
	}
	
}
