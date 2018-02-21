package com.rmsi.mast.viewer.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.hibernate.GenericHibernateDAO;
import com.rmsi.mast.studio.domain.ProjectRegion;
import com.rmsi.mast.viewer.dao.ProjectRegionDao;

/**
 * 
 * @author Abhay.Pandey
 *
 */
@Repository
public class ProjectRegionHibernateDao extends GenericHibernateDAO<ProjectRegion, Integer>
implements ProjectRegionDao{

	@SuppressWarnings("unchecked")
	@Override
	public ProjectRegion findProjectRegionById(Integer id) {
		List<ProjectRegion> project =new ArrayList<ProjectRegion>();

		try {
			project =getEntityManager().createQuery("Select p from ProjectRegion p where p.isactive=true and p.hierarchyid = :Id").setParameter("Id", id).getResultList();
			if(project.size()>0)
				return project.get(0);
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
