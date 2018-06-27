package com.rmsi.mast.viewer.service;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.LaExtParcelSplitLand;

public interface LaExtParcelSplitLandService {

	
	@Transactional
	public void  addLaExtParcelSplitLandService(LaExtParcelSplitLand objLaExtParcelSplitLandService);
	
	@Transactional
	public boolean deleteLaExtParcelSplitBylandId (Long landid);
}
