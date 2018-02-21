package com.rmsi.mast.viewer.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.LaSpatialunitLand;
/**
 * 
 * @author Abhay.Pandey
 *
 */

public interface LaSpatialunitLandDao extends GenericDAO<LaSpatialunitLand, Integer>{

	List<LaSpatialunitLand> getLaSpatialunitLandDetails(Long landid);
	
	List<LaSpatialunitLand> getLaSpatialunitLandDetailsQ(Integer landid);
	
	boolean updateLaSpatialunitLand(LaSpatialunitLand laSpatialunitLand);
	
	boolean addLaSpatialunitLand(LaSpatialunitLand laSpatialunitLand);
}
