package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.AccountDTO;
import in.co.rays.project_3.dto.UserProfileDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class AccountModelHibImpl implements AccountModelInt {

	@Override
	public void add(AccountDTO dto) throws ApplicationException, DuplicateRecordException {

		AccountDTO existDto = findByCode(dto.getAccountCode());

		if (existDto != null) {
			throw new DuplicateRecordException("Account Already Exist");
		}

		Session session = HibDataSource.getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.commit();
			throw new ApplicationException("Exception in add account" + e.getMessage());
		} finally {
			session.close();
		}

	}

	@Override
	public void update(AccountDTO dto) throws ApplicationException, DuplicateRecordException {

		AccountDTO existsDto = findByCode(dto.getAccountCode());

		if (existsDto != null && existsDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Account Already exists");
		}

		Session session = HibDataSource.getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.commit();
			throw new ApplicationException("Exception in update account" + e.getMessage());
		} finally {
			session.close();
		}

	}

	@Override
	public void delete(AccountDTO dto) throws ApplicationException {

		Session session = HibDataSource.getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.commit();
			throw new ApplicationException("Exception in delete account" + e.getMessage());
		} finally {
			session.close();
		}

	}

	@Override
	public AccountDTO findByPk(long pk) throws ApplicationException {

		AccountDTO dto = null;
		Session session = null;

		try {
			session = HibDataSource.getSession();
			dto = (AccountDTO) session.get(AccountDTO.class, pk);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in account by pk");

		} finally {
			session.close();
		}
		return dto;
	}

	@Override
	public AccountDTO findByCode(String accountCode) throws ApplicationException {

		AccountDTO dto = null;
		Session session = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(AccountDTO.class);
			criteria.add(Restrictions.eq("accountCode", accountCode));

			List list = criteria.list();

			if (list != null && list.size() > 0) {
				dto = (AccountDTO) list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in accoount by Code" + e.getMessage());
		}
		return dto;
	}

	@Override
	public List list() throws ApplicationException {

		return search(null, 0, 0);
	}

	@Override
	public List search(AccountDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(AccountDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getAccountCode() != null && dto.getAccountCode().length() > 0) {
					criteria.add(Restrictions.like("accountCode", dto.getAccountCode() + "%"));
				}

				if (dto.getUsername() != null && dto.getUsername().length() > 0) {
					criteria.add(Restrictions.like("userName", dto.getUsername() + "%"));
				}
				if (dto.getAccountType() != null && dto.getAccountType().length() > 0) {
					criteria.add(Restrictions.like("accountType", dto.getAccountType() + "%"));
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
			throw new ApplicationException("Exception in Account Search: " + e.getMessage());

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;

	}

}
