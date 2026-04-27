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
	
	public MediaModelInt getMediaModel() {
		
		MediaModelInt MediaModel =(MediaModelInt) modelCache.get("MediaModel");
		
		if (MediaModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				MediaModel = new MediaModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				
			}
			modelCache.put("MediaModel", MediaModel);
		}
		return MediaModel;
	}
	
   public DataImportModelInt getDataModel() {
	   
	   DataImportModelInt DataModel = (DataImportModelInt) modelCache.get("DataModel");
	   
	   if (DataModel == null) {
		if ("Hibernate".equals(DATABASE)) {
			DataModel = new DataImportModelHibImpl();
		}
		if ("JDBC".equals(DATABASE)) {
			
		}
		modelCache.put("DataModel", DataModel);
	}
	return DataModel;
   }
   
   public DeviceModelInt getDeviceModel() {
	   
	   DeviceModelInt DeviceModel =(DeviceModelInt) modelCache.get("DeviceModel");
	   
	   if (DeviceModel == null) {
		if ("Hibernate".equals(DATABASE)) {
			DeviceModel = new DeviceModelHibImpl();
		}
		if ("JDBC".equals(DATABASE)) {
			
		}
		modelCache.put("DeviceModel", DeviceModel);
	}
	return DeviceModel;
   }
   
   public BroadcastModelInt getBroadcastModel() {
	   
	   BroadcastModelInt BroadcastModel =(BroadcastModelInt) modelCache.get("BroadcastModel"); 
	   
	   if (BroadcastModel == null) {
		   if ("Hibernate".equals(DATABASE)) {
			   BroadcastModel = new BroadcastModelHibImpl();
		}
		   if ("JDBC".equals(DATABASE)) {
			
		}
		   modelCache.put("BroadcastModel", BroadcastModel);
		
	}
	return BroadcastModel;
   }
   
   public GeoFenceModelInt getGeoFenceModel() {
	   
	   GeoFenceModelInt GeoFenceModel =(GeoFenceModelInt) modelCache.get("GeoFenceModel");
	   
	   if (GeoFenceModel == null) {
		   if ("Hibernate".equals(DATABASE)) {
			GeoFenceModel = new GeoFenceHibImpl();
		}
		   if ("JDBC".equals(DATABASE)) {
			
		}
		   modelCache.put("GeoFenceModel", GeoFenceModel);
		
	}
	return GeoFenceModel;
   }
		
}
