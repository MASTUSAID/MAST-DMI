package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.LaLease;
import com.rmsi.mast.studio.domain.LaSurrenderLease;

/**
 * 
 * @author Abhay.Pandey
 *
 */

public interface LaLeaseDao extends GenericDAO<LaLease, Integer>{

	LaLease saveLease(LaLease laLease);
	LaLease getLeaseById(Integer leaseId);
	boolean disablelease(Long personid, Long landid);
	boolean islandunderlease(Long landid);
	LaSurrenderLease getSurrenderLeaseById(Integer leaseId);
	List<LaLease> getleaseeListByLandId(Long landId);
	LaLease getleaseobjbylandandprocessid(Long landId, Long processId);
}
