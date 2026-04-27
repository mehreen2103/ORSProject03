package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.DataImportDTO;
import in.co.rays.project_3.dto.GeoFenceDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class GeoFenceHibImpl implements GeoFenceModelInt{

	@Override
	public void add(GeoFenceDTO dto) throws ApplicationException, DuplicateRecordException {
		

		GeoFenceDTO existDto = findByName(dto.getGeoFenceCode());

		if (existDto != null) {
			throw new DuplicateRecordException("Fence already exists");
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
			throw new ApplicationException("Exception in add Fence" +e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(GeoFenceDTO dto) throws ApplicationException, DuplicateRecordException {
		
		GeoFenceDTO existDto = findByName(dto.getGeoFenceCode());

		if (existDto != null && existDto.getId() != dto.getId()) {
			
			throw new DuplicateRecordException("Fence already exist");
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
			throw new ApplicationException("Exception in update fence" + e.getMessage());
			
		}finally {
			session.close();
		}
		
	}

	@Override
	public void delete(GeoFenceDTO dto) throws ApplicationException {
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ApplicationException("Exception in delete fence" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public GeoFenceDTO findByPk(long pk) throws ApplicationException {
		Session session = null;
		GeoFenceDTO dto = null;
		
		try {
			session = 	HibDataSource.getSession();
			dto =(GeoFenceDTO) session.get(GeoFenceDTO.class, pk);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in fence by pk " + e.getMessage());
			
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public GeoFenceDTO findByName(String geoFenceCode) throws ApplicationException {
		
		
		Session session = null;
		GeoFenceDTO dto = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(GeoFenceDTO.class);
			criteria.add(Restrictions.eq("geoFenceCode", geoFenceCode));

			List list = criteria.list();

			if (list != null && list.size() > 0) {
				dto = (GeoFenceDTO) list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Fence by Code" + e.getMessage());

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
	public List search(GeoFenceDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(GeoFenceDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getGeoFenceCode() != null && dto.getGeoFenceCode().length() > 0) {
					criteria.add(Restrictions.like("geoFenceCode", dto.getGeoFenceCode() + "%"));
				}
				
				if (dto.getLocationName() != null && dto.getLocationName().length() > 0) {
					criteria.add(Restrictions.like("locationName", dto.getLocationName() + "%"));
				}

				if (dto.getRadius() != null && dto.getRadius().length() > 0) {
					criteria.add(Restrictions.like("radius", dto.getRadius() + "%")); 
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
			throw new ApplicationException("Exception in Fence Search: " + e.getMessage());

		} finally {
			if (session != null) { 
				session.close();
			}
		}

		return list;

	}

}
