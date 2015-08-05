/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.SoilQualityValues;
import com.rmsi.mast.studio.mobile.dao.SoilQualityValuesDao;

/**
 * @author Shruti.Thakur
 *
 */
@Repository
public class SoilQualityValuesHibernateDao extends
		GenericHibernateDAO<SoilQualityValues, Integer> implements
		SoilQualityValuesDao {

	private static final Logger logger = Logger
			.getLogger(SoilQualityValuesHibernateDao.class);

	@Override
	public SoilQualityValues getSoilQualityValuesById(int soilQualityValuesId) {
		try {

			String query = "select sq.* from soil_quality_values sq inner join"
					+ " attribute_options ao on ao.parent_id = sq.id where "
					+ "ao.id =" + soilQualityValuesId;

			@SuppressWarnings("unchecked")
			List<SoilQualityValues> soilQualityValues = getEntityManager()
					.createNativeQuery(query, SoilQualityValues.class).getResultList();

			if (soilQualityValues != null && soilQualityValues.size() > 0) {
				return soilQualityValues.get(0);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
		return null;
	}

}
