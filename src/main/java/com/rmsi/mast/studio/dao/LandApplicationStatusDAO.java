package com.rmsi.mast.studio.dao;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.LandApplicationStatus;

@Transactional
public interface LandApplicationStatusDAO extends GenericDAO<LandApplicationStatus, Integer>{
	
	
public LandApplicationStatus getLandApplicationStatusByLandId(Long landId);
public LandApplicationStatus addLandApplicationStatus(LandApplicationStatus spatialUnit);
}
