package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.BrokerDTO;
import in.co.rays.project_3.dto.ClientDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class BrokerModelHibImpl implements BrokerModelInt{

	@Override
	public void add(BrokerDTO dto) throws ApplicationException, DuplicateRecordException {
	
		BrokerDTO existDto = null;
		existDto = findBYName(dto.getBrokerName());
		
		if (existDto != null) {
			throw new DuplicateRecordException("Broker already exists");
		}
		
		 Session session = HibDataSource.getSession();
		 
		 Transaction tx = null;
		 
		 try {
			 
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
			
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Add Broker" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(BrokerDTO dto) throws DuplicateRecordException, ApplicationException {
		
		BrokerDTO existDto = findBYName(dto.getBrokerName());
		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Broker already exist");
		}
		
		Session session = null;
		Transaction tx = null;
		
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
			
		} catch (HibernateException e) {
			tx.rollback();
			throw new ApplicationException("Exception in update Broker" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void delete(BrokerDTO dto) throws ApplicationException {
		
		Session session = null;
		Transaction tx = null;
		
		try {
			
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			
		} catch (HibernateException e) {
			tx.rollback();
			throw new ApplicationException("Exception in Delete Broker" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public BrokerDTO findByPk(long pk) throws ApplicationException {
		
		Session session = null;
		BrokerDTO dto = null;
		
		try {
			session = HibDataSource.getSession();
			dto = (BrokerDTO) session.get(BrokerDTO.class, pk);
			
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Broker by pk");
		}finally {
			session.close();
		}
		return dto;
	}
	@Override
	public BrokerDTO findBYName(String brokerName) throws ApplicationException {

	    Session session = null;
	    BrokerDTO dto = null;

	    try {
	        session = HibDataSource.getSession();
 System.out.println("in Name methods ");
	        Criteria criteria = session.createCriteria(BrokerDTO.class);
	        criteria.add(Restrictions.eq("brokerName", brokerName));

	        List list = criteria.list();

	        if (list != null && list.size() > 0) {
	            dto = (BrokerDTO) list.get(0);
	        }

	    } catch (HibernateException e) {
	        e.printStackTrace(); // 👈 IMPORTANT (debug ke liye)
	        throw new ApplicationException("Exception in getting Broker by Name: " + e.getMessage());
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
	public List search(BrokerDTO dto, int pageNo, int pageSize) throws ApplicationException {
	    
	    Session session = null;
	    List list = null;
	    
	    try {
	        
	        session = HibDataSource.getSession();
	        Criteria criteria = session.createCriteria(BrokerDTO.class);
	        
	        if (dto != null) {
	            
	            if (dto.getId() > 0) {
	                criteria.add(Restrictions.eq("id", dto.getId()));
	            }
	            
	            if (dto.getBrokerName() != null && dto.getBrokerName().length() > 0) {
	                criteria.add(Restrictions.like("brokerName", dto.getBrokerName() + "%"));
	            }

	            if (dto.getCompany() != null && dto.getCompany().length() > 0) {
	                criteria.add(Restrictions.like("company", dto.getCompany() + "%"));
	            }
	        }
	        
	        if (pageSize > 0) {
	            pageNo = (pageNo - 1 ) * pageSize;
	            criteria.setFirstResult(pageNo);
	            criteria.setMaxResults(pageSize);
	        }
	        
	        list = criteria.list();

	    } catch (HibernateException e) {
	        e.printStackTrace();
	        throw new ApplicationException("Exception in Broker Search: " + e.getMessage());
	    } finally {
	        session.close();
	    }
	    return list;
	}}
