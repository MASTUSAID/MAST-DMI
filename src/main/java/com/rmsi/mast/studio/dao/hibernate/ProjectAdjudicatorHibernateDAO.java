
package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ProjectAdjudicatorDAO;
import com.rmsi.mast.studio.domain.ProjectAdjudicator;
import com.rmsi.mast.viewer.dao.hibernate.LandRecordsHibernateDAO;


@Repository
public class ProjectAdjudicatorHibernateDAO extends GenericHibernateDAO<ProjectAdjudicator, Integer>
		implements ProjectAdjudicatorDAO {
	private static final Logger logger = Logger.getLogger(LandRecordsHibernateDAO.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectAdjudicator> findByProject(String projname) {

		try {
			Query query = getEntityManager().createQuery("Select pa from ProjectAdjudicator pa where pa.projectName = :projname");
			List<ProjectAdjudicator> adjList = query.setParameter("projname", projname).getResultList();
			

			if(adjList.size() > 0){
				return adjList;
			}		
			else
			{
				return null;
			}
		} catch (Exception e) {

			logger.error(e);
			return null;
		}

	}

	@Override
	public void deleteByProject(String projectName) {
		
		try{
			String qry = "Delete from ProjectAdjudicator pa where pa.projectName =:projectName";
			Query query = getEntityManager().createQuery(qry).setParameter("projectName", projectName);
				int count = query.executeUpdate();
	
		}
		catch(Exception e){
			logger.error(e);
			
	}

		
	}
	
}
