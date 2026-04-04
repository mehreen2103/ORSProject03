package in.co.rays.project_3.dto;

public class PhotographerDTO extends BaseDTO {
	
//	private Long Id;
	private String photographerName;
	private String eventType;
	private Double charges;
	
	public String getPhotographerName() {
		return photographerName;
	}
	public void setPhotographerName(String photographerName) {
		this.photographerName = photographerName;
	}
	
	
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	
	public Double getCharges() {
		return charges;
	}
	public void setCharges(Double charges) {
		this.charges = charges;
	}
	@Override
	public String getKey() {
		
		return id + "";
	}
	@Override
	public String getValue() {
		
		return photographerName;
	}

}
