/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.LandType;
import com.rmsi.mast.studio.mobile.dao.LandTypeDao;

/**
 * @author Shruti.Thakur
 *
 */
@Repository
public class LandTypeHibernateDao extends
		GenericHibernateDAO<LandType, Integer> implements LandTypeDao {

	private static final Logger logger = Logger
			.getLogger(LandTypeHibernateDao.class);

	@Override
	public LandType getLandTypeById(int landTypeId) {
		try {

			String query = "select lt.* from land_type lt inner join"
					+ " attribute_options ao on ao.parent_id = lt.landtype_id "
					+ "where ao.id = " + landTypeId;

			@SuppressWarnings("unchecked")
			List<LandType> landType = getEntityManager().createNativeQuery(
					query, LandType.class).getResultList();

			if (landType != null && landType.size() > 0) {
				return landType.get(0);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
		return null;
	}

}
