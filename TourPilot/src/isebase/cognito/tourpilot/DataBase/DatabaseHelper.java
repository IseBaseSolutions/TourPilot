package isebase.cognito.tourpilot.DataBase;

import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTask;
import isebase.cognito.tourpilot.Data.AdditionalTask.AdditionalTaskDAO;
import isebase.cognito.tourpilot.Data.AdditionalWork.AdditionalWork;
import isebase.cognito.tourpilot.Data.AdditionalWork.AdditionalWorkDAO;
import isebase.cognito.tourpilot.Data.Address.Address;
import isebase.cognito.tourpilot.Data.Address.AddressDAO;
import isebase.cognito.tourpilot.Data.Answer.Answer;
import isebase.cognito.tourpilot.Data.Answer.AnswerDAO;
import isebase.cognito.tourpilot.Data.AnsweredCategory.AnsweredCategory;
import isebase.cognito.tourpilot.Data.AnsweredCategory.AnsweredCategoryDAO;
import isebase.cognito.tourpilot.Data.Category.Category;
import isebase.cognito.tourpilot.Data.Category.CategoryDAO;
import isebase.cognito.tourpilot.Data.CustomRemark.CustomRemark;
import isebase.cognito.tourpilot.Data.CustomRemark.CustomRemarkDAO;
import isebase.cognito.tourpilot.Data.Diagnose.Diagnose;
import isebase.cognito.tourpilot.Data.Diagnose.DiagnoseDAO;
import isebase.cognito.tourpilot.Data.Doctor.Doctor;
import isebase.cognito.tourpilot.Data.Doctor.DoctorDAO;
import isebase.cognito.tourpilot.Data.Employment.Employment;
import isebase.cognito.tourpilot.Data.Employment.EmploymentDAO;
import isebase.cognito.tourpilot.Data.EmploymentInterval.EmploymentInterval;
import isebase.cognito.tourpilot.Data.EmploymentInterval.EmploymentIntervalDAO;
import isebase.cognito.tourpilot.Data.EmploymentVerification.EmploymentVerification;
import isebase.cognito.tourpilot.Data.EmploymentVerification.EmploymentVerificationDAO;
import isebase.cognito.tourpilot.Data.ExtraCategory.ExtraCategory;
import isebase.cognito.tourpilot.Data.ExtraCategory.ExtraCategoryDAO;
import isebase.cognito.tourpilot.Data.Information.Information;
import isebase.cognito.tourpilot.Data.Information.InformationDAO;
import isebase.cognito.tourpilot.Data.Link.Link;
import isebase.cognito.tourpilot.Data.Link.LinkDAO;
import isebase.cognito.tourpilot.Data.Option.Option;
import isebase.cognito.tourpilot.Data.Option.OptionDAO;
import isebase.cognito.tourpilot.Data.Patient.Patient;
import isebase.cognito.tourpilot.Data.Patient.PatientDAO;
import isebase.cognito.tourpilot.Data.PatientRemark.PatientRemark;
import isebase.cognito.tourpilot.Data.PatientRemark.PatientRemarkDAO;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTour;
import isebase.cognito.tourpilot.Data.PilotTour.PilotTourDAO;
import isebase.cognito.tourpilot.Data.Question.Question;
import isebase.cognito.tourpilot.Data.Question.QuestionDAO;
import isebase.cognito.tourpilot.Data.QuestionSetting.QuestionSetting;
import isebase.cognito.tourpilot.Data.QuestionSetting.QuestionSettingDAO;
import isebase.cognito.tourpilot.Data.RelatedQuestionSetting.RelatedQuestionSetting;
import isebase.cognito.tourpilot.Data.RelatedQuestionSetting.RelatedQuestionSettingDAO;
import isebase.cognito.tourpilot.Data.Relative.Relative;
import isebase.cognito.tourpilot.Data.Relative.RelativeDAO;
import isebase.cognito.tourpilot.Data.Task.Task;
import isebase.cognito.tourpilot.Data.Task.TaskDAO;
import isebase.cognito.tourpilot.Data.Tour.Tour;
import isebase.cognito.tourpilot.Data.Tour.TourDAO;
import isebase.cognito.tourpilot.Data.UserRemark.UserRemark;
import isebase.cognito.tourpilot.Data.UserRemark.UserRemarkDAO;
import isebase.cognito.tourpilot.Data.WayPoint.WayPoint;
import isebase.cognito.tourpilot.Data.WayPoint.WayPointDAO;
import isebase.cognito.tourpilot.Data.Work.Work;
import isebase.cognito.tourpilot.Data.Work.WorkDAO;
import isebase.cognito.tourpilot.Data.Worker.Worker;
import isebase.cognito.tourpilot.Data.Worker.WorkerDAO;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
//import com.example.ormtest.DatabaseHelper;
//import com.example.ormtest.Goal;
//import com.example.ormtest.GoalDAO;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String TAG = DatabaseHelper.class.getSimpleName();

	// имя файла базы данных который будет храниться в
	// /data/data/APPNAME/DATABASE_NAME.db
	private static final String DATABASE_NAME = "TourPilot.db";

	// с каждым увеличением версии, при нахождении в устройстве БД с предыдущей
	// версией будет выполнен метод onUpgrade();
	private static final int DATABASE_VERSION = 23;

	// ссылки на DAO соответсвующие сущностям, хранимым в БД
	// private GoalDAO goalDao = null;
	private AdditionalTaskDAO additionalTaskDao = null;
	private AdditionalWorkDAO additionalWorkDao = null;
	private AddressDAO addressDao = null;
	private AnswerDAO answerDao = null;
	private AnsweredCategoryDAO answeredCategoryDAO = null;
	private CategoryDAO categoryDao = null;
	private CustomRemarkDAO customRemarkDao = null;
	private DiagnoseDAO diagnoseDao = null;
	private DoctorDAO doctorDao = null;
	private EmploymentDAO employmentDao = null;
	private EmploymentIntervalDAO employmentIntervalDao = null;
	private EmploymentVerificationDAO employmentVerificationDao = null;
	private ExtraCategoryDAO extraCategoryDAO = null;
	private InformationDAO informationDao = null;
	private LinkDAO linkDao = null;
	private OptionDAO optionDao = null;
	private PatientDAO patientDao = null;
	private PatientRemarkDAO patientRemarkDao = null;
	private PilotTourDAO pilotTourDao = null;
	private QuestionDAO questionDao = null;
	private QuestionSettingDAO questionSettingDao = null;
	private RelativeDAO relativeDao = null;
	private TaskDAO taskDao = null;
	private TourDAO tourDao = null;
	private UserRemarkDAO userRemarkDao = null;
	private WayPointDAO wayPointDao = null;
	private WorkDAO workDao = null;
	private WorkerDAO workerDao = null;
	private RelatedQuestionSettingDAO relatedQuestionSettingDao = null;
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		createDataTables();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource arg1, int oldVer,
			int newVer) {
//		try {
//			db.execSQL("ALTER TABLE " + HelperFactory.getHelper().getOptionDAO().getTableInfo().getTableName() + " ADD COLUMN " + Option.IS_LOCK_OPTIONS_FIELD + " INTEGER DEFAULT 0");		
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			db.execSQL("ALTER TABLE " + HelperFactory.getHelper().getUserRemarkDAO().getTableInfo().getTableName() + " ADD COLUMN " + UserRemark.CHECKED_IDS_FIELD + " TEXT");
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			TableUtils.createTable(connectionSource, CustomRemark.class);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			TableUtils.createTable(connectionSource, Question.class);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			TableUtils.createTable(connectionSource, Category.class);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			TableUtils.createTable(connectionSource, Link.class);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			TableUtils.createTable(connectionSource, QuestionSetting.class);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			TableUtils.createTable(connectionSource, Answer.class);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			TableUtils.createTable(connectionSource, AnsweredCategory.class);			
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			TableUtils.createTable(connectionSource, ExtraCategory.class);			
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			TableUtils.createTable(connectionSource, RelatedQuestionSetting.class);			
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			db.execSQL("ALTER TABLE " + HelperFactory.getHelper().getWorkerDAO().getTableInfo().getTableName() + " ADD COLUMN " + Worker.IS_ACTIVE_FIELD + " SMALLINT");
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			db.execSQL("ALTER TABLE " + HelperFactory.getHelper().getWorkerDAO().getTableInfo().getTableName() + " ADD COLUMN " + Worker.PHONE_FIELD + " VARCHAR");
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			db.execSQL("ALTER TABLE " + HelperFactory.getHelper().getWorkerDAO().getTableInfo().getTableName() + " ADD COLUMN " + Worker.PRIVATE_PHONE_FIELD + " VARCHAR");
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			db.execSQL("ALTER TABLE " + HelperFactory.getHelper().getWorkerDAO().getTableInfo().getTableName() + " ADD COLUMN " + Worker.MOBILE_PHONE_FIELD + " VARCHAR");
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			db.execSQL("ALTER TABLE " + HelperFactory.getHelper().getOptionDAO().getTableInfo().getTableName() + " ADD COLUMN " + Option.IS_WORKER_PHONES_FIELD + " SMALLINT");			
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			db.execSQL("ALTER TABLE " + HelperFactory.getHelper().getOptionDAO().getTableInfo().getTableName() + " ADD COLUMN " + Option.IS_TOUR_ACTIVITY_FIELD + " SMALLINT");			
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			db.execSQL("ALTER TABLE " + HelperFactory.getHelper().getUserRemarkDAO().getTableInfo().getTableName() + " ADD COLUMN " + UserRemark.CHECKED_IDS_FIELD + " VARCHAR");			
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			db.execSQL("ALTER TABLE " + HelperFactory.getHelper().getOptionDAO().getTableInfo().getTableName() + " ADD COLUMN " + Option.IS_TOUR_ACTIVITY_FIELD + " SMALLINT");
//			Option.Instance().setTourActivity(HelperFactory.getHelper().getTourDAO().load().size() > 0);
//			Option.Instance().save();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
		clearAllData();
	}
	
	public void createDataTables() {
		try {
			TableUtils.createTable(connectionSource, Work.class);
			TableUtils.createTable(connectionSource, Worker.class);
			TableUtils.createTable(connectionSource, AdditionalTask.class);
			TableUtils.createTable(connectionSource, Question.class);
			TableUtils.createTable(connectionSource, Category.class);
			TableUtils.createTable(connectionSource, Link.class);
			TableUtils.createTable(connectionSource, Employment.class);
			TableUtils.createTable(connectionSource, AdditionalWork.class);
			TableUtils.createTable(connectionSource, Address.class);
			TableUtils.createTable(connectionSource, Answer.class);
			TableUtils.createTable(connectionSource, AnsweredCategory.class);
			TableUtils.createTable(connectionSource, CustomRemark.class);
			TableUtils.createTable(connectionSource, Diagnose.class);
			TableUtils.createTable(connectionSource, Doctor.class);
			TableUtils.createTable(connectionSource, EmploymentInterval.class);
			TableUtils.createTable(connectionSource, EmploymentVerification.class);
			TableUtils.createTable(connectionSource, ExtraCategory.class);
			TableUtils.createTable(connectionSource, Information.class);
			TableUtils.createTable(connectionSource, Option.class);
			TableUtils.createTable(connectionSource, Patient.class);
			TableUtils.createTable(connectionSource, PatientRemark.class);
			TableUtils.createTable(connectionSource, PilotTour.class);
			TableUtils.createTable(connectionSource, QuestionSetting.class);
			TableUtils.createTable(connectionSource, Relative.class);
			TableUtils.createTable(connectionSource, Task.class);
			TableUtils.createTable(connectionSource, Tour.class);
			TableUtils.createTable(connectionSource, UserRemark.class);
			TableUtils.createTable(connectionSource, WayPoint.class);
			TableUtils.createTable(connectionSource, RelatedQuestionSetting.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteDataTables() {
		try {
			TableUtils.dropTable(connectionSource, Option.class, true);
			TableUtils.dropTable(connectionSource, Worker.class, true);
			TableUtils.dropTable(connectionSource, AdditionalTask.class, true);
			TableUtils.dropTable(connectionSource, Question.class, true);
			TableUtils.dropTable(connectionSource, Category.class, true);
			TableUtils.dropTable(connectionSource, Link.class, true);
			TableUtils.dropTable(connectionSource, Employment.class, true);
			TableUtils.dropTable(connectionSource, AdditionalWork.class, true);
			TableUtils.dropTable(connectionSource, Address.class, true);
			TableUtils.dropTable(connectionSource, Answer.class, true);
			TableUtils.dropTable(connectionSource, AnsweredCategory.class, true);
			TableUtils.dropTable(connectionSource, CustomRemark.class, true);
			TableUtils.dropTable(connectionSource, Diagnose.class, true);
			TableUtils.dropTable(connectionSource, Doctor.class, true);
			TableUtils.dropTable(connectionSource, EmploymentInterval.class, true);
			TableUtils.dropTable(connectionSource, EmploymentVerification.class, true);
			TableUtils.dropTable(connectionSource, ExtraCategory.class, true);
			TableUtils.dropTable(connectionSource, Information.class, true);
			TableUtils.dropTable(connectionSource, Patient.class, true);
			TableUtils.dropTable(connectionSource, PatientRemark.class, true);
			TableUtils.dropTable(connectionSource, PilotTour.class, true);
			TableUtils.dropTable(connectionSource, QuestionSetting.class, true);
			TableUtils.dropTable(connectionSource, Relative.class, true);
			TableUtils.dropTable(connectionSource, Task.class, true);
			TableUtils.dropTable(connectionSource, Tour.class, true);
			TableUtils.dropTable(connectionSource, UserRemark.class, true);
			TableUtils.dropTable(connectionSource, WayPoint.class, true);
			TableUtils.dropTable(connectionSource, Work.class, true);
			TableUtils.dropTable(connectionSource, RelatedQuestionSetting.class, true);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void clearAllData() {
		deleteDataTables();
		createDataTables();
	}
	
	public void clearWorkerData() {
		try {
			TableUtils.clearTable(connectionSource, Tour.class);
			TableUtils.clearTable(connectionSource, Patient.class);
			TableUtils.clearTable(connectionSource, Task.class);
			TableUtils.clearTable(connectionSource, Diagnose.class);
			TableUtils.clearTable(connectionSource, Address.class);
			TableUtils.clearTable(connectionSource, Doctor.class);
			TableUtils.clearTable(connectionSource, Information.class);
			TableUtils.clearTable(connectionSource, PatientRemark.class);
			TableUtils.clearTable(connectionSource, Relative.class);
			TableUtils.clearTable(connectionSource, PilotTour.class);
			TableUtils.clearTable(connectionSource, Employment.class);
			TableUtils.clearTable(connectionSource, Work.class);
			TableUtils.clearTable(connectionSource, EmploymentInterval.class);
			TableUtils.clearTable(connectionSource, UserRemark.class);
			TableUtils.clearTable(connectionSource, EmploymentVerification.class);
			TableUtils.clearTable(connectionSource, WayPoint.class);
			TableUtils.clearTable(connectionSource, QuestionSetting.class);
			TableUtils.clearTable(connectionSource, Answer.class);
			TableUtils.clearTable(connectionSource, AnsweredCategory.class);
			TableUtils.clearTable(connectionSource, ExtraCategory.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public AdditionalTaskDAO getAdditionalTaskDAO() {
		try {
			return additionalTaskDao == null ? additionalTaskDao = new AdditionalTaskDAO(getConnectionSource(), AdditionalTask.class) : additionalTaskDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public AdditionalWorkDAO getAdditionalWorkDAO() {
		try {
			return additionalWorkDao == null ? additionalWorkDao = new AdditionalWorkDAO(getConnectionSource(), AdditionalWork.class) : additionalWorkDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public AddressDAO getAddressDAO() {
		try {
			return addressDao == null ? addressDao = new AddressDAO(getConnectionSource(), Address.class) : addressDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public AnswerDAO getAnswerDAO() {
		try {
			return answerDao == null ? answerDao = new AnswerDAO(getConnectionSource(), Answer.class) : answerDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public AnsweredCategoryDAO getAnsweredCategoryDAO() {
		try {
			return answeredCategoryDAO == null ? answeredCategoryDAO = new AnsweredCategoryDAO(getConnectionSource(), AnsweredCategory.class) : answeredCategoryDAO;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public CategoryDAO getCategoryDAO() {
		try {
			return categoryDao == null ? categoryDao = new CategoryDAO(getConnectionSource(), Category.class) : categoryDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public CustomRemarkDAO getCustomRemarkDAO() {
		try {
			return customRemarkDao == null ? customRemarkDao = new CustomRemarkDAO(getConnectionSource(), CustomRemark.class) : customRemarkDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public DiagnoseDAO getDiagnoseDAO() {
		try {
			return diagnoseDao == null ? diagnoseDao = new DiagnoseDAO(getConnectionSource(), Diagnose.class) : diagnoseDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public DoctorDAO getDoctorODA() {
		try {
			return doctorDao == null ? doctorDao = new DoctorDAO(getConnectionSource(), Doctor.class) : doctorDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public EmploymentDAO getEmploymentDAO() {
		try {
			return employmentDao == null ? employmentDao = new EmploymentDAO(getConnectionSource(), Employment.class) : employmentDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public EmploymentIntervalDAO getEmploymentIntervalDAO() {
		try {
			return employmentIntervalDao == null ? employmentIntervalDao = new EmploymentIntervalDAO(getConnectionSource(), EmploymentInterval.class) : employmentIntervalDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public EmploymentVerificationDAO getEmploymentVerificationDAO() {
		try {
			return employmentVerificationDao == null ? employmentVerificationDao = new EmploymentVerificationDAO(getConnectionSource(), EmploymentVerification.class) : employmentVerificationDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ExtraCategoryDAO getExtraCategoryDAO() {
		try {
			return extraCategoryDAO == null ? extraCategoryDAO = new ExtraCategoryDAO(getConnectionSource(), ExtraCategory.class) : extraCategoryDAO;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public InformationDAO getInformationDAO() {
		try {
			return informationDao == null ? informationDao = new InformationDAO(getConnectionSource(), Information.class) : informationDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public LinkDAO getLinkDAO() {
		try {
			return linkDao == null ? linkDao = new LinkDAO(getConnectionSource(), Link.class) : linkDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public OptionDAO getOptionDAO() {
		try {
			return optionDao == null ? optionDao = new OptionDAO(getConnectionSource(), Option.class) : optionDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PatientDAO getPatientDAO() {
		try {
			return patientDao == null ? patientDao = new PatientDAO(getConnectionSource(), Patient.class) : patientDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PatientRemarkDAO getPatientRemarkDAO() {
		try {
			return patientRemarkDao == null ? patientRemarkDao = new PatientRemarkDAO(getConnectionSource(), PatientRemark.class) : patientRemarkDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PilotTourDAO getPilotTourDAO() {
		try {
			return pilotTourDao == null ? pilotTourDao = new PilotTourDAO(getConnectionSource(), PilotTour.class) : pilotTourDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public QuestionDAO getQuestionDAO() {
		try {
			return questionDao == null ? questionDao = new QuestionDAO(getConnectionSource(), Question.class) : questionDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public QuestionSettingDAO getQuestionSettingDAO() {
		try {
			return questionSettingDao == null ? questionSettingDao = new QuestionSettingDAO(getConnectionSource(), QuestionSetting.class) : questionSettingDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public RelativeDAO getRelativeDAO() {
		try {
			return relativeDao == null ? relativeDao = new RelativeDAO(getConnectionSource(), Relative.class) : relativeDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public TaskDAO getTaskDAO() {
		try {
			return taskDao == null ? taskDao = new TaskDAO(getConnectionSource(), Task.class) : taskDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public TourDAO getTourDAO() {
		try {
			return tourDao == null ? tourDao = new TourDAO(getConnectionSource(), Tour.class) : tourDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public UserRemarkDAO getUserRemarkDAO() {
		try {
			return userRemarkDao == null ? userRemarkDao = new UserRemarkDAO(getConnectionSource(), UserRemark.class) : userRemarkDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public WayPointDAO getWayPointDAO() {
		try {
			return wayPointDao == null ? wayPointDao = new WayPointDAO(getConnectionSource(), WayPoint.class) : wayPointDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public WorkDAO getWorkDAO() {
		try {
			return workDao == null ? workDao = new WorkDAO(getConnectionSource(), Work.class) : workDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public WorkerDAO getWorkerDAO() {
		try {
			return workerDao == null ? workerDao = new WorkerDAO(getConnectionSource(), Worker.class) : workerDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public RelatedQuestionSettingDAO getRelatedQuestionSettingDAO() {
		try {
			return relatedQuestionSettingDao == null ? relatedQuestionSettingDao = new RelatedQuestionSettingDAO(getConnectionSource(), RelatedQuestionSetting.class) : relatedQuestionSettingDao;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// выполняется при закрытии приложения
	@Override
	public void close() {
		super.close();
		additionalTaskDao = null;
		additionalWorkDao = null;
		addressDao = null;
		answerDao = null;
		answeredCategoryDAO = null;
		categoryDao = null;
		customRemarkDao = null;
		diagnoseDao = null;
		doctorDao = null;
		employmentDao = null;
		employmentIntervalDao = null;
		employmentVerificationDao = null;
		extraCategoryDAO = null;
		informationDao = null;
		linkDao = null;
		optionDao = null;
		patientDao = null;
		patientRemarkDao = null;
		questionDao = null;
		questionSettingDao = null;
		relativeDao = null;
		taskDao = null;
		tourDao = null;
		userRemarkDao = null;
		wayPointDao = null;
		workDao = null;
		workerDao = null;
		relatedQuestionSettingDao = null;
	}

}
