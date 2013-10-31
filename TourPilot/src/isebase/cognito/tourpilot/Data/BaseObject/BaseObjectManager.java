package isebase.cognito.tourpilot.Data.BaseObject;

import isebase.cognito.tourpilot.DataBase.DataBaseWrapper;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.StaticResources.StaticResources;

import java.lang.reflect.Method;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public abstract class BaseObjectManager <T> {

	private Class<T> entityClass;
	protected DataBaseWrapper dbHelper;
	protected String[] TABLE_COLUMNS;
	protected SQLiteDatabase database;

	public BaseObjectManager(Class<T> entityClass) {
		this.entityClass = entityClass;
		dbHelper = new DataBaseWrapper(StaticResources.getBaseContext());
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

	public T add(String name) {

		ContentValues values = new ContentValues();

		values.put(DataBaseWrapper.NAME, name);

		long objectID = database.insert(getRecTableName(), null, values);

		Cursor cursor = database.query(getRecTableName(),
				TABLE_COLUMNS, DataBaseWrapper.ID + " = "
						+ objectID, null, null, null, null);

		cursor.moveToFirst();
		T newComment = null;
		try {
			newComment = parseObject(cursor);
			cursor.close();
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
		return newComment;
	}

	public void delete(Class<T> object) {
		try {
			long id = (Long) object.getMethod("getID").invoke(object);
			System.out.println("Comment deleted with id: " + id);
			database.delete(getRecTableName(), DataBaseWrapper.ID + " = " + id, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public List<T> load() {
		List<T> objects = new ArrayList<T>();
		try {
			Cursor cursor = database.query(getRecTableName(),
					TABLE_COLUMNS, null, null, null, null, null);	
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				T object = parseObject(cursor);			
				objects.add(object);
				cursor.moveToNext();
			}	
			cursor.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return objects;
	}

	private T parseObject(Cursor cursor) throws InstantiationException, IllegalAccessException {		
		T object = getRecType().newInstance();
		Method[] methods = object.getClass().getMethods();
		for (int i = 0; i < cursor.getColumnCount(); i++)
		{
	        for (Method method : methods) {
	        	MapField annos = method.getAnnotation(MapField.class);
	            if (annos != null) {
	                try 
	                {
	                	if (!annos.DatabaseField().equals(cursor.getColumnName(i)))
	                		continue;
	                	if (method.getParameterTypes()[0] == int.class)
	                		method.invoke(object, cursor.getInt(i));
	                	if (method.getParameterTypes()[0] == String.class)
	                		method.invoke(object, cursor.getString(i));
	                	if (method.getParameterTypes()[0] == Blob.class)
	                		method.invoke(object, cursor.getBlob(i));
	                	if (method.getParameterTypes()[0] == Double.class)
	                		method.invoke(object, cursor.getDouble(i));
	                	if (method.getParameterTypes()[0] == Float.class)
	                		method.invoke(object, cursor.getFloat(i));
	                	if (method.getParameterTypes()[0] == Long.class)
	                		method.invoke(object, cursor.getLong(i));
	                	if (method.getParameterTypes()[0] == Short.class)
	                		method.invoke(object, cursor.getShort(i));
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
