/**
 * 
 */
package com.rmsi.mast.studio.mobile.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;

import com.rmsi.mast.studio.domain.AttributeValues;
import com.rmsi.mast.studio.domain.NaturalPerson;
import com.rmsi.mast.studio.domain.NonNaturalPerson;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.studio.domain.SpatialUnit;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;

/**
 * @author shruti.thakur
 *
 */
public interface UserDataService {

	/**
	 * This will do authentication based on email id
	 * 
	 * @param email
	 * @param passwd
	 * @return
	 */
	User authenticateByEmail(String email, String passwd);

	/**
	 * This method is used to get User Project based on UserId
	 * 
	 * @param userId
	 * @return
	 */
	String getDefaultProjectByUserId(int userId);

	/**
	 * This will be used to insert data in Source Document and Save Files
	 * 
	 * @param sourceDocument
	 * @return
	 */
	SourceDocument uploadMultimedia(SourceDocument sourceDocument,
			MultipartFile mpFile, File documentsDir);

	/**
	 * This method will sync the data entered by trusted intermediary after
	 * survey via mobile
	 * 
	 * @param spatialUnit
	 * @param naturalPersonList
	 * @param nonNaturalPersonList
	 * @param socialTenureList
	 * @return
	 */
	Long syncSurveyProjectData(SpatialUnit spatialUnit,
			List<NaturalPerson> naturalPersonList,
			List<NonNaturalPerson> nonNaturalPersonList,
			List<SocialTenureRelationship> socialTenureList,
			List<AttributeValues> customAttributeList,
			Map<String, List<List<AttributeValues>>> attributeValuesMap);

	/**
	 * This will be used to fetch record from source document if USIN and
	 * FielName matches
	 * 
	 * @param fileName
	 * @param usin
	 * @return
	 */
	SourceDocument findMultimedia(String fileName, Long usin);

	/**
	 * It will fetch person based on mobile group id and Usin
	 * 
	 * @param mobileGroupId
	 * @param usin
	 * @return
	 */
	Long findPersonByMobileGroupIdandUsin(String mobileGroupId, Long usin);

	@Transactional
	boolean updateNaturalPersonAttribValues(NaturalPerson naturalPerson,
			String project);

	@Transactional
	boolean updateTenureAttribValues(SocialTenureRelationship socialTenure,
			String project);

	@Transactional
	boolean updateNonNaturalPersonAttribValues(
			NonNaturalPerson nonnaturalPerson, String project);

	@Transactional
	boolean updateGeneralAttribValues(SpatialUnitTable spatialUnit,
			String project);

	@Transactional
	boolean updateMultimediaAttribValues(SourceDocument sourcedocument,
			String project);

	/**
	 * Update the status of SpatialUnit
	 * 
	 * @param userId
	 * @param usin
	 * @return
	 */
	List<Long> updateAdjudicatedData(Long userId, List<Long> usin);

}
