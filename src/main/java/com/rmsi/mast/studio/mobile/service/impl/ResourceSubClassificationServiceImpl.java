package com.rmsi.mast.studio.mobile.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.ResourceSubClassificationDAO;
import com.rmsi.mast.studio.domain.ResourceSubClassification;
import com.rmsi.mast.studio.mobile.service.ResourceSubClassificationService;

@Service
public class ResourceSubClassificationServiceImpl implements ResourceSubClassificationService{
	
	@Autowired
	ResourceSubClassificationDAO resourceSubClassificationdao;
	

	@Override
	public List<ResourceSubClassification> getAllSubClassifications() {
		
		return resourceSubClassificationdao.findAll();
	}


	@Override
	public ResourceSubClassification getById(Integer Id) {
		return  resourceSubClassificationdao.getById(Id);
	}

}
