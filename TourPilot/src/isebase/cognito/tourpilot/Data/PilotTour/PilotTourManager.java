package isebase.cognito.tourpilot.Data.PilotTour;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentManager;
import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.Data.Tour.TourManager;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;

public class PilotTourManager extends BaseObjectManager<PilotTour> {

	public static final String TableName = "PilotTours";
	
	private static PilotTourManager instance;

	public static PilotTourManager Instance() {
		if (instance != null)
			return instance;
		instance = new PilotTourManager();
		instance.open();
		return instance;
	}
	
	public PilotTourManager() {
		super(PilotTour.class);
	}

	@Override
	public String getRecTableName() {
		return TableName;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db) {
	}

	public List<PilotTour> loadPilotTours() {
		String strSQL = String.format("SELECT " +
				"t1.%3$s as _id, " +
				"t2._id as tour_id, " +
				"t2.is_common_tour, " +
				"t1.date as plan_date, " +
				"t2.name as name, " +
				"t2.checksum as checksum, " +
				"t2.was_sent as was_sent, " +
				"t2.is_server_time as is_server_time " +
				"FROM %1$s t1 INNER JOIN %2$s t2 ON t1.tour_id = t2._id " +
				"GROUP BY t1.%3$s",
				EmploymentManager.TableName,
				TourManager.TableName,
				Employment.PilotTourIDField);
		return load(strSQL);		
	}

}
