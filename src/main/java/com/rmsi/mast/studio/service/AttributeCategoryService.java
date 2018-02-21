

package com.rmsi.mast.studio.service;

import java.util.List;

import com.rmsi.mast.studio.domain.AttributeCategory;
import com.rmsi.mast.studio.domain.DatatypeId;


public interface AttributeCategoryService {

	List<AttributeCategory> findallAttributeCategories();
	List<AttributeCategory> findAttributeCategoryByTypeId(Integer id);
	List<AttributeCategory> findAllAttributeCategory();



}
