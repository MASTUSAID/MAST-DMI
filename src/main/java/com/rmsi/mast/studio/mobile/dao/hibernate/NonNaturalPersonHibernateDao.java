package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.NonNaturalPerson;
import com.rmsi.mast.studio.mobile.dao.NonNaturalPersonDao;

@Repository
public class NonNaturalPersonHibernateDao extends
		GenericHibernateDAO<NonNaturalPerson, Long> implements
		NonNaturalPersonDao {
	private static final Logger logger = Logger.getLogger(NonNaturalPersonHibernateDao.class);

	@Override
	public List<NonNaturalPerson> findById(Long id) {

		try {
			Query query = getEntityManager()
					.createQuery(
							"Select nnp from NonNaturalPerson nnp where (nnp.person_gid = :gid and nnp.active=true)");
			@SuppressWarnings("unchecked")
			List<NonNaturalPerson> nonnaturalperson = query.setParameter("gid",
					id).getResultList();

			if (nonnaturalperson.size() > 0) {
				return nonnaturalperson;
			} else {
				return null;
			}
		} catch (Exception e) {

			logger.error(e);
			throw e;
		}

	}

	@Override
	public NonNaturalPerson addNonNaturalPerson(
			NonNaturalPerson nonNaturalPerson) {

		try {

			return makePersistent(nonNaturalPerson);

		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
	}

	@Override
	public void addOrUpdateNonNaturalPerson(
			List<NonNaturalPerson> nonNaturalPersonList) {
		// TODO Auto-generated method stub

	}

}
