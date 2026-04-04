package in.co.rays.project_3.model;
import java.util.List;
import in.co.rays.project_3.dto.PhotographerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface PhotographerModelInt {
	
	public void add(PhotographerDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void update(PhotographerDTO dto) throws ApplicationException, DuplicateRecordException;
	
	public void delete(PhotographerDTO dto) throws ApplicationException;
	
	public PhotographerDTO findbypk(long pk) throws ApplicationException;
	
	public PhotographerDTO findbyName( String photgrapherName) throws ApplicationException;
	
    public List list() throws ApplicationException;
	
	public List search(PhotographerDTO dto, int pageNo, int pageSize) throws ApplicationException;
	

}
