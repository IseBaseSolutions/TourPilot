package isebase.cognito.tourpilot.Data.BaseObject;

import isebase.cognito.tourpilot.DataBase.DataBaseWrapper;
import isebase.cognito.tourpilot.DataBase.MapField;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public abstract class BaseObjectManager <T> {

	private Class<T> entityClass;
	protected DataBaseWrapper dbHelper;
	protected String[] TABLE_COLUMNS;
	protected SQLiteDatabase database;

	public BaseObjectManager(Context context, Class<T> entityClass) {
		this.entityClass = entityClass;
		dbHelper = new DataBaseWrapper(context);
	}

	public Class<T> getRecType()
	{		
		return entityClass;
	}

	public abstract String getRecTableName();

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
		getTableColumns();
	}

	public void close() {
		dbHelper.close();
	}

	public Class<T> add(String name) throws InstantiationException, IllegalAccessException {

		ContentValues values = new ContentValues();

		values.put(DataBaseWrapper.NAME, name);

		long objectID = database.insert(getRecTableName(), null, values);

		Cursor cursor = database.query(getRecTableName(),
				TABLE_COLUMNS, DataBaseWrapper.ID + " = "
						+ objectID, null, null, null, null);

		cursor.moveToFirst();

		Class<T> newComment = parseObject(cursor);
		cursor.close();
		return newComment;
	}

	public void delete(BaseObject object) {
		long id = object.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(getRecTableName(), DataBaseWrapper.ID
				+ " = " + id, null);
	}

	public List<Class<T>> load() throws InstantiationException, IllegalAccessException {
		List<Class<T>> objects = new ArrayList<Class<T>>();
		Cursor cursor = database.query(getRecTableName(),
				TABLE_COLUMNS, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Class<T> object = parseObject(cursor);			
			objects.add(object);
			cursor.moveToNext();
		}

		cursor.close();
		return objects;
	}

	private Class<T> parseObject(Cursor cursor) throws InstantiationException, IllegalAccessException {		
		Class<T> object = getRecType();
		Method[] methods = object.getClass().getMethods();
		for (int i = 0; i < cursor.getColumnCount(); i++)
		{
			String a = cursor.getColumnName(i);
	        for (Method method : methods) {
	        	MapField annos = method.getAnnotation(MapField.class);
	            if (annos != null) {
	                try 
	                {
	                	if (annos.DatabaseField().equals(a) && method.getReturnType() == Integer.class.getClass())
	                		method.invoke(getRecType(), cursor.getInt(i));
	                	if (annos.DatabaseField().equals(a) && method.getReturnType() == String.class.getClass())
	                		method.invoke(getRecType(), cursor.getString(i));
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }
		}
		return object;
	}
	
	private void getTableColumns()
	{
		ArrayList<String> list = new ArrayList<String>();
		Method[] methods = getRecType().getMethods();
        for (Method method : methods) {
        	MapField annos = method.getAnnotation(MapField.class);
            if (annos != null) {
            	list.add(annos.DatabaseField());
            }
        }
        TABLE_COLUMNS = list.toArray(new String[list.size()]);
	}
	
}
