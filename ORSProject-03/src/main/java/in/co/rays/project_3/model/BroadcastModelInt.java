package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.BroadcastDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface BroadcastModelInt {
	
	public void add(BroadcastDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(BroadcastDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(BroadcastDTO dto) throws ApplicationException;
	
	public BroadcastDTO findbyPk(long pk) throws ApplicationException;
	
	public BroadcastDTO findByName(String broadcastCode) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List Search(BroadcastDTO dto, int pageNo, int pageSize) throws ApplicationException;

}
