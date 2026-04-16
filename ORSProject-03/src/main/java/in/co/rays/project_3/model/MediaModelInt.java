package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.MediaDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface MediaModelInt {
	
	public void add(MediaDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(MediaDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(MediaDTO dto) throws ApplicationException;
	
	public MediaDTO findByPk(long pk) throws ApplicationException;
	
	public MediaDTO fingByName(String mediaName) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(MediaDTO dto, int pageNo, int pageSize) throws ApplicationException;

}
