/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.OccupancyType;
import com.rmsi.mast.studio.mobile.dao.OccupancyTypeDao;

/**
 * @author Shruti.Thakur
 *
 */
@Repository
public class OccupancyTypeHibernateDao extends
		GenericHibernateDAO<OccupancyType, Integer> implements OccupancyTypeDao {
	
	private static final Logger logger = Logger.getLogger(OccupancyTypeHibernateDao.class);

	@Override
	public OccupancyType getOccupancyTypeById(int occId) {

		try {
			String query = "select o.* from occupancy_type o inner join attribute_options ao	"
					+ "on ao.parent_id = o.occupancy_type_id where ao.id = "
					+ occId;

			@SuppressWarnings("unchecked")
			List<OccupancyType> occupancyType = getEntityManager()
					.createNativeQuery(query, OccupancyType.class)
					.getResultList();

			if (occupancyType != null && occupancyType.size() > 0) {
				return occupancyType.get(0);
			}
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return null;
	}
}
