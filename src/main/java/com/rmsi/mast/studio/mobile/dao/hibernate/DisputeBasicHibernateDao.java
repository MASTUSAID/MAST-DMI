package com.rmsi.mast.studio.mobile.dao.hibernate;

import org.apache.log4j.Logger;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.fetch.DisputeBasic;
import com.rmsi.mast.studio.mobile.service.impl.DisputeBasicDAO;

public class DisputeBasicHibernateDao extends GenericHibernateDAO<DisputeBasic, Long> implements DisputeBasicDAO {
    private static final Logger logger = Logger.getLogger(DisputeBasicHibernateDao.class);

	@Override
	public DisputeBasic save(DisputeBasic dispute) {
		// TODO Auto-generated method stub
		 try {
	            return makePersistent(dispute);
	        } catch (Exception e) {
	            logger.error(e);
	            return null;
	        }
		
	
	}

}
