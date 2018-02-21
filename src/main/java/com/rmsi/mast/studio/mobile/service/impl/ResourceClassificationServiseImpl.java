package com.rmsi.mast.studio.mobile.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.ResourceClassificationDAO;
import com.rmsi.mast.studio.domain.ResourceClassification;
import com.rmsi.mast.studio.domain.ResourceSubClassification;
import com.rmsi.mast.studio.mobile.service.ResourceClassificationServise;


@Service
public class ResourceClassificationServiseImpl implements ResourceClassificationServise {

	
	@Autowired
	ResourceClassificationDAO resourceClassificationdao;
	
	
	
	@Override
	public List<ResourceClassification> getAllClassifications() {
		return resourceClassificationdao.findAll();
	}


	@Override
	public ResourceClassification getById(Integer Id) {
		return resourceClassificationdao.getById(Id);
	}

}
