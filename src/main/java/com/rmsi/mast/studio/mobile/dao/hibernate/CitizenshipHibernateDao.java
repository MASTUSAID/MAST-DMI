/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.Citizenship;
import com.rmsi.mast.studio.domain.MaritalStatus;
import com.rmsi.mast.studio.mobile.dao.CitizenshipDao;


@Repository
public class CitizenshipHibernateDao extends
		GenericHibernateDAO<Citizenship, Long> implements CitizenshipDao {
	private static final Logger logger = Logger.getLogger(CitizenshipHibernateDao.class);
	@Override
	public Citizenship getCitizensbyId(int val) {
		try {
			String query = "select cz.* from citizenship cz inner join attribute_options ao	"
					+ "on ao.parent_id = cz.id where ao.id = "
					+ val;
			@SuppressWarnings("unchecked")
			List<Citizenship> citizenship = getEntityManager()
					.createNativeQuery(query, Citizenship.class)
					.getResultList();

			if (citizenship != null && citizenship.size() > 0) {
				return citizenship.get(0);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}

		return null;
	}
	@Override
	public Citizenship findBycitizenId(long citizenship) {
		try {
			String query = "select cz.* from citizenship cz where cz.id ="
					+ citizenship;
			@SuppressWarnings("unchecked")
			List<Citizenship> citizenshipobj = getEntityManager()
					.createNativeQuery(query, Citizenship.class)
					.getResultList();

			if (citizenshipobj != null && citizenshipobj.size() > 0) {
				return citizenshipobj.get(0);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}

		return null;
	}
	
	
}