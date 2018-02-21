package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.ShareType;
import com.rmsi.mast.viewer.dao.LandShareTypeDao;
/**
 * 
 * @author Abhay.Pandey
 *
 */
@Repository
public class LandShareTypeHibernateDao extends GenericHibernateDAO<ShareType, Integer>
implements LandShareTypeDao{

	Logger logger = Logger.getLogger(LandShareTypeHibernateDao.class);
	@SuppressWarnings("unchecked")
	@Override
	public List<ShareType> getAlllandsharetype() {
		
		try {
			String str= "select st from ShareType st where isactive = true";
			Query query= getEntityManager().createQuery(str);
			List<ShareType> lstShareType = query.getResultList();
			return lstShareType;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

}
