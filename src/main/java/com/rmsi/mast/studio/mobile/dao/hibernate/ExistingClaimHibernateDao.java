package com.rmsi.mast.studio.mobile.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.ExistingClaim;
import com.rmsi.mast.studio.domain.SpatialUnit;
import com.rmsi.mast.studio.mobile.dao.ExistingClaimDao;
import com.rmsi.mast.studio.mobile.dao.SpatialUnitDao;

@Repository
public class ExistingClaimHibernateDao extends
GenericHibernateDAO<ExistingClaim, Long> implements ExistingClaimDao{

	@Override
	public ExistingClaim addExistingClaim(ExistingClaim existingClaim) {

        try {
            return makePersistent(existingClaim);

        } catch (Exception ex) {
            System.out.println("Exception while adding data...." + ex);
            throw ex;
        }
    }

}
