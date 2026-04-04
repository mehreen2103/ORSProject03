package in.co.rays.project_3.model;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.BrokerDTO;
import in.co.rays.project_3.dto.PhotographerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class PhotographerModelHibImpl implements PhotographerModelInt{

	@Override
	public void add(PhotographerDTO dto) throws ApplicationException, DuplicateRecordException {
		
		PhotographerDTO existDto = null;
		existDto = findbyName(dto.getPhotographerName());
		
		if (existDto != null) {
			throw new DuplicateRecordException("Photographer already exists");
		}
		
		
		 Session session = HibDataSource.getSession();
		 Transaction tx = null;
		  
		  try {  
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
			
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			
			throw new ApplicationException("Exception in add Photographer" + e.getMessage());
			
		}finally {
			session.close();
		}	
	}

	@Override
	public void update(PhotographerDTO dto) throws ApplicationException, DuplicateRecordException {
		
		PhotographerDTO existDto = findbyName(dto.getPhotographerName());
		
		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Photographer already exist");
		}
		
		Session session = null;
		Transaction tx = null;
		
		try {
			
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
			
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			
			throw new ApplicationException("Exception in update photographer" + e.getMessage());
			
		}finally {
			
			session.close();
		}
	}

	@Override
	public void delete(PhotographerDTO dto) throws ApplicationException {
		
		Session session = null;
		Transaction tx = null;
		
		try {
			
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in delete photographer" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public PhotographerDTO findbypk(long pk) throws ApplicationException {
		
		Session session = null;
		PhotographerDTO dto = null;
		
		try {
			session = HibDataSource.getSession();
			dto = (PhotographerDTO) session.get(PhotographerDTO.class, pk);
			
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Photographer by pk");
		}finally {
			session.close();
		}
		return dto;
		
	}

	@Override
	public PhotographerDTO findbyName(String photgrapherName) throws ApplicationException {
		
		Session session  = null;
		PhotographerDTO dto = null;
		
		try {
			session = HibDataSource.getSession();
		    dto = (PhotographerDTO) session.get(PhotographerDTO.class, photgrapherName);
		    
		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Photographer by Name");
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
public List search(PhotographerDTO dto, int pageNo, int pageSize) throws ApplicationException {
	    
	    Session session = null;
	    List list = null;
	    
	    try {
	        
	        session = HibDataSource.getSession();
	        Criteria criteria = session.createCriteria(PhotographerDTO.class);
	        
	        if (dto != null) {
	            
	            if (dto.getId() > 0) {
	                criteria.add(Restrictions.eq("id", dto.getId()));
	            }
	            
	            if (dto.getPhotographerName() != null && dto.getPhotographerName().length() > 0) {
	                criteria.add(Restrictions.like("photographerName", dto.getPhotographerName() + "%"));
	            }

	            if (dto.getEventType() != null && dto.getEventType().length() > 0) {
	                criteria.add(Restrictions.like("eventtype", dto.getEventType() + "%"));
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
	        throw new ApplicationException("Exception in Photographer Search: " + e.getMessage());
	    } finally {
	        session.close();
	    }
	    return list;
	}


}
