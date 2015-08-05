/**
 * 
 */
package com.rmsi.mast.studio.mobile.dao;

import java.util.List;

import com.rmsi.mast.studio.dao.GenericDAO;
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.AttributeValues;
import com.rmsi.mast.studio.domain.Surveyprojectattribute;

/**
 * @author shruti.thakur
 *
 */
public interface SurveyProjectAttributeDao extends
		GenericDAO<Surveyprojectattribute, Long> {

	List<AttributeMaster> getSurveyAttributes(String projectId);

	List<AttributeValues> getSurveyAttributeValues(String projectId);

	Surveyprojectattribute getSurveyProjectAttributeId(long attributeId,
			String projectId);

	boolean updatesurveyProject(Surveyprojectattribute surveyprojectattribute);
	
	
	boolean  surveyAttributesByName(long id, String name, Integer attributeorder,Long attributecategory);

	boolean deleteMappedAttribute(List<Long> uids);

	List<String> findnaturalCustom(String project);
	
	
	
	
}
