package in.co.rays.project_3.dto;

public class LocationDTO extends BaseDTO{
	
//    private Long id;
    private String city;
    private String state;
    private String country;
    private String locationstatus;
    
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getLocationstatus() {
		return locationstatus;
	}
	public void setLocationstatus(String locationstatus) {
		this.locationstatus = locationstatus;
	}
	
	
	@Override
	public String getKey() {
		
		return id + "";
	}
	@Override
	public String getValue() {
		
		return locationstatus;
	} 

}
