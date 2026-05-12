package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.RegistrationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface RegistrationModelInt {
	
	public void add(RegistrationDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(RegistrationDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(RegistrationDTO dto) throws ApplicationException;
	
	public RegistrationDTO findByPk(long pk) throws ApplicationException;
	
	public RegistrationDTO findByCode(String registrationCode) throws ApplicationException;
	
	public List list()throws ApplicationException;
	
	public List search(RegistrationDTO dto, int pageNo, int pageSize)throws ApplicationException;

}
