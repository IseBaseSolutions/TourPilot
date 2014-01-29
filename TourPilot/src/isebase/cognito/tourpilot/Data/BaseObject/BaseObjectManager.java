package isebase.cognito.tourpilot.Data.BaseObject;

import isebase.cognito.tourpilot.DataBase.DataBaseWrapper;
import isebase.cognito.tourpilot.DataBase.MapField;
import isebase.cognito.tourpilot.Utils.Utilizer;
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

	public void delete(long id) {
		try {
			DataBaseWrapper.Instance()
					.getReadableDatabase()
					.delete(getRecTableName(), BaseObject.IDField + " = " + id,
							null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void delete(List<? extends BaseObject> items){
		String ids = Utilizer.getIDsString(items);
		if(ids.equals(""))
			return;
		try {
			DataBaseWrapper.Instance()
					.getReadableDatabase()
					.delete(getRecTableName(), BaseObject.IDField + " IN (" + ids + ")",
							null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	
	}
	
	public void delete(Class<T> item) {
		try {
			int id = (Integer) item.getMethod("getID").invoke(item);
			delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<T> loadByIDs(int[] ids) {
		return loadByIDs(Utilizer.getIDsString(ids));
	}

	public List<T> loadAllByIDs(String ids) {
		List<T> items = loadByIDs(ids);
		afterLoad(items);
		return items;
	}
	
	public List<T> loadByIDs(String ids) {
		List<T> items = new ArrayList<T>();
		if (ids.equals(""))
			return items;
		Cursor cursor = null;
		try {
			cursor = DataBaseWrapper
					.Instance()
					.getReadableDatabase()
					.query(getRecTableName(), TABLE_COLUMNS,
							BaseObject.IDField + " IN(" + ids + ") ", null,
							null, null, null);
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
		}
		return items;
	}

	public List<T> loadAllWhereOrder(String whereField, String whereClouse, String orderBy){
		return loadWhere(whereField, whereClouse, orderBy, true);
	}
	
	public List<T> loadWhereOrder(String whereField, String whereClouse, String orderBy){
		return loadWhere(whereField, whereClouse, orderBy, false);
	}
	
	public List<T> loadAll(String whereField, String whereClouse){
		return loadWhere(whereField, whereClouse, "", true);
	}
	
	public List<T> load(String whereField, String whereClouse){
		return loadWhere(whereField, whereClouse, "", false);
	}
	
	public List<T> load(String whereField, int whereClouse){
		return loadWhere(whereField, String.valueOf(whereClouse), "", false);
	}
		
	private List<T> loadWhere(String whereField, String whereClouse,String orderBy, boolean withAll) {
		List<T> items = new ArrayList<T>();
		Cursor cursor = null;
		try {
			cursor = DataBaseWrapper
					.Instance()
					.getReadableDatabase()
					.query(getRecTableName(), TABLE_COLUMNS,
							whereField + " = " + whereClouse, null, null, null,
							orderBy);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				T object = parseObject(cursor);
				items.add(object);
				cursor.moveToNext();
			}
			if(withAll)
				afterLoad(items);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return items;
	}

	public List<T> load(String strSQL) {
		List<T> items = new ArrayList<T>();
		Cursor cursor = null;

		try {
			cursor = DataBaseWrapper
					.Instance()
					.getReadableDatabase().rawQuery(strSQL, new String[]{});
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
		}
		return items;
	}

	public void beforeSave(T item){}
	
	public void afterSave(T item){}

	public void save(List<T> items) {
		for (T item : items)
			save(item);
	}

	public void save(T item) {
		try {
			beforeSave(item);
			int id = (Integer) item.getClass().getMethod("getID").invoke(item);
			if (id == BaseObject.EMPTY_ID || load(id) == null) {
				int itemID = (int) DataBaseWrapper
						.Instance()
						.getWritableDatabase()
						.insert(getRecTableName(),
								null,
								id == BaseObject.EMPTY_ID ? getValues(item)
										: getValuesWithID(item));
				item.getClass().getMethod("setID", int.class)
						.invoke(item, (int) itemID);
			} else {
				DataBaseWrapper
						.Instance()
						.getWritableDatabase()
						.update(getRecTableName(), getValues(item),
								BaseObject.IDField + " = " + id, null);
			}
			afterSave(item);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	public List<T> loadAll(String groupBy, String having, String orderBy) {
		List<T> items = load(groupBy, having, orderBy);
		afterLoad(items);
		return items;
	}

	public List<T> loadAll() {
		List<T> items = load();
		afterLoad(items);
		return items;
	}

	public T loadAll(long id) {
		T item = load(id);
		afterLoad(item);
		return item;
	}

	public void afterLoad(List<T> items) {
	}

	public void afterLoad(T item) {
	}

	public List<T> load() {
		return load(null, null, null);
	}

	public List<T> load(String groupBy, String having, String orderBy) {
		List<T> items = new ArrayList<T>();
		Cursor cursor = null;
		try {
			cursor = DataBaseWrapper
					.Instance()
					.getReadableDatabase() /////ERROR
					.query(getRecTableName(), TABLE_COLUMNS, null, null,
							groupBy, having, orderBy);
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
		}
		return items;
	}

	public T load(long id) {
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
			if (cursor.getCount() == 0)
				return null;
			item = parseObject(cursor);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return item;
	}

	private T parseObject(Cursor cursor) 
			throws InstantiationException, IllegalAccessException {
		T item = getRecType().newInstance();
		Method[] methods = item.getClass().getMethods();
		for (Method method : methods) {
			try {
				if (!method.getReturnType().equals(Void.TYPE))
					continue;
				MapField annos = method.getAnnotation(MapField.class);
				if (annos == null) 
					continue;			
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
							.getColumnIndex(annos.DatabaseField())) != 0);
				else if (method.getParameterTypes()[0].equals(Date.class))
					method.invoke(item, new Date(cursor.getLong(cursor
							.getColumnIndex(annos.DatabaseField()))));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return item;
	}

	private void getTableColumns() {
		ArrayList<String> list = new ArrayList<String>();
		Method[] methods = getRecType().getMethods();
		for (Method method : methods) {
			MapField annos = method.getAnnotation(MapField.class);
			if (annos != null && method.getReturnType().equals(Void.TYPE))
				list.add(annos.DatabaseField());
		}
		TABLE_COLUMNS = list.toArray(new String[list.size()]);
	}

	private ContentValues getValuesWithID(T item) {
		return getValues(item, true);
	}

	private ContentValues getValues(T item) {
		return getValues(item, false);
	}

	private ContentValues getValues(T item, boolean withID) {
		ContentValues values = new ContentValues();
		for (Method method : item.getClass().getMethods()) {
			try {
				if (method.getReturnType().equals(Void.TYPE))
					continue;
				MapField annos = method.getAnnotation(MapField.class);
				if (annos == null)
					continue;
				if (method.getReturnType().equals(int.class)) {
					if (annos.DatabaseField() == BaseObject.IDField && !withID) {

					} else {
						values.put(annos.DatabaseField(), Integer
								.parseInt(method.invoke(item).toString()));
					}
				} else if (method.getReturnType().equals(String.class))
					values.put(annos.DatabaseField(),
							(String) method.invoke(item));
				else if (method.getReturnType().equals(boolean.class))
					values.put(annos.DatabaseField(), Boolean
							.parseBoolean(method.invoke(item).toString()));
				else if (method.getReturnType().equals(double.class))
					values.put(annos.DatabaseField(),
							Double.parseDouble(method.invoke(item).toString()));
				else if (method.getReturnType().equals(float.class))
					values.put(annos.DatabaseField(),
							Float.parseFloat(method.invoke(item).toString()));
				else if (method.getReturnType().equals(long.class))
					values.put(annos.DatabaseField(),
							Long.parseLong(method.invoke(item).toString()));
				else if (method.getReturnType().equals(short.class))
					values.put(annos.DatabaseField(),
							Short.parseShort(method.invoke(item).toString()));
				else if (method.getReturnType().equals(byte.class))
					values.put(annos.DatabaseField(),
							Byte.parseByte(method.invoke(item).toString()));
				else if (method.getReturnType().equals(Date.class))
					values.put(annos.DatabaseField(),
							((Date) method.invoke(item)).getTime());
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return values;
	}

	public abstract void onUpgrade(SQLiteDatabase db);

	protected void addColumn(SQLiteDatabase db, String colName, String colType) {
		Cursor tableInfo = null;
		try {
			tableInfo = db.rawQuery(String.format("PRAGMA table_info(%1$s)",
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

	public long getCheckSums() {
		long lngChecksum = 0;
		List<T> elements = load();
		for (T element : elements)
			if (!((BaseObject)element).getWasSent())
				lngChecksum += ((BaseObject) element).getCheckSum();
		return lngChecksum;
	}

	public String forServer() {
		String strResult = "";
		List<T> elements = load();
		for (T element : elements) {
			String forServer = ((BaseObject) element).forServer();
			if (forServer.length() > 0)
				strResult += forServer + "\0";
		}
		strResult += ".\0";
		return strResult;
	}
	
	public int getIntValue(String strSQL) {
		Cursor cursor = null;
		int retVal = 0;
		try {
			cursor = DataBaseWrapper
					.Instance()
					.getReadableDatabase()
					.rawQuery(strSQL, null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				retVal = cursor.getInt(0);
				cursor.moveToNext();
				break;
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
		}		
		return retVal;
	}
	
	/**
	 * Send an SQL request to sum checksums from items where was_sent = FALSE
	 * @return checksum of items where was_sent = FALSE
	 * */
	public long getCheckSumByRequest(){
		return getLongValue(String.format("SELECT SUM(checksum) " +
				"FROM %1$s WHERE was_sent = 0", getRecTableName()));
	}
	
	public long getLongValue(String strSQL){
		Cursor cursor = null;
		long retVal = 0;
		try {
			cursor = DataBaseWrapper.Instance()
					.getReadableDatabase().rawQuery(strSQL, null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				retVal = cursor.getLong(0);
				cursor.moveToNext();
				break;
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
		}		
		return retVal;
	}
	
	public void execSQL(String strSQL){
		try {
			DataBaseWrapper.Instance().getReadableDatabase().execSQL(strSQL);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clearTable(){
		DataBaseWrapper.Instance().getReadableDatabase().execSQL("DELETE FROM " + getRecTableName());
	}
	
	public String getDone() {
		List<T> elements = load(BaseObject.WasSentField, "0");
		String strDone = "";
		for (T element : elements)
			strDone += ((BaseObject)element).getDone() + "\0";
		return strDone;	
	}
	
	public List<T> sortByStrIDs(List<T> items, String strIDs) {
		List<T> sortedItems = new ArrayList<T>();
		String[] arr = strIDs.contains(" ") ? strIDs.split(", ") : strIDs.split(",");
		for(int i = 0; i < arr.length; i++)
			for (T item : items)
				if (Integer.parseInt(arr[i]) == ((BaseObject)item).getID())
					sortedItems.add(item);
		return sortedItems;
	}
	
	/**
	 * Update table. Set was_sent to TRUE for all items where was_sent = FALSE
	 */
	public void updateNotSent(){
		execSQL(String.format("update %1$s set was_sent = 1 where was_sent = 0",
				getRecTableName()));
	}
	
	/**
	 * Update table. Set was_sent to TRUE for each item in items collection
	 * @param  items - collection to change was_sent to TRUE
	 */
	public void updateNotSent(Iterable<? extends BaseObject> items){
		String ids = Utilizer.getIDsString(items);
		if(ids != "")
			execSQL(String.format("update %1$s set was_sent = 1 where %2$s in (%3$s)",
					getRecTableName(),
					BaseObject.IDField,
					ids));
	}
	
}
