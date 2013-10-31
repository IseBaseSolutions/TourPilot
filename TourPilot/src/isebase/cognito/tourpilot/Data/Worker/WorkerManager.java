package isebase.cognito.tourpilot.Data.Worker;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObjectManager;
import android.content.Context;

public class WorkerManager extends BaseObjectManager<Worker> {
	
	public WorkerManager(Context context) {
		super(context, Worker.class);
	}

	@Override
	public String getRecTableName() {
		return dbHelper.WORKERS;
	}

//	private DataBaseWrapper dbHelper;
//	private String[] WORKER_TABLE_COLUMNS = { DataBaseWrapper.ID, DataBaseWrapper.NAME };
//	private SQLiteDatabase database;
//
//	public void open() throws SQLException {
//		database = dbHelper.getWritableDatabase();
//	}
//
//	public void close() {
//		dbHelper.close();
//	}
//
//	public Worker add(String name) {
//
//		ContentValues values = new ContentValues();
//
//		values.put(DataBaseWrapper.NAME, name);
//
//		long workerId = database.insert(DataBaseWrapper.WORKERS, null, values);
//
//		Cursor cursor = database.query(DataBaseWrapper.WORKERS,
//				WORKER_TABLE_COLUMNS, DataBaseWrapper.ID + " = "
//						+ workerId, null, null, null, null);
//
//		cursor.moveToFirst();
//
//		Worker newComment = parseWorker(cursor);
//		cursor.close();
//		return newComment;
//	}
//
//	public void deleteWorker(Worker worker) {
//		long id = worker.getId();
//		System.out.println("Comment deleted with id: " + id);
//		database.delete(DataBaseWrapper.WORKERS, DataBaseWrapper.ID
//				+ " = " + id, null);
//	}
//
//	private Worker parseWorker(Cursor cursor) {
//		Worker worker = new Worker();
//		for (int i = 0; i < cursor.getColumnCount(); i++)
//		{
//			String a = cursor.getColumnName(i);
//			Method[] methods = getRecType().getMethods();
//	        for (Method method : methods) {
//	        	MapField annos = method.getAnnotation(MapField.class);
//	            if (annos != null) {
//	                try 
//	                {
//	                	if (annos.DatabaseField().equals(a) && method.getReturnType() == Integer.TYPE)
//	                		method.invoke(getRecType(), cursor.getInt(i));
//	                	if (annos.DatabaseField().equals(a) && method.getReturnType() == Integer.TYPE)
//	                		method.invoke(getRecType(), cursor.getInt(i));
//	                } catch (Exception e) {
//	                    e.printStackTrace();
//	                }
//	            }
//	        }
//		}
//		worker.setId((cursor.getInt(0)));
//		worker.setName(cursor.getString(1));
//		return worker;
//	}
//
//	@Override
//	public String getRecTableName() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
