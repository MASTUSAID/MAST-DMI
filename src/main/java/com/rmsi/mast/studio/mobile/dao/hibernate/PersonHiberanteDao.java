/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.Person;
import com.rmsi.mast.studio.mobile.dao.PersonDao;

/**
 * @author Shruti.Thakur
 *
 */
@Repository
public class PersonHiberanteDao extends GenericHibernateDAO<Person, Long>
		implements PersonDao {
	private static final Logger logger = Logger.getLogger(PersonHiberanteDao.class);
	@Override
	public Person addPerson(List<Person> personList) {

		try {
			Iterator<Person> personIter = personList.iterator();

			Person person = null;

			while (personIter.hasNext()) {

				person = personIter.next();

				makePersistent(person);

			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
		return null;
	}

	@Override
	public Person findPersonById(Long gid) {

		String query = "select p from Person p where p.person_gid =:gid";

		try {

			@SuppressWarnings("unchecked")
			List<Person> personList = getEntityManager().createQuery(query)
					.setParameter("gid", gid).getResultList();

			if (personList.size() > 0) {

				return personList.get(0);

			}

		} catch (Exception ex) {
			
			logger.error(ex);

			System.out.println("Exception in fetching PERSON:::: " + ex);
			throw ex;

		}
		return null;
	}

}
