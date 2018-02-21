package com.rmsi.mast.studio.mobile.dao;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.ResourceAttributeValues;
import com.rmsi.mast.studio.domain.ResourcePOIAttributeValues;

public interface ResourcePOIAttributeValuesDAO extends GenericDAO<ResourcePOIAttributeValues, Integer>{
	
	ResourcePOIAttributeValues addResourcePOIAttributeValues (ResourcePOIAttributeValues resourceAttributevalues);

}
