package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.StockDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface StockModelInt {
	
	public void add(StockDTO dto) throws ApplicationException,DuplicateRecordException;
	
	public void update(StockDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(StockDTO dto) throws ApplicationException;
	
	public StockDTO findByPk(long pk) throws ApplicationException;
	
	public StockDTO findByName(String stockName) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(StockDTO dto , int pageNo, int pageSize) throws ApplicationException;

}
