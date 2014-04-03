package isebase.cognito.tourpilot.Connection;

import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.EmploymentVerification.EmploymentVerification;
import isebase.cognito.tourpilot.Data.EmploymentVerification.EmploymentVerificationManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Question.Answer.Answer;
import isebase.cognito.tourpilot.Data.Question.Answer.AnswerManager;
import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.Data.Task.TaskManager;
import isebase.cognito.tourpilot.Data.UserRemark.UserRemark;
import isebase.cognito.tourpilot.Data.UserRemark.UserRemarkManager;
import isebase.cognito.tourpilot.Data.WayPoint.WayPoint;
import isebase.cognito.tourpilot.Data.WayPoint.WayPointManager;
import isebase.cognito.tourpilot.Data.Work.Work;
import isebase.cognito.tourpilot.Data.Work.WorkManager;
import isebase.cognito.tourpilot.DataBase.HelperFactory;
import isebase.cognito.tourpilot.NewData.NewAnswer.NewAnswer;
import isebase.cognito.tourpilot.NewData.NewEmployment.NewEmployment;
import isebase.cognito.tourpilot.NewData.NewEmploymentVerification.NewEmploymentVerification;
import isebase.cognito.tourpilot.NewData.NewTask.NewTask;
import isebase.cognito.tourpilot.NewData.NewUserRemark.NewUserRemark;
import isebase.cognito.tourpilot.NewData.NewWayPoint.NewWayPoint;
import isebase.cognito.tourpilot.NewData.NewWork.NewWork;

import java.util.ArrayList;
import java.util.List;

public class SentObjectVerification {
	
	private static SentObjectVerification instance;
	
	public List<Task> sentTasks = new ArrayList<Task>();
	public List<NewTask> sentNewTasks = new ArrayList<NewTask>();
	public List<Employment> sentEmployments = new ArrayList<Employment>();
	public List<NewEmployment> sentNewEmployments = new ArrayList<NewEmployment>();
	public List<Work> sentWorks = new ArrayList<Work>();
	public List<NewWork> sentNewWorks = new ArrayList<NewWork>();
	public List<UserRemark> sentUserRemarks = new ArrayList<UserRemark>();
	public List<NewUserRemark> sentNewUserRemarks = new ArrayList<NewUserRemark>();
	public List<EmploymentVerification> sentEmploymentVerifications = new ArrayList<EmploymentVerification>();
	public List<NewEmploymentVerification> sentNewEmploymentVerifications = new ArrayList<NewEmploymentVerification>();
	public List<WayPoint> sentWayPoints = new ArrayList<WayPoint>();
	public List<NewWayPoint> sentNewWayPoints = new ArrayList<NewWayPoint>();
	public List<Answer> sentAnswers = new ArrayList<Answer>();
	public List<NewAnswer> sentNewAnswers = new ArrayList<NewAnswer>();
	
	public static SentObjectVerification Instance() {
		return instance == null ? instance = new SentObjectVerification() : instance;
	}
	
	public void setWasSent() {
		TaskManager.Instance().updateNotSent(sentTasks);
		EmploymentManager.Instance().updateNotSent(sentEmployments);
		UserRemarkManager.Instance().updateNotSent(sentUserRemarks);
		EmploymentVerificationManager.Instance().updateNotSent(sentEmploymentVerifications);
		AnswerManager.Instance().updateNotSent(sentAnswers);
		WayPointManager.Instance().delete(sentWayPoints);
		clear();
	}
	
	public void clear(){
		sentTasks.clear();
		sentEmployments.clear();
		sentWorks.clear();
		sentUserRemarks.clear();
		sentEmploymentVerifications.clear();
		sentWayPoints.clear();
		sentAnswers.clear();
	}

}
