package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.ReportDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class ReportModelHibImpl implements ReportModelInt{

	@Override
	public void add(ReportDTO dto) throws ApplicationException, DuplicateRecordException {
		
		ReportDTO existsDto = findByName(dto.getReportType());
		
		if (existsDto != null) {
			throw new DuplicateRecordException("Report type  Already exists");
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
			throw new ApplicationException("Exception in add report" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(ReportDTO dto) throws ApplicationException, DuplicateRecordException {


		ReportDTO existDto = findByName(dto.getReportType());
		
		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Report type  Already exists");
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
			throw new ApplicationException("Exception in update report" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void delete(ReportDTO dto) throws ApplicationException {
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		 try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ApplicationException("Exception in delete report" + e.getMessage());
			
		}finally {
			session.close();
		}
	}

	@Override
	public ReportDTO findByPk(long pk) throws ApplicationException {
		
		ReportDTO dto = null;
		Session session = null;
		
		try {
			session = HibDataSource.getSession();
			dto = (ReportDTO)  session.get(ReportDTO.class, pk);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in report by pk" + e.getMessage());
			
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public ReportDTO findByName(String reportType) throws ApplicationException {
		
		ReportDTO dto = null;
		Session session = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(ReportDTO.class);

			criteria.add(Restrictions.eq("reportType", reportType));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (ReportDTO) list.get(0);
			}

		} catch (Exception e) {

			e.printStackTrace();

			throw new ApplicationException("Exception in report By Code " + e.getMessage());

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
	public List search(ReportDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ReportDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				
				if (dto.getReportType() != null && dto.getReportType().length() > 0) {
					criteria.add(Restrictions.like("reportType", dto.getReportType() + "%"));
				}
				
				if (dto.getGeneratedDate() != null && dto.getGeneratedDate().getTime() > 0) {
					criteria.add(Restrictions.like("generatedDate", dto.getGeneratedDate() + "%"));
				}

				if (dto.getRemarks() != null && dto.getRemarks().length() > 0) {
					criteria.add(Restrictions.like("remarks", dto.getRemarks() + "%"));
				}
				

			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Report Search: " + e.getMessage());

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}
	
	

}
