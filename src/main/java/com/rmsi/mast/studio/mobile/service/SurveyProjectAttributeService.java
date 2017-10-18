/**
 * 
 */
package com.rmsi.mast.studio.mobile.service;

import java.util.List;
import java.util.Map;

import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.ClaimType;
import com.rmsi.mast.studio.domain.ProjectAdjudicator;
import com.rmsi.mast.studio.domain.ProjectHamlet;
import com.rmsi.mast.studio.domain.Surveyprojectattribute;
import com.rmsi.mast.studio.mobile.transferobjects.Property;

/**
 * @author shruti.thakur
 *
 */
public interface SurveyProjectAttributeService {

	/**
	 * This will fetch attributes from Attribute Master through
	 * SurveyProjectAttributes
	 * 
	 * @param projectId
	 * @return
	 */
	List<AttributeMaster> getSurveyAttributesByProjectId(String projectId);

	/**
	 * Returns list of properties by project id and property status
	 * 
	 * @param projectId Project id
	 * @param statusId Property status code. If 0 value is provided, properties with all statuses will be returned.
	 * @return
	 */
	List<Property> getProperties(String projectId, int statusId);

	/**
	 * This will fetch SurveyProjectId based on attributeId and projectId
	 * 
	 * @param attributeId
	 * @param projectId
	 * @param statusId
	 * @return
	 */
	Long getSurveyProjectAttributeId(long attributeId, String projectId);

        /** Returns list of survey project attributes by project ID (name)*/
        List<Surveyprojectattribute> getSurveyProjectAttributes(String projectId);
        
	/**
	 * This will fetch list of adjudicators by project id
	 * 
	 * @param projectId
	 * @return
	 */
	List<ProjectAdjudicator> getProjectAdjudicatorByProjectId(String projectId);

	/**
	 * This will get the list of hamlet by project id
	 * @param projectId
	 * @return
	 */
	List<ProjectHamlet> getProjectHamletsByProjectId(String projectId);
}
