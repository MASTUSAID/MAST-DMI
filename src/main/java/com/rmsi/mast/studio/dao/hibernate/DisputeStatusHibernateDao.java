package com.rmsi.mast.studio.dao.hibernate;

import com.rmsi.mast.studio.dao.DisputeStatusDao;
import com.rmsi.mast.studio.domain.DisputeStatus;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class DisputeStatusHibernateDao extends GenericHibernateDAO<DisputeStatus, Integer> implements DisputeStatusDao {
    private static final Logger logger = Logger.getLogger(DisputeStatusHibernateDao.class);
}
