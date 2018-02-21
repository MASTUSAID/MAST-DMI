package com.rmsi.mast.viewer.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.fetch.ClaimBasicLand;

public interface ClaimBasicLandDao extends GenericDAO<ClaimBasicLand, Long>{

	ClaimBasicLand getClaimBasicLandById(Long id);
}
