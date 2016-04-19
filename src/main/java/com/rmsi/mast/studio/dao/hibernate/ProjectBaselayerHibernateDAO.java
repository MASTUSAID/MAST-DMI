

package com.rmsi.mast.studio.dao.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ProjectBaselayerDAO;
import com.rmsi.mast.studio.dao.ProjectDAO;
import com.rmsi.mast.studio.dao.ProjectLayergroupDAO;
import com.rmsi.mast.studio.dao.UserProjectDAO;
import com.rmsi.mast.studio.dao.UserRoleDAO;
import com.rmsi.mast.studio.domain.Bookmark;
import com.rmsi.mast.studio.domain.LayerField;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectBaselayer;
import com.rmsi.mast.studio.domain.ProjectLayergroup;
import com.rmsi.mast.studio.domain.Unit;
import com.rmsi.mast.studio.domain.UserProject;
import com.rmsi.mast.studio.domain.UserRole;
import com.rmsi.mast.studio.mobile.dao.hibernate.SurveyProjectAttributeHibernateDao;


@Repository
public class ProjectBaselayerHibernateDAO extends GenericHibernateDAO<ProjectBaselayer, Long>
		implements ProjectBaselayerDAO {
	private static final Logger logger = Logger.getLogger(ProjectBaselayerHibernateDAO.class);

	@Override
	public void deleteProjectBaselayer(String name) {
		
		try{
			Query query = getEntityManager().createQuery(
					"Delete from ProjectBaselayer pbl where pbl.projectBean.name =:name")
					.setParameter("name", name);
			
			int count = query.executeUpdate();
			System.out.println("Deleted count: " + count+"-"+name);
			
		}catch(Exception e){
		logger.error(e);
			
		}
		
		
		
	}

	@Override
	public void addProjectBaselayer(Set<ProjectBaselayer> projectBaselayer,String projectname) 
	{		
		//Delete the existing record
		deleteProjectBaselayer(projectname);	 
		
		//insert new record
	    Iterator iter1 = projectBaselayer.iterator();
	    while (iter1.hasNext()) 
	    {	      
	    	ProjectBaselayer pbl=new ProjectBaselayer();
	    	pbl=(ProjectBaselayer) iter1.next();	    	
	    	makePersistent(pbl);
	    }
	}


		
	
}
