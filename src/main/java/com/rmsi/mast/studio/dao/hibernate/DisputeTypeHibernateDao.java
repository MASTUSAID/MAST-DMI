package com.rmsi.mast.studio.dao.hibernate;

import com.rmsi.mast.studio.dao.DisputeTypeDao;
import com.rmsi.mast.studio.domain.DisputeType;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class DisputeTypeHibernateDao extends GenericHibernateDAO<DisputeType, Integer> implements DisputeTypeDao {
    private static final Logger logger = Logger.getLogger(DisputeTypeHibernateDao.class);
}
