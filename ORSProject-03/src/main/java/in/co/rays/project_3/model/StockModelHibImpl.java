package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.CacheDTO;
import in.co.rays.project_3.dto.StockDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class StockModelHibImpl implements StockModelInt {

	@Override
	public void add(StockDTO dto) throws ApplicationException, DuplicateRecordException {
		
		StockDTO existDto = findByName(dto.getStockName());

		if (existDto != null) {
			throw new DuplicateRecordException("Stock already exists");
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
			throw new ApplicationException("Exception in add stock");
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(StockDTO dto) throws ApplicationException, DuplicateRecordException {
		
		StockDTO existDto = findByName(dto.getStockName());

		if (existDto != null && existDto.getId() != dto.getId()) {
			
			throw new DuplicateRecordException("Stock already exist");
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
			throw new ApplicationException("Exception in update stock");
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void delete(StockDTO dto) throws ApplicationException {
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ApplicationException("Exception in delete stock");
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public StockDTO findByPk(long pk) throws ApplicationException {
		
		Session session = null;
		StockDTO dto ;
		
		try {
			session = HibDataSource.getSession();
			dto = (StockDTO)session.get(StockDTO.class, pk);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Excption in stock by pk" + e.getMessage());
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public StockDTO findByName(String stockName) throws ApplicationException {
		
		Session session = null;
		StockDTO dto = null;
		
		try {
			session = HibDataSource.getSession();
			
			Criteria criteria = session.createCriteria(StockDTO.class);
			criteria.add(Restrictions.eq("stockName", stockName));
			
			List list = criteria.list();
			
			if (list != null && list.size() > 0) {
				dto = (StockDTO) list.get(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting stock by name");
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
	public List search(StockDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(StockDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getStockName() != null && dto.getStockName().length() > 0) {
					criteria.add(Restrictions.like("stockName", dto.getStockName() + "%"));
				}
				
				if (dto.getPrice() != null && dto.getPrice().length() > 0) {
					criteria.add(Restrictions.like("price", dto.getPrice() + "%"));
				}

				if (dto.getQuantity() != null && dto.getQuantity().length() > 0) {
					criteria.add(Restrictions.like("quantity", dto.getQuantity() + "%")); 
				}
				
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in stock Search: " + e.getMessage());

		} finally {
			if (session != null) { 
				session.close();
			}
		}

		return list;
	}

}
