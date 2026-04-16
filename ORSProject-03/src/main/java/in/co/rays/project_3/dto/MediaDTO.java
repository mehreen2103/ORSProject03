package in.co.rays.project_3.dto;

import java.util.Date;

public class MediaDTO extends BaseDTO{
	
//	private Long id;
	private String mediaName;
	private Date coverageDate;
	private String reporter;
	
	public String getMediaName() {
		return mediaName;
	}
	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	
	public Date getCoverageDate() {
		return coverageDate;
	}
	public void setCoverageDate(Date coverageDate) {
		this.coverageDate = coverageDate;
	}
	
	public String getReporter() {
		return reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	
	
	@Override
	public String getKey() {
		
		return id + "";
	}
	@Override
	public String getValue() {
		
		return mediaName;
	}
	
	
	

}
