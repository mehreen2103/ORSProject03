package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.BroadcastDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class BroadcastModelHibImpl implements BroadcastModelInt{

	@Override
	public void add(BroadcastDTO dto) throws ApplicationException, DuplicateRecordException {
		
		BroadcastDTO existDto = findByName(dto.getBroadcastCode());

		if (existDto != null) {
			throw new DuplicateRecordException("BroadCast Code already exists");
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
			throw new ApplicationException("Exception in add Broadcast" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(BroadcastDTO dto) throws ApplicationException, DuplicateRecordException {
		
		BroadcastDTO existDto = findByName(dto.getBroadcastCode());

		if (existDto != null && existDto.getId() != dto.getId()) {
			
			throw new DuplicateRecordException("BroadCast Code already exist");
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
			throw new ApplicationException("Exception in Update Broadcast" +e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void delete(BroadcastDTO dto) throws ApplicationException {
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ApplicationException("Exception in delete Broadcast" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public BroadcastDTO findbyPk(long pk) throws ApplicationException {
		
		Session session = null;
		BroadcastDTO dto = null;
		
		try {
			session = HibDataSource.getSession();
			dto =(BroadcastDTO) session.get(BroadcastDTO.class,pk);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in broadcast by pk" + e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public BroadcastDTO findByName(String broadcastCode) throws ApplicationException {
		
		
		Session session = null;
		BroadcastDTO dto = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(BroadcastDTO.class);
			criteria.add(Restrictions.eq("broadcastCode", broadcastCode));

			List list = criteria.list();

			if (list != null && list.size() > 0) {
				dto = (BroadcastDTO) list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Broadcast by Code" + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public List list() throws ApplicationException {
		return Search(null, 0, 0);
	}

	@Override
	public List Search(BroadcastDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(BroadcastDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getBroadcastCode() != null && dto.getBroadcastCode().length() > 0) {
					criteria.add(Restrictions.like("broadcastCode", dto.getBroadcastCode() + "%"));
				}
				
				if (dto.getMessage() != null && dto.getMessage().length() > 0) {
					criteria.add(Restrictions.like("message", dto.getMessage() + "%"));
				}

				if (dto.getSentBy() != null && dto.getSentBy().length() > 0) {
					criteria.add(Restrictions.like("sentBy", dto.getSentBy() + "%")); 
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
			throw new ApplicationException("Exception in Broadcast Search: " + e.getMessage());

		} finally {
			if (session != null) { 
				session.close();
			}
		}

		return list;

	}

}
