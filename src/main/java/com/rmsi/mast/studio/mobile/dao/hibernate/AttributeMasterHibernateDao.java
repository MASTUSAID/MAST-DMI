/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.mobile.dao.AttributeMasterDao;

/**
 * @author Shruti.Thakur
 *
 */
@Repository
public class AttributeMasterHibernateDao extends
		GenericHibernateDAO<AttributeMaster, Long> implements
		AttributeMasterDao {
	

	@Override
	public AttributeMaster getAttributteMasterById(long attributeId) {

		String query = "select a from AttributeMaster a where a.id = :attributeId";

		@SuppressWarnings("unchecked")
		List<AttributeMaster> attributeMaster = getEntityManager()
				.createQuery(query).setParameter("attributeId", attributeId)
				.getResultList();

		if (attributeMaster.size() > 0) {
			return attributeMaster.get(0);
		}

		return null;
	}

}
