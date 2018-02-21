package com.rmsi.mast.studio.service;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.fetch.ClaimBasic;

public interface ClaimBasicService {

	
	@Transactional
	ClaimBasic saveClaimBasicDAO(ClaimBasic objClaimBasic);
	
}
