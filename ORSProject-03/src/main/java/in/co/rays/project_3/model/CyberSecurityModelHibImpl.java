package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.CyberSecurityDTO;
import in.co.rays.project_3.dto.DispatchDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class CyberSecurityModelHibImpl implements CyberSecurityModelInt{

	@Override
	public void add(CyberSecurityDTO dto) throws ApplicationException, DuplicateRecordException {
		
		CyberSecurityDTO existsDto = findByName(dto.getThreatType());

		if (existsDto != null) {
			throw new DuplicateRecordException("Threat Type  Already exists");
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
			throw new ApplicationException("exception in add cyber security" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(CyberSecurityDTO dto) throws ApplicationException, DuplicateRecordException {
		
		CyberSecurityDTO existDto = findByName(dto.getThreatType());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Threat Type  Already exists");
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
			throw new ApplicationException("exception in update cyber security" + e.getMessage());
		}finally {
			session.close();
		}
		
		
	}

	@Override
	public void delete(CyberSecurityDTO dto) throws ApplicationException {
		

		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ApplicationException("exception in delete cyber security" + e.getMessage());
		}finally {
			session.close();
		}
		
		
	}

	@Override
	public CyberSecurityDTO findByPk(long pk) throws ApplicationException {
		
		CyberSecurityDTO dto = null;
		Session session = null;

		try {
			session = HibDataSource.getSession();
			dto = (CyberSecurityDTO) session.get(CyberSecurityDTO.class, pk);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in cybersecurity by pk" + e.getMessage());

		} finally {
			session.close();
		}
		return dto;
	}

	@Override
	public CyberSecurityDTO findByName(String threatType) throws ApplicationException {
		
		CyberSecurityDTO dto = null;
		Session session = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(CyberSecurityDTO.class);

			criteria.add(Restrictions.eq("threatType", threatType));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (CyberSecurityDTO) list.get(0);
			}

		} catch (Exception e) {

			e.printStackTrace();

			throw new ApplicationException("Exception in cybersecurity By Code " + e.getMessage());

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
	public List search(CyberSecurityDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CyberSecurityDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				
				if (dto.getThreatType() != null && dto.getThreatType().length() > 0) {
					criteria.add(Restrictions.like("threatType", dto.getThreatType() + "%"));
				}

				if (dto.getSeverity() != null && dto.getSeverity().length() > 0) {
					criteria.add(Restrictions.like("severity", dto.getSeverity() + "%"));
				}


				if (dto.getDetectedTime() != null) {
					criteria.add(Restrictions.eq("detectedTime", dto.getDetectedTime()));
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
			throw new ApplicationException("Exception in cybersecurity Search: " + e.getMessage());

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;

	}
	
	

}
