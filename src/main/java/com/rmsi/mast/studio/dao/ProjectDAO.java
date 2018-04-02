

package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.fetch.ProjectData;
import com.rmsi.mast.studio.domain.fetch.ProjectDetails;

public interface ProjectDAO extends GenericDAO<Project, Long> {
	
	Project findByName(String name);
	Project findByProjectId(Integer name);

	List<String> getProjectNames();
	
	boolean deleteProject(Integer id);
	
	List<Project> findAllProjects();
	
        List<ProjectDetails> getAllProjectsDetails();
        
	List<Project> getAllUserProjects();
	
	List<Project> getProjectsByOwner(String email);

	boolean checkduplicatename(String projectName);

	List<Project> getAllProjectsNames();
	
	public List<ProjectData> getAllProjectInfo() ;
}
