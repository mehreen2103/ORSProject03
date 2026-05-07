package in.co.rays.project_3.model;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import in.co.rays.project_3.dto.UserProfileDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class UserProfileModelHibImpl implements UserProfileModelInt {

	@Override
	public void add(UserProfileDTO dto) throws ApplicationException, DuplicateRecordException {
		
		UserProfileDTO existDto = findByName(dto.getProfileCode());
		
		if (existDto != null) {
			throw new DuplicateRecordException("User Profile Already Exist");
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
			throw new ApplicationException("Exception in add UserProfile" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(UserProfileDTO dto) throws ApplicationException, DuplicateRecordException {
		
		UserProfileDTO existsDto = findByName(dto.getProfileCode());
		
		if (existsDto != null && existsDto.getId() != dto.getId()) {
		        throw new DuplicateRecordException("UserProfile Already exists");	
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
			throw new ApplicationException("Exception in update UserProfile" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void delete(UserProfileDTO dto) throws ApplicationException {
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.rollback();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			tx.rollback();
			throw new ApplicationException("Exception in delete UserProfile" + e.getMessage());
		}
		
	}

	@Override
	public UserProfileDTO findByPk(long pk) throws ApplicationException {
		
		UserProfileDTO dto = null;
		Session session = null;
		
		try {
			session = HibDataSource.getSession();
			dto =(UserProfileDTO) session.get(UserProfileDTO.class, pk);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in user profile by pk");
			
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public UserProfileDTO findByName(String profileCode) throws ApplicationException {
		
		UserProfileDTO dto = null;
		Session session = null;
		
		try {
			session = HibDataSource.getSession();
			
			Criteria criteria = session.createCriteria(UserProfileDTO.class);
			criteria.add(Restrictions.eq("profileCode", profileCode));
			
			List list = criteria.list();
			
			if (list != null && list.size() > 0) {
				dto =(UserProfileDTO) list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in userProfile by code");
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
	public List search(UserProfileDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(UserProfileDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getProfileCode() != null && dto.getProfileCode().length() > 0) {
					criteria.add(Restrictions.like("profileCode", dto.getProfileCode() + "%"));
				}
				
				if (dto.getUserName() != null && dto.getUserName().length() > 0) {
					criteria.add(Restrictions.like("userName", dto.getUserName() + "%"));
				}
				if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
					criteria.add(Restrictions.like("mobileNo", dto.getMobileNo() + "%"));
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
			throw new ApplicationException("Exception in UserProfile Search: " + e.getMessage());

		} finally {
			if (session != null) { 
				session.close();
			}
		}

		return list;

	}

}
