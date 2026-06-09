package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.SmartDeviceDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface SmartDeviceModelInt {
	
	public void add(SmartDeviceDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(SmartDeviceDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(SmartDeviceDTO dto) throws ApplicationException;
	
	public SmartDeviceDTO findByPk(long pk) throws ApplicationException;
	
	public SmartDeviceDTO findByName(String deviceName) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search (SmartDeviceDTO dto, int pageNo, int pageSize) throws ApplicationException;
	

}
