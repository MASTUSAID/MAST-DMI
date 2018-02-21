package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.ResourceCustomAttributes;
import com.rmsi.mast.studio.domain.ResourceClassification;

public interface ResourceCustomAttributesDAO extends GenericDAO<ResourceCustomAttributes, Integer> {
	
	List<ResourceCustomAttributes> getByProjectId(Integer Id);
	
	ResourceCustomAttributes getByCustomattributeId(Integer Id);
}
