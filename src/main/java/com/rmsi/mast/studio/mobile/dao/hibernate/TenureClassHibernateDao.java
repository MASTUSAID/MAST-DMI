/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.TenureClass;
import com.rmsi.mast.studio.mobile.dao.TenureClassDao;

/**
 * @author Shruti.Thakur
 *
 */
@Repository
public class TenureClassHibernateDao extends
		GenericHibernateDAO<TenureClass, Integer> implements TenureClassDao {
	private static final Logger logger = Logger.getLogger(TenureClassHibernateDao.class);

	@Override
	public TenureClass getTenureClassById(int tenureId) {

		try {
			String query = "select t.* from tenure_class t inner join "
					+ "attribute_options ao on ao.parent_id = t.tenureclass_id "
					+ "where ao.id =" + tenureId;

			@SuppressWarnings("unchecked")
			List<TenureClass> tenureClass = getEntityManager()
					.createNativeQuery(query, TenureClass.class)
					.getResultList();

			if (tenureClass != null && tenureClass.size() > 0) {
				return tenureClass.get(0);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
		return null;
	}

}
