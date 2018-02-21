package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.fetch.ClaimBasicLand;
import com.rmsi.mast.viewer.dao.ClaimBasicLandDao;

@Repository
public class ClaimBasicLandHibernateDao extends GenericHibernateDAO<ClaimBasicLand, Long> implements ClaimBasicLandDao{

	@SuppressWarnings("unchecked")
	@Override
	public ClaimBasicLand getClaimBasicLandById(Long id) {
		
		Logger logger = Logger.getLogger(ClaimBasicLandHibernateDao.class);
		try {
			Query query = getEntityManager().createQuery(
			        "select cb from ClaimBasicLand cb where cb.isactive = true and cb.landid =:Id ");
			List<ClaimBasicLand> lstClaimBasicLand = query.setParameter("Id", id).getResultList();

			return lstClaimBasicLand.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return null;
		}
	}

}
