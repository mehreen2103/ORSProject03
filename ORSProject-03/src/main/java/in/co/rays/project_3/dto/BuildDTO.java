package in.co.rays.project_3.dto;

public class BuildDTO extends BaseDTO{
	
//	private Long id ;
	private String buildCode;
	private String buildVersion;
	private String triggeredBy;
	private String status;

	public String getBuildCode() {
		return buildCode;
	}
	public void setBuildCode(String buildCode) {
		this.buildCode = buildCode;
	}
	public String getBuildVersion() {
		return buildVersion;
	}
	public void setBuildVersion(String buildVersion) {
		this.buildVersion = buildVersion;
	}
	public String getTriggeredBy() {
		return triggeredBy;
	}
	public void setTriggeredBy(String triggeredBy) {
		this.triggeredBy = triggeredBy;
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
		
		return buildCode;
	}
	
	

}
