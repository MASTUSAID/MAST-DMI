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
import java.math.BigInteger;
import javax.persistence.NoResultException;
import javax.persistence.Query;

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

    @Override
    public Long findPersonIdClientId(String clientId, Long usin) {
        Query q = getEntityManager().createNativeQuery("SELECT p.person_gid "
                + "FROM person p inner join social_tenure_relationship r on p.person_gid = r.person_gid "
                + "WHERE p.mobile_group_id = :clientId and r.usin = :usin limit 1");
        q.setParameter("clientId", clientId).setParameter("usin", usin);

        Object personId;

        try {
            personId = q.getSingleResult();
        } catch (NoResultException nre) {
            personId = null;
        }

        if (personId == null) {
            // Search through non natural representative
            q = getEntityManager().createNativeQuery("SELECT np.gid "
                    + "FROM (natural_person np inner join person p on np.gid = p.person_gid) inner join (non_natural_person nnp inner join social_tenure_relationship r on nnp.non_natural_person_gid = r.person_gid) on nnp.poc_gid = np.gid "
                    + "WHERE p.mobile_group_id = :clientId and r.usin = :usin limit 1");
            q.setParameter("clientId", clientId).setParameter("usin", usin);

            try {
                personId = q.getSingleResult();
            } catch (NoResultException nre) {
                personId = null;
            }

            if (personId == null) {
                // Search through dispute
                q = getEntityManager().createNativeQuery("SELECT p.person_gid "
                        + "FROM dispute d inner join (dispute_person dp inner join person p on dp.person_id = p.person_gid) on d.id = dp.dispute_id "
                        + "WHERE p.mobile_group_id = :clientId and d.usin = :usin");
                q.setParameter("clientId", clientId).setParameter("usin", usin);
                
                try {
                    personId = q.getSingleResult();
                } catch (NoResultException nre) {
                    personId = null;
                }
            }
        }

        if (personId != null) {
            return ((BigInteger) personId).longValue();
        }

        return null;
    }
}
