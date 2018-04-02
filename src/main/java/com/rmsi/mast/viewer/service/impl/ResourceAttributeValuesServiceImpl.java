package com.rmsi.mast.viewer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.domain.ResourceAttributeValues;
import com.rmsi.mast.studio.domain.fetch.ResourceDetails;
import com.rmsi.mast.studio.mobile.dao.ResourceAttributeValuesDAO;
import com.rmsi.mast.viewer.service.ResourceAttributeValuesService;

@Service
public class ResourceAttributeValuesServiceImpl implements ResourceAttributeValuesService {

	
	@Autowired
	ResourceAttributeValuesDAO  resourceAttributeValuesDAO;

	@Override
	public List<ResourceAttributeValues> getResourceAttributeValuesBylandId(Integer projectId, Integer Id) {
		
		return resourceAttributeValuesDAO.getResourceAttributeValuesBylandId(projectId, Id);
	}

	@Override
	public List<ResourceDetails> getAllresouceByproject(String project,Integer startfrom) {
		return resourceAttributeValuesDAO.getAllresouceByproject(project,startfrom);
	}

	@Override
	public Integer getAllresouceCountByproject(String project) {
		// TODO Auto-generated method stub
		return resourceAttributeValuesDAO.getAllresouceCountByproject(project);
	}

	@Override
	public List<ResourceAttributeValues> getResourceAttributeValuesByMasterlandid(
			Integer Id) {
		// TODO Auto-generated method stub
		
		return  resourceAttributeValuesDAO.getResourceAttributeValuesByMasterlandid(Id);
	}

	@Override
	public List<Object[]> getResourceAttributeValuesAndDatatypeBylandId(Integer projectId,
			Integer Id) {
		return resourceAttributeValuesDAO.getResourceAttributeValuesAndDatatypeBylandId(projectId, Id);
	}
	
}
