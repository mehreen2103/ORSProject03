package in.co.rays.project_3.dto;

public class PasswordDTO extends BaseDTO{
	
//	private Long id;
	private String passwordCode;
	private String username;
	private String passwordValue;
	private String status;
	
	public String getPasswordCode() {
		return passwordCode;
	}
	public void setPasswordCode(String passwordCode) {
		this.passwordCode = passwordCode;
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public String getPasswordValue() {
		return passwordValue;
	}
	public void setPasswordValue(String passwordValue) {
		this.passwordValue = passwordValue;
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
		
		return status;
	}
	
	

}
