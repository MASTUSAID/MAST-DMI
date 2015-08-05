/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.SlopeValues;
import com.rmsi.mast.studio.mobile.dao.SlopeValuesDao;

/**
 * @author Shruti.Thakur
 *
 */
@Repository
public class SlopeValuesHibernateDao extends
		GenericHibernateDAO<SlopeValues, Integer> implements SlopeValuesDao {

	private static final Logger logger = Logger
			.getLogger(SlopeValuesHibernateDao.class);

	@Override
	public SlopeValues getSlopeValuesById(int slopeValuesId) {

		try {

			String query = "select sv.* from slope_values sv inner join "
					+ "attribute_options ao on ao.parent_id = sv.id where ao.id = "
					+ slopeValuesId;

			@SuppressWarnings("unchecked")
			List<SlopeValues> slopeValues = getEntityManager()
					.createNativeQuery(query, SlopeValues.class).getResultList();

			if (slopeValues != null && slopeValues.size() > 0) {
				return slopeValues.get(0);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
		return null;
	}

}
