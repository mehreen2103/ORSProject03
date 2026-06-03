package in.co.rays.project_3.model;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import in.co.rays.project_3.dto.DispatchDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class DispatchModelHibImpl implements DispatchModelInt {

	@Override
	public void add(DispatchDTO dto) throws ApplicationException, DuplicateRecordException {

		DispatchDTO existsDto = findByName(dto.getCourierName());

		if (existsDto != null) {
			throw new DuplicateRecordException("Courier Name  Already exists");
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
			throw new ApplicationException("Exception in add dispatch" + e.getMessage());
		} finally {
			session.close();
		}

	}

	@Override
	public void update(DispatchDTO dto) throws ApplicationException, DuplicateRecordException {

		DispatchDTO existDto = findByName(dto.getCourierName());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("courier name  Already exists");
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
			throw new ApplicationException("Exception in update dispatch" + e.getMessage());
		} finally {
			session.close();
		}

	}

	@Override
	public void delete(DispatchDTO dto) throws ApplicationException {

		Session session = HibDataSource.getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ApplicationException("Exception in delete dispatch" + e.getMessage());
		} finally {
			session.close();
		}

	}

	@Override
	public DispatchDTO findByPk(long pk) throws ApplicationException {

		DispatchDTO dto = null;
		Session session = null;

		try {
			session = HibDataSource.getSession();
			dto = (DispatchDTO) session.get(DispatchDTO.class, pk);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in dispatch by pk" + e.getMessage());

		} finally {
			session.close();
		}
		return dto;
	}

	@Override
	public DispatchDTO findByName(String courierName) throws ApplicationException {

		DispatchDTO dto = null;
		Session session = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(DispatchDTO.class);

			criteria.add(Restrictions.eq("courierName", courierName));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (DispatchDTO) list.get(0);
			}

		} catch (Exception e) {

			e.printStackTrace();

			throw new ApplicationException("Exception in dispatch By Code " + e.getMessage());

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
	public List search(DispatchDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(DispatchDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getDispatchDate() != null) {
					criteria.add(Restrictions.eq("dispatchDate", dto.getDispatchDate()));
				}

				if (dto.getStatus() != null && dto.getStatus().length() > 0) {
					criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
				}

				if (dto.getCourierName() != null && dto.getCourierName().length() > 0) {
					criteria.add(Restrictions.like("courierName", dto.getCourierName() + "%"));
				}

			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in dipatch Search: " + e.getMessage());

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

}
