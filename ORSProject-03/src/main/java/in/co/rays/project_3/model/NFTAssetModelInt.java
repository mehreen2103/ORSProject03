package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.NFTAssetDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface NFTAssetModelInt {
	
	public void add(NFTAssetDTO dto) throws ApplicationException,DuplicateRecordException;
	
	public void update(NFTAssetDTO dto) throws ApplicationException,DuplicateRecordException;
	
	public void delete(NFTAssetDTO dto) throws ApplicationException;
	
	public NFTAssetDTO findByPk(long pk) throws ApplicationException;
	
	public NFTAssetDTO findByName(String assetName) throws ApplicationException;
	
	public List list() throws ApplicationException;
	
	public List search(NFTAssetDTO dto, int pageNo, int pageSize) throws ApplicationException;

}
