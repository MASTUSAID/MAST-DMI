

package com.rmsi.mast.studio.dao.hibernate;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;












import com.rmsi.mast.studio.dao.ProjectDAO;
import com.rmsi.mast.studio.dao.ProjectDataDAO;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectSpatialData;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.mobile.dao.hibernate.SurveyProjectAttributeHibernateDao;


@Repository
public class ProjectDataHibernateDAO extends GenericHibernateDAO<ProjectSpatialData, Long>
		implements ProjectDataDAO {
	private static final Logger logger = Logger.getLogger(ProjectDataHibernateDAO.class);
	
	@Override
	public boolean deleteData(Long id) {
		
		try{
			String qry = "Delete from ProjectSpatialData p where p.id =:id";
			Query query = getEntityManager().createQuery(qry).setParameter("id", id.intValue());
				int count = query.executeUpdate();
	
			if(count > 0){
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e){
			logger.error(e);
			return false;
	}

		
	}

	@Override
	public List<ProjectSpatialData> selectedAttachment(String name) {
		
		if(name.equals("All"))
		{
		
			Query query = getEntityManager().createQuery("Select c from ProjectSpatialData c") ;
			
			List<ProjectSpatialData> selectedattachment = query.getResultList();	
			return selectedattachment;
			
		}
	
		else {
			Query query = getEntityManager().createQuery("Select c from ProjectSpatialData c where c.name = :name") ;
		
			query.setParameter("name",name);
		List<ProjectSpatialData> selectedattachment = query.getResultList();		
		
		if(selectedattachment.size() > 0){
			return selectedattachment;
		}		
		else
		{
			return null;
		}
		
		
	}
	
	}

	@Override
	public void deleteByProjectName(String name) {

		
		try{
			Query query = getEntityManager().createQuery(
					"Delete from ProjectSpatialData psd where psd.name =:name")
					.setParameter("name", name);
			
			int count = query.executeUpdate();
			
			System.out.println(count);
			System.out.println(count);
			
		}catch(Exception e){
			logger.error(e);
			
		}

		
		
	}
	

	
}
