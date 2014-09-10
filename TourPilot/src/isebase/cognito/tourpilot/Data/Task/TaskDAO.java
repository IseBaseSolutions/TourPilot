package isebase.cognito.tourpilot.Data.Task;

import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTask;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectDAO;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.DataBase.HelperFactory;
import isebase.cognito.tourpilot.Utils.DateUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class TaskDAO extends BaseObjectDAO<Task> {

	public TaskDAO(ConnectionSource connectionSource,
			Class<Task> dataClass) throws SQLException {
		super(connectionSource, dataClass);
	}
	
	public void createTasks(List<AdditionalTask> additionalTasks){
		List<Task> addedAdditionalTasks = new ArrayList<Task>();
		List<Task> tasks = HelperFactory.getHelper().getTaskDAO().load(Task.EMPLOYMENT_ID_FIELD, String.valueOf(Option.Instance().getEmploymentID()));
		Task normalTask = null;
		for (Task task : tasks) {
			if (!task.isFirstTask() && !task.isLastTask()) {
				normalTask = task;
				break;
			}
		}
		for(AdditionalTask additionalTask : additionalTasks) {
			Task createdTask = null;
			if (normalTask != null)
				createdTask = new Task(additionalTask, normalTask.getPlanDate());
			else
				createdTask = new Task(additionalTask, DateUtils.getSynchronizedTime());
			addedAdditionalTasks.add(createdTask);
		}

		save(addedAdditionalTasks);
	}
	
	public int getFirstSymbol(int employmentID){
		try {
			return (int) queryRawValue(String.format("select substr(leistungs,1,1) as val from Tasks where employment_id = %d limit 1",	employmentID));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public void deleteByEmploymentID(int employmentID) {
		DeleteBuilder<Task, Integer> deleteBuilder = deleteBuilder();
		try {
			deleteBuilder.where().eq(Task.EMPLOYMENT_ID_FIELD, employmentID);
			deleteBuilder.delete();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
