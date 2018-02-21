package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.ResourceSubClassification;

public interface ResourceSubClassificationDAO extends GenericDAO<ResourceSubClassification, Integer>{

	List<ResourceSubClassification> getAllSubClassifications();
	
	ResourceSubClassification getById(Integer Id);
	
}
