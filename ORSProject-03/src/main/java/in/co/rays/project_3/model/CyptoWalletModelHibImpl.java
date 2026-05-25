package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.CryptoWalletDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class CyptoWalletModelHibImpl implements CryptoWalletModelInt{

	@Override
	public void add(CryptoWalletDTO dto) throws ApplicationException, DuplicateRecordException {
		
		CryptoWalletDTO existsDto = findByName(dto.getCoinName());

		if (existsDto != null) {
			throw new DuplicateRecordException("CoinName  Already exists");
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
			throw new ApplicationException("Exception in add CryptoWallet" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void update(CryptoWalletDTO dto) throws ApplicationException, DuplicateRecordException {
		
		CryptoWalletDTO existDto = findByName(dto.getCoinName());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException(" CoinName  Already exists");
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
			throw new ApplicationException("Exception in update CryptoWallet" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public void delete(CryptoWalletDTO dto) throws ApplicationException {
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new ApplicationException("Exception in delete CryptoWallet" + e.getMessage());
		}finally {
			session.close();
		}
		
	}

	@Override
	public CryptoWalletDTO findByPk(long pk) throws ApplicationException {
		
		CryptoWalletDTO dto = null;
		Session session = null;
		
		try {
			session = HibDataSource.getSession();
			dto = (CryptoWalletDTO)session.get(CryptoWalletDTO.class, pk);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in pk by cryptowallet" + e.getMessage());
			
		}finally {
			session.close();
		}
		return dto;
	}

	@Override
	public CryptoWalletDTO findByName(String coinName) throws ApplicationException {


		CryptoWalletDTO dto = null;
		Session session = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(CryptoWalletDTO.class);

			criteria.add(Restrictions.eq("coinName", coinName));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (CryptoWalletDTO) list.get(0);
			}

		} catch (Exception e) {

			e.printStackTrace();

			throw new ApplicationException("Exception in Cryptowallet By coinname " + e.getMessage());

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
	public List search(CryptoWalletDTO dto, int pageNo, int pageSize) throws ApplicationException {


		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CryptoWalletDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				
				if (dto.getCoinName() != null && dto.getCoinName().length() > 0) {
					criteria.add(Restrictions.like("coinName", dto.getCoinName() + "%"));
				}
				
				if (dto.getQuantity() != null && dto.getQuantity().length() > 0) {
					criteria.add(Restrictions.like("quantity", dto.getQuantity() + "%"));
				}
				
				if (dto.getCurrentPrice() != null && dto.getCurrentPrice().length() > 0) {
					criteria.add(Restrictions.like("currentPrice", dto.getCurrentPrice() + "%"));
				}

				if (dto.getTotalValue() != null && dto.getTotalValue().length() > 0) {
					criteria.add(Restrictions.like("totalValue", dto.getTotalValue() + "%"));
				}
				
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in CourierWallet Search: " + e.getMessage());

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

}
