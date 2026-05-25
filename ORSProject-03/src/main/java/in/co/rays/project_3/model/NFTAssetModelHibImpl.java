package in.co.rays.project_3.model;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import in.co.rays.project_3.dto.NFTAssetDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class NFTAssetModelHibImpl implements NFTAssetModelInt{

	@Override
	public void add(NFTAssetDTO dto) throws ApplicationException, DuplicateRecordException {
		
		  NFTAssetDTO existsDto = findByName(dto.getAssetName());
			
			if (existsDto != null) {
				throw new DuplicateRecordException("NFT Asset  Already exists");
			}
			
			Session session = HibDataSource.getSession();
			Transaction tx = null;
			
			try {
				tx = session.beginTransaction();
				session.save(dto);
				tx.commit();
				
			} catch (Exception e) {
				e.printStackTrace();
				tx.rollback();
				throw new ApplicationException("Exception in add NFTAsset" + e.getMessage());
				
			}finally {
				session.close();
			}
		
	}

	@Override
	public void update(NFTAssetDTO dto) throws DuplicateRecordException, ApplicationException {
		
		 NFTAssetDTO existDto = findByName(dto.getAssetName());
			
			if (existDto != null && existDto.getId() != dto.getId()) {
				throw new DuplicateRecordException("NFTAsset  Already exists");
			}
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			tx.rollback();
			throw new ApplicationException("Exception in update NFTAsset" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void delete(NFTAssetDTO dto) throws ApplicationException {
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ApplicationException("Exception in delete NFTAsset" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public NFTAssetDTO findByPk(long pk) throws ApplicationException {
		
		NFTAssetDTO dto = null;
		Session session = null;
		
		try {
			session = HibDataSource.getSession();
			dto = (NFTAssetDTO)  session.get(NFTAssetDTO.class, pk);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in NFTAsset by pk" + e.getMessage());
			
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public NFTAssetDTO findByName(String assetName) throws ApplicationException {
		
		NFTAssetDTO dto = null;
		Session session = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(NFTAssetDTO.class);

			criteria.add(Restrictions.eq("assetName", assetName));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (NFTAssetDTO) list.get(0);
			}

		} catch (Exception e) {

			e.printStackTrace();

			throw new ApplicationException("Exception in NFTAsset By Code " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
		}

		return dto;

	}

	@Override
	public List list() throws ApplicationException {
		
		return search(null, 0, 0);
	}

	@Override
	public List search(NFTAssetDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(NFTAssetDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				
				if (dto.getNftCode() != null && dto.getNftCode().length() > 0) {
					criteria.add(Restrictions.like("nftCode", dto.getNftCode() + "%"));
				}
				
				if (dto.getAssetName() != null && dto.getAssetName().length() > 0) {
					criteria.add(Restrictions.like("assetName", dto.getAssetName() + "%"));
				}

				if (dto.getOwnerName() != null && dto.getOwnerName().length() > 0) {
					criteria.add(Restrictions.like("ownerName", dto.getOwnerName() + "%"));
				}
				
				if (dto.getStatus() != null && dto.getStatus().length() > 0) {
					criteria.add(Restrictions.like("status", dto.getStatus()));
				}

			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in NFTAsset Search: " + e.getMessage());

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	
	}

}
