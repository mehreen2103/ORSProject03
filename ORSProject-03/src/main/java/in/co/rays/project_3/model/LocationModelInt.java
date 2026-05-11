package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.LocationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface LocationModelInt {

	public void add(LocationDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(LocationDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(LocationDTO dto) throws ApplicationException;
	
	public LocationDTO FindByPK(long pk) throws ApplicationException;
	
	public LocationDTO findByName(String state) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(LocationDTO dto, int pageNo, int pageSize) throws ApplicationException;
}
