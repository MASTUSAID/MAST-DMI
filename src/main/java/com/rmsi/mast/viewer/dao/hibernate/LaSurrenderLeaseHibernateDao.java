package com.rmsi.mast.viewer.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.LaLease;
import com.rmsi.mast.studio.domain.LaSurrenderLease;
import com.rmsi.mast.viewer.dao.LaLeaseDao;
import com.rmsi.mast.viewer.dao.LaSurrenderLeaseDao;

@Repository
public class LaSurrenderLeaseHibernateDao extends GenericHibernateDAO<LaSurrenderLease, Integer> implements LaSurrenderLeaseDao{


	Logger logger = Logger.getLogger(LaSurrenderLeaseHibernateDao.class);
	
	@Override
	public LaSurrenderLease savesurrenderLease(LaSurrenderLease laLease) {
		try {
            return makePersistent(laLease);

        } catch (Exception ex) {
            logger.error(ex);
            throw ex;
        }
	}


}
