package com.rmsi.mast.studio.dao;

import com.rmsi.mast.studio.domain.ResourceClassification;

public interface ResourceClassificationDAO extends GenericDAO<ResourceClassification, Integer>{
	
	ResourceClassification getById(Integer Id);
	

}
