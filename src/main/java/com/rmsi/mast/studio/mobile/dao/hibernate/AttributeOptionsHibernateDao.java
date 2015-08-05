/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.AttributeOptions;
import com.rmsi.mast.studio.mobile.dao.AttributeOptionsDao;

/**
 * @author Shruti.Thakur
 *
 */
@Repository
public class AttributeOptionsHibernateDao extends
		GenericHibernateDAO<AttributeOptions, Integer> implements
		AttributeOptionsDao {
	private static final Logger logger = Logger.getLogger(AttributeOptionsHibernateDao.class);

	@Override
	public List<AttributeOptions> getAttributeOptions(Long attributeId) {

		String query = "select a from AttributeOptions a where a.attributeId = :attributeId";

		try {
			@SuppressWarnings("unchecked")
			List<AttributeOptions> attributeOptions = getEntityManager()
					.createQuery(query)
					.setParameter("attributeId", (int) (long) attributeId)
					.getResultList();

			if (!attributeOptions.isEmpty()) {
				return attributeOptions;
			}
		} catch (Exception ex) {
			logger.error(ex);
			
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getAttributeOptionsId(Integer attributeId,int parentid) 
	{
		String query = "select a.id from AttributeOptions a where a.attributeId = :attributeId "
				+ "and a.parentId =:parentId";

		try {
			List<Integer> attributeids = getEntityManager()
					.createQuery(query)
					.setParameter("attributeId",attributeId)
					.setParameter("parentId",parentid)
					.getResultList();

			if (attributeids!=null && attributeids.size()>0) {
				return attributeids.get(0).toString();
			}
		} catch (Exception ex) {
			logger.error(ex);
			
		}
		return null;
	}

}
