package isebase.cognito.tourpilot.DataBase;

import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Option.OptionManager;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Data.Tour.TourManager;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Data.Worker.WorkerManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseWrapper extends SQLiteOpenHelper {
				
	private static final String DATABASE_NAME = "TourPilot.db";
	private static final int DATABASE_VERSION = 2;

	private static final String WORKERS_TABLE_CREATE = "CREATE TABLE "
			+ WorkerManager.Instance().getRecTableName() + "(" 
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ BaseObject.NameField + " TEXT NOT NULL, "
			+ BaseObject.CheckSumField + " INTEGER "
			+ Worker.IsUseGPSField + " INTEGER "
			+ Worker.ActualDateField + " INTEGER "
			+ ");";

	private static final String TOURS_TABLE_CREATE = "CREATE TABLE " 
			+ TourManager.Instance().getRecTableName() + "(" 
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ BaseObject.NameField + " TEXT NOT NULL, "
			+ BaseObject.CheckSumField + " INTEGER "
			+ ");";

	private static final String PATIENTS_TABLE_CREATE = "CREATE TABLE "
			+ PatientManager.Instance().getRecTableName() + "(" 
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ BaseObject.NameField + " TEXT NOT NULL, "
			+ BaseObject.CheckSumField + " INTEGER, "
			+ Patient.AddressField + " TEXT, "
			+ Patient.IsDoneField + " INTEGER NOT NULL DEFAULT 0 "
			+ ");";
	
	private static final String OPTIONS_TABLE_CREATE = "CREATE TABLE "
			+ OptionManager.Instance().getRecTableName() + "(" 
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ BaseObject.NameField + " TEXT, "
			+ BaseObject.CheckSumField + " INTEGER, "
			+ Option.WorkerIDField + " INTEGER, "
			+ Option.TourIDField  + " INTEGER, "
			+ Option.EmploymentIDField + " INTEGER, "
			+ Option.ServerIPField + " TEXT, "
			+ Option.ServerPortField + " INTEGER "
			+ ");";

	public DataBaseWrapper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(WORKERS_TABLE_CREATE);
		db.execSQL(TOURS_TABLE_CREATE);
		db.execSQL(PATIENTS_TABLE_CREATE);
		db.execSQL(OPTIONS_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("database log", "on update");
		WorkerManager.Instance().onUpdate(db);
	}
}
