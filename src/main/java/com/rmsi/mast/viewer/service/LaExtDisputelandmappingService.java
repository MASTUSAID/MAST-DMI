package com.rmsi.mast.viewer.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.LaExtDisputelandmapping;

public interface LaExtDisputelandmappingService {

	
	LaExtDisputelandmapping findLaExtDisputelandmappingByPartyId(long partyid);
	
	List<LaExtDisputelandmapping> findLaExtDisputelandmappingListByLandId(long landid);
	
	
	@Transactional
	LaExtDisputelandmapping saveLaExtDisputelandmapping(LaExtDisputelandmapping objLaExtDisputelandmapping);
}
