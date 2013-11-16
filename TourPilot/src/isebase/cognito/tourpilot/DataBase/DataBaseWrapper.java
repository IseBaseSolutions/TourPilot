package isebase.cognito.tourpilot.DataBase;

import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTask;
import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTaskManager;
import isebase.cognito.tourpilot.Data.AdditionalWork.AdditionalWorkManager;
import isebase.cognito.tourpilot.Data.Address.Address;
import isebase.cognito.tourpilot.Data.Address.AddressManager;
import isebase.cognito.tourpilot.Data.BaseObject.BaseObject;
import isebase.cognito.tourpilot.Data.Diagnose.DiagnoseManager;
import isebase.cognito.tourpilot.Data.Doctor.Doctor;
import isebase.cognito.tourpilot.Data.Doctor.DoctorManager;
import isebase.cognito.tourpilot.Data.Information.Information;
import isebase.cognito.tourpilot.Data.Information.InformationManager;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientManager;
import isebase.cognito.tourpilot.Data.PatientRemark.PatientRemarkManager;
import isebase.cognito.tourpilot.Data.Relative.Relative;
import isebase.cognito.tourpilot.Data.Relative.RelativeManager;
import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.Data.Task.TaskManager;
import isebase.cognito.tourpilot.Data.Tour.Tour;
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

	public static final int DATABASE_VERSION = 11;

	private static final String WORKERS_TABLE_CREATE = 
			"CREATE TABLE " + WorkerManager.TableName + "(" 
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ BaseObject.NameField + " TEXT NOT NULL, "
			+ BaseObject.CheckSumField + " INTEGER, "
			+ BaseObject.WasSentField + " INTEGER, "
			+ BaseObject.IsServerTimeField + " INTEGER, "
			+ Worker.IsUseGPSField + " INTEGER, " 
			+ Worker.ActualDateField + " INTEGER " 
			+ ");";

	private static final String TOURS_TABLE_CREATE = 
			"CREATE TABLE " + TourManager.TableName + "(" 
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ BaseObject.NameField + " TEXT NOT NULL, " 
			+ BaseObject.CheckSumField + " INTEGER, "
			+ BaseObject.WasSentField + " INTEGER, "
			+ BaseObject.IsServerTimeField + " INTEGER, "
			+ Tour.IsCommonTourField + " INTEGER "
			+ ");";

	private static final String PATIENTS_TABLE_CREATE = 
			"CREATE TABLE " + PatientManager.TableName + "(" 
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ BaseObject.NameField + " TEXT NOT NULL, "
			+ BaseObject.CheckSumField + " INTEGER, "
			+ BaseObject.WasSentField + " INTEGER, "
			+ BaseObject.IsServerTimeField + " INTEGER, "
			+ Patient.SurnameField + " TEXT, "
			+ Patient.IsAdditionalField + " INTEGER, "
			+ Patient.AddressIDField + " INTEGER, "
			+ Patient.SexField + " TEXT, "
			+ Patient.DoctorIDsField + " TEXT, "
			+ Patient.RelativeIDsField + " TEXT, "
			+ Patient.CatalogKKTypeField + " INTEGER, "
			+ Patient.CatalogPKTypeField + " INTEGER, "
			+ Patient.CatalogSATypeField + " INTEGER, "
			+ Patient.CatalogPRTypeField + " INTEGER "
			+ ");";

	private static final String OPTIONS_TABLE_CREATE = 
			"CREATE TABLE " + Option.TableName + "("
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Option.WorkerIDField + " INTEGER, " 
			+ Option.TourIDField + " INTEGER, "
			+ Option.PatientIDField + " INTEGER, "
			+ Option.ServerIPField + " TEXT, "
			+ Option.ServerPortField + " INTEGER, " 
			+ Option.IsAutoField + " INTEGER "
			+ ");";

	private static final String TASKS_TABLE_CREATE = 
			"CREATE TABLE " + TaskManager.TableName + "("
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ BaseObject.NameField + " TEXT, "
			+ BaseObject.CheckSumField + " INTEGER, "
			+ BaseObject.WasSentField + " INTEGER, "
			+ BaseObject.IsServerTimeField + " INTEGER, "
			+ Task.WorkerIDField + " INTEGER, "
			+ Task.PatientIDField + " INTEGER, "
			+ Task.StateField + " INTEGER NOT NULL DEFAULT 0, "
			+ Task.PlanDateField + " INTEGER, "
			+ Task.LeistungsField + " TEXT, "
			+ Task.MinutePriceField + " INTEGER, "
			+ Task.TourIDField + " INTEGER, "
			+ Task.IsAdditionalTaskField + " INTEGER, "
			+ Task.AdditionalTaskIDField + " INTEGER, "
			+ Task.EmploymentIDField + " INTEGER, "
			+ Task.RealDateField + " INTEGER, "
			+ Task.ManualDateField + " INTEGER "
			+ ");";
	
	private static final String ADDITIONAL_TASKS_TABLE_CREATE = 
			"CREATE TABLE " + AdditionalTaskManager.TableName + "("
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ BaseObject.NameField + " TEXT, "
			+ BaseObject.WasSentField + " INTEGER, "
			+ BaseObject.IsServerTimeField + " INTEGER, "
			+ BaseObject.CheckSumField + " INTEGER, "
			+ AdditionalTask.CatalogTypeField + " INTEGER, "
			+ AdditionalTask.QualityField + " INTEGER "
			+ ");";
	
	public static final String ADDITIONAL_WORKS_TABLE_CREATE = 
			"CREATE TABLE " + AdditionalWorkManager.TableName + "("
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ BaseObject.WasSentField + " INTEGER, "
			+ BaseObject.IsServerTimeField + " INTEGER, "
			+ BaseObject.NameField + " TEXT, "
			+ BaseObject.CheckSumField + " INTEGER "
			+ ");";

	public static final String DIAGNOSES_TABLE_CREATE = 
			"CREATE TABLE " + DiagnoseManager.TableName + "("
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ BaseObject.WasSentField + " INTEGER, "
			+ BaseObject.IsServerTimeField + " INTEGER, "
			+ BaseObject.NameField + " TEXT, "
			+ BaseObject.CheckSumField + " INTEGER "
			+ ");";
	
	public static final String ADDRESS_TABLE_CREATE = 
			"CREATE TABLE " + AddressManager.TableName + "("
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Address.StreetField + " TEXT, "
			+ Address.ZipField + " TEXT, "
			+ Address.CityField + " TEXT, "
			+ Address.PhoneField + " TEXT "
			+ ");";
	
	private static final String DOCTORS_TABLE_CREATE = 
			"CREATE TABLE " + DoctorManager.TableName + "(" 
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ BaseObject.NameField + " TEXT NOT NULL, "
			+ BaseObject.CheckSumField + " INTEGER, "
			+ BaseObject.WasSentField + " INTEGER, "
			+ BaseObject.IsServerTimeField + " INTEGER, "
			+ Doctor.SurnameField + " TEXT, "  
			+ Doctor.AddressIDField + " INTEGER "
			+ ");";

	private static final String INFORMATIONS_TABLE_CREATE = 
			"CREATE TABLE " + InformationManager.TableName + "(" 
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ BaseObject.NameField + " TEXT NOT NULL, "
			+ BaseObject.CheckSumField + " INTEGER, "
			+ BaseObject.WasSentField + " INTEGER, "
			+ BaseObject.IsServerTimeField + " INTEGER, "
			+ Information.EmploymentCodeField + " INTEGER, "  
			+ Information.FromDateField + " INTEGER, "  
			+ Information.TilldateField + " INTEGER, "  
			+ Information.ReadTimeField + " INTEGER, "  
			+ Information.IsFromServerField + " INTEGER "  
			+ ");";
	
	private static final String PATIENTS_REMARK_TABLE_CREATE = 
			"CREATE TABLE " + PatientRemarkManager.TableName + "(" 
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ BaseObject.NameField + " TEXT NOT NULL, "
			+ BaseObject.CheckSumField + " INTEGER, "
			+ BaseObject.WasSentField + " INTEGER, "
			+ BaseObject.IsServerTimeField + " INTEGER "
			+ ");";
	
	private static final String RELATIVES_TABLE_CREATE = 	
			"CREATE TABLE " + RelativeManager.TableName + "(" 
			+ BaseObject.IDField + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ BaseObject.NameField + " TEXT NOT NULL, "
			+ BaseObject.CheckSumField + " INTEGER, "
			+ BaseObject.WasSentField + " INTEGER, "
			+ BaseObject.IsServerTimeField + " INTEGER, "
			+ Relative.SurnameField + " TEXT, "
			+ Relative.ShipField + " TEXT, "
			+ Relative.AddressIDField + " INTEGER "
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
		
	private static String[] createDataTables = new String[]{
		TOURS_TABLE_CREATE,
		PATIENTS_TABLE_CREATE,
		TASKS_TABLE_CREATE,
		DIAGNOSES_TABLE_CREATE,
		ADDRESS_TABLE_CREATE,
		DOCTORS_TABLE_CREATE,
		INFORMATIONS_TABLE_CREATE,
		PATIENTS_REMARK_TABLE_CREATE,
		RELATIVES_TABLE_CREATE,		
		
		WORKERS_TABLE_CREATE,
		ADDITIONAL_TASKS_TABLE_CREATE,
		ADDITIONAL_WORKS_TABLE_CREATE		
	};
		
	private static String[] deleteDataTables = new String[]{		
		"DROP TABLE IF EXISTS " + TourManager.TableName,
		"DROP TABLE IF EXISTS " + PatientManager.TableName,
		"DROP TABLE IF EXISTS " + TaskManager.TableName,
		"DROP TABLE IF EXISTS " + DiagnoseManager.TableName,
		"DROP TABLE IF EXISTS " + AddressManager.TableName,
		"DROP TABLE IF EXISTS " + DoctorManager.TableName,
		"DROP TABLE IF EXISTS " + InformationManager.TableName,
		"DROP TABLE IF EXISTS " + PatientRemarkManager.TableName,
		"DROP TABLE IF EXISTS " + RelativeManager.TableName,
		
		"DROP TABLE IF EXISTS " + WorkerManager.TableName,
		"DROP TABLE IF EXISTS " + AdditionalTaskManager.TableName,
		"DROP TABLE IF EXISTS " + AdditionalWorkManager.TableName
	};
			
	private static String[] clearWorkerDependedDataTables = new String[]{
		"DELETE FROM " + TourManager.TableName,
		"DELETE FROM " + PatientManager.TableName,
		"DELETE FROM " + TaskManager.TableName,
		"DELETE FROM " + DiagnoseManager.TableName,
		"DELETE FROM " + AddressManager.TableName,
		"DELETE FROM " + DoctorManager.TableName,
		"DELETE FROM " + InformationManager.TableName,
		"DELETE FROM " + PatientRemarkManager.TableName,
		"DELETE FROM " + RelativeManager.TableName,	
	};
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(OPTIONS_TABLE_CREATE);
			for(String createTable : createDataTables)
				db.execSQL(createTable);
		} catch(Exception e){
			e.printStackTrace();
		}
		finally{
		}
	}

	public boolean clearAllData(){
		boolean retVal = true;	
				for(String deleteTable : deleteDataTables){
			try{
				Instance().getReadableDatabase().execSQL(deleteTable);
			}catch(Exception ex){
				retVal = false;
				ex.printStackTrace();
				continue;
			}
		}		
		for(String createTable : createDataTables){
			try{
				Instance().getReadableDatabase().execSQL(createTable);
			}catch(Exception ex){
				retVal = false;
				ex.printStackTrace();
				continue;
			}
		}		
		try{
			if (DataBaseWrapper.Instance().getReadableDatabase() != null)
				DataBaseWrapper.Instance().getReadableDatabase().close();
			DataBaseWrapper.Instance().close();
		}catch(Exception ex){
			ex.printStackTrace();
			retVal = false;
		}
		
		return retVal;	
	}
	
	public boolean clearWorkerData(){
		boolean retVal = true;
		try{			
			for(String clearTable : clearWorkerDependedDataTables)
				Instance().getReadableDatabase().execSQL(clearTable);						
		}catch(Exception ex){
			ex.printStackTrace();
			retVal = false;
		}
		finally{
			if (DataBaseWrapper.Instance().getReadableDatabase() != null)
				DataBaseWrapper.Instance().getReadableDatabase().close();
			DataBaseWrapper.Instance().close();
		}
		return retVal;
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
