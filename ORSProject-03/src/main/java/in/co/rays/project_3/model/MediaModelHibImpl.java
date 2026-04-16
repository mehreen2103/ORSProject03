package in.co.rays.project_3.model;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.BuildDTO;
import in.co.rays.project_3.dto.MediaDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class MediaModelHibImpl implements MediaModelInt{

	@Override
	public void add(MediaDTO dto) throws ApplicationException, DuplicateRecordException {
	
		MediaDTO existDto = fingByName(dto.getMediaName());

		if (existDto != null) {
			throw new DuplicateRecordException("Media already exists");
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
			throw new ApplicationException("Exception in add media" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(MediaDTO dto) throws ApplicationException, DuplicateRecordException {
		
		MediaDTO existDto = fingByName(dto.getMediaName());

		if (existDto != null && existDto.getId() != dto.getId()) {
			
			throw new DuplicateRecordException("Media already exist");
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
			throw new ApplicationException("Exception in update media" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void delete(MediaDTO dto) throws ApplicationException {

	    Session session = HibDataSource.getSession();
	    Transaction tx = null;

	    try {
	        tx = session.beginTransaction();

	        session.delete(dto);

	        tx.commit();   //yaha commit hona chahiye

	    } catch (Exception e) {
	        e.printStackTrace();
	        if (tx != null) {
	            tx.rollback();   // rollback only in error
	        }
	        throw new ApplicationException("Exception in delete media " + e.getMessage());

	    } finally {
	        session.close();
	    }
	}

	@Override
	public MediaDTO findByPk(long pk) throws ApplicationException {
	
		Session session = null;
		MediaDTO dto = null;
		
		try {
			session = HibDataSource.getSession();
		    dto = (MediaDTO)session.get(MediaDTO.class, pk);
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Media by pk" + e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public MediaDTO fingByName(String mediaName) throws ApplicationException {
		
		Session session = null;
		MediaDTO dto = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(MediaDTO.class);
			criteria.add(Restrictions.eq("mediaName", mediaName));

			List list = criteria.list();

			if (list != null && list.size() > 0) {
				dto = (MediaDTO) list.get(0);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Media by Name" + e.getMessage());

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
	public List search(MediaDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(MediaDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getMediaName() != null && dto.getMediaName().length() > 0) {
					criteria.add(Restrictions.like("mediaName", dto.getMediaName() + "%"));
				}
				
				if (dto.getCoverageDate() != null && dto.getCoverageDate().getTime() > 0) {
					criteria.add(Restrictions.like("coverageDate", dto.getCoverageDate() + "%"));
				}

				if (dto.getReporter() != null && dto.getReporter().length() > 0) {
					criteria.add(Restrictions.like("reporter", dto.getReporter() + "%")); 
				}
				
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Media Search: " + e.getMessage());

		} finally {
			if (session != null) { 
				session.close();
			}
		}

		return list;
	}


}


