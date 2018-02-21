package com.rmsi.mast.studio.mobile.service;

import java.util.List;

import com.rmsi.mast.studio.domain.LaSpatialunitAOI;

public interface LaSpatialunitAOIService {

	
	List<LaSpatialunitAOI> getAllClassifications(); 
	List<LaSpatialunitAOI> getByUserId(Integer Id);
}
