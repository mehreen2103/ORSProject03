package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.BuildDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface BuildModelInt {
	
	public void add(BuildDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(BuildDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(BuildDTO dto) throws ApplicationException;
	
	
	public BuildDTO findbypk (long pk) throws ApplicationException;
	
	public BuildDTO findbyname(String build) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(BuildDTO dto , int pageNo, int pageSize) throws ApplicationException;
	

}
