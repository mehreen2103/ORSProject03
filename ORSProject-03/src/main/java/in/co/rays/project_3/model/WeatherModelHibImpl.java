package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.RegistrationDTO;
import in.co.rays.project_3.dto.WeatherDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class WeatherModelHibImpl  implements WeatherModelInt{

	@Override
	public void add(WeatherDTO dto) throws ApplicationException, DuplicateRecordException {
		
     WeatherDTO existsDto = findByCode(dto.getAlertCode());
		
		if (existsDto != null) {
			throw new DuplicateRecordException("Weather Code Already exists");
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
			throw new ApplicationException("Exception in add weather" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(WeatherDTO dto) throws ApplicationException, DuplicateRecordException {
		
    WeatherDTO existDto = findByCode(dto.getAlertCode());
		
		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Weather Code Already exists");
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
			throw new ApplicationException("Exception in update weather" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void delete(WeatherDTO dto) throws ApplicationException {

		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ApplicationException("Exception in delete weather" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public WeatherDTO findByPk(long pk) throws ApplicationException {
		
		WeatherDTO dto = null;
		Session session = null;
		
		try {
			session = HibDataSource.getSession();
		  dto =(WeatherDTO)	session.get(WeatherDTO.class, pk);
		  
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in weather by pk");
			
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public WeatherDTO findByCode(String alertCode) throws ApplicationException {

		WeatherDTO dto = null;
		Session session = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(WeatherDTO.class);

			criteria.add(Restrictions.eq("alertCode", alertCode));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (WeatherDTO) list.get(0);
			}

		} catch (Exception e) {

			e.printStackTrace();

			throw new ApplicationException("Exception in weather By Code " + e.getMessage());

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
	public List search(WeatherDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(WeatherDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getAlertCode() != null && dto.getAlertCode().length() > 0) {
					criteria.add(Restrictions.like("alertCode", dto.getAlertCode() + "%"));
				}

				if (dto.getCityName() != null && dto.getCityName().length() > 0) {
					criteria.add(Restrictions.like("cityName", dto.getCityName() + "%"));
				}
				
				if (dto.getTemperature() != null && dto.getTemperature().length() > 0) {
					criteria.add(Restrictions.like("temperature", dto.getTemperature() + "%"));
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
			throw new ApplicationException("Exception in Weather Search: " + e.getMessage());

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

}
