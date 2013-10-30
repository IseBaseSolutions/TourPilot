package isebase.cognito.tourpilot.Data.BaseObject;

import isebase.cognito.tourpilot.DataBase.DataBaseWrapper;
import isebase.cognito.tourpilot.DataBase.MapField;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public abstract class BaseObjectManager {

	// Database fields
	private DataBaseWrapper dbHelper;
	private String[] TABLE_COLUMNS;// = { DataBaseWrapper.ID, DataBaseWrapper.NAME };
	private SQLiteDatabase database;

	public BaseObjectManager(Context context) {
		dbHelper = new DataBaseWrapper(context);
	}
	
	public Type GetRecType()
	{
		return BaseObject.class.getClass();
	}

	public abstract String GetRecTableName();

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public BaseObject addWorker(String name) {

		ContentValues values = new ContentValues();

		values.put(DataBaseWrapper.NAME, name);

		long objectID = database.insert(GetRecTableName(), null, values);

		// now that the student is created return it ...
		Cursor cursor = database.query(GetRecTableName(),
				getTableColumns(), DataBaseWrapper.ID + " = "
						+ objectID, null, null, null, null);

		cursor.moveToFirst();

		BaseObject newComment = parseObject(cursor);
		cursor.close();
		return newComment;
	}

	public void deleteWorker(BaseObject object) {
		long id = object.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(GetRecTableName(), DataBaseWrapper.ID
				+ " = " + id, null);
	}

	public List<BaseObject> loadAll() {
		List<BaseObject> objects = new ArrayList<BaseObject>();

		Cursor cursor = database.query(GetRecTableName(),
				getTableColumns(), null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			BaseObject object = parseObject(cursor);
			objects.add(object);
			cursor.moveToNext();
		}

		cursor.close();
		return objects;
	}

	private BaseObject parseObject(Cursor cursor) {
		BaseObject baseObject = new BaseObject();
		Method[] methods = baseObject.getClass().getMethods();
		int i = 0;
        for (Method method : methods) {
        	MapField annos = method.getAnnotation(MapField.class);
            if (annos != null) {
                try {
                	int index = cursor.getColumnIndex(annos.DatabaseField());
                	switch(annos.Number())
                	{
	                	case 0: method.invoke(baseObject, cursor.getInt(index));
	                	break;
	                	case 1: method.invoke(baseObject, cursor.getDouble(index));
	                	break;
	                	case 2: method.invoke(baseObject, cursor.getFloat(index));
	                	break;
	                	case 3: method.invoke(baseObject, cursor.getLong(index));
	                	break;
	                	case 4: method.invoke(baseObject,cursor.getShort(index));
	                	break;
	                	case 5: method.invoke(baseObject,cursor.getString(index));
	                	break;
	                	case 6: method.invoke(baseObject,cursor.getBlob(index));
	                	break;
	                	case 7: method.invoke(baseObject,cursor.getBlob(index));
	                	break;
                	}

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }        
		baseObject.setId((cursor.getInt(0)));
		baseObject.setName(cursor.getString(1));
		return baseObject;
	}
	
	private String[] getTableColumns()
	{
		if (TABLE_COLUMNS != null)
			return TABLE_COLUMNS;
		BaseObject baseObject = new BaseObject();
		Method[] methods = baseObject.getClass().getMethods();
		int i = 0;
        for (Method method : methods) {
        	MapField annos = method.getAnnotation(MapField.class);
            if (annos != null) {
                try {
                	TABLE_COLUMNS[i++] = annos.DatabaseField();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
		return TABLE_COLUMNS;		
	}
	
}
