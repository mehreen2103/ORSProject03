package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.CourierDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface CourierModelInt {
	
	public void add(CourierDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(CourierDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(CourierDTO dto) throws ApplicationException;
	
	public CourierDTO findByPk(long pk)throws ApplicationException;
	
	public CourierDTO findByName(String senderName)throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(CourierDTO dto, int pageNo, int pageSize)throws ApplicationException;
	
	

}
