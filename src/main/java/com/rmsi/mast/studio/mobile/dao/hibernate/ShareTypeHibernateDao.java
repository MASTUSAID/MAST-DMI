/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.ShareType;
import com.rmsi.mast.studio.mobile.dao.ShareTypeDao;

/**
 * @author Shruti.Thakur
 *
 */
@Repository
public class ShareTypeHibernateDao extends
		GenericHibernateDAO<ShareType, Integer> implements ShareTypeDao {
	private static final Logger logger = Logger.getLogger(ShareTypeHibernateDao.class);

	@Override
	public ShareType getTenureRelationshipTypeById(int tenureRelationTypeId) {
		try {

			String query = "select s.* from share_type s inner join attribute_options ao	"
					+ "on ao.parent_id = s.gid where ao.id = "
					+ tenureRelationTypeId;

			@SuppressWarnings("unchecked")
			List<ShareType> relationshipType = getEntityManager()
					.createNativeQuery(query, ShareType.class).getResultList();

			if (relationshipType != null && relationshipType.size() > 0) {
				return relationshipType.get(0);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
		return null;
	}
}
