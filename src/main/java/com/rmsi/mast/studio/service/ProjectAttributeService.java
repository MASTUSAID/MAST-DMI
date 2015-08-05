package com.rmsi.mast.studio.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rmsi.mast.studio.domain.AttributeCategory;
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.Project;
import com.rmsi.mast.studio.domain.Surveyprojectattribute;
import com.rmsi.mast.studio.domain.UserProject;

public interface ProjectAttributeService {

	List<Project> findallProjects();

	List<AttributeCategory> findallCategories();

	List<Surveyprojectattribute> displaySelectedCategory(Long uid,String name);

	List<Surveyprojectattribute> findAllSurveyProjects();

	List<Surveyprojectattribute> displaySelectedAttribute(Long uid,String project);

	List<Surveyprojectattribute> displayAttribute(Long uid);

	AttributeMaster findProjectById(long l);

	@Transactional
	boolean addsurveyProject(String[] id, String projName,Long attributecategory);
	
	
	@Transactional
	boolean deleteallcategory(Long attributecategory, String projName);
	
	
	@Transactional
	boolean  updatesurveyProject(long[] result, String projName, Long attributecategory);


	boolean checkForProjectAttributeMapping(List<Long> uids);

	@Transactional
	boolean deleteMappedAttribute(List<Long> uids);

	List<UserProject> findUserProjects(Integer id);


	//boolean updatesurveyProject(long id, String projName,Integer attributecategory);
	
	//@Transactional
	//boolean updatesurveyProjectSave(long[] id, String projName,Long attributecategory);
	


}
