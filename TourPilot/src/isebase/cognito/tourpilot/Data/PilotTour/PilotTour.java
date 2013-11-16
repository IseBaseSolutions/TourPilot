package isebase.cognito.tourpilot.Data.PilotTour;

import java.text.SimpleDateFormat;
import java.util.Date;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.DataBase.MapField;

public class PilotTour extends BaseObject {

	public static final String PlanDateField = "plan_date";
	public static final String IsCommonTourField = "is_common_tour";
	public static final String TourIDField = "tour_id";

	private Date planDate;
	private boolean isCommonTour;
	private int tourID;
	
	@MapField(DatabaseField = IsCommonTourField)
	public boolean getIsCommonTour() {
		return isCommonTour;
	}

	@MapField(DatabaseField = IsCommonTourField)
	public void setIsCommonTour(boolean isCommonTour) {
		this.isCommonTour = isCommonTour;
	}
	
	@MapField(DatabaseField = PlanDateField)
	public Date getPlanDate() {
		return planDate;
	}
	
	@MapField(DatabaseField = PlanDateField)
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	
	@MapField(DatabaseField = TourIDField)
	public int getTourID() {
		return tourID;
	}
	
	@MapField(DatabaseField = TourIDField)
	public void setTourID(int tourID) {
		this.tourID = tourID;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("EE MM.dd");
		String dayOfTheWeek = simpleDateformat.format(getPlanDate());
		return String.format("%s - %s", getName(), dayOfTheWeek);
	}
	
	@Override
	public String forServer() {
		// TODO Auto-generated method stub
		return null;
	}	

}
