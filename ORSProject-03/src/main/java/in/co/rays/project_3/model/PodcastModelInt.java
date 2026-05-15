package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.PodcastDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface PodcastModelInt {
	
	public void add(PodcastDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(PodcastDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(PodcastDTO dto) throws ApplicationException;
	
	public PodcastDTO findByPk(long pk) throws ApplicationException;
	
	public PodcastDTO findByCode(String podcastCode) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(PodcastDTO dto, int pageNo, int pageSize) throws ApplicationException;


}
