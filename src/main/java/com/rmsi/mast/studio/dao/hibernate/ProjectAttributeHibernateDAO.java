package com.rmsi.mast.studio.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.rmsi.mast.studio.dao.ProjectAttributeDAO;
import com.rmsi.mast.studio.domain.Surveyprojectattribute;
import com.rmsi.mast.studio.mobile.dao.hibernate.SurveyProjectAttributeHibernateDao;



@Repository
public class ProjectAttributeHibernateDAO extends GenericHibernateDAO<Surveyprojectattribute, Long>
		implements ProjectAttributeDAO 
		{
	private static final Logger logger = Logger.getLogger(ProjectAttributeHibernateDAO.class);



	@Override
	public List<Surveyprojectattribute> selectedCategory(Long uid,String name) {

		try{
			//			Integer uids = uid.intValue();
			Query query = getEntityManager().createQuery("Select s from Surveyprojectattribute s where s.attributeMaster.laExtAttributecategory.attributecategoryid = :uid and s.name.name=:name and s.attributeMaster.isactive = true order by s.attributeorder") ;
			query.setParameter("uid",uid);
			query.setParameter("name", name);
			List<Surveyprojectattribute> selectedcategory = query.getResultList();		

			if(selectedcategory.size() > 0){
				return selectedcategory;
			}		
			else
			{
				return new ArrayList<Surveyprojectattribute>();
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
		
			@Override
			public List<Surveyprojectattribute> selectedAttributes(Long uid) {
				Query query = getEntityManager().createQuery("Select s from Surveyprojectattribute s where s.attributeMaster.laExtAttributecategory.attributecategoryid = :uid") ;
				query.setParameter("uid",uid);
				List<Surveyprojectattribute> selectedcategory = query.getResultList();		
		
				if(selectedcategory.size() > 0){
					return selectedcategory;
				}		
				else
				{
					return null;
				}
		
			}
		
			@Override
			public List<Surveyprojectattribute> displayselectedlist(Long uid,String project) 
			{		
		try{
				Query query = getEntityManager().createQuery("Select s from Surveyprojectattribute s where s.attributeMaster.laExtAttributecategory.attributecategoryid = :uid and s.name.name=:project") ;
				query.setParameter("uid",uid);
				query.setParameter("project", project);
				List<Surveyprojectattribute> selectedcategory = query.getResultList();		
		
				if(selectedcategory.size() > 0){
					return selectedcategory;
				}		
				else
				{
					return null;
				}
				
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
			}
			
			@Override
			public boolean deleteEntries(int attributeCategory,String project) 
			{		
		
				Query query = getEntityManager().createQuery("DELETE from Surveyprojectattribute s where s.attributecategoryid = :uid and s.name=:project") ;
				query.setParameter("uid",attributeCategory);
				query.setParameter("project", project);
				int rowsaffected = query.executeUpdate();
		
				if(rowsaffected > 0){
					return true;
				}		
				else
				{
					return false;
				}
			}

			@Override
			public boolean checkdeleteAttribute(Long id) {
				
				
			try {
				Query query = getEntityManager().createQuery("Select s from Surveyprojectattribute s where s.attributeMaster.id = :uid") ;
				query.setParameter("uid",id);
				List<Surveyprojectattribute> selectedcategory = query.getResultList();		

				if(selectedcategory.size() > 0){
					return true;
				}		
				else
				{
					return false;
				}
			} catch (Exception e) {
			logger.error(e);
			}
			return false;
		}

			@Override
			public void deleteByProjectName(String name) {

				
				try{
					Query query = getEntityManager().createQuery(
							"Delete from Surveyprojectattribute sp where sp.name =:name")
							.setParameter("name", name);
					
					int count = query.executeUpdate();
					System.out.println(count);
					System.out.println(count);
				}catch(Exception e){
					logger.error(e);
					
				}
				}

			
			
			@Override
			public List<Surveyprojectattribute> displaySelectedCategoryById(Long uid, String name, Integer id) {
				
				try{
					Query query = getEntityManager().createQuery("Select s from Surveyprojectattribute s where s.attributeMaster.laExtAttributecategory.attributecategoryid = :uid and s.name.name=:name and s.attributeMaster.laExtAttributecategory.categorytype.categorytypeid=:id   and s.attributeMaster.isactive = true order by s.attributeorder") ;
					query.setParameter("uid",uid);
					query.setParameter("name", name);
					query.setParameter("id", id);
					List<Surveyprojectattribute> selectedcategory = query.getResultList();		

					if(selectedcategory.size() > 0){
						return selectedcategory;
					}		
					else
					{
						return new ArrayList<Surveyprojectattribute>();
					}
				}catch(Exception e){
					e.printStackTrace();
					return null;
				}
				
				
			}
			
			
			
		

		}

	
	
	

