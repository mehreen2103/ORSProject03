package in.co.rays.project_3.dto;

public class DataImportDTO extends BaseDTO{
	
//	private Long id;
	private String importLogCode;
	private String fileName;
	private String importedBy;
	private String status;
	
	
	public String getImportLogCode() {
		return importLogCode;
	}
	public void setImportLogCode(String importLogCode) {
		this.importLogCode = importLogCode;
	}
	
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	public String getImportedBy() {
		return importedBy;
	}
	public void setImportedBy(String importedBy) {
		this.importedBy = importedBy;
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
		
		return fileName;
	}
	
	

}
