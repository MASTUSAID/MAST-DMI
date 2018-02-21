package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.PersonType;
import com.rmsi.mast.viewer.dao.PersonTypeLDao;
/**
 * 
 * @author Abhay.Pandey
 *
 */

@Repository
public class PersonTypeLHibernateDao extends
GenericHibernateDAO<PersonType, Integer> implements PersonTypeLDao{

	private static final Logger logger = Logger.getLogger(PersonTypeLHibernateDao.class);
	@Override
	public PersonType getPersonTypeById(int personTypeGid) {
		try {
			String query = "select p from PersonType p where p.persontypeid = :personTypeid";

			@SuppressWarnings("unchecked")
			List<PersonType> personType = getEntityManager().createQuery(query)
					.setParameter("personTypeid", personTypeGid)
					.getResultList();

			if (personType.size() > 0) {
				return personType.get(0);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
		return null;
	}
}

