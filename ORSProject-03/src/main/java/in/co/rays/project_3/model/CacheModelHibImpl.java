package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.CacheDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class CacheModelHibImpl implements CacheModelInt{

	@Override
	public void add(CacheDTO dto) throws ApplicationException, DuplicateRecordException {
		
		CacheDTO existDto = findByName(dto.getKeyName());

		if (existDto != null) {
			throw new DuplicateRecordException("Cache already exists");
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
			throw new ApplicationException("Exception in add Cache" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(CacheDTO dto) throws ApplicationException, DuplicateRecordException {
		
		CacheDTO existDto = findByName(dto.getKeyName());

		if (existDto != null && existDto.getId() != dto.getId()) {
			
			throw new DuplicateRecordException("Cache already exist");
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
			throw new ApplicationException("Exception in update cache" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void delete(CacheDTO dto) throws ApplicationException {
		
		Session session = null;
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ApplicationException("Exception in delete cache" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public CacheDTO findByPk(long pk) throws ApplicationException {
		
		Session session = null;
		CacheDTO dto = null;
		
		try {
			session = HibDataSource.getSession();
			dto =(CacheDTO) session.get(CacheDTO.class, pk);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in cache by pk");
			
		}finally {
			session.close();
		}
		return dto;
				
	}

	@Override
	public CacheDTO findByName(String keyName) throws ApplicationException {
		
		Session session = null;
		CacheDTO dto = null;
		
		try {
			session = HibDataSource.getSession();
			
			Criteria criteria = session.createCriteria(CacheDTO.class);
			criteria.add(Restrictions.eq("keyName", keyName));
			
			List list = criteria.list();
			
			if (list != null && list.size() > 0) {
				dto = (CacheDTO) list.get(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting cache by name");
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public List list() throws ApplicationException {
		
		return search(null, 0, 0);
	}

	@Override
	public List search(CacheDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CacheDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getCacheCode() != null && dto.getCacheCode().length() > 0) {
					criteria.add(Restrictions.like("cacheCode", dto.getCacheCode() + "%"));
				}
				
				if (dto.getKeyName() != null && dto.getKeyName().length() > 0) {
					criteria.add(Restrictions.like("keyName", dto.getKeyName() + "%"));
				}

				if (dto.getValue() != null && dto.getValue().length() > 0) {
					criteria.add(Restrictions.like("value", dto.getValue() + "%")); 
				}
				
				if (dto.getStatus() != null && dto.getStatus().length() > 0) {
					criteria.add(Restrictions.like("status", dto.getStatus() + "%")); 
				}
				
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Cache Search: " + e.getMessage());

		} finally {
			if (session != null) { 
				session.close();
			}
		}

		return list;

	}
}

