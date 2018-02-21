package com.rmsi.mast.studio.mobile.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.ResourceAttributeValues;
import com.rmsi.mast.studio.domain.fetch.ResourceDetails;

public interface ResourceAttributeValuesDAO extends GenericDAO<ResourceAttributeValues, Integer>{
	
	 ResourceAttributeValues addResourceAttributeValues (ResourceAttributeValues resourceAttributevalues);
	 List<ResourceAttributeValues> getResourceAttributeValuesBylandId(Integer Id);
	 List<ResourceDetails> getAllresouceByproject(String project,Integer startfrom);
	 Integer getAllresouceCountByproject(String project);
	 List<ResourceAttributeValues> getResourceAttributeValuesByMasterlandid(Integer Id);
	 
	 List<Object[]> getResourceAttributeValuesAndDatatypeBylandId(Integer Id);

}
