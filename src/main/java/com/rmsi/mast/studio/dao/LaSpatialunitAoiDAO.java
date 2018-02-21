package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.LaSpatialunitAOI;
import com.rmsi.mast.studio.domain.ResourceClassification;

public interface LaSpatialunitAoiDAO extends GenericDAO<LaSpatialunitAOI, Integer>{

	
	List<LaSpatialunitAOI> getByUserId(Integer Id);
	
}
