package com.rmsi.mast.studio.mobile.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.CustomAttributes;
import com.rmsi.mast.studio.domain.ResourceAttributeValues;
import com.rmsi.mast.studio.domain.ResourcePOIAttributeValues;

public interface CustomAttributesDAO extends GenericDAO<CustomAttributes, Integer>{

	CustomAttributes addResourceCustomAttributeValues (CustomAttributes resourceAttributevalues);
	
	List<CustomAttributes> getResourceAttributeValuesBylandId(Integer projectId, Integer landId);
	
	List<CustomAttributes> getResourceCustomAttributeValuesBylandId(Integer projectId, Integer landId);
	
	List<ResourcePOIAttributeValues> getResourcePoiValuesBylandId(Integer projectId, Integer landId);
}
