package in.co.rays.project_3.dto;

public class DeviceDTO extends BaseDTO {
	
//	private Long id;
	private String deviceSessionCode;
	private String deviceName;
	private String userName;
	private String status;
	
 
	public String getDeviceSessionCode() {
		return deviceSessionCode;
	}
	public void setDeviceSessionCode(String deviceSessionCode) {
		this.deviceSessionCode = deviceSessionCode;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
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
		
		return deviceName;
	}
	
	

}
