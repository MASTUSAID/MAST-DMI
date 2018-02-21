package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.fetch.AllocateUser;
import com.rmsi.mast.studio.domain.fetch.La_spatialunit_aoi;

public interface AllocateUserDao extends GenericDAO<AllocateUser, Long>{

	
	List<AllocateUser> getAllocateUser();
	
	boolean updateUserAllocation(String[] userId,String[] allocID, long prjid, int created_by, int modifiedby, int applicationstatusid);
	
	//@@rmsi 
		boolean updateUserAllocationAoi(String[] allocId, String aoiName);

		List<La_spatialunit_aoi> getResourceAllInfo(String[] allocId);
}
