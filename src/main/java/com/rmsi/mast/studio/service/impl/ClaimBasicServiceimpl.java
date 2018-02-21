package com.rmsi.mast.studio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.domain.fetch.ClaimBasic;
import com.rmsi.mast.studio.service.ClaimBasicService;
import com.rmsi.mast.viewer.dao.ClaimBasicDAO;


@Service
public class ClaimBasicServiceimpl implements ClaimBasicService {

	
	@Autowired
	ClaimBasicDAO claimBasicDAO;

	@Override
	public ClaimBasic saveClaimBasicDAO(ClaimBasic objClaimBasic) {
		return claimBasicDAO.saveClaimBasicDAO(objClaimBasic);
	}
	
	
	
	
	
}
