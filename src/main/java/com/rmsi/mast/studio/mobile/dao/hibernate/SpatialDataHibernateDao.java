/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.ProjectSpatialData;
import com.rmsi.mast.studio.mobile.dao.SpatialDataDao;

/**
 * @author shruti.thakur
 *
 */
@Repository
public class SpatialDataHibernateDao  extends GenericHibernateDAO<ProjectSpatialData, Integer> implements SpatialDataDao{

	
	@Override
	public ProjectSpatialData getProjectSpatialData(int mbTilesId) {
		String query = "select p from ProjectSpatialData p where p.id = :mbTilesId";

		@SuppressWarnings("unchecked")
		List<ProjectSpatialData> projectSpatialData = getEntityManager().createQuery(query)
				.setParameter("mbTilesId", mbTilesId).getResultList();

		if (projectSpatialData != null && projectSpatialData.size() > 0) {
			return projectSpatialData.get(0);
		}

		return null;
	}

	@Override
	public List<ProjectSpatialData> getProjectSpatialDataByProjectId(String projectId) {
		String query = "select p from ProjectSpatialData p where p.name = :projectId";

		@SuppressWarnings("unchecked")
		List<ProjectSpatialData> projectSpatialData = getEntityManager().createQuery(query)
				.setParameter("projectId", projectId).getResultList();
		 
		if (projectSpatialData != null && projectSpatialData.size() > 0) {
			return projectSpatialData;
		}

		return null;
	}

}
