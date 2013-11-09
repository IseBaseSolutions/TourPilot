package isebase.cognito.tourpilot.Data.BaseObject;

import isebase.cognito.tourpilot.DataBase.DataBaseWrapper;
import isebase.cognito.tourpilot.DataBase.MapField;

import java.lang.reflect.Method;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public abstract class BaseObjectManager<T> {

	private Class<T> entityClass;
	protected String[] TABLE_COLUMNS;

	public BaseObjectManager(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public Class<T> getRecType() {
		return entityClass;
	}

	public abstract String getRecTableName();

	public void open() throws SQLException {
		getTableColumns();
	}

	public void close() {
		if (DataBaseWrapper.Instance().getReadableDatabase() != null)
			DataBaseWrapper.Instance().getReadableDatabase().close();
		DataBaseWrapper.Instance().close();
	}

	public void delete(int id) {
		try {
			DataBaseWrapper
					.Instance()
					.getReadableDatabase()
					.delete(getRecTableName(), BaseObject.IDField + " = " + id,
							null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
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

	public List<T> load(String whereField, String whereClouse) {
		List<T> items = new ArrayList<T>();
		Cursor cursor = null;
		try {
			cursor = DataBaseWrapper
					.Instance()
					.getReadableDatabase()
					.query(getRecTableName(), TABLE_COLUMNS,
							whereField + " = " + whereClouse, null, null, null,
							null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				T object = parseObject(cursor);
				items.add(object);
				cursor.moveToNext();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			close();
		}
		return items;
	}

	public void save(List<T> items) {
		for (T item : items)
			save(item);
	}

	public void save(T item) {
		try {
			int id = (Integer) item.getClass().getMethod("getId").invoke(item);
			if (id == BaseObject.EMPTY_ID || load(id) == null) {
				int itemID = (int) DataBaseWrapper.Instance()
						.getReadableDatabase()
						.insert(getRecTableName(), null, getValues(item));
				item.getClass().getMethod("setId", int.class)
						.invoke(item, (int) itemID);
			} else {
				DataBaseWrapper
						.Instance()
						.getReadableDatabase()
						.update(getRecTableName(), getValues(item),
								BaseObject.IDField + " = " + id, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public List<T> loadAll() {
		List<T> items = load();
		afterLoad(items);
		return items;
	}

	public T loadAll(int id) {
		T item = load(id);
		afterLoad(item);
		return item;
	}

	public void afterLoad(List<T> items) {
	}

	public void afterLoad(T item) {
	}

	public List<T> load() {
		List<T> items = new ArrayList<T>();
		Cursor cursor = null;
		try {
			cursor = DataBaseWrapper
					.Instance()
					.getReadableDatabase()
					.query(getRecTableName(), TABLE_COLUMNS, null, null, null,
							null, null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				T object = parseObject(cursor);
				items.add(object);
				cursor.moveToNext();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			close();
		}
		return items;
	}

	public T load(int id) {
		Cursor cursor = null;
		T item = null;
		try {
			cursor = DataBaseWrapper
					.Instance()
					.getReadableDatabase()
					.query(getRecTableName(), TABLE_COLUMNS,
							BaseObject.IDField + " = " + id, null, null, null,
							null);
			cursor.moveToFirst();
			item = parseObject(cursor);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			close();
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
					else if (method.getParameterTypes()[0].equals(boolean.class))
						method.invoke(item, cursor.getInt(cursor
								.getColumnIndex(annos.DatabaseField())) == 1);
					else if (method.getParameterTypes()[0].equals(Date.class))
						method.invoke(item, new Date(cursor.getInt(cursor
								.getColumnIndex(annos.DatabaseField()))));

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
			if (annos != null && method.getReturnType().equals(Void.TYPE)) {
				list.add(annos.DatabaseField());
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
				if (annos == null)
					continue;
				if (method.getReturnType().equals(int.class) && annos.DatabaseField() != BaseObject.IDField)
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
					values.put(annos.DatabaseField(),
							Long.parseLong(method.invoke(item).toString()));
				else if (method.getReturnType().equals(short.class))
					values.put(annos.DatabaseField(), Short
							.parseShort(method.invoke(item).toString()));
				else if (method.getReturnType().equals(byte.class))
					values.put(annos.DatabaseField(),
							Byte.parseByte(method.invoke(item).toString()));
				else if (method.getReturnType().equals(Date.class))
					values.put(annos.DatabaseField(),
							((Date) method.invoke(item)).getDate());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}

	public abstract void onUpgrade(SQLiteDatabase db);

	protected void addColumn(SQLiteDatabase db, String colName, String colType) {
		Cursor tableInfo = null;
		try {
			tableInfo = db
					.rawQuery(String.format("PRAGMA table_info(%1$s)",
							getRecTableName()), null);
			tableInfo.moveToFirst();
			boolean isColumnExists = false;
			while (!tableInfo.isAfterLast()) {
				String currColName = tableInfo.getString(1);
				System.out.println(currColName);
				if (currColName.equals(colName)) {
					isColumnExists = true;
					break;
				}
				tableInfo.moveToNext();
			}
			if (!isColumnExists) {
				db.execSQL(String.format("ALTER TABLE %1$s ADD %2$s %3$s",
						getRecTableName(), colName, colType));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (tableInfo != null)
				tableInfo.close();
		}
	}
}
