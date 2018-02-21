package com.rmsi.mast.studio.dao;

import com.rmsi.mast.studio.domain.ResourceAttributeValues;
import com.rmsi.mast.studio.domain.ResourceLandClassificationMapping;

public interface ResourceLandClassificationMappingDAO extends GenericDAO<ResourceLandClassificationMapping, Integer>{
	
	ResourceLandClassificationMapping addResourceLandClassifications (ResourceLandClassificationMapping resourceAttributevalues);

}
