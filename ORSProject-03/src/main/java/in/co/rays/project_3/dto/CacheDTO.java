package in.co.rays.project_3.dto;

public class CacheDTO extends BaseDTO {
	
//	private Long id;
	private String cacheCode;
	private String keyName;
	private String value;
	private String status;
	
	public String getCacheCode() {
		return cacheCode;
	}
	public void setCacheCode(String cacheCode) {
		this.cacheCode = cacheCode;
	}
	
	
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
	
	

}
