package com.rmsi.mast.viewer.service;

import java.util.List;

import com.rmsi.mast.studio.domain.ResourceAttributeValues;
import com.rmsi.mast.studio.domain.fetch.ResourceDetails;

public interface ResourceAttributeValuesService {

	 List<ResourceAttributeValues> getResourceAttributeValuesBylandId(Integer projectId, Integer Id);
	 List<ResourceDetails> getAllresouceByproject(String project,Integer startfrom);
	 Integer getAllresouceCountByproject(String project);
	 List<ResourceAttributeValues> getResourceAttributeValuesByMasterlandid(Integer Id);
	 List<Object[]> getResourceAttributeValuesAndDatatypeBylandId(Integer projectId, Integer Id);
	 
	 
}
