package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.DataImportDTO;
import in.co.rays.project_3.dto.MediaDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class DataImportModelHibImpl implements DataImportModelInt{

	@Override
	public void add(DataImportDTO dto) throws ApplicationException, DuplicateRecordException {
		
		DataImportDTO existDto = findByName(dto.getFileName());

		if (existDto != null) {
			throw new DuplicateRecordException("Data already exists");
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
			
			throw new ApplicationException("Exception in  add Data " + e.getMessage());
		}finally {
			session.close();
		}
		
		
	}

	@Override
	public void update(DataImportDTO dto) throws ApplicationException, DuplicateRecordException {
		
		DataImportDTO existDto = findByName(dto.getFileName());

		if (existDto != null && existDto.getId() != dto.getId()) {
			
			throw new DuplicateRecordException("Data already exist");
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
			
			throw new ApplicationException("Exception in add Data" + e.getMessage());
		}
		
	}

	@Override
	public void delete(DataImportDTO dto) throws ApplicationException {
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ApplicationException("Exception in delete Data" + e.getMessage());
		}
		
	}

	@Override
	public DataImportDTO findByPk(long pk) throws ApplicationException {
		
		Session session = null;
		DataImportDTO dto = null;
		
		try {
			session = HibDataSource.getSession();
			dto = (DataImportDTO)session.get(DataImportDTO.class, pk);
			
		} catch (Exception e) {
			throw new ApplicationException("Exception in Data by pk" + e.getMessage());
			
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public DataImportDTO findByName(String fileName) throws ApplicationException {
		
		Session session = null;
		DataImportDTO dto = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(DataImportDTO.class);
			criteria.add(Restrictions.eq("fileName", fileName));

			List list = criteria.list();

			if (list != null && list.size() > 0) {
				dto = (DataImportDTO) list.get(0);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Data by Name" + e.getMessage());

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
	public List search(DataImportDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(DataImportDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getImportLogCode() != null && dto.getImportLogCode().length() > 0) {
					criteria.add(Restrictions.like("importLogCode", dto.getImportLogCode() + "%"));
				}
				
				if (dto.getFileName() != null && dto.getFileName().length() > 0) {
					criteria.add(Restrictions.like("fileName", dto.getFileName() + "%"));
				}

				if (dto.getImportedBy() != null && dto.getImportedBy().length() > 0) {
					criteria.add(Restrictions.like("importedBy", dto.getImportedBy() + "%")); 
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
			throw new ApplicationException("Exception in Data Search: " + e.getMessage());

		} finally {
			if (session != null) { 
				session.close();
			}
		}

		return list;
	}

	}

