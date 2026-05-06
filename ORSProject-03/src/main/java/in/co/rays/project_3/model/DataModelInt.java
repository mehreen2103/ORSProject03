package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.DataDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface DataModelInt {
	
	
	public void add(DataDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(DataDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(DataDTO dto) throws ApplicationException;
	
	public DataDTO findByPk(long pk) throws ApplicationException;
	
	public DataDTO findByName(String mappingCode) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(DataDTO dto, int pageNo, int pageSize) throws ApplicationException;

}
