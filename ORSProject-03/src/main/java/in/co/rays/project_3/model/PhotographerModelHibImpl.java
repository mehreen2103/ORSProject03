package in.co.rays.project_3.model;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.PhotographerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class PhotographerModelHibImpl implements PhotographerModelInt {

	@Override
	public void add(PhotographerDTO dto) throws ApplicationException, DuplicateRecordException {

		PhotographerDTO existDto = findbyName(dto.getPhotographerName());

		if (existDto != null) {
			throw new DuplicateRecordException("Photographer already exists");
		}

		Session session = HibDataSource.getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			throw new ApplicationException("Exception in add Photographer " + e.getMessage());

		} finally {
			session.close();
		}
	}

	@Override
	public void update(PhotographerDTO dto) throws ApplicationException, DuplicateRecordException {

		PhotographerDTO existDto = findbyName(dto.getPhotographerName());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Photographer already exists");
		}

		Session session = HibDataSource.getSession(); // ✅ FIX
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw new ApplicationException("Exception in update Photographer " + e.getMessage());

		} finally {
			session.close();
		}
	}

	@Override
	public void delete(PhotographerDTO dto) throws ApplicationException {

		Session session = HibDataSource.getSession(); // ✅ FIX
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw new ApplicationException("Exception in delete Photographer " + e.getMessage());

		} finally {
			session.close();
		}
	}

	@Override
	public PhotographerDTO findbypk(long pk) throws ApplicationException {

		Session session = null;
		PhotographerDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (PhotographerDTO) session.get(PhotographerDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Photographer by PK");

		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public PhotographerDTO findbyName(String photographerName) throws ApplicationException {

		Session session = null;
		PhotographerDTO dto = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(PhotographerDTO.class);
			criteria.add(Restrictions.eq("photographerName", photographerName));

			List list = criteria.list();

			if (list != null && list.size() > 0) {
				dto = (PhotographerDTO) list.get(0);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Photographer by Name");

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
	public List search(PhotographerDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(PhotographerDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getPhotographerName() != null && dto.getPhotographerName().length() > 0) {
					criteria.add(Restrictions.like("photographerName", dto.getPhotographerName() + "%"));
				}

				if (dto.getEventType() != null && dto.getEventType().length() > 0) {
					criteria.add(Restrictions.like("eventType", dto.getEventType() + "%")); // ✅ FIX
				}
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Photographer Search: " + e.getMessage());

		} finally {
			if (session != null) { // ✅ MUST
				session.close();
			}
		}

		return list;
	}
}