package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.ExistingClaim;

public interface ExistingClaimDao extends GenericDAO<ExistingClaim, Long>{
	
	ExistingClaim addExistingClaim(ExistingClaim existingClaim);

}
