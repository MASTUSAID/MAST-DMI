/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.GroupType;
import com.rmsi.mast.studio.mobile.dao.GroupTypeDao;

/**
 * @author Shruti.Thakur
 *
 */
@Repository
public class GroupTypeHibernateDao extends
		GenericHibernateDAO<GroupType, Integer> implements GroupTypeDao {

	private static final Logger logger = Logger
			.getLogger(GroupTypeHibernateDao.class);

	@Override
	public GroupType getGroupTypeById(int groupTypeId) {
		try {

			String query = "select gt.* from group_type gt inner join "
					+ "attribute_options ao on ao.parent_id = gt.group_id where"
					+ " ao.id = " + groupTypeId;

			@SuppressWarnings("unchecked")
			List<GroupType> groupType = getEntityManager().createNativeQuery(
					query, GroupType.class).getResultList();

			if (groupType != null && groupType.size() > 0) {
				return groupType.get(0);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
		return null;
	}

}
