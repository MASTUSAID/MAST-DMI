/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.ProjectSpatialData;

/**
 * @author shruti.thakur
 *
 */
public interface SpatialDataDao extends GenericDAO<ProjectSpatialData, Integer> {

	/**
	 * It will get ProjectSpatialData from database by mbTilesId
	 * 
	 * @param mbTilesId
	 * @return
	 */
	ProjectSpatialData getProjectSpatialData(int mbTilesId);

	/**
	 * It will get ProjectSpatialData from database by mbTilesId
	 * 
	 * @param projectId
	 * @return
	 */
	List<ProjectSpatialData> getProjectSpatialDataByProjectId(String projectId);

}
