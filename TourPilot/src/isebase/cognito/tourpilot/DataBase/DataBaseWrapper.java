package isebase.cognito.tourpilot.DataBase;

import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTask;
import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTaskManager;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Option.OptionManager;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.Data.Task.TaskManager;
import isebase.cognito.tourpilot.Data.Tour.TourManager;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;
import isebase.cognito.tourpilot.StaticResources.StaticResources;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseWrapper extends SQLiteOpenHelper {

	public static final String TYPE_INTEGER = "INTEGER";
	public static final String TYPE_DATE = TYPE_INTEGER;
	public static final String TYPE_ENUM = TYPE_INTEGER;
	
	public static final String TYPE_TEXT = "TEXT";
	
	public static final String TYPE_REAL = "REAL";
	public static final String TYPE_FLOAT = TYPE_REAL;
	public static final String TYPE_DOUBLE = TYPE_REAL;
	
	public static final String TYPE_BLOB = "BLOB";
	
	public static final String TYPE_NULL = "NULL";	
	
	private static final String DATABASE_NAME = "TourPilot.db";

	public static final int DATABASE_VERSION = 7;

	private static final String WORKERS_TABLE_CREATE = "CREATE TABLE "
			+ WorkerManager.TableName + "(" 
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ BaseObject.NameField + " TEXT NOT NULL, "
			+ BaseObject.CheckSumField + " INTEGER, "
			+ Worker.IsUseGPSField + " INTEGER, "
			+ Worker.ActualDateField + " INTEGER "
			+ ");";

	private static final String TOURS_TABLE_CREATE = "CREATE TABLE " 
			+ TourManager.TableName + "(" 
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ BaseObject.NameField + " TEXT NOT NULL, "
			+ BaseObject.CheckSumField + " INTEGER "
			+ ");";

	private static final String PATIENTS_TABLE_CREATE = "CREATE TABLE "
			+ PatientManager.TableName + "(" 
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ BaseObject.NameField + " TEXT NOT NULL, "
			+ BaseObject.CheckSumField + " INTEGER, "
			+ Patient.AddressField + " TEXT, "
			+ Patient.IsDoneField + " INTEGER NOT NULL DEFAULT 0 "
			+ ");";
	
	private static final String OPTIONS_TABLE_CREATE = "CREATE TABLE "
			+ OptionManager.TableName + "(" 
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ BaseObject.NameField + " TEXT, "
			+ BaseObject.CheckSumField + " INTEGER, "
			+ Option.WorkerIDField + " INTEGER, "
			+ Option.TourIDField  + " INTEGER, "
			+ Option.EmploymentIDField + " INTEGER, "
			+ Option.ServerIPField + " TEXT, "
			+ Option.ServerPortField + " INTEGER "
			+ ");";
	
	private static final String TASKS_TABLE_CREATE = "CREATE TABLE "
			+ TaskManager.TableName + "("
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ BaseObject.NameField + " TEXT NOT NULL, "
			+ BaseObject.CheckSumField + " INTEGER, "
			+ Task.StateField + " INTEGER NOT NULL DEFAULT 0 "
			+ ");";
	
	private static final String ADDITIONAL_TASKS_TABLE_CREATE = "CREATE TABLE "
			+ AdditionalTaskManager.TableName + "("
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ BaseObject.NameField + " TEXT NOT NULL, "
			+ BaseObject.CheckSumField + " INTEGER, "
			+ AdditionalTask.CatalogTypeField+ " INTEGER, "
			+ AdditionalTask.QualityField+ " INTEGER "
			+ ");";
	
	public DataBaseWrapper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	private static DataBaseWrapper instance;

	public static DataBaseWrapper Instance() {
		if (instance != null)
			return instance;
		instance = new DataBaseWrapper(StaticResources.getBaseContext());
		return instance;
	}	
		
	@Override
	public void onCreate(SQLiteDatabase db) {
		try{
			db.execSQL(WORKERS_TABLE_CREATE);
			db.execSQL(TOURS_TABLE_CREATE);
			db.execSQL(PATIENTS_TABLE_CREATE);
			db.execSQL(OPTIONS_TABLE_CREATE);
			db.execSQL(TASKS_TABLE_CREATE);			
		}
		catch(Exception ex){
			
		}
		finally{
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		WorkerManager.Instance().onUpgrade(db);
		TourManager.Instance().onUpgrade(db);
	}
}
