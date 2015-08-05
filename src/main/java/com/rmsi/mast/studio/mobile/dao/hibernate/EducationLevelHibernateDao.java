/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.EducationLevel;
import com.rmsi.mast.studio.mobile.dao.EducationLevelDao;

/**
 * @author shruti.thakur
 *
 */
@Repository
public class EducationLevelHibernateDao extends
		GenericHibernateDAO<EducationLevel, Integer> implements
		EducationLevelDao {
	private static final Logger logger = Logger.getLogger(EducationLevelHibernateDao.class);

	@Override
	public EducationLevel getEducationLevelById(int educationLevelId) {

		try {
			String query = "select e.* from education_level e inner join attribute_options ao on ao.parent_id = e.level_id where ao.id ="
					+ educationLevelId;

			@SuppressWarnings("unchecked")
			List<EducationLevel> educationLevel = getEntityManager()
					.createNativeQuery(query, EducationLevel.class)
					.getResultList();

			if (educationLevel != null && educationLevel.size() > 0) {
				return educationLevel.get(0);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
		return null;
	}
}
