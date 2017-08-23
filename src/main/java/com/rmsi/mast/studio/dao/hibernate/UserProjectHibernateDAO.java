

package com.rmsi.mast.studio.dao.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ProjectDAO;
import com.rmsi.mast.studio.dao.ProjectLayergroupDAO;
import com.rmsi.mast.studio.dao.UserProjectDAO;
import com.rmsi.mast.studio.dao.UserRoleDAO;
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.Bookmark;
import com.rmsi.mast.studio.domain.LayerField;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.ProjectLayergroup;
import com.rmsi.mast.studio.domain.Unit;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.UserProject;
import com.rmsi.mast.studio.domain.UserRole;


@Repository
public class UserProjectHibernateDAO extends GenericHibernateDAO<UserProject, Long>
		implements UserProjectDAO {
	private static final Logger logger = Logger.getLogger(UserProjectHibernateDAO.class);

	@SuppressWarnings("unchecked")
	public void addUserProject(Set<UserProject> userProjectList,String projectname)
	{	
		try {
			//Delete the existing record
			deleteUserProjectByProject(projectname);
			
			//Insert new record
			Iterator iter1 = userProjectList.iterator();
			while (iter1.hasNext()) 
			{	      
				UserProject up=(UserProject) iter1.next();
				makePersistent(up);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
public List<String> getUsersByProject(String name){
		
		TypedQuery<String> query = 
			getEntityManager().createQuery("Select up.user.username from UserProject up where up.projectBean.name = :name", String.class).setParameter("name", name);
		
		return query.getResultList();
	}


public List<String> getUserNameByProject(String id){
	
	TypedQuery<String> query = 
			getEntityManager().createQuery("Select up.user.username from UserProject up where up.projectBean.name = :name", String.class).setParameter("name", id);
	return query.getResultList();
}


public List<String> getUserProject(String id){
	
	TypedQuery<String> query = 
			getEntityManager().createQuery("where up.projectBean.name = :name", String.class).setParameter("name", id);
	return query.getResultList();
}

@SuppressWarnings("unchecked")
public void deleteUserProjectByProject(String project) {
	
	try{
		Query query = getEntityManager().createQuery(
				"Delete from UserProject up where up.projectBean.name =:name")
				.setParameter("name", project);
		
		int count = query.executeUpdate();
		System.out.println("Delete UserProject count: " + count);
		
	}catch(Exception e){
		logger.error(e);
		
}
	
}

@SuppressWarnings("unchecked")
public void deleteUserProjectByUser(Integer id) {
	
	try{
		String qry = "Delete from UserProject up where up.user.id =:id";
		Query query = getEntityManager().createQuery(qry).setParameter("id", id);
		System.out.println("UserProjectHibernateDao: " + qry);
		
		int count = query.executeUpdate();
		System.out.println("Delete UserProject count: " + count);
		
	}catch(Exception e){
		logger.error(e);
		
}
	
}


@Override
public boolean checkUserProject(Integer val, String defProjName) 
{
	try{
		String qry = "Select up from UserProject up where up.user.id =:id and up.projectBean.name =:name";
		Query query = getEntityManager().createQuery(qry).setParameter("id", val).setParameter("name", defProjName);

		List<UserProject> count = query.getResultList();
		if(count.size()>0)
		{
			return false;
		}		
		else
		{
			UserProject up =  new UserProject();
			Project project=new Project();
			User user  = new User();
			user.setId(val);
			project.setName(defProjName);
			up.setProjectBean(project);
			up.setUser(user);
			makePersistent(up);
			return true;
		}
	}catch(Exception e){
		logger.error(e);
		return false;

	}




}


@Override
public List<UserProject> findUserProjectsById(Integer id) {		
	
	try {
		Query query = getEntityManager().createQuery("Select up from UserProject up where up.user.id =:id");
		query.setParameter("id", id);
		List<UserProject> selectedproj = query.getResultList();		

		if(selectedproj.size() > 0){
			return selectedproj;
		}		
		else
		{
			return null;
		}
	} catch (Exception e) {
		
		e.printStackTrace();
		return null;
	}
}
	
	
	
	/*@SuppressWarnings("unchecked")
	public List<UserProject> findAllUserProject(String name) {
		
		List<UserProject> userProject =
			getEntityManager().createQuery("Select up from UserProject up where up.projectBean.name = :name").setParameter("name", name).getResultList();
		
			return userProject;		
	}	

	@SuppressWarnings("unchecked")
	public void deleteUserProject(String name) {
		System.out.println("HDAO DELETE >>>>>>>"+ name);
		List<UserProject> userProjectList=findAllUserProject(name);
		if(userProjectList.size() > 0){
				for(int i=0;i<userProjectList.size();i++){			
					UserProject up=new UserProject();
					up=userProjectList.get(i);
					System.out.println("DELETEING ID >>>>>>>"+ Long.parseLong(up.getId().toString()));				
					//makeTransientByID(long (plg.getId());			
					makeTransientByID(Long.parseLong(up.getId().toString()));
				}
		}
	}
	*/
	

	
	
}
