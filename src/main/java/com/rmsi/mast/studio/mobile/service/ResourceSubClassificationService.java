package com.rmsi.mast.studio.mobile.service;

import java.util.List;

import com.rmsi.mast.studio.domain.ResourceSubClassification;

public interface ResourceSubClassificationService {
	
	List<ResourceSubClassification> getAllSubClassifications(); 
	ResourceSubClassification getById(Integer Id);
	

}
