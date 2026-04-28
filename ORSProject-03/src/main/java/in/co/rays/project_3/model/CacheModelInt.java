package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.CacheDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface CacheModelInt {
	
	public void add(CacheDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(CacheDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(CacheDTO dto) throws ApplicationException;
	
	public CacheDTO findByPk(long pk) throws ApplicationException;
	
	public CacheDTO findByName(String keyName) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(CacheDTO dto, int pageNo, int pageSize) throws ApplicationException;

}
