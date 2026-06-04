package in.co.rays.project_3.dto;

import java.util.Date;

public class CyberSecurityDTO extends BaseDTO{

//	private Long id;
	private String threatType;
	private String severity;
	private Date detectedTime;
	private String status;
	
	
	public String getThreatType() {
		return threatType;
	}
	public void setThreatType(String threatType) {
		this.threatType = threatType;
	}
	
	
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	
	
	public Date getDetectedTime() {
		return detectedTime;
	}
	public void setDetectedTime(Date detectedTime) {
		this.detectedTime = detectedTime;
	}
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id + "";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return threatType;
	}
	
	

}
