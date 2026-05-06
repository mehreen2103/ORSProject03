package in.co.rays.project_3.dto;

public class DataDTO extends BaseDTO{
	
//	private Long id;
	private String mappingCode;
	private String sourceField;
	private String targetField;
	private String status;
	
	public String getMappingCode() {
		return mappingCode;
	}
	public void setMappingCode(String mappingCode) {
		this.mappingCode = mappingCode;
	}
	public String getSourceField() {
		return sourceField;
	}
	public void setSourceField(String sourceField) {
		this.sourceField = sourceField;
	}
	public String getTargetField() {
		return targetField;
	}
	public void setTargetField(String targetField) {
		this.targetField = targetField;
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
		
		return mappingCode;
	}
	
	

}
