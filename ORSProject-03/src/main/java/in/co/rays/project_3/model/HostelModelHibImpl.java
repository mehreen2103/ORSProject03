package in.co.rays.project_3.model;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import in.co.rays.project_3.dto.HostelDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class HostelModelHibImpl implements HostelModelInt{

	@Override
	public void add(HostelDTO dto) throws ApplicationException, DuplicateRecordException {
		
		HostelDTO existsDto = findByRoom(dto.getRoomNo());
		
		if (existsDto != null) {
			throw new DuplicateRecordException("Hostel Already exists");
		}
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.commit();
			throw new ApplicationException("Exception in add Hostel" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(HostelDTO dto) throws ApplicationException, DuplicateRecordException {
		
    HostelDTO existsDto = findByRoom(dto.getRoomNo());
		
		if (existsDto != null && existsDto.getId() != dto.getId()) {
		        throw new DuplicateRecordException("Hostel Already exists");	
		}

		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.commit();
			throw new ApplicationException("Exception in uodate Hostel" +e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void delete(HostelDTO dto) throws ApplicationException {
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.commit();
			throw new ApplicationException("Exception in delete Hostel" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public HostelDTO findByPk(long pk) throws ApplicationException {
		
		HostelDTO dto = null;
		Session session = null;
		
		try {
			session = HibDataSource.getSession();
		   dto =(HostelDTO) session.get(HostelDTO.class, pk);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in hostel by pk" + e.getMessage());
		}finally {
			session.close();
		}
		return dto ;
	}

	@Override
	public HostelDTO findByRoom(String roomNo) throws ApplicationException {


		HostelDTO dto = null;
		Session session = null;
		
		try {
			session = HibDataSource.getSession();
			
			Criteria criteria = session.createCriteria(HostelDTO.class);
			criteria.add(Restrictions.eq("roomNo", roomNo));
			
			List list = criteria.list();
			
			if (list != null && list.size() > 0) {
				dto =(HostelDTO) list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Hostel by Name");
			
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
	public List search(HostelDTO dto, int pageNO, int pageSize) throws ApplicationException {
		
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(HostelDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getRoomNo() != null && dto.getRoomNo().length() > 0) {
					criteria.add(Restrictions.like("roomNo", dto.getRoomNo() + "%"));
				}
				
				if (dto.getCapacity() != null && dto.getCapacity().length() > 0) {
					criteria.add(Restrictions.like("capacity", dto.getCapacity() + "%"));
				}
				
				if (dto.getJoinDate() != null && dto.getJoinDate().getDate() > 0) {
					criteria.add(Restrictions.eq("dob", dto.getJoinDate()));
				}
				
				if (dto.getStatus() != null && dto.getStatus().length() > 0) {
					criteria.add(Restrictions.like("status", dto.getStatus() + "%")); 
				}
				
			}

			if (pageSize > 0) {
				pageNO = (pageNO - 1) * pageSize;
				criteria.setFirstResult(pageNO);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Hostel by Search: " + e.getMessage());

		} finally {
			if (session != null) { 
				session.close();
			}
		}

		return list;


	}

}
