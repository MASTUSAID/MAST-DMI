package com.rmsi.mast.studio.mobile.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.SpatialUnitPersonWithInterest;

public interface SpatialUnitPersonWithInterestDao extends
		GenericDAO<SpatialUnitPersonWithInterest, Long> {

	/**
	 * This will add person to database
	 * 
	 * @param person
	 * @return
	 */
	SpatialUnitPersonWithInterest addNextOfKin(
			List<SpatialUnitPersonWithInterest> nextOfKinList, Long usin);

        List<SpatialUnitPersonWithInterest> findByUsin(Long usin);
}
