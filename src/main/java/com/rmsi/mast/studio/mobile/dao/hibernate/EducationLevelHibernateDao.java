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
			String query = "select e.* from la_partygroup_educationlevel e inner join la_ext_attributeoptions ao on ao.parentid = e.educationlevelid where ao.attributeoptionsid ="
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

	@Override
	public EducationLevel getEducationLevelBypk(int educationLevelId) {
		
		try {
			String query = "select e from EducationLevel e  where e.educationlevelid =:id";

			@SuppressWarnings("unchecked")
			List<EducationLevel> educationLevel = getEntityManager().createQuery(query).setParameter("id", educationLevelId).getResultList();
					

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
