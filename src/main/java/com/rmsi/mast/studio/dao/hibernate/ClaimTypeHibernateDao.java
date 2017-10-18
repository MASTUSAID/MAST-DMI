package com.rmsi.mast.studio.dao.hibernate;

import com.rmsi.mast.studio.domain.ClaimType;
import com.rmsi.mast.studio.dao.ClaimTypeDao;
import org.springframework.stereotype.Repository;

@Repository
public class ClaimTypeHibernateDao extends GenericHibernateDAO<ClaimType, String> implements ClaimTypeDao {
    
}
