package com.rmsi.mast.viewer.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.fetch.ClaimBasic;
import com.rmsi.mast.viewer.dao.ClaimBasicDAO;


@Repository
public class ClaimBasicHibernateDAO  extends  GenericHibernateDAO<ClaimBasic, Integer>implements ClaimBasicDAO {

	@Override
	public ClaimBasic saveClaimBasicDAO(ClaimBasic objClaimBasic) {
		
		try {
            return makePersistent(objClaimBasic);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

}
