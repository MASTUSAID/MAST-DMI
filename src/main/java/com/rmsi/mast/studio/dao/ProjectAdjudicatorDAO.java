

package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.ProjectAdjudicator;

public interface ProjectAdjudicatorDAO extends GenericDAO<ProjectAdjudicator, Integer> {

	List<ProjectAdjudicator> findByProject(String projname);

	void deleteByProject(String projectName);
	
	
}
