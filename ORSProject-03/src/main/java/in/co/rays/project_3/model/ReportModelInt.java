package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.ReportDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface ReportModelInt {
	
	public void add(ReportDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(ReportDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete (ReportDTO dto) throws ApplicationException;
	
	public ReportDTO findByPk(long pk) throws ApplicationException;
	
	public ReportDTO findByName(String reportType) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search (ReportDTO dto , int pageNo, int pageSize)throws ApplicationException;
}
