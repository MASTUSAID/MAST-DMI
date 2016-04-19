

package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.ProjectDataDAO;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectSpatialData;
import com.rmsi.mast.studio.service.ProjectDataService;

@Service
public class ProjectDataServiceImpl implements ProjectDataService {

	@Autowired
	ProjectDataDAO projectDataDAO ;

	@Override
	public ProjectSpatialData saveUploadedDocuments(ProjectSpatialData objDocument) {
		
		return projectDataDAO.makePersistent(objDocument);
		
	}

	@Override
	public List<ProjectSpatialData> findAllProjectdata() {
		
		return projectDataDAO.findAll();
	}

	@Override
	public boolean deleteProjectData(Long id) {
		
		return projectDataDAO.deleteData(id);
	
	}

	@Override
	public List<ProjectSpatialData> displaySelectedProject(String name) {
		
		return  projectDataDAO.selectedAttachment(name);
	}




	

	
	
	
}
