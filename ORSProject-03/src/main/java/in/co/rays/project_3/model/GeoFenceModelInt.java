package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.GeoFenceDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface GeoFenceModelInt {
	
	public void add(GeoFenceDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(GeoFenceDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(GeoFenceDTO dto) throws ApplicationException;
	
	public GeoFenceDTO findByPk(long pk) throws ApplicationException;
	
	public GeoFenceDTO findByName(String geoFenceCode) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(GeoFenceDTO dto, int pageNo, int pageSize) throws ApplicationException;

}
