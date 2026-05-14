package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.PasswordDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class PasswordModelHibImpl implements PasswordModelInt{

	@Override
	public void add(PasswordDTO dto) throws ApplicationException, DuplicateRecordException {
		
     PasswordDTO existsDto = findByCode(dto.getPasswordCode());
		
		if (existsDto != null) {
			throw new DuplicateRecordException("Password Code Already exists");
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
			throw new ApplicationException("Exception in add password" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(PasswordDTO dto) throws ApplicationException, DuplicateRecordException {
		
		 PasswordDTO existDto = findByCode(dto.getPasswordCode());
			
			if (existDto != null && existDto.getId() != dto.getId()) {
				throw new DuplicateRecordException("Password Code Already exists");
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
			throw new ApplicationException("Exception in update password" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void delete(PasswordDTO dto) throws ApplicationException {
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ApplicationException("Exception in delete password" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public PasswordDTO findByPk(long pk) throws ApplicationException {

		PasswordDTO dto = null;
		Session session = null;
		
		try {
			session = HibDataSource.getSession();
			dto = (PasswordDTO)  session.get(PasswordDTO.class, pk);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in password by pk" + e.getMessage());
		}finally {
			session.close();
		}
		return dto;

		
	}

	@Override
	public PasswordDTO findByCode(String passwordCode) throws ApplicationException {
		
		PasswordDTO dto = null;
		Session session = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(PasswordDTO.class);

			criteria.add(Restrictions.eq("passwordCode", passwordCode));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (PasswordDTO) list.get(0);
			}

		} catch (Exception e) {

			e.printStackTrace();

			throw new ApplicationException("Exception in Password By Code " + e.getMessage());

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
	public List search(PasswordDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(PasswordDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getPasswordCode() != null && dto.getPasswordCode().length() > 0) {
					criteria.add(Restrictions.like("passwordCode", dto.getPasswordCode() + "%"));
				}

				if (dto.getUsername() != null && dto.getUsername().length() > 0) {
					criteria.add(Restrictions.like("username", dto.getUsername() + "%"));
				}
				
				if (dto.getPasswordValue() != null && dto.getPasswordValue().length() > 0) {
					criteria.add(Restrictions.like("passwordValue", dto.getPasswordValue() + "%"));
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
			throw new ApplicationException("Exception in Password Search: " + e.getMessage());

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

}
