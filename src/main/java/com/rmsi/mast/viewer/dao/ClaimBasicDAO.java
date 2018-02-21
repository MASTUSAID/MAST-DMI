package com.rmsi.mast.viewer.dao;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.fetch.ClaimBasic;

public interface ClaimBasicDAO {

	@Transactional
	ClaimBasic saveClaimBasicDAO(ClaimBasic objClaimBasic);
}
