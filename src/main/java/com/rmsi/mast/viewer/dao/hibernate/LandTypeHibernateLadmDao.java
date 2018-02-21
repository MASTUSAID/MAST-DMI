package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.LandType;
import com.rmsi.mast.viewer.dao.LandTypeLadmDAO;

/**
 * 
 * @author Abhay.Pandey
 *
 */

@Repository
public class LandTypeHibernateLadmDao extends GenericHibernateDAO<LandType, Integer>
implements LandTypeLadmDAO{

	Logger logger = Logger.getLogger(LandTypeHibernateLadmDao.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LandType> getAllLandType() {
		try {
			Query query = getEntityManager().createQuery("select lt from LandType lt where isactive = true");
			List<LandType> lstLandTypes = query.getResultList();
			return lstLandTypes;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

}
