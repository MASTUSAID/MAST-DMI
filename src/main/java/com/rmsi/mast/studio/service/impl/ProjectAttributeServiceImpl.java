package com.rmsi.mast.studio.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.AttributeCategoryDAO;
import com.rmsi.mast.studio.dao.AttributeMasterDAO;
import com.rmsi.mast.studio.dao.ProjectAttributeDAO;
import com.rmsi.mast.studio.dao.ProjectDAO;
import com.rmsi.mast.studio.dao.UserProjectDAO;
import com.rmsi.mast.studio.domain.AttributeCategory;
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.Surveyprojectattribute;
import com.rmsi.mast.studio.domain.UserProject;
import com.rmsi.mast.studio.mobile.dao.SurveyProjectAttributeDao;
import com.rmsi.mast.studio.mobile.dao.hibernate.AttributeValuesHiberanteDao;
import com.rmsi.mast.studio.service.ProjectAttributeService;
import com.rmsi.mast.viewer.web.mvc.LandRecordsController;


@Service
public class ProjectAttributeServiceImpl implements ProjectAttributeService 
{
	
	private static final Logger logger = Logger.getLogger(ProjectAttributeServiceImpl.class);

	@Autowired
	ProjectAttributeDAO projectAttributeDao;
	
	@Autowired
	AttributeCategoryDAO attributeCategoryDao;
	
	@Autowired
	ProjectDAO projectDao;
	
	@Autowired
	AttributeMasterDAO attributeMasterDAO;
	@Autowired
	SurveyProjectAttributeDao surveyProjectAttributeDao;
	
	@Autowired
	AttributeValuesHiberanteDao attributeValuesDAO;
	
	@Autowired
	UserProjectDAO userProjectDAO;
	
	
	@Override
	public List<Project> findallProjects() {
		return projectDao.findAllProjects();
	}
	@Override
	public List<AttributeCategory> findallCategories() {
		return attributeCategoryDao.findAll();
	}
	@Override
	public List<Surveyprojectattribute> displaySelectedCategory(Long uid,String name) {
		return projectAttributeDao.selectedCategory(uid,name);
	}
	@Override
	public List<Surveyprojectattribute> findAllSurveyProjects() {
	return projectAttributeDao.findAll();
	}
	
	@Override
	public List<Surveyprojectattribute> displaySelectedAttribute(Long uid,
			String project) {
		return projectAttributeDao.displayselectedlist(uid,project);
	}
	@Override
	public List<Surveyprojectattribute> displayAttribute(Long uid) {
	
		return projectAttributeDao.selectedAttributes(uid);
	}

	@Override
	public AttributeMaster findProjectById(long l) {
		return attributeMasterDAO.findById( l, false);
	}
	@Override
	public boolean addsurveyProject(String[] id, String projName, Long attributecategory) 
	{
		try 
		{
			//projectAttributeDao.deleteEntries(attributecategory.intValue(), projName);
			for (int i = 0; i <id.length; i++) 
			{
				String[] splitids = id[i].split("_");
				if(splitids.length==1)
				{
					Surveyprojectattribute surveyprojectattribute = new Surveyprojectattribute();
					AttributeMaster attributeMasterObj = findProjectById(Long.parseLong(splitids[0]));
					surveyprojectattribute.setAttributeMaster(attributeMasterObj);
					surveyprojectattribute.setName(projName);
					surveyprojectattribute.setAttributecategoryid(attributecategory.intValue());					
					projectAttributeDao.makePersistent(surveyprojectattribute);
				}
			}
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean deleteallcategory(Long attributecategory, String projName) {
		projectAttributeDao.deleteEntries(attributecategory.intValue(), projName);
		 return true;
	}
	
	// add by RMSI NK for save up and down project attribute start 
	@Override
	public boolean updatesurveyProject(long[] id, String projName, Long attributecategory) 
	{
		try 
		{
			int order = 1;
			
			//projectAttributeDao.deleteEntries(attributecategory.intValue(), projName);
			for (Long i:id) 
			{
				
				
				 surveyProjectAttributeDao.surveyAttributesByName(i, projName,order,attributecategory);
				 order=order+1;
			}
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}	
	//add by RMSI NK for save up and down project attribute end
	
	@Override
	public boolean checkForProjectAttributeMapping(List<Long> uids)
	{
		return attributeValuesDAO.checkEntieswithUid(uids);		
	}
	
	@Override
	public boolean deleteMappedAttribute(List<Long> uids)
	{
		return surveyProjectAttributeDao.deleteMappedAttribute(uids);		
	}
	@Override
	public List<UserProject>findUserProjects(Integer id) {
		
		return userProjectDAO.findUserProjectsById(id);
	}
	
	
}

