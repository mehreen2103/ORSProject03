package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.AccountDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface AccountModelInt {
	
	public void add (AccountDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(AccountDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(AccountDTO dto)throws ApplicationException;
	
	public AccountDTO findByPk(long pk) throws ApplicationException;
	
	public AccountDTO findByCode(String accountCode) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(AccountDTO dto, int  pageNo, int pageSize)throws ApplicationException;

}
