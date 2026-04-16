package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.BuildDTO;
import in.co.rays.project_3.dto.DashboardDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class BuildModelHibIml implements BuildModelInt{

	@Override
	public void add(BuildDTO dto) throws ApplicationException, DuplicateRecordException {
		
		BuildDTO existDto = findbyname(dto.getBuildCode());

		if (existDto != null) {
			throw new DuplicateRecordException("Build already exists");
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
				throw new ApplicationException("Exception in add Build" + e.getMessage());			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(BuildDTO dto) throws ApplicationException, DuplicateRecordException {
		
		BuildDTO existDto = findbyname(dto.getBuildCode());

		if (existDto != null && existDto.getId() != dto.getId()) {
		}
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
			    throw new ApplicationException("Exception in update build" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void delete(BuildDTO dto) throws ApplicationException {
		 
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if(tx!= null)
				tx.rollback();
			throw new ApplicationException("Exception in delete build " + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public BuildDTO findbypk(long pk) throws ApplicationException {
		
		Session session = null;
		BuildDTO dto = null;
		
		try {
			session = HibDataSource.getSession();
			dto = (BuildDTO) session.get(BuildDTO.class, pk);
			
			
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting build by pk" + e.getMessage());
		}finally {
			session .close();
		}
		return dto;
		
	}

	@Override
	public BuildDTO findbyname(String build) throws ApplicationException {
		
		Session session = null;
		BuildDTO dto = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(BuildDTO.class);
			criteria.add(Restrictions.eq("buildCode", build));

			List list = criteria.list();

			if (list != null && list.size() > 0) {
				dto = (BuildDTO) list.get(0);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Build by Name" + e.getMessage());

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
	public List search(BuildDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(BuildDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getBuildCode() != null && dto.getBuildCode().length() > 0) {
					criteria.add(Restrictions.like("buildCode", dto.getBuildCode() + "%"));
				}
				
				if (dto.getBuildVersion() != null && dto.getBuildVersion().length() > 0) {
					criteria.add(Restrictions.like("buildVersion", dto.getBuildVersion() + "%"));
				}

				if (dto.getTriggeredBy() != null && dto.getTriggeredBy().length() > 0) {
					criteria.add(Restrictions.like("triggeredBy", dto.getTriggeredBy() + "%")); 
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
			throw new ApplicationException("Exception in Build Search: " + e.getMessage());

		} finally {
			if (session != null) { 
				session.close();
			}
		}

		return list;
	}

	}


