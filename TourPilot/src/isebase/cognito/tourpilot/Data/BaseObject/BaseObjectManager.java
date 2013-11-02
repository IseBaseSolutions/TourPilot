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

public abstract class BaseObjectManager<T> {

	private Class<T> entityClass;
	protected DataBaseWrapper dbHelper;
	protected String[] TABLE_COLUMNS;
	protected SQLiteDatabase database;

	public BaseObjectManager(Class<T> entityClass) {
		this.entityClass = entityClass;
		dbHelper = new DataBaseWrapper(StaticResources.getBaseContext());
	}

	public Class<T> getRecType() {
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

	public T add(T object) {

		long objectID = database.insert(getRecTableName(), null,
				getValues(object));

		Cursor cursor = database.query(getRecTableName(), TABLE_COLUMNS,
				DataBaseWrapper.ID + " = " + objectID, null, null, null, null);

		cursor.moveToFirst();
		T newComment = null;
		try {
			newComment = parseObject(cursor);
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newComment;
	}

	public void delete(Class<T> object) {
		try {
			long id = (Long) object.getMethod("getID").invoke(object);
			System.out.println("Comment deleted with id: " + id);
			database.delete(getRecTableName(), DataBaseWrapper.ID + " = " + id,
					null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save(T object) {
		try {
			long id = (Long) object.getClass().getMethod("getID")
					.invoke(object);
			database.update(getRecTableName(), getValues(object),
					DataBaseWrapper.ID + " = " + id, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<T> load() {
		List<T> objects = new ArrayList<T>();
		try {
			Cursor cursor = database.query(getRecTableName(), TABLE_COLUMNS,
					null, null, null, null, null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				T object = parseObject(cursor);
				objects.add(object);
				cursor.moveToNext();
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objects;
	}

	private T parseObject(Cursor cursor) throws InstantiationException,
			IllegalAccessException {
		T object = getRecType().newInstance();
		Method[] methods = object.getClass().getMethods();
		for (Method method : methods) {
			if (!method.getReturnType().equals(Void.TYPE))
				continue;
			MapField annos = method.getAnnotation(MapField.class);
			if (annos != null) {
				try {
					if (method.getParameterTypes()[0].equals(int.class))
						method.invoke(object, cursor.getInt(cursor
								.getColumnIndex(annos.DatabaseField())));
					else if (method.getParameterTypes()[0] == String.class)
						method.invoke(object, cursor.getString(cursor
								.getColumnIndex(annos.DatabaseField())));
					else if (method.getParameterTypes()[0].equals(Blob.class))
						method.invoke(object, cursor.getBlob(cursor
								.getColumnIndex(annos.DatabaseField())));
					else if (method.getParameterTypes()[0].equals(double.class))
						method.invoke(object, cursor.getDouble(cursor
								.getColumnIndex(annos.DatabaseField())));
					else if (method.getParameterTypes()[0].equals(float.class))
						method.invoke(object, cursor.getFloat(cursor
								.getColumnIndex(annos.DatabaseField())));
					else if (method.getParameterTypes()[0].equals(long.class))
						method.invoke(object, cursor.getLong(cursor
								.getColumnIndex(annos.DatabaseField())));
					else if (method.getParameterTypes()[0].equals(short.class))
						method.invoke(object, cursor.getShort(cursor
								.getColumnIndex(annos.DatabaseField())));
					else if (method.getParameterTypes()[0]
							.equals(boolean.class))
						method.invoke(object, cursor.getInt(cursor
								.getColumnIndex(annos.DatabaseField())) == 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return object;
	}

	private void getTableColumns() {
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

	private ContentValues getValues(T object) {
		ContentValues values = new ContentValues();
		try {
			for (Method method : object.getClass().getMethods()) {
				if (method.getReturnType().equals(Void.TYPE))
					continue;
				MapField annos = method.getAnnotation(MapField.class);
				if (annos != null) {
					if (method.getReturnType().equals(int.class))
						values.put(annos.DatabaseField(), Integer
								.parseInt(method.invoke(object).toString()));
					else if (method.getReturnType().equals(String.class))
						values.put(annos.DatabaseField(),
								(String) method.invoke(object));
					else if (method.getReturnType().equals(boolean.class))
						values.put(annos.DatabaseField(), Boolean
								.parseBoolean(method.invoke(object).toString()));
					else if (method.getReturnType().equals(double.class))
						values.put(annos.DatabaseField(), Double
								.parseDouble(method.invoke(object).toString()));
					else if (method.getReturnType().equals(float.class))
						values.put(annos.DatabaseField(), Float
								.parseFloat(method.invoke(object).toString()));
					else if (method.getReturnType().equals(long.class))
						values.put(annos.DatabaseField(), Long.parseLong(method
								.invoke(object).toString()));
					else if (method.getReturnType().equals(short.class))
						values.put(annos.DatabaseField(), Short
								.parseShort(method.invoke(object).toString()));
					else if (method.getReturnType().equals(byte.class))
						values.put(annos.DatabaseField(), Byte.parseByte(method
								.invoke(object).toString()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}

}
