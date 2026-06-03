package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.DispatchDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface DispatchModelInt {
	
	public void add (DispatchDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(DispatchDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(DispatchDTO dto) throws ApplicationException;
	
	public DispatchDTO findByPk(long  pk) throws ApplicationException;
	
	public DispatchDTO findByName(String  courierName) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(DispatchDTO dto, int pageNo, int pageSize)throws ApplicationException;

}
