package com.rmsi.mast.studio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.mobile.dao.AttributeOptionsDao;
import com.rmsi.mast.studio.service.AttributeOptionsService;

@Service
public class AttributeOptionsServiceImpl  implements AttributeOptionsService{

	@Autowired
	AttributeOptionsDao  attributeOptionsDao; 
	
	@Override
	public boolean deleteAttributeOptionsbyId(Long id) {

		return attributeOptionsDao.deleteAttributeOptionsbyId(id);
	}
	
	
	

}
