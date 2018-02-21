package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.LaExtDisputelandmapping;

public interface LaExtDisputelandmappingDAO {
	
 
	LaExtDisputelandmapping findLaExtDisputelandmappingByPartyId(long partyid);
	 LaExtDisputelandmapping saveLaExtDisputelandmapping(LaExtDisputelandmapping objLaExtDisputelandmapping);
	 LaExtDisputelandmapping findLaExtDisputelandmappingById(Integer Id);
	List<LaExtDisputelandmapping> findLaExtDisputelandmappingByLandId(Long Id);
	
	LaExtDisputelandmapping findLaExtDisputelandmappingByLandIdDisputeIdAndPartyId(Long landId,Integer disputeId,Long partyId );
}
