package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.AttributeCategory;
import com.rmsi.mast.studio.domain.DatatypeId;

public interface AttributeCategoryDAO extends GenericDAO<AttributeCategory, Long> {


	
	List<AttributeCategory> findAttributeCategoryByTypeId(Integer id);
	
	List<AttributeCategory> findAllAttributeCategory();
}
