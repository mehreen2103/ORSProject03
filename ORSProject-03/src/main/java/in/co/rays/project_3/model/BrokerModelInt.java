package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.BrokerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface BrokerModelInt {
	
	public void add(BrokerDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(BrokerDTO dto) throws DuplicateRecordException, ApplicationException;
	
	public void delete(BrokerDTO dto) throws ApplicationException;
	
	public BrokerDTO findByPk(long pk) throws ApplicationException;
	
	public BrokerDTO findBYName(String brokerName) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(BrokerDTO dto, int pageNo, int pageSize) throws ApplicationException;
	
	

}
