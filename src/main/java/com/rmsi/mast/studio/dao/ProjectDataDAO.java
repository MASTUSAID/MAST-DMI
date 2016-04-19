

package com.rmsi.mast.studio.dao;

import java.util.List;

import com.rmsi.mast.studio.domain.ProjectSpatialData;

public interface ProjectDataDAO extends GenericDAO<ProjectSpatialData, Long> {

	boolean deleteData(Long id);

	List<ProjectSpatialData> selectedAttachment(String name);

	void deleteByProjectName(String name);
	
}
