package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.WeatherDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface WeatherModelInt {

	public void add(WeatherDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(WeatherDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(WeatherDTO dto) throws ApplicationException;
	
	public WeatherDTO findByPk(long pk) throws ApplicationException;

	public WeatherDTO findByCode(String alertCode) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(WeatherDTO dto, int pageNo, int pageSize) throws ApplicationException;
}
