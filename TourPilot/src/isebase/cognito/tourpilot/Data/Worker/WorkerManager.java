package isebase.cognito.tourpilot.Data.Worker;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import isebase.cognito.tourpilot.DataBase.DataBaseWrapper;

public class WorkerManager {
	
	// Database fields
	private DataBaseWrapper dbHelper;
	private String[] WORKER_TABLE_COLUMNS = { DataBaseWrapper.ID, DataBaseWrapper.NAME };
	private SQLiteDatabase database;

	public WorkerManager(Context context) {
		dbHelper = new DataBaseWrapper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Worker addWorker(String name) {

		ContentValues values = new ContentValues();

		values.put(DataBaseWrapper.NAME, name);

		long workerId = database.insert(DataBaseWrapper.WORKERS, null, values);

		// now that the student is created return it ...
		Cursor cursor = database.query(DataBaseWrapper.WORKERS,
				WORKER_TABLE_COLUMNS, DataBaseWrapper.ID + " = "
						+ workerId, null, null, null, null);

		cursor.moveToFirst();

		Worker newComment = parseWorker(cursor);
		cursor.close();
		return newComment;
	}

	public void deleteWorker(Worker worker) {
		long id = worker.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(DataBaseWrapper.WORKERS, DataBaseWrapper.ID
				+ " = " + id, null);
	}

	public List<Worker> loadAll() {
		List<Worker> workers = new ArrayList<Worker>();

		Cursor cursor = database.query(DataBaseWrapper.WORKERS,
				WORKER_TABLE_COLUMNS, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Worker worker = parseWorker(cursor);
			workers.add(worker);
			cursor.moveToNext();
		}

		cursor.close();
		return workers;
	}

	private Worker parseWorker(Cursor cursor) {
		Worker worker = new Worker();
//		int i = 0;
//		while (cursor.)
//		{
//			String columnName = cursor.getColumnName(i);
//			i++;
//		}
		worker.setId((cursor.getInt(0)));
		worker.setName(cursor.getString(1));
		return worker;
	}

}
