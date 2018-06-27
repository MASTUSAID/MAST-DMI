package com.rmsi.mast.studio.mobile.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	SpatialUnitPersonWithInterest addNextOfKin(List<SpatialUnitPersonWithInterest> nextOfKinList, Long usin);
     List<SpatialUnitPersonWithInterest> findByUsin(Long usin);
     SpatialUnitPersonWithInterest findSpatialUnitPersonWithInterestById(Long id);
     SpatialUnitPersonWithInterest findSpatialUnitPersonWithInterestByObj(SpatialUnitPersonWithInterest obj, Long landId);
     List<SpatialUnitPersonWithInterest> findByUsinandTransid(Long usin, Long transid);
    
     
     
}
