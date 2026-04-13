package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.DashboardDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class DashboardModelHibImpl implements DashboardModelInt{

	@Override
	public void add(DashboardDTO dto) throws ApplicationException, DuplicateRecordException {
		
		DashboardDTO existDto = findbyname(dto.getDashboardName());

		if (existDto != null) {
			throw new DuplicateRecordException("DashBoard already exists");
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
			throw new ApplicationException("Exception in add DashBoard " + e.getMessage());

		} finally {
			session.close();
		}
		
	
	}

	@Override
	public void update(DashboardDTO dto) throws ApplicationException, DuplicateRecordException {

		DashboardDTO existDto = findbyname(dto.getDashboardName());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("DashBoard already exists");
		}

		Session session = HibDataSource.getSession(); 
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw new ApplicationException("Exception in update DashBoard " + e.getMessage());

		} finally {
			session.close();
		}
	}
		
	

	@Override
	public void delete(DashboardDTO dto) throws ApplicationException {
		
		Session session = HibDataSource.getSession(); // FIX
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw new ApplicationException("Exception in delete Dashboard " + e.getMessage());

		} finally {
			session.close();
		}
	}

	@Override
	public DashboardDTO findbypk(long pk) throws ApplicationException {
		
		Session session = null;
		DashboardDTO dto = null;
		
		try {
			session = HibDataSource.getSession();
			dto = (DashboardDTO) session.get(DashboardDTO.class, pk);
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting dashboard by pk" + e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public DashboardDTO findbyname(String dashboardName) throws ApplicationException {
		
		Session session = null;
		DashboardDTO dto = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(DashboardDTO.class);
			criteria.add(Restrictions.eq("dashboardName", dashboardName));

			List list = criteria.list();

			if (list != null && list.size() > 0) {
				dto = (DashboardDTO) list.get(0);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Dashboard by Name" + e.getMessage());

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
	public List search(DashboardDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(DashboardDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getDashboardCode() != null && dto.getDashboardCode().length() > 0) {
					criteria.add(Restrictions.like("dashboardCode", dto.getDashboardCode() + "%"));
				}
				
				if (dto.getDashboardName() != null && dto.getDashboardName().length() > 0) {
					criteria.add(Restrictions.like("dashboardName", dto.getDashboardName() + "%"));
				}

				if (dto.getUserName() != null && dto.getUserName().length() > 0) {
					criteria.add(Restrictions.like("userName", dto.getUserName() + "%")); 
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
			throw new ApplicationException("Exception in Dashboard Search: " + e.getMessage());

		} finally {
			if (session != null) { 
				session.close();
			}
		}

		return list;
	}

}
