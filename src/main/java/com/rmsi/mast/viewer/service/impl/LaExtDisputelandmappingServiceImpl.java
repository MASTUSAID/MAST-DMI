package com.rmsi.mast.viewer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.dao.LaExtDisputelandmappingDAO;
import com.rmsi.mast.studio.domain.LaExtDisputelandmapping;
import com.rmsi.mast.viewer.service.LaExtDisputelandmappingService;

@Service
public class LaExtDisputelandmappingServiceImpl implements LaExtDisputelandmappingService {

	@Autowired
	LaExtDisputelandmappingDAO laExtDisputelandmappingDAO;
	
	
	@Override
	public LaExtDisputelandmapping findLaExtDisputelandmappingByPartyId(long partyid) {
		
		return laExtDisputelandmappingDAO.findLaExtDisputelandmappingByPartyId(partyid);
	}


	@Override
	@Transactional
	public LaExtDisputelandmapping saveLaExtDisputelandmapping(LaExtDisputelandmapping objLaExtDisputelandmapping) {
		// TODO Auto-generated method stub
		return laExtDisputelandmappingDAO.saveLaExtDisputelandmapping(objLaExtDisputelandmapping);
	}


	@Override
	public List<LaExtDisputelandmapping> findLaExtDisputelandmappingListByLandId(
			long landid) {
		// TODO Auto-generated method stub
		return laExtDisputelandmappingDAO.findLaExtDisputelandmappingByLandId(landid);
	}




}
