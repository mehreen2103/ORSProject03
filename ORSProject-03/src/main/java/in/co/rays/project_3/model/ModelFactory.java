package in.co.rays.project_3.model;

import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * ModelFactory decides which model implementation run
 * 
 * @author mehre
 *
 */
public final class ModelFactory {

	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.project_3.bundle.system");
	private static final String DATABASE = rb.getString("DATABASE");
	private static ModelFactory mFactory = null;
	private static HashMap modelCache = new HashMap();

	private ModelFactory() {

	}

	public static ModelFactory getInstance() {
		if (mFactory == null) {
			mFactory = new ModelFactory();
		}
		return mFactory;
	}



	public MarksheetModelInt getMarksheetModel() {
		MarksheetModelInt marksheetModel = (MarksheetModelInt) modelCache.get("marksheetModel");
		if (marksheetModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				marksheetModel = new MarksheetModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				marksheetModel = new MarksheetModelJDBCImpl();
			}
			modelCache.put("marksheetModel", marksheetModel);
		}
		return marksheetModel;
	}

	public CollegeModelInt getCollegeModel() {
		CollegeModelInt collegeModel = (CollegeModelInt) modelCache.get("collegeModel");
		if (collegeModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				collegeModel = new CollegeModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				collegeModel = new CollegeModelJDBCImpl();
			}
			modelCache.put("collegeModel", collegeModel);
		}
		return collegeModel;
	}

	public RoleModelInt getRoleModel() {
		RoleModelInt roleModel = (RoleModelInt) modelCache.get("roleModel");
		if (roleModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				roleModel = new RoleModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				roleModel = new RoleModelJDBCImpl();
			}
			modelCache.put("roleModel", roleModel);
		}
		return roleModel;
	}

	public UserModelInt getUserModel() {

		UserModelInt userModel = (UserModelInt) modelCache.get("userModel");
		if (userModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				userModel = new UserModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				userModel = new UserModelJDBCImpl();
			}
			modelCache.put("userModel", userModel);
		}

		return userModel;
	}

	public StudentModelInt getStudentModel() {
		StudentModelInt studentModel = (StudentModelInt) modelCache.get("studentModel");
		if (studentModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				studentModel = new StudentModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				studentModel = new StudentModelJDBCImpl();
			}
			modelCache.put("studentModel", studentModel);
		}

		return studentModel;
	}

	public CourseModelInt getCourseModel() {
		CourseModelInt courseModel = (CourseModelInt) modelCache.get("courseModel");
		if (courseModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				courseModel = new CourseModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				courseModel = new CourseModelJDBCImpl();
			}
			modelCache.put("courseModel", courseModel);
		}

		return courseModel;
	}

	public TimetableModelInt getTimetableModel() {

		TimetableModelInt timetableModel = (TimetableModelInt) modelCache.get("timetableModel");

		if (timetableModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				timetableModel = new TimetableModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				timetableModel = new TimetableModelJDBCImpl();
			}
			modelCache.put("timetableModel", timetableModel);
		}

		return timetableModel;
	}

	public SubjectModelInt getSubjectModel() {
		SubjectModelInt subjectModel = (SubjectModelInt) modelCache.get("subjectModel");
		if (subjectModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				subjectModel = new SubjectModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				subjectModel = new SubjectModelJDBCImpl();
			}
			modelCache.put("subjectModel", subjectModel);
		}

		return subjectModel;
	}

	public FacultyModelInt getFacultyModel() {
		FacultyModelInt facultyModel = (FacultyModelInt) modelCache.get("facultyModel");
		if (facultyModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				facultyModel = new FacultyModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				facultyModel = new FacultyModelJDBCImpl();
			}
			modelCache.put("facultyModel", facultyModel);
		}

		return facultyModel;
	}
	
	
	//usecases
	
	public WatchlistModelInt getWatchlistModel() {
		WatchlistModelInt watchlistModel = (WatchlistModelInt) modelCache.get("watchlistModel");
		if (watchlistModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				watchlistModel = new WatchlistModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
			}
		}
		return watchlistModel;
	}
	
	public EventModelInt getEventModel() {
		EventModelInt eventModel = (EventModelInt) modelCache.get("eventModel");
		if (eventModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				eventModel = new EventModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
			}
		}
		return eventModel;
	}
	
	public PaymentModelInt getPaymentModel() {
		PaymentModelInt paymentModel = (PaymentModelInt) modelCache.get("paymentModel");
		if (paymentModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				paymentModel = new PaymentModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
			}
		}
		return paymentModel;
	}
	
	public LocationModelInt getLocationModel() {
		LocationModelInt locationModel = (LocationModelInt) modelCache.get("locationModel");
		if (locationModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				locationModel = new LocationModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
			}
		}
		return locationModel;
	}
	
	public ProfileModelInt getProfileModel() {
		ProfileModelInt ProfileModel = (ProfileModelInt) modelCache.get("ProfileModel");
		if (ProfileModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				ProfileModel = new ProfileModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
			}
		}
		return ProfileModel;
	}
	
	public InquiryModelInt getInquiryModel() {

		InquiryModelInt inquiryModel = (InquiryModelInt) modelCache.get("inquiryModel");
		if (inquiryModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				inquiryModel = new InquiryModelHibImp();
			}
			modelCache.put("inquiryModel", inquiryModel);
		}
		return inquiryModel;
	}
	
	public TaskModelInt getTaskModel() {
	    TaskModelInt taskModel =
	            (TaskModelInt) modelCache.get("taskModel");

	    if (taskModel == null) {

	        if ("Hibernate".equals(DATABASE)) {
	            taskModel = new TaskModelHibImpl();
	        }

	        if ("JDBC".equals(DATABASE)) {
	           
	        }

	        modelCache.put("taskModel", taskModel);
	    }

	    return taskModel;
	}
	
	public MaintenanceModelInt getMaintenanceModel() {

	    MaintenanceModelInt maintenanceModel =
	            (MaintenanceModelInt) modelCache.get("maintenanceModel");

	    if (maintenanceModel == null) {

	        if ("Hibernate".equals(DATABASE)) {
	            maintenanceModel = new MaintenanceModelHibImpl();
	        }

	        if ("JDBC".equals(DATABASE)) {
	        }

	        modelCache.put("maintenanceModel", maintenanceModel);
	    }

	    return maintenanceModel;
	}
	
	public ClientModelInt getClientModel() {
		ClientModelInt clientModel = (ClientModelInt) modelCache.get("clientModel");

		if (clientModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				clientModel = new ClientModelHibImpl();
			}

			if ("JDBC".equals(DATABASE)) {
			}
		}
		return clientModel;
	}
	
	public BrokerModelInt getBrokeModel() {
		BrokerModelInt brokerModel = (BrokerModelInt) modelCache.get("brokerModel");
		
		if (brokerModel == null) {
			
			if ("Hibernate".equals(DATABASE)) {
				brokerModel = new BrokerModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
			}
		}
		return brokerModel;
		
	}
	public PhotographerModelInt getPhotographerModel() {

	    PhotographerModelInt photographerModel = (PhotographerModelInt) modelCache.get("photographerModel");

	    if (photographerModel == null) {

	        if ("Hibernate".equals(DATABASE)) {
	            photographerModel = new PhotographerModelHibImpl();
	        }

	        if ("JDBC".equals(DATABASE)) {
	            // yaha tum JDBC implementation de sakti ho future me
	            // photographerModel = new PhotographerModelJDBCImpl();
	        }

	        modelCache.put("photographerModel", photographerModel);
	    }

	    return photographerModel;
	}
	
	public DashboardModelInt getDashboardModel() {
		
	DashboardModelInt dashboardModel =(DashboardModelInt) modelCache.get("dashboardModel");
	
	if (dashboardModel == null) {
		
		
		if ("Hibernate".equals(DATABASE)) {
			dashboardModel = new DashboardModelHibImpl();
		}
		if ("JDBC".equals(DATABASE)) {
			
		}
		modelCache.put("dashboardModel", dashboardModel);
		
	}
	return dashboardModel;
	}
	
	public BuildModelInt getBuildModel() {
		
		BuildModelInt BuildModel =(BuildModelInt) modelCache.get("BuildModel");
		
		if (BuildModel == null) {
			
			
			if ("Hibernate".equals(DATABASE)) {
				BuildModel = new BuildModelHibIml();
			}
			if ("JDBC".equals(DATABASE)) {
				
			}
			modelCache.put("BuildModel", BuildModel);
			
		}
		return BuildModel;
		}
	
}
