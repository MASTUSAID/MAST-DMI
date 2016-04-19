

package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectSpatialData;

public interface ProjectDataService {
	
	@Transactional
	ProjectSpatialData saveUploadedDocuments(ProjectSpatialData objDocument);


	
	@Transactional
	boolean deleteProjectData(Long id);

	List<ProjectSpatialData> findAllProjectdata();



	List<ProjectSpatialData> displaySelectedProject(String name);

}
