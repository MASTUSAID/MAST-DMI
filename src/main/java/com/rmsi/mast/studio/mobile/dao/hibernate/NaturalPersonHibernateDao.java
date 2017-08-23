/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.NaturalPerson;
import com.rmsi.mast.studio.mobile.dao.NaturalPersonDao;

/**
 * @author Shruti.Thakur
 *
 */

@Repository
public class NaturalPersonHibernateDao extends
GenericHibernateDAO<NaturalPerson, Integer> implements NaturalPersonDao {
	private static final Logger logger = Logger.getLogger(NaturalPersonHibernateDao.class);

	@Override
	public NaturalPerson addNaturalPerson(NaturalPerson naturalPerson) {

		try {

			return makePersistent(naturalPerson);

		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
	}

	@Override
	public void getNaturalPersonByProjectId(String projectId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<NaturalPerson> findById(Long id) {
		try {
			Query query = getEntityManager().createQuery(
					"Select p from Person p where p.person_gid = :gid");
			@SuppressWarnings("unchecked")
			List<NaturalPerson> naturalperson = query.setParameter("gid", id)
			.getResultList();

			if (naturalperson.size() > 0) {
				return naturalperson;
			} else {
				return new ArrayList<NaturalPerson>();
			}
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}

	}

	@Override
	public List<NaturalPerson> getNaturalPerson() {
		String query = "select n from NaturalPerson n";

		@SuppressWarnings("unchecked")
		List<NaturalPerson> naturalPerson = getEntityManager().createQuery(
				query).getResultList();

		if (!naturalPerson.isEmpty()) {
			return naturalPerson;
		}

		return null;
	}

	@Override
	public ArrayList<Long>  findOwnerByGid(ArrayList<Long> naturalPerson) {


		if(naturalPerson.size()>1)
		{ 
			String stmt = "Select np from NaturalPerson np where (np.person_gid in :gid and np.owner=true)";

			Query query = getEntityManager().createQuery(stmt);


			@SuppressWarnings("unchecked")
			List<NaturalPerson>  naturalpersonlst = query.setParameter("gid", naturalPerson).getResultList();

			if(naturalpersonlst.size()>0)

			{
				ArrayList<Long> naturalPerson1= new ArrayList<Long>();
				
				for (int i = 0; i < naturalpersonlst.size(); i++) {
					naturalPerson1.add(naturalpersonlst.get(i).getPerson_gid());
				}
				return naturalPerson1;


			}

			else{

				return naturalPerson;
			}


		}

		else{

			return	naturalPerson;

		}

	}

}
