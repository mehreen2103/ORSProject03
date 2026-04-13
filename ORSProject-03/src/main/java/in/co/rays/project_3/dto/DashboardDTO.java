package in.co.rays.project_3.dto;

public class DashboardDTO extends BaseDTO{
	
//	protected long id;
	private String dashboardCode;
	private String dashboardName;
	private String userName;
	private String status;
		
	public String getDashboardCode() {
		return dashboardCode;
	}
	public void setDashboardCode(String dashboardCode) {
		this.dashboardCode = dashboardCode;
	}
	
	
	public String getDashboardName() {
		return dashboardName;
	}
	public void setDashboardName(String dashboardName) {
		this.dashboardName = dashboardName;
	}
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String getKey() {
		
		return id + "";
	}
	@Override
	public String getValue() {
		
		return dashboardName;
	}
	
	
	

}
