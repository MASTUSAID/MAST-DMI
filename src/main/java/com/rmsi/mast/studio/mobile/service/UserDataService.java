/**
 * 
 */
package com.rmsi.mast.studio.mobile.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.multipart.MultipartFile;

import com.rmsi.mast.studio.domain.NaturalPerson;
import com.rmsi.mast.studio.domain.NonNaturalPerson;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.studio.domain.Surveyprojectattribute;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.studio.mobile.transferobjects.Property;

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
	 * @param mpFile
	 * @param documentsDir
	 * @return
	 */
	SourceDocument uploadMultimedia(SourceDocument sourceDocument,
			MultipartFile mpFile, File documentsDir);

        /** 
         * Saves claim/property and returns property ID, generated on the server. 
         * @param prop Property object to save
         * @param projectName Project name/ID
         * @param userId User ID who created the claim
         * @return 
         */
        Map<String, String> saveClaims(List<Property> properties, String projectName, int userId) throws Exception;
        
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
	 * @return
	 */
	Long findPersonByMobileGroupId(String mobileGroupId, Long usin);

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
