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

	public void delete(int id){
		try {
			System.out.println("Comment deleted with id: " + id);
			database.delete(getRecTableName(), BaseObject.IDField + " = " + id, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Class<T> item) {
		try {
			int id = (Integer) item.getMethod("getId").invoke(item);
			delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save(List<T> items){
		for(T item: items)
			save(item);
	}
	
	public void save(T item) {
		try {
			int id = (Integer) item.getClass().getMethod("getId").invoke(item);
			if(id == BaseObject.EMPTY_ID || load(id) == null ){
				int itemID = (int) database.insert(getRecTableName(), null, getValues(item));
				item.getClass().getMethod("setId").invoke(item, itemID);
			}
			else{
				database.update(getRecTableName(), getValues(item), BaseObject.IDField + " = " + id, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<T> loadAll(){
		List<T> items = load();
		afterLoad(items);
		return items;
	}
	
	public T loadAll(int id){
		T item = load(id);
		afterLoad(item);
		return item;
	}
	
	public void afterLoad(List<T> items){}
	public void afterLoad(T item){}
	
	public List<T> load() {
		List<T> items = new ArrayList<T>();
		Cursor cursor = null;
		try {
			cursor = database.query(getRecTableName(), TABLE_COLUMNS,
					null, null, null, null, null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				T object = parseObject(cursor);
				items.add(object);
				cursor.moveToNext();
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if(cursor != null)
				cursor.close();
		}
		return items;
	}

	public T load(int id) {
		Cursor cursor = null;
		T item = null;
		try {
			cursor = database.query(getRecTableName(), TABLE_COLUMNS, BaseObject.IDField + " = " + id, null, null, null, null);
			cursor.moveToFirst();
			item = parseObject(cursor);			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if(cursor != null)
				cursor.close();
		}
		return item;
	}

	private T parseObject(Cursor cursor) throws InstantiationException,
			IllegalAccessException {
		T item = getRecType().newInstance();
		Method[] methods = item.getClass().getMethods();
		for (Method method : methods) {
			if (!method.getReturnType().equals(Void.TYPE))
				continue;
			MapField annos = method.getAnnotation(MapField.class);
			if (annos != null) {
				try {
					if (method.getParameterTypes()[0].equals(int.class))
						method.invoke(item, cursor.getInt(cursor
								.getColumnIndex(annos.DatabaseField())));
					else if (method.getParameterTypes()[0] == String.class)
						method.invoke(item, cursor.getString(cursor
								.getColumnIndex(annos.DatabaseField())));
					else if (method.getParameterTypes()[0].equals(Blob.class))
						method.invoke(item, cursor.getBlob(cursor
								.getColumnIndex(annos.DatabaseField())));
					else if (method.getParameterTypes()[0].equals(double.class))
						method.invoke(item, cursor.getDouble(cursor
								.getColumnIndex(annos.DatabaseField())));
					else if (method.getParameterTypes()[0].equals(float.class))
						method.invoke(item, cursor.getFloat(cursor
								.getColumnIndex(annos.DatabaseField())));
					else if (method.getParameterTypes()[0].equals(long.class))
						method.invoke(item, cursor.getLong(cursor
								.getColumnIndex(annos.DatabaseField())));
					else if (method.getParameterTypes()[0].equals(short.class))
						method.invoke(item, cursor.getShort(cursor
								.getColumnIndex(annos.DatabaseField())));
					else if (method.getParameterTypes()[0]
							.equals(boolean.class))
						method.invoke(item, cursor.getInt(cursor
								.getColumnIndex(annos.DatabaseField())) == 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return item;
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

	private ContentValues getValues(T item) {
		ContentValues values = new ContentValues();
		try {
			for (Method method : item.getClass().getMethods()) {
				if (method.getReturnType().equals(Void.TYPE))
					continue;
				MapField annos = method.getAnnotation(MapField.class);
				if (annos != null) {
					if (method.getReturnType().equals(int.class))
						values.put(annos.DatabaseField(), Integer
								.parseInt(method.invoke(item).toString()));
					else if (method.getReturnType().equals(String.class))
						values.put(annos.DatabaseField(),
								(String) method.invoke(item));
					else if (method.getReturnType().equals(boolean.class))
						values.put(annos.DatabaseField(), Boolean
								.parseBoolean(method.invoke(item).toString()));
					else if (method.getReturnType().equals(double.class))
						values.put(annos.DatabaseField(), Double
								.parseDouble(method.invoke(item).toString()));
					else if (method.getReturnType().equals(float.class))
						values.put(annos.DatabaseField(), Float
								.parseFloat(method.invoke(item).toString()));
					else if (method.getReturnType().equals(long.class))
						values.put(annos.DatabaseField(), Long.parseLong(method
								.invoke(item).toString()));
					else if (method.getReturnType().equals(short.class))
						values.put(annos.DatabaseField(), Short
								.parseShort(method.invoke(item).toString()));
					else if (method.getReturnType().equals(byte.class))
						values.put(annos.DatabaseField(), Byte.parseByte(method
								.invoke(item).toString()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}

	public abstract void onUpdate(SQLiteDatabase db);
	
	protected void addColumn(SQLiteDatabase db, String colName, String colType){
		db.execSQL(String.format("ALTER TABLE %1$s ADD %2$s %3$s" 
					, getRecTableName()
					, colName
					, colType));
	}	
}
