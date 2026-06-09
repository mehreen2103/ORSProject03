package in.co.rays.project_3.dto;

public class SmartDeviceDTO extends BaseDTO {
	
//	private Long id;
	private String deviceName;
	private String room;
	private String status;
	private String doubleUsage;
	
	
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDoubleUsage() {
		return doubleUsage;
	}
	public void setDoubleUsage(String doubleUsage) {
		this.doubleUsage = doubleUsage;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id + "";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return deviceName;
	}


}
