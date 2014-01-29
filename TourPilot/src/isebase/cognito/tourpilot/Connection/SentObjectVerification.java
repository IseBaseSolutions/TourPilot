package isebase.cognito.tourpilot.Connection;

import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.EmploymentVerification.EmploymentVerification;
import isebase.cognito.tourpilot.Data.EmploymentVerification.EmploymentVerificationManager;
import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.Data.Task.TaskManager;
import isebase.cognito.tourpilot.Data.UserRemark.UserRemark;
import isebase.cognito.tourpilot.Data.UserRemark.UserRemarkManager;
import isebase.cognito.tourpilot.Data.WayPoint.WayPoint;
import isebase.cognito.tourpilot.Data.WayPoint.WayPointManager;
import isebase.cognito.tourpilot.Data.Work.Work;
import isebase.cognito.tourpilot.Data.Work.WorkManager;
import java.util.ArrayList;
import java.util.List;

public class SentObjectVerification {
	
	private static SentObjectVerification instance;
	
	public List<Task> sentTasks = new ArrayList<Task>();
	public List<Employment> sentEmployments = new ArrayList<Employment>();
	public List<Work> sentWorks = new ArrayList<Work>();
	public List<UserRemark> sentUserRemarks = new ArrayList<UserRemark>();
	public List<EmploymentVerification> sentEmploymentVerifications = new ArrayList<EmploymentVerification>();
	public List<WayPoint> sentWayPoint = new ArrayList<WayPoint>();
	
	public static SentObjectVerification Instance() {
		return instance == null ? instance = new SentObjectVerification() : instance;
	}
	
	public void setWasSent() {
		/*		
 		for(Task task : sentTasks)
			task.setWasSent(true);
		TaskManager.Instance().save(sentTasks);
		
		for(Employment employment : sentEmployments)
			employment.setWasSent(true);
		EmploymentManager.Instance().save(sentEmployments);
		
		for(Work work : sentWorks)
			work.setWasSent(true);
		WorkManager.Instance().save(sentWorks);
		
		for(UserRemark userRemark : sentUserRemarks)
			userRemark.setWasSent(true);
		UserRemarkManager.Instance().save(sentUserRemarks);
		
		for(EmploymentVerification emplVerification : sentEmploymentVerifications)
			emplVerification.setWasSent(true);
		EmploymentVerificationManager.Instance().save(sentEmploymentVerifications);
		
		for(WayPoint wayPoint : sentWayPoint)
			wayPoint.setWasSent(true);
		WayPointManager.Instance().save(sentWayPoint);			
		*/

		TaskManager.Instance().updateNotSent(sentTasks);
		EmploymentManager.Instance().updateNotSent(sentEmployments);
		WorkManager.Instance().updateNotSent(sentWorks);
		UserRemarkManager.Instance().updateNotSent(sentUserRemarks);
		EmploymentVerificationManager.Instance().updateNotSent(sentEmploymentVerifications);
		WayPointManager.Instance().delete(sentWayPoint);
		clear();
	}
	
	public void clear(){
		sentTasks.clear();
		sentEmployments.clear();
		sentWorks.clear();
		sentUserRemarks.clear();
		sentEmploymentVerifications.clear();
		sentWayPoint.clear();
	}

}
