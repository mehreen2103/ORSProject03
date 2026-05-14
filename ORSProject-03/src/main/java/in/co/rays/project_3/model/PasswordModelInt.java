package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.PasswordDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface PasswordModelInt {
	
	public void add(PasswordDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(PasswordDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(PasswordDTO dto) throws ApplicationException;
	
	public PasswordDTO findByPk(long pk) throws ApplicationException;
	
	public PasswordDTO findByCode(String passwordCode) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(PasswordDTO dto, int pageNo, int pageSize) throws ApplicationException;

}
