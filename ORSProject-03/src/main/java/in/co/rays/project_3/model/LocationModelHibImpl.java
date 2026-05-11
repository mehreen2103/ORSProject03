package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.AccountDTO;
import in.co.rays.project_3.dto.LocationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class LocationModelHibImpl implements LocationModelInt{

	@Override
	public void add(LocationDTO dto) throws ApplicationException, DuplicateRecordException {
		
		LocationDTO existDto = findByName(dto.getCity());
		
		if (existDto != null) {
			throw new DuplicateRecordException("Location already exist");
		}
		
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ApplicationException("Exception in add location" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(LocationDTO dto) throws ApplicationException, DuplicateRecordException {
		
		LocationDTO existDto = findByName(dto.getCity());
		
		if (existDto != null && existDto.getId() != dto.getId()) {

			throw new DuplicateRecordException("Location already exists");
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
			throw new ApplicationException("Exception in update location" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void delete(LocationDTO dto) throws ApplicationException {
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ApplicationException("Exception in delete location" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public LocationDTO FindByPK(long pk) throws ApplicationException {
		
		LocationDTO dto = null;
		Session session = null;
		
		try {
			session = HibDataSource.getSession();
			dto = (LocationDTO)session.get(LocationDTO.class, pk);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in location by pk" + e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public LocationDTO findByName(String state) throws ApplicationException {

		LocationDTO dto = null;
		Session session = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(LocationDTO.class);

			criteria.add(Restrictions.eq("state", state));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (LocationDTO) list.get(0);
			}

		} catch (Exception e) {

			e.printStackTrace();

			throw new ApplicationException("Exception in location by state");

		} finally {

			session.close();
		}

		return dto;
	}

	@Override
	public List list() throws ApplicationException {
		
		return search(null, 0, 0);
	}

	@Override
	public List search(LocationDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(LocationDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getCity() != null && dto.getCity().length() > 0) {
					criteria.add(Restrictions.like("city", dto.getCity() + "%"));
				}

				if (dto.getState() != null && dto.getState().length() > 0) {
					criteria.add(Restrictions.like("state", dto.getState() + "%"));
				}
				
				if (dto.getCountry() != null && dto.getCountry().length() > 0) {
					criteria.add(Restrictions.like("country", dto.getCountry() + "%"));
				}
				
				if (dto.getLocationstatus() != null && dto.getLocationstatus().length() > 0) {
					criteria.add(Restrictions.like("locationstatus", dto.getLocationstatus()));
				}

			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Location Search: " + e.getMessage());

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

}
