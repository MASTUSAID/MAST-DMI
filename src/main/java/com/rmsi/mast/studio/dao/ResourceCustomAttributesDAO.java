package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.ResourceCustomAttributes;
import com.rmsi.mast.studio.domain.ResourceClassification;
import com.rmsi.mast.studio.domain.ResourcePOIAttributeValues;

public interface ResourceCustomAttributesDAO extends GenericDAO<ResourceCustomAttributes, Integer> {
	
	List<ResourceCustomAttributes> getByProjectId(Integer Id);
	
	ResourceCustomAttributes getByCustomattributeId(Integer Id);
	List<Object[]> getResourceCustomAttributeValuesAndDatatypeBylandId(Integer projectId, Integer Id);
	
	List<Object[]> getResourcePoiDatatypeBylandId(Integer projectId, Integer Id);
	
	List<ResourcePOIAttributeValues> getResourcePoiDataBylandId(Integer projectId, Integer Id);
}
