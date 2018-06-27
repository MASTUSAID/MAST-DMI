package com.rmsi.mast.viewer.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.LaExtRegistrationLandShareType;


public interface LaExtRegistrationLandShareTypeDao extends GenericDAO<LaExtRegistrationLandShareType, Long>{
	
	
	LaExtRegistrationLandShareType getShareTypeObjectByLandId(Long landid);
	
	boolean updateRegistrationSharetype(Long sharetypeid, Long landId);

}
		