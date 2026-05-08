package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.HostelDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface HostelModelInt {
	
	public void add(HostelDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(HostelDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(HostelDTO dto) throws ApplicationException;
	
	public HostelDTO findByPk(long pk) throws ApplicationException;
	
	public HostelDTO findByRoom(String roomNo) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(HostelDTO dto , int pageNO, int pageSize) throws ApplicationException;
	

}
