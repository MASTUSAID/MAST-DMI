/**
 * 
 */
package com.rmsi.mast.studio.mobile.service;

import java.util.List;

import com.rmsi.mast.studio.domain.ProjectSpatialData;

/**
 * @author shruti.thakur
 *
 */
public interface SpatialDataService {

	
	/**
	 * This method declaration will be used to get the object of ProjectSpatialData entity by MbTilesId
	 * @param mbTilesId
	 * @return
	 */
	ProjectSpatialData getProjectSpatialData(int mbTilesId);

	/**
	 * This method declaration will be used to get the object of ProjectSpatialData entity by ProjectId
	 * @param projectId
	 * @return
	 */
	List<ProjectSpatialData> getProjectSpatialDataByProjectId(String projectId);
	
}
