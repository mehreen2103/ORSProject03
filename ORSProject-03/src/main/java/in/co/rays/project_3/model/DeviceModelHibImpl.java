package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.DataImportDTO;
import in.co.rays.project_3.dto.DeviceDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class DeviceModelHibImpl implements DeviceModelInt {

	@Override
	public void add(DeviceDTO dto) throws ApplicationException, DuplicateRecordException {
		
		DeviceDTO existDto = findByName(dto.getDeviceName());

		if (existDto != null) {
			throw new DuplicateRecordException("Device already exists");
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
			
			throw new ApplicationException("Exception in add Device" + e.getMessage());
		}finally {
			session.close();
		}
				
	}

	@Override
	public void update(DeviceDTO dto) throws ApplicationException, DuplicateRecordException {
		
		DeviceDTO existDto = findByName(dto.getDeviceName());

		if (existDto != null && existDto.getId() != dto.getId()) {
			
			throw new DuplicateRecordException("Device already exist");
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
			throw new ApplicationException("Exception in update Device" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void delete(DeviceDTO dto) throws ApplicationException {
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exceprion in delete device" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public DeviceDTO findByPk(long pk) throws ApplicationException {
		
		Session session = null;
		DeviceDTO dto = null;
		
		try {
			session = HibDataSource.getSession();
			dto = (DeviceDTO)session.get(DeviceDTO.class, pk);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Device by pk"+ e.getMessage());
			
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public DeviceDTO findByName(String deviceName) throws ApplicationException {
		
		Session session = null;
		DeviceDTO dto = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(DeviceDTO.class);
			criteria.add(Restrictions.eq("deviceName", deviceName));

			List list = criteria.list();

			if (list != null && list.size() > 0) {
				dto = (DeviceDTO) list.get(0);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Device by Name" + e.getMessage());

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
	public List search(DeviceDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(DeviceDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getDeviceSessionCode() != null && dto.getDeviceSessionCode().length() > 0) {
					criteria.add(Restrictions.like("deviceSessionCode", dto.getDeviceSessionCode() + "%"));
				}
				
				if (dto.getDeviceName() != null && dto.getDeviceName().length() > 0) {
					criteria.add(Restrictions.like("deviceName", dto.getDeviceName() + "%"));
				}

				if (dto.getUserName() != null && dto.getUserName().length() > 0) {
					criteria.add(Restrictions.like("userName", dto.getUserName() + "%")); 
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
			throw new ApplicationException("Exception in Device Search: " + e.getMessage());

		} finally {
			if (session != null) { 
				session.close();
			}
		}

		return list;

	}

}
