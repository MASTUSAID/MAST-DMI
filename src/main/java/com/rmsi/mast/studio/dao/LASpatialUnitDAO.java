package com.rmsi.mast.studio.dao;

public interface LASpatialUnitDAO {

	boolean  updateSpatialUnit(long landid,Integer applicationstatusid,Integer workflowstatusid);
	boolean  deleteSpatialUnit(long landid,Integer applicationstatusid,Integer workflowstatusid);
	
	
}
