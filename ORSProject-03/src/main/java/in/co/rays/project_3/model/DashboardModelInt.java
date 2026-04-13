package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.DashboardDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface DashboardModelInt {
	
	public void add (DashboardDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update (DashboardDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(DashboardDTO dto) throws ApplicationException;
	
	public DashboardDTO findbypk(long pk) throws ApplicationException;
	
	public DashboardDTO findbyname(String dashboardName) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(DashboardDTO dto, int pageNo,int pageSize) throws ApplicationException;

}
