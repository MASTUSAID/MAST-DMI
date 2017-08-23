/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.mobile.dao.SocialTenureDao;

/**
 * @author Shruti.Thakur
 *
 */
@Repository
public class SocialTenureHibernateDao extends
		GenericHibernateDAO<SocialTenureRelationship, Integer> implements
		SocialTenureDao {
	private static final Logger logger = Logger.getLogger(SocialTenureHibernateDao.class);

	@Override
	public List<SocialTenureRelationship> getSocailTenure() {
		try {
			String query = "select s from SocialTenureRelationship s";

			@SuppressWarnings("unchecked")
			List<SocialTenureRelationship> socailTenureList = getEntityManager()
					.createQuery(query).getResultList();

			if (!socailTenureList.isEmpty()) {
				return socailTenureList;
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
		return null;
	}

	@Override
	public SocialTenureRelationship addSocialTenure(
			SocialTenureRelationship socialTenureRelationship) {

		try {
			return makePersistent(socialTenureRelationship);
		} catch (Exception ex) {
			logger.error(ex);
			throw ex;
		}
	}

	@Override
	public List<SocialTenureRelationship> findSocailTenureByUsin(Long usin) {
		String query = "select str.* from social_tenure_relationship "
				+ "str inner join spatial_unit su on su.usin = str.usin where"
				+ " su.usin =" + usin +"and str.isactive=true";

		try {

			@SuppressWarnings("unchecked")
			List<SocialTenureRelationship> tenureList = getEntityManager()
					.createNativeQuery(query, SocialTenureRelationship.class).getResultList();

			if (tenureList.size() > 0) {

				return tenureList;

			}

		} catch (Exception ex) {

			logger.error(ex);
			throw ex;

		}
		return null;
	}

}
