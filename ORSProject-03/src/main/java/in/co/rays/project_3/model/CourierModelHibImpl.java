package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.CourierDTO;
import in.co.rays.project_3.dto.NFTAssetDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class CourierModelHibImpl implements CourierModelInt{

	@Override
	public void add(CourierDTO dto) throws ApplicationException, DuplicateRecordException {
		
		 CourierDTO existsDto = findByName(dto.getSenderName());
			
			if (existsDto != null) {
				throw new DuplicateRecordException("Courier  Already exists");
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
				throw new ApplicationException("Exception in add Courier" + e.getMessage());
				
			}finally {
				session.close();
			}
		
	}

	@Override
	public void update(CourierDTO dto) throws ApplicationException, DuplicateRecordException {
		
		 CourierDTO existDto = findByName(dto.getSenderName());
			
			if (existDto != null && existDto.getId() != dto.getId()) {
				throw new DuplicateRecordException("Courier  Already exists");
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
			throw new ApplicationException("Exception in update courier" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void delete(CourierDTO dto) throws ApplicationException {
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			tx.rollback();
			throw new ApplicationException("Exception in update courier" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public CourierDTO findByPk(long pk) throws ApplicationException {
		
		CourierDTO dto = null;
		Session session = null;
		
		try {
			session = HibDataSource.getSession();
			dto = (CourierDTO)  session.get(CourierDTO.class, pk);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Courier by pk" + e.getMessage());
			
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public CourierDTO findByName(String senderName) throws ApplicationException {
		
		CourierDTO dto = null;
		Session session = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(CourierDTO.class);

			criteria.add(Restrictions.eq("senderName", senderName));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (CourierDTO) list.get(0);
			}

		} catch (Exception e) {

			e.printStackTrace();

			throw new ApplicationException("Exception in Courier By Code " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
		}

		return dto;
	}

	@Override
	public List list() throws ApplicationException{
		
		return search(null, 0, 0);
	}

	@Override
	public List search(CourierDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CourierDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				
				if (dto.getSenderName() != null && dto.getSenderName().length() > 0) {
					criteria.add(Restrictions.like("senderName", dto.getSenderName() + "%"));
				}
				
				if (dto.getRecieverName() != null && dto.getRecieverName().length() > 0) {
					criteria.add(Restrictions.like("recieverName", dto.getRecieverName() + "%"));
				}

				if (dto.getWeight() != null && dto.getWeight().length() > 0) {
					criteria.add(Restrictions.like("weight", dto.getWeight() + "%"));
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
			throw new ApplicationException("Exception in Courier Search: " + e.getMessage());

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

}
