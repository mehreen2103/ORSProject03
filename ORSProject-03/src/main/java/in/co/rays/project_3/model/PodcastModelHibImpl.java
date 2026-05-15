package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.PasswordDTO;
import in.co.rays.project_3.dto.PodcastDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class PodcastModelHibImpl implements PodcastModelInt {

	@Override
	public void add(PodcastDTO dto) throws ApplicationException, DuplicateRecordException {

     PodcastDTO existsDto = findByCode(dto.getPodcastCode());
		
		if (existsDto != null) {
			throw new DuplicateRecordException("podcast Code Already exists");
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
			throw new ApplicationException("Exception in add podcast" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(PodcastDTO dto) throws ApplicationException, DuplicateRecordException {


		 PodcastDTO existDto = findByCode(dto.getPodcastCode());
			
			if (existDto != null && existDto.getId() != dto.getId()) {
				throw new DuplicateRecordException("podcast Code Already exists");
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
			throw new ApplicationException("Exception in update podcast" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void delete(PodcastDTO dto) throws ApplicationException {
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(dto);
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
	public PodcastDTO findByPk(long pk) throws ApplicationException {
		
		PodcastDTO dto = null;
		Session session = null;
		
		try {
			session = HibDataSource.getSession();
			dto = (PodcastDTO)  session.get(PodcastDTO.class, pk);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in podcast by pk" + e.getMessage());
			
		}finally {
			session.close();
		}
		return dto;
		
	}

	@Override
	public PodcastDTO findByCode(String podcastCode) throws ApplicationException {
		
		PodcastDTO dto = null;
		Session session = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(PodcastDTO.class);

			criteria.add(Restrictions.eq("podcastCode", podcastCode));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (PodcastDTO) list.get(0);
			}

		} catch (Exception e) {

			e.printStackTrace();

			throw new ApplicationException("Exception in podcast By Code " + e.getMessage());

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
	public List search(PodcastDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(PodcastDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getPodcastCode() != null && dto.getPodcastCode().length() > 0) {
					criteria.add(Restrictions.like("podcastCode", dto.getPodcastCode() + "%"));
				}

				if (dto.getPodcastTitle() != null && dto.getPodcastTitle().length() > 0) {
					criteria.add(Restrictions.like("podcastTitle", dto.getPodcastTitle() + "%"));
				}
				
				if (dto.getHostname() != null && dto.getHostname().length() > 0) {
					criteria.add(Restrictions.like("hostname", dto.getHostname() + "%"));
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
			throw new ApplicationException("Exception in podcast Search: " + e.getMessage());

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}
	
	

}
