package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.DisputeStatus;

public interface DisputeStatusDao extends GenericDAO<DisputeStatus, Integer> {
	
	public  DisputeStatus getDisputeStatusById(int id);
	public List<DisputeStatus> getAllDisputeStatus();

}
