package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.CryptoWalletDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface CryptoWalletModelInt {
	
	public void add(CryptoWalletDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(CryptoWalletDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(CryptoWalletDTO dto) throws ApplicationException;
	
	public CryptoWalletDTO findByPk(long pk) throws ApplicationException;
	
	public CryptoWalletDTO findByName(String coinName) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(CryptoWalletDTO dto, int pageNo, int pageSize) throws ApplicationException;

}
