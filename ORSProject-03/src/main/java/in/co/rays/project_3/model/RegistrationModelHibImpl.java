package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.ExistsSubqueryExpression;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.RegistrationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class RegistrationModelHibImpl implements RegistrationModelInt {

	@Override
	public void add(RegistrationDTO dto) throws ApplicationException, DuplicateRecordException {
		
		RegistrationDTO existsDto = findByCode(dto.getRegistrationCode());
		
		if (existsDto != null) {
			throw new DuplicateRecordException("Registration Already Code");
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
			throw new ApplicationException("Exception in add Registration" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(RegistrationDTO dto)  throws ApplicationException, DuplicateRecordException{
		
		RegistrationDTO existDto = findByCode(dto.getRegistrationCode());
		
		if (existDto!= null && existDto.getId() != dto.getId() ) {
			throw new DuplicateRecordException("Registration Already exists");
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
			throw new ApplicationException("Exception in update Registration" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void delete(RegistrationDTO dto)  throws ApplicationException{
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ApplicationException("Exception in delete Registration" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public RegistrationDTO findByPk(long pk) throws ApplicationException {
		
		RegistrationDTO dto = null;
		Session session = null;
		 
		try {
			session = HibDataSource.getSession();
			dto = (RegistrationDTO) session.get(RegistrationDTO.class, pk);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in registration by pk" + e.getMessage());
			
		}
		return dto;
		 
	}

	@Override
	public RegistrationDTO findByCode(String registrationCode) throws ApplicationException {
	
		RegistrationDTO dto = null;
		Session session = null;
		
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(RegistrationDTO.class);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Registration by code" + e.getMessage());
		}
		return dto;
	}

	@Override
	public List list() throws ApplicationException {
		
		return search(null, 0, 0);
	}

	@Override
	public List search(RegistrationDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(RegistrationDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getRegistrationCode() != null && dto.getRegistrationCode().length() > 0) {
					criteria.add(Restrictions.like("registrationCode", dto.getRegistrationCode() + "%"));
				}

				if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
					criteria.add(Restrictions.like("firstName", dto.getFirstName() + "%"));
				}
				
				if (dto.getLastName() != null && dto.getLastName().length() > 0) {
					criteria.add(Restrictions.like("lastName", dto.getLastName() + "%"));
				}
				
				if (dto.getStatus() != null && dto.getStatus().length() > 0) {
					criteria.add(Restrictions.like("locationstatus", dto.getStatus()));
				}

			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Registration Search: " + e.getMessage());

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

}
