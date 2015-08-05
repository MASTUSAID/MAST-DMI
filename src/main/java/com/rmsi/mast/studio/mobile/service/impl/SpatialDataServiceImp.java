/**
 * 
 */
package com.rmsi.mast.studio.mobile.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.domain.ProjectSpatialData;
import com.rmsi.mast.studio.mobile.dao.SpatialDataDao;
import com.rmsi.mast.studio.mobile.service.SpatialDataService;

/**
 * @author shruti.thakur
 *
 */
@Service
public class SpatialDataServiceImp implements SpatialDataService {

	@Autowired
	SpatialDataDao spatialData;

	@Override
	public ProjectSpatialData getProjectSpatialData(int mbTilesId) {

		return spatialData.getProjectSpatialData(mbTilesId);

	}

	@Override
	public List<ProjectSpatialData> getProjectSpatialDataByProjectId(
			String projectId) {

		return spatialData.getProjectSpatialDataByProjectId(projectId);

	}

}
