/**
 * 
 */
package com.rmsi.mast.studio.mobile.service;

import java.util.List;
import java.util.Map;

import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.ProjectAdjudicator;
import com.rmsi.mast.studio.domain.ProjectHamlet;

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
	 * This will fetch attribute values from AttributeValues through
	 * SurveyProjectAttributes
	 * 
	 * @param projectId
	 * @param statusId
	 * @return
	 */
	Map<Long, List<List<Object>>> getSurveyAttributeValuesByProjectId(String projectId, int statusId);

	/**
	 * This will fetch SurveyProjectId based on attributeId and projectId
	 * 
	 * @param attributeId
	 * @param projectId
	 * @param statusId
	 * @return
	 */
	Long getSurveyProjectAttributeId(long attributeId, String projectId);

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
