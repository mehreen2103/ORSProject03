package in.co.rays.project_3.dto;

public class PodcastDTO extends BaseDTO{
	
//	private Long id;
	private String podcastCode;
	private String podcastTitle;
	private String hostname;
	private String status;
	
	public String getPodcastCode() {
		return podcastCode;
	}
	public void setPodcastCode(String podcastCode) {
		this.podcastCode = podcastCode;
	}
	public String getPodcastTitle() {
		return podcastTitle;
	}
	public void setPodcastTitle(String podcastTitle) {
		this.podcastTitle = podcastTitle;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
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
		return status;
	}
	
	

}
