package com.rmsi.mast.studio.mobile.service;

import java.util.List;

import com.rmsi.mast.studio.domain.ResourceClassification;

public interface ResourceClassificationServise {
	
	List<ResourceClassification> getAllClassifications(); 
	ResourceClassification getById(Integer Id);

}
