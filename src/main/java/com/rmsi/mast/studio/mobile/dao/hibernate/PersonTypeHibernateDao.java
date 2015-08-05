/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.PersonType;
import com.rmsi.mast.studio.mobile.dao.PersonTypeDao;

/**
 * @author Shruti.Thakur
 *
 */
@Repository
public class PersonTypeHibernateDao extends
		GenericHibernateDAO<PersonType, Long> implements PersonTypeDao {
	private static final Logger logger = Logger.getLogger(PersonTypeHibernateDao.class);

	@Override
	public PersonType getPersonTypeById(long personTypeGid) {

		try {
			String query = "select p from PersonType p where p.person_type_gid = :personTypeGid";

			@SuppressWarnings("unchecked")
			List<PersonType> personType = getEntityManager().createQuery(query)
					.setParameter("personTypeGid", personTypeGid)
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
