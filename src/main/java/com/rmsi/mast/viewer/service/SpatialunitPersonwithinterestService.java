package com.rmsi.mast.viewer.service;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.SpatialUnitPersonWithInterest;

public interface SpatialunitPersonwithinterestService {

	
	@Transactional
	SpatialUnitPersonWithInterest  save(SpatialUnitPersonWithInterest objSpatialunitPersonwithinterest);
	
	SpatialUnitPersonWithInterest  findByObject(SpatialUnitPersonWithInterest objSpatialunitPersonwithinterest, Long landId);
	 
	SpatialUnitPersonWithInterest findSpatialUnitPersonWithInterestById(Long id);
	
}
