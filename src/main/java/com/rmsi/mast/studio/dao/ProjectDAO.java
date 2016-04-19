

package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.Project;

public interface ProjectDAO extends GenericDAO<Project, Long> {
	
	Project findByName(String name);

	List<String> getProjectNames();
	
	boolean deleteProject(String name);
	
	List<Project> findAllProjects();
	
	List<Project> getAllUserProjects();
	
	List<Project> getProjectsByOwner(String email);

	boolean checkduplicatename(String projectName);
}
