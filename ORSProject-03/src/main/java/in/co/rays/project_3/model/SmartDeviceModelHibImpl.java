package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.SmartDeviceDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class SmartDeviceModelHibImpl implements SmartDeviceModelInt  {

	@Override
	public void add(SmartDeviceDTO dto) throws ApplicationException, DuplicateRecordException {
		
		SmartDeviceDTO existsDto = findByName(dto.getDeviceName());

		if (existsDto != null) {
			throw new DuplicateRecordException("Device Name  Already exists");
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
			throw new ApplicationException("Exception in add smartdevice" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(SmartDeviceDTO dto) throws ApplicationException, DuplicateRecordException {
		
		SmartDeviceDTO existDto = findByName(dto.getDeviceName());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Device Name  Already exists");
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
			throw new ApplicationException("Exception in update smartdevice" + e.getMessage());
		}finally {
			session.close();
		}
	}

	@Override
	public void delete(SmartDeviceDTO dto) throws ApplicationException {
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ApplicationException("Exception in delete smartdevice" + e.getMessage());
		}finally {
			session.close();
		}
	}

	@Override
	public SmartDeviceDTO findByPk(long pk) throws ApplicationException {
		
		SmartDeviceDTO dto = null;
		Session session = null;
		
		try {
			session = HibDataSource.getSession();
			dto = (SmartDeviceDTO)session.get(SmartDeviceDTO.class, pk);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in pk by smartdevice" + e.getMessage());
			
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public SmartDeviceDTO findByName(String deviceName) throws ApplicationException {
		
		SmartDeviceDTO dto = null;
		Session session = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(SmartDeviceDTO.class);

			criteria.add(Restrictions.eq("deviceName", deviceName));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (SmartDeviceDTO) list.get(0);
			}

		} catch (Exception e) {

			e.printStackTrace();

			throw new ApplicationException("Exception in Cryptowallet By deviceName " + e.getMessage());

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
	public List search(SmartDeviceDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(SmartDeviceDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				
				if (dto.getDeviceName() != null && dto.getDeviceName().length() > 0) {
					criteria.add(Restrictions.like("deviceName", dto.getDeviceName() + "%"));
				}

				if (dto.getRoom() != null && dto.getRoom().length() > 0) {
					criteria.add(Restrictions.like("room", dto.getRoom() + "%"));
				}

				if (dto.getStatus() != null && dto.getStatus().length() > 0) {
					criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
				}
				
				if (dto.getDoubleUsage() != null && dto.getDoubleUsage().length() > 0) {
					criteria.add(Restrictions.like("doubleUsage", dto.getDoubleUsage() + "%"));
				}

			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in smartdevice Search: " + e.getMessage());

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

}
