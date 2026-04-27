package in.co.rays.project_3.dto;

public class GeoFenceDTO extends BaseDTO
{
	
//	private Long id;
	private String geoFenceCode;
	private String locationName;
	private String radius;
	private String status;
	
	
	public String getGeoFenceCode() {
		return geoFenceCode;
	}
	public void setGeoFenceCode(String geoFenceCode) {
		this.geoFenceCode = geoFenceCode;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getRadius() {
		return radius;
	}
	public void setRadius(String radius) {
		this.radius = radius;
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
		
		return geoFenceCode;
	}
	
	

}
