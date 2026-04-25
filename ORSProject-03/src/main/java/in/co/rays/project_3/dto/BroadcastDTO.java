package in.co.rays.project_3.dto;

public class BroadcastDTO extends BaseDTO {
	
//	private Long id;
	private String broadcastCode;
	private String message;
	private String sentBy;
	private String status;
	

	
	public String getBroadcastCode() {
		return broadcastCode;
	}
	public void setBroadcastCode(String broadcastCode) {
		this.broadcastCode = broadcastCode;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSentBy() {
		return sentBy;
	}
	public void setSentBy(String sentBy) {
		this.sentBy = sentBy;
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
     
		return broadcastCode;
	}
	
	

}
