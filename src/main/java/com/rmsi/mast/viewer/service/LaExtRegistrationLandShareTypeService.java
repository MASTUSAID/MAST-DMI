package com.rmsi.mast.viewer.service;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.LaExtRegistrationLandShareType;


import com.rmsi.mast.studio.domain.LaExtRegistrationLandShareType;

public interface LaExtRegistrationLandShareTypeService {
	
	LaExtRegistrationLandShareType getShareTypeObjectByLandId(Long landid);
	
	@Transactional
	boolean updateRegistrationSharetype(Long sharetypeid, Long landId);

	@Transactional
	void addLaExtRegistrationLandShareType(LaExtRegistrationLandShareType objLaExtRegistrationLandShareType);
	
}
