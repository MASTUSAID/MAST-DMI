package com.rmsi.mast.studio.mobile.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.CustomAttributeOptions;

public interface CustomAttributeOptionsDAO extends GenericDAO<CustomAttributeOptions, Integer> {
	
	/**
	 * This will be used for getting Attribute Options by its ID
	 * 
	 * @param attributeId
	 * @return
	 */
	List<CustomAttributeOptions> getAttributeOptions(Integer attributeId);

}
