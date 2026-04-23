package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.DataImportDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface DataImportModelInt {
	
	public void add(DataImportDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(DataImportDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(DataImportDTO dto) throws ApplicationException;
	
	public DataImportDTO findByPk(long pk) throws ApplicationException;
	
	public DataImportDTO findByName(String fileName) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(DataImportDTO dto, int pageNo, int pageSize) throws ApplicationException;

	
}
