

package com.rmsi.mast.studio.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.AttributeCategoryDAO;
import com.rmsi.mast.studio.dao.DataTypeIdDAO;
import com.rmsi.mast.studio.domain.AttributeCategory;
import com.rmsi.mast.studio.domain.DatatypeId;
import com.rmsi.mast.studio.service.AttributeCategoryService;
import com.rmsi.mast.studio.service.DataTypeIdService;

@Service
public class AttributeCategoryServiceImpl implements AttributeCategoryService {

	@Autowired
	AttributeCategoryDAO attributecategoryDAO;
	
	
	
	@Override
	public List<AttributeCategory> findallAttributeCategories() {
		return  attributecategoryDAO.findAll();
		
	}



	


}
