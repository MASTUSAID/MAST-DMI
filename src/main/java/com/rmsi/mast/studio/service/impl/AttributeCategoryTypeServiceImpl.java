package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.AttributeCategoryTypeDAO;
import com.rmsi.mast.studio.domain.AttributeCategoryType;
import com.rmsi.mast.studio.service.AttributeCategoryTypeService;


@Service
public class AttributeCategoryTypeServiceImpl  implements AttributeCategoryTypeService {

	@Autowired
	AttributeCategoryTypeDAO attributeCategoryTypeDAO;
	
	public List<AttributeCategoryType> getAllAttributeCategoryType() {
		return attributeCategoryTypeDAO.getAllAttributeCategoryType();
	}

	@Override
	public List<AttributeCategoryType> getAllAttributeCategoryTypeById(Integer id) {
		// TODO Auto-generated method stub
		return attributeCategoryTypeDAO.getAllAttributeCategoryTypeById(id);
	}

	
	
	
}
