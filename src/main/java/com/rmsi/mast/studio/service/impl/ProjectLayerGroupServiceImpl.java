

package com.rmsi.mast.studio.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.ProjectLayergroupDAO;
import com.rmsi.mast.studio.service.ProjectLayerGroupService;

@Service
public class ProjectLayerGroupServiceImpl implements ProjectLayerGroupService {

	@Autowired
	private ProjectLayergroupDAO projLyrDAO;

	@Override
	public void deleteProjectLayergroupById(Long id) {
		projLyrDAO.makeTransientByID(id);
	}
	
	public List<String> getProjectLayers(String project){
		return projLyrDAO.getLayersByProjectName(project);
	}
}
