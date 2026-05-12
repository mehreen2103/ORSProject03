package in.co.rays.project_3.dto;

public class RegistrationDTO extends BaseDTO {
	
//	private Long id;
	private String registrationCode;
	private String firstName;
	private String lastName;
	private String status;
	
	
	public String getRegistrationCode() {
		return registrationCode;
	}
	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
