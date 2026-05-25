package in.co.rays.project_3.model;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import in.co.rays.project_3.dto.EmployeeDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class EmployeeModelHibImpl implements EmployeeModelInt {

	@Override
	public void add(EmployeeDTO dto) throws ApplicationException, DuplicateRecordException {

		EmployeeDTO existsDto = findByName(dto.getEmployeeCode());

		if (existsDto != null) {
			throw new DuplicateRecordException("Employee  Already exists");
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
			throw new ApplicationException("Exception in add Employee" + e.getMessage());

		} finally {
			session.close();
		}

	}

	@Override
	public void update(EmployeeDTO dto) throws ApplicationException, DuplicateRecordException {

		EmployeeDTO existDto = findByName(dto.getEmployeeCode());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Employee Code  Already exists");
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
			throw new ApplicationException("Exception in update Employee" + e.getMessage());

		} finally {
			session.close();
		}

	}

	@Override
	public void delete(EmployeeDTO dto) throws ApplicationException {
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			tx.rollback();
			throw new ApplicationException("Exception in delete Employee" + e.getMessage());
			
		}finally {
			session.close();
		}

	}

	@Override
	public EmployeeDTO findByPk(long pk) throws ApplicationException {
		
		EmployeeDTO dto = null;
		Session session = null;
		
		try {
			session = HibDataSource.getSession();
			dto = (EmployeeDTO)  session.get(EmployeeDTO.class, pk);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Employee by pk" + e.getMessage());
			
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public EmployeeDTO findByName(String employeeCode) throws ApplicationException {
		
		EmployeeDTO dto = null;
		Session session = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(EmployeeDTO.class);

			criteria.add(Restrictions.eq("employeeCode", employeeCode));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (EmployeeDTO) list.get(0);
			}

		} catch (Exception e) {

			e.printStackTrace();

			throw new ApplicationException("Exception in Employee By Code " + e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}
		}

		return dto;
	}

	@Override
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return search(null, 0, 0);
	}

	@Override
	public List search(EmployeeDTO dto, int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(EmployeeDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				
				if (dto.getEmployeeCode() != null && dto.getEmployeeCode().length() > 0) {
					criteria.add(Restrictions.like("employeeCode", dto.getEmployeeCode() + "%"));
				}
				
				if (dto.getEmployeeName() != null && dto.getEmployeeName().length() > 0) {
					criteria.add(Restrictions.like("employeeName", dto.getEmployeeName() + "%"));
				}
				
				if (dto.getBasicSalary() != null && dto.getBasicSalary().length() > 0) {
					criteria.add(Restrictions.like("basicSalary", dto.getBasicSalary() + "%"));
				}

				if (dto.getBonus() != null && dto.getBonus().length() > 0) {
					criteria.add(Restrictions.like("bonus", dto.getBonus() + "%"));
				}
				
				if (dto.getNetsalary() != null && dto.getNetsalary().length() > 0) {
					criteria.add(Restrictions.like("netsalary", dto.getNetsalary()));
				}

			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Courier Search: " + e.getMessage());

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

}
