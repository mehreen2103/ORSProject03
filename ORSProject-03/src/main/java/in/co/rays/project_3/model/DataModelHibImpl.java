
package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.DataDTO;
import in.co.rays.project_3.dto.DeviceDTO;
import in.co.rays.project_3.dto.StockDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class DataModelHibImpl implements DataModelInt{

	@Override
	public void add(DataDTO dto) throws ApplicationException, DuplicateRecordException {
		
		DataDTO existDto = findByName(dto.getMappingCode());

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
			throw new ApplicationException("Exception in add data" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(DataDTO dto) throws ApplicationException, DuplicateRecordException {
		
		DataDTO existDto = findByName(dto.getMappingCode());

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
			throw new ApplicationException("Exception in update data" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void delete(DataDTO dto) throws ApplicationException {
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.rollback();
			
		} catch (Exception e) {
		e.printStackTrace();
		tx.rollback();
		
		throw new ApplicationException("Exception in delete data" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public DataDTO findByPk(long pk) throws ApplicationException {
		
		Session session = null;
		DataDTO dto = null;
		
		 try {
			session = HibDataSource.getSession();
			dto = (DataDTO)session.get(DataDTO.class, pk);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in pk by data" + e.getMessage());
			
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public DataDTO findByName(String mappingCode) throws ApplicationException {
		
		Session session = null;
		DataDTO dto = null;
		
		try {
			session = HibDataSource.getSession();
			
			Criteria criteria = session.createCriteria(DataDTO.class);
			criteria.add(Restrictions.eq("mappingCode", mappingCode));
			
			List list = criteria.list();
			
			if (list != null && list.size() > 0) {
				dto = (DataDTO) list.get(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Data by name");
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
	public List search(DataDTO dto, int pageNo, int pageSize) throws ApplicationException {
		

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(DataDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getMappingCode() != null && dto.getMappingCode().length() > 0) {
					criteria.add(Restrictions.like("mappingCode", dto.getMappingCode() + "%"));
				}
				
				if (dto.getSourceField() != null && dto.getSourceField().length() > 0) {
					criteria.add(Restrictions.like("sourceField", dto.getSourceField() + "%"));
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
