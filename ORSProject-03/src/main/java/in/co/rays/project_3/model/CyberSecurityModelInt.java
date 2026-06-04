package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.CyberSecurityDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface CyberSecurityModelInt {
	
	public void add (CyberSecurityDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(CyberSecurityDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(CyberSecurityDTO dto)throws ApplicationException;
	
	public CyberSecurityDTO findByPk(long pk) throws ApplicationException;
	
	public CyberSecurityDTO findByName(String threatType) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(CyberSecurityDTO dto, int pageNo, int pageSize) throws ApplicationException;

}
