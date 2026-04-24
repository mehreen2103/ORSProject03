package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.DeviceDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface DeviceModelInt {
	
	public void add(DeviceDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(DeviceDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(DeviceDTO dto) throws ApplicationException;
	
	public DeviceDTO findByPk(long pk) throws ApplicationException;
	
	public DeviceDTO findByName(String deviceName) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(DeviceDTO dto, int pageNo, int pageSize) throws ApplicationException;

}
