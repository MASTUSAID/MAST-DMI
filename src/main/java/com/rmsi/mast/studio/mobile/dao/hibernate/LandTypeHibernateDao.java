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

			String query = "select lt.* from la_baunit_landtype lt inner join"
					+ " la_ext_attributeoptions ao on ao.parentid = lt.landtypeid "
					+ "where ao.attributeoptionsid = " + landTypeId;

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

	@Override
	public LandType getLandTypeBylandtypeId(int landTypeId) {
		try {

			String query = "select lt from LandType lt where lt.landtypeid  = " + landTypeId;

			@SuppressWarnings("unchecked")
			List<LandType> landType = getEntityManager().createQuery(query).getResultList();

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
