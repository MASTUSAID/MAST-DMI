

package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ProjectAreaDAO;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectArea;
import com.rmsi.mast.studio.mobile.dao.hibernate.SurveyProjectAttributeHibernateDao;


@Repository
public class ProjectAreaHibernateDAO extends GenericHibernateDAO<ProjectArea, Long>
		implements ProjectAreaDAO {
	private static final Logger logger = Logger.getLogger(ProjectAreaHibernateDAO.class);

	@Override
	public boolean checkProjectName(String projectName) {
		
		try {
			
			Query query = getEntityManager().createQuery("Select p from ProjectArea p where p.project.name = :name");
		
		query.setParameter("name",projectName);
		List rows = query.getResultList();
		if(rows.size()>0)
		{
			
			
			return true;
			
		}
		else
			return false;
	} catch (Exception e) {
		
		logger.error(e);
		return false;
	}
		
	}

	@Override
	public boolean updateProjectArea(String projectName) {
		
		Query query = getEntityManager().createQuery("UPDATE ProjectArea u where u.name = :name");
		
		query.setParameter("name",projectName);

		int rows = query.executeUpdate();
		
		if(rows>0)
		{
			return true;
		}
		else
			return false;
	}

	@Override
	public void deleteByProjectAreaName(String name) {
		

		
		try{
			Query query = getEntityManager().createQuery(
					"Delete from ProjectArea pa where pa.projectBean.name =:name")
					.setParameter("name", name);
			
			int count = query.executeUpdate();
			
		}catch(Exception e){
			logger.error(e);
			
		}
		
		
		
	
		
		
	}

	@Override
	public List<ProjectArea> findByProjectName(String projectName) {
try {
			
			Query query = getEntityManager().createQuery("Select p from ProjectArea p where p.projectName = :name");
		
		query.setParameter("name",projectName);
		List<ProjectArea> projectArea = query.getResultList();
		if(projectArea.size()>0)
		{
			return projectArea;	
		}
		else
			return projectArea;
		
	} catch (Exception e) {
		
		logger.error(e);
	return null;
	}
	}

	
}
