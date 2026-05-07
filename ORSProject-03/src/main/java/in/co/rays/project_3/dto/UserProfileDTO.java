package in.co.rays.project_3.dto;

public class UserProfileDTO extends BaseDTO{
	
//	private Long id;
	private String profileCode;
	private String userName;
	private String mobileNo;
	private String status;
	
	public String getProfileCode() {
		return profileCode;
	}
	public void setProfileCode(String profileCode) {
		this.profileCode = profileCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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
	
		return userName;
	}
	
	

}
