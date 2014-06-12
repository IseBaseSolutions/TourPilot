package isebase.cognito.tourpilot.Data.Task;

import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTask;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectDAO;

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
		for(AdditionalTask additionalTask : additionalTasks)
			addedAdditionalTasks.add(new Task(additionalTask));
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
