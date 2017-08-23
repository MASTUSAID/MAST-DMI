package com.rmsi.mast.studio.mobile.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.rmsi.mast.studio.dao.PersonTypeDAO;
import com.rmsi.mast.studio.dao.UserDAO;
import com.rmsi.mast.studio.domain.AttributeValues;
import com.rmsi.mast.studio.domain.NaturalPerson;
import com.rmsi.mast.studio.domain.NonNaturalPerson;
import com.rmsi.mast.studio.domain.Person;
import com.rmsi.mast.studio.domain.PersonType;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.studio.domain.SpatialUnit;
import com.rmsi.mast.studio.domain.SpatialUnitPersonWithInterest;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.WorkflowStatusHistory;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.studio.domain.fetch.SpatialunitDeceasedPerson;
import com.rmsi.mast.studio.mobile.dao.AttributeOptionsDao;
import com.rmsi.mast.studio.mobile.dao.AttributeValuesDao;
import com.rmsi.mast.studio.mobile.dao.NaturalPersonDao;
import com.rmsi.mast.studio.mobile.dao.NonNaturalPersonDao;
import com.rmsi.mast.studio.mobile.dao.PersonDao;
import com.rmsi.mast.studio.mobile.dao.SocialTenureDao;
import com.rmsi.mast.studio.mobile.dao.SourceDocumentDao;
import com.rmsi.mast.studio.mobile.dao.SpatialUnitDao;
import com.rmsi.mast.studio.mobile.dao.SpatialUnitPersonWithInterestDao;
import com.rmsi.mast.studio.mobile.dao.StatusDao;
import com.rmsi.mast.studio.mobile.dao.UserDataDao;
import com.rmsi.mast.studio.mobile.dao.WorkflowStatusHistoryDao;
import com.rmsi.mast.studio.mobile.service.SurveyProjectAttributeService;
import com.rmsi.mast.studio.mobile.service.UserDataService;
import com.rmsi.mast.viewer.dao.SpatialUnitDeceasedPersonDao;
import com.rmsi.mast.viewer.service.LandRecordsService;

/**
 * @author shruti.thakur
 *
 */
@Service
public class UserDataServiceImpl implements UserDataService {

	@Autowired
	UserDAO userdao;

	@Autowired
	UserDataDao userData;

	@Autowired
	SpatialUnitDao spatialUnitDao;

	@Autowired
	NaturalPersonDao naturalPersonDao;

	@Autowired
	NonNaturalPersonDao nonNaturalPersonDao;

	@Autowired
	SocialTenureDao socialTenureDao;

	@Autowired
	PersonDao personDao;

	@Autowired
	StatusDao status;

	@Autowired
	AttributeValuesDao attributeValuesDao;

	@Autowired
	WorkflowStatusHistoryDao workflowStatusHistoryDao;

	@Autowired
	SurveyProjectAttributeService surveyProjectAttribute;

	@Autowired
	AttributeOptionsDao attributeOptionsDao;

	@Autowired
	SourceDocumentDao sourceDocumentDao;
	
	@Autowired
	SpatialUnitPersonWithInterestDao spatialUnitPersonWithInterestDao;
	
	@Autowired
	SpatialUnitDeceasedPersonDao spatialUnitDeceasedPersonDao;
	
	@Autowired PersonTypeDAO personTypeDAO;
 
	private static final Logger logger = Logger
			.getLogger(UserDataServiceImpl.class.getName());

	@Override
	public User authenticateByEmail(String email, String passwd) {

		User user = userdao.findByUniqueName(email);

		if (user != null) {
			String decryptedPass = decryptPassword(user.getPassword());
			if (decryptedPass.equals(passwd)) {
				user.setPassword(decryptedPass);
				return user;
			} else {
				System.out.println("Incorrect Password");
			}
		} else {
			System.out.println("Authentication Failed, username doesn't exist");
		}
		return null;
	}

	@Override
	public String getDefaultProjectByUserId(int userId) {

		User user = userData.getUserByUserId(userId);

		if (user != null) {
			return user.getDefaultproject();
		}

		return null;
	}

	@Override
	@Transactional(noRollbackFor = Exception.class)
	public Long syncSurveyProjectData(SpatialUnit spatialUnit,
			List<SpatialunitDeceasedPerson>deceasedPersonList,
			List<SpatialUnitPersonWithInterest> nextOfKinList,
			List<NaturalPerson> naturalPersonList,
			List<NonNaturalPerson> nonNaturalPersonList,
			List<SocialTenureRelationship> socialTenureList,
			List<AttributeValues> customAttributeList,
			Map<String, List<List<AttributeValues>>> attributeValuesMap) {

		// It will store the PK of Spatial Unit
		long usin;

		try {

			/**
			 * If data is not present in database than insert data (distinction
			 * made on the bases of IMEI No. and Survey Date)
			 */
			if (spatialUnitDao.findByImeiandTimeStamp(
					spatialUnit.getImeiNumber(), spatialUnit.getSurveyDate()) == null) {

				/**
				 * Data in spatial unit will be "New" because complete data will
				 * be uploaded by Trusted Intermediary
				 */
				spatialUnit.setStatus(status.getStatusById(1));

				/**
				 * 2) Save data into Spatial Unit Table and gets its generated
				 * Id
				 */
				usin = spatialUnitDao.addSpatialUnit(spatialUnit).getUsin();
				spatialUnitDao.clear();
				System.out.println("Spatial Unit saved successfully, Id: "
						+ usin);

				/**
				 * 3) As data is successfully saved, add all attributes in
				 * 'attributeValuesMap' with key spatial unit in Attribute Table
				 * with parent id as 'usin'
				 */
				attributeValuesDao
						.addAttributeValues(customAttributeList, usin);

				System.out
						.println("Attributes of general and custom category saved successfully");

				/**
				 * 4) If person type is natural
				 */
				if (nonNaturalPersonList.size() == 0) {

					Iterator<NaturalPerson> naturalPersonIter = naturalPersonList
							.iterator();

					long gid;

					int i = 0;
					NaturalPerson naturalPerson = null;
					SocialTenureRelationship socialTenure = null;

					while (naturalPersonIter.hasNext()) {

						naturalPerson = naturalPersonIter.next();

						naturalPerson.setResident(true);

						naturalPerson.setActive(true);

						naturalPerson.setAlias(naturalPerson.getFirstName());
						/**
						 * 4.1) Adding data in table "natural_person" 4.2) Add
						 * the natural person attributes saved above in
						 * Attribute table 4.3) Adding data in table
						 * social_tenure_relationship
						 */

						socialTenure = socialTenureList.get(0);

						socialTenure.setUsin(usin);

						socialTenure.setPerson_gid(naturalPersonDao
								.addNaturalPerson(naturalPerson));

						gid = socialTenureDao.addSocialTenure(socialTenure)
								.getGid();

						/**
						 * 4.4) Add the natural person attributes and social
						 * tenure saved above in Attribute table
						 */

						attributeValuesDao.addAttributeValues(
								attributeValuesMap.get("NaturalPerson").get(i++),
								socialTenure.getPerson_gid().getPerson_gid());
						attributeValuesDao
								.addAttributeValues(
										attributeValuesMap.get("SocialTenure")
												.get(0), gid);
					}
				}
				/**
				 * 5) If person type is non natural
				 */
				else if (nonNaturalPersonList.size() > 0) {

					long gid;

					/**
					 * 5.1) Adding data in table "natural_person"
					 */
					naturalPersonList.get(0).setResident(true);

					naturalPersonList.get(0).setAlias(
							naturalPersonList.get(0).getFirstName());

					naturalPersonList.get(0).setActive(true);
					
					//setting person subtype administrator for non natural person
					naturalPersonList.get(0).setPersonSubType(personTypeDAO.findById(4l, false));

					gid = naturalPersonDao.addNaturalPerson(
							naturalPersonList.get(0)).getPerson_gid();

					/**
					 * 5.2) Add the natural person attributes saved above in
					 * Attribute table
					 */
					attributeValuesDao.addAttributeValues(attributeValuesMap
							.get("NaturalPerson").get(0), gid);

					// Setting poc_gid
					nonNaturalPersonList.get(0).setPoc_gid(gid);

					/**
					 * 5.3) Adding data in table "non_natural_person" 5.5)
					 * Adding data in table social_tenure_relationship
					 */
					SocialTenureRelationship socialTenure = socialTenureList
							.get(0);

					socialTenure.setUsin(usin);

					nonNaturalPersonList.get(0).setActive(true);

					socialTenure.setPerson_gid(nonNaturalPersonDao
							.addNonNaturalPerson(nonNaturalPersonList.get(0)));

					gid = socialTenureDao.addSocialTenure(socialTenure)
							.getGid();

					/**
					 * 5.6) Add the natural person attributes saved above in
					 * Attribute table
					 */
					attributeValuesDao.addAttributeValues(attributeValuesMap
							.get("NonNaturalPerson").get(0), socialTenure
							.getPerson_gid().getPerson_gid());

					attributeValuesDao.addAttributeValues(attributeValuesMap
							.get("SocialTenure").get(0), gid);
				}

				/**
				 * 6. Adding nextOfKin to database
				 */
				if(nextOfKinList.size() > 0){
					spatialUnitPersonWithInterestDao.addNextOfKin(nextOfKinList, usin);
				}
				
				if(deceasedPersonList.size() > 0){
					spatialUnitDeceasedPersonDao.addDeceasedPerson(deceasedPersonList, usin);
				}
				/**
				 * Managing Workflow Status History after saving all data to the
				 * datatbase
				 */
				WorkflowStatusHistory workflowStatusHistory = new WorkflowStatusHistory();

				workflowStatusHistory.setUsin(usin);
				workflowStatusHistory.setWorkflow_status_id(spatialUnit
						.getStatus().getWorkflowStatusId());
				workflowStatusHistory.setUserid(spatialUnit.getUserid());
				workflowStatusHistory
						.setStatus_change_time(new SimpleDateFormat(
								"dd/MM/yyyy HH:mm:ss")
								.parse(new SimpleDateFormat(
										"dd/MM/yyyy HH:mm:ss")
										.format(new Date())));

				workflowStatusHistoryDao
						.addWorkflowStatusHistory(workflowStatusHistory);

				return usin;
			} else {
				/**
				 * If data is present in database than in response send the
				 * primary key of that record
				 */
				return spatialUnitDao.findByImeiandTimeStamp(
						spatialUnit.getImeiNumber(),
						spatialUnit.getSurveyDate()).getUsin();
			}

		} catch (ParseException pex) {
			logger.error("Exception in PARSING DATE: ", pex);
		} catch (Exception ex) {
			logger.error("Exception in SYNC SERVICE: ", ex);
			throw ex;
		}

		return null;
	}

	@Override
	@Transactional(noRollbackFor = Exception.class)
	public SourceDocument uploadMultimedia(SourceDocument sourceDocument,
			MultipartFile mpFile, File documentsDir) {

		/** 1) Insert source document */
		sourceDocument = sourceDocumentDao.addSourceDocument(sourceDocument);

		/** 2) Insert values in AttributeValues */
		AttributeValues attributeValues;

		List<AttributeValues> attributeValuesList = new ArrayList<AttributeValues>();

		if ((sourceDocument.getComments() != null)) {

			attributeValues = new AttributeValues();

			attributeValues.setUid(surveyProjectAttribute
					.getSurveyProjectAttributeId(10, spatialUnitDao
							.getSpatialUnitByUsin(sourceDocument.getUsin())
							.getProject()));

			attributeValues.setValue(sourceDocument.getComments());

			attributeValuesList.add(attributeValues);
		}
		if ((sourceDocument.getEntity_name() != null)) {

			attributeValues = new AttributeValues();

			attributeValues.setUid(surveyProjectAttribute
					.getSurveyProjectAttributeId(11, spatialUnitDao
							.getSpatialUnitByUsin(sourceDocument.getUsin())
							.getProject()));

			attributeValues.setValue(sourceDocument.getEntity_name());

			attributeValuesList.add(attributeValues);
		}

		attributeValuesDao.addAttributeValues(attributeValuesList,
				Long.valueOf(sourceDocument.getGid()));

		/** 3) Save file on server **/
		try {
			byte[] document = mpFile.getBytes();

			if (sourceDocument.getGid() != 0) {

				String fileExtension = sourceDocument
						.getScanedSourceDoc()
						.substring(
								sourceDocument.getScanedSourceDoc()
										.indexOf(".") + 1,
								sourceDocument.getScanedSourceDoc().length())
						.toLowerCase();

				/** Create the file on Server */
				File serverFile = new File(documentsDir + File.separator
						+ sourceDocument.getGid() + "." + fileExtension);

				if(serverFile.length()<=0)
				{
				logger.error("file not exist");	
					
				}
				else{
					BufferedOutputStream outputStream = new BufferedOutputStream(
							new FileOutputStream(serverFile));

					outputStream.write(document);

					outputStream.close();
				}
				
				
			}
		} catch (MultipartException | IOException ex) {

			logger.error("Exception", ex);

		}
		return sourceDocument;

	}

	@Override
	public SourceDocument findMultimedia(String fileName, Long usin) {

		return sourceDocumentDao.findByUsinandFile(fileName, usin);

	}

	@Override
	public Long findPersonByMobileGroupIdandUsin(String mobileGroupId, Long usin) {

		try {
			List<SocialTenureRelationship> tenureList = socialTenureDao
					.findSocailTenureByUsin(usin);

			Iterator<SocialTenureRelationship> tenureItr = tenureList
					.iterator();

			while (tenureItr.hasNext()) {

				SocialTenureRelationship tenure = tenureItr.next();

				Person person = personDao.findPersonById(tenure.getPerson_gid()
						.getPerson_gid());

				if (person.getPerson_type_gid().getPerson_type_gid() == 2) {

					/** if person is non-natural */
					return nonNaturalPersonDao.findById(person.getPerson_gid())
							.get(0).getPoc_gid();

				} else if (person.getPerson_type_gid().getPerson_type_gid() == 1) {

					/** if person is natural */
					if (person.getMobileGroupId().equals(mobileGroupId)) {
						return person.getPerson_gid();
					}
				}
			}
		} catch (Exception ex) {

			logger.error("Exception", ex);
			System.out.println("Exception while finding PERSON:: " + ex);
			throw ex;
		}
		return null;
	}

	/**
	 * This methods decrypts the password
	 * 
	 * @param enycPasswd
	 *            : Encrypted Password
	 * @return: Returns the decrypted password
	 */
	private String decryptPassword(String enycPasswd) {

		final String ENCRYPT_KEY = "HG58YZ3CR9";
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(ENCRYPT_KEY);
		encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
		String decPasswd = encryptor.decrypt(enycPasswd);

		return decPasswd;
	}

	@Override
	public boolean updateNaturalPersonAttribValues(NaturalPerson naturalPerson,
			String project) {
		try {
			List<AttributeValues> attribsList = new ArrayList<AttributeValues>();
			AttributeValues attributeValues = new AttributeValues();

			if (StringUtils.isNotEmpty(naturalPerson.getFirstName())) {
				long attributeId = 1;
				attributeValues.setParentuid(naturalPerson.getPerson_gid());
				attributeValues.setValue(naturalPerson.getFirstName());
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(naturalPerson.getLastName())) {
				long attributeId = 2;
				attributeValues.setParentuid(naturalPerson.getPerson_gid());
				attributeValues.setValue(naturalPerson.getLastName());
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(naturalPerson.getMiddleName())) {
				long attributeId = 3;
				attributeValues.setParentuid(naturalPerson.getPerson_gid());
				attributeValues.setValue(naturalPerson.getMiddleName());
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(naturalPerson.getAlias())) {
				long attributeId = 29;
				attributeValues.setParentuid(naturalPerson.getPerson_gid());
				attributeValues.setValue(naturalPerson.getAlias());
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (naturalPerson.getGender() != null) {
				Long attributeId = 4L;
				String value = attributeOptionsDao.getAttributeOptionsId(
						attributeId.intValue(), (int) naturalPerson.getGender()
								.getGenderId());
				if (value == null) {
					System.out.println("Null value for AttributeID:"
							+ attributeId);
					throw new NullPointerException();
				}
				attributeValues.setParentuid(naturalPerson.getPerson_gid());
				attributeValues.setValue(value);
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(naturalPerson.getMobile())) {
				long attributeId = 5;
				attributeValues.setParentuid(naturalPerson.getPerson_gid());
				attributeValues.setValue(naturalPerson.getMobile());
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(naturalPerson.getIdentity())) {
				long attributeId = 30;
				attributeValues.setParentuid(naturalPerson.getPerson_gid());
				attributeValues.setValue(naturalPerson.getIdentity());
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (naturalPerson.getAge() != 0) {
				long attributeId = 21;
				attributeValues.setParentuid(naturalPerson.getPerson_gid());
				attributeValues.setValue(naturalPerson.getAge() + "");
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(naturalPerson.getOccupation())) {
				long attributeId = 19;
				attributeValues.setValue(naturalPerson.getOccupation());
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (naturalPerson.getEducation() != null) {
				Long attributeId = 20L;
				String value = attributeOptionsDao.getAttributeOptionsId(
						attributeId.intValue(), (int) naturalPerson
								.getEducation().getLevelId());
				if (value == null) {
					System.out.println("Null value for AttributeID:"
							+ attributeId);
					throw new NullPointerException();
				}
				attributeValues.setParentuid(naturalPerson.getPerson_gid());
				attributeValues.setValue(value);
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(naturalPerson.getTenure_Relation())) {
				long attributeId = 25;
				attributeValues.setValue(naturalPerson.getTenure_Relation());
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(naturalPerson.getHouseholdRelation())) {
				long attributeId = 26;
				attributeValues.setParentuid(naturalPerson.getPerson_gid());
				attributeValues.setValue(naturalPerson.getHouseholdRelation());
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(naturalPerson.getWitness())) {
				long attributeId = 27;
				attributeValues.setParentuid(naturalPerson.getPerson_gid());
				attributeValues.setValue(naturalPerson.getWitness());
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (naturalPerson.getMarital_status() != null) {
				Long attributeId = 22L;
				String value = attributeOptionsDao.getAttributeOptionsId(
						attributeId.intValue(), (int) naturalPerson
								.getMarital_status().getMaritalStatusId());
				if (value == null) {
					System.out.println("Null value for AttributeID:"
							+ attributeId);
					throw new NullPointerException();
				}
				attributeValues.setParentuid(naturalPerson.getPerson_gid());
				attributeValues.setValue(value);
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(naturalPerson.getOwner().toString())) {
				long attributeId = 40;
				attributeValues.setParentuid(naturalPerson.getPerson_gid());
				attributeValues.setValue(naturalPerson.getOwner().toString());
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(naturalPerson.getAdministator())) {
				long attributeId = 41;
				attributeValues.setParentuid(naturalPerson.getPerson_gid());
				attributeValues.setValue(naturalPerson.getAdministator());
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(naturalPerson.getCitizenship_id().toString())) {
				Long attributeId = 42L;
				
				String value = attributeOptionsDao.getAttributeOptionsId(
						attributeId.intValue(), (int) naturalPerson
								.getCitizenship_id().getId());
				if (value == null) {
					System.out.println("Null value for AttributeID:"
							+ attributeId);
					throw new NullPointerException();
				}
				
				attributeValues.setParentuid(naturalPerson.getPerson_gid());
				attributeValues.setValue(value);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(naturalPerson.getResident_of_village()
					.toString())) {
				long attributeId = 43;
				attributeValues.setParentuid(naturalPerson.getPerson_gid());
				attributeValues.setValue(naturalPerson.getResident_of_village()
						.toString());
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(naturalPerson.getPersonSubType()
					.toString())) {
				Long attributeId = 54L;
				String value = attributeOptionsDao.getAttributeOptionsId(
						attributeId.intValue(), (int) naturalPerson
								.getPersonSubType().getPerson_type_gid());
				if (value == null) {
					System.out.println("Null value for AttributeID:"
							+ attributeId);
					throw new NullPointerException();
				}
				attributeValues.setParentuid(naturalPerson.getPerson_gid());
				attributeValues.setValue(value);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}

			attributeValuesDao.updateAttributeValues(attribsList);
		} catch (Exception e) {
			logger.error("Exception", e);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateTenureAttribValues(
			SocialTenureRelationship socialTenure, String project) {
		try {
			List<AttributeValues> attribsList = new ArrayList<AttributeValues>();
			AttributeValues attributeValues = new AttributeValues();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			if (socialTenure.getSocial_tenure_startdate() != null) {
				long attributeId = 32;
				attributeValues.setParentuid(Long.parseLong(socialTenure
						.getGid() + ""));
				attributeValues.setValue(dateFormat.format(socialTenure
						.getSocial_tenure_startdate()));
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (socialTenure.getSocial_tenure_enddate() != null) {
				long attributeId = 33;
				attributeValues.setParentuid(Long.parseLong(socialTenure
						.getGid() + ""));
				attributeValues.setValue(dateFormat.format(socialTenure
						.getSocial_tenure_enddate()));
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (socialTenure.getTenure_duration() != 0) {
				long attributeId = 13;
				attributeValues.setParentuid(Long.parseLong(socialTenure
						.getGid() + ""));
				attributeValues
						.setValue(socialTenure.getTenure_duration() + "");
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (socialTenure.getShare_type() != null) {
				Long attributeId = 31L;
				String value = attributeOptionsDao.getAttributeOptionsId(

				attributeId.intValue(), socialTenure.getShare_type().getGid());
				/*
				 * String value = attributeOptionsDao.getAttributeOptionsId(
				 * attributeId.intValue(), 1);
				 */

				if (value == null) {
					System.out.println("Null value for AttributeID:"
							+ attributeId);
					throw new NullPointerException();
				}
				attributeValues.setParentuid(Long.parseLong(socialTenure
						.getGid() + ""));
				attributeValues.setValue(value);
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (socialTenure.getOccupancy_type_id() != null) {
				Long attributeId = 24L;
				String value = attributeOptionsDao.getAttributeOptionsId(
						attributeId.intValue(), socialTenure
								.getOccupancy_type_id().getOccId());
				if (value == null) {
					System.out.println("Null value for AttributeID:"
							+ attributeId);
					throw new NullPointerException();
				}
				attributeValues.setParentuid(Long.parseLong(socialTenure
						.getGid() + ""));
				attributeValues.setValue(value);
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (socialTenure.getTenureclass_id() != null) {
				Long attributeId = 23L;
				String value = attributeOptionsDao.getAttributeOptionsId(
						attributeId.intValue(), socialTenure
								.getTenureclass_id().getTenureId());
				if (value == null) {
					System.out.println("Null value for AttributeID:"
							+ attributeId);
					throw new NullPointerException();
				}
				attributeValues.setParentuid(Long.parseLong(socialTenure
						.getGid() + ""));
				attributeValues.setValue(value);
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}

			attributeValuesDao.updateAttributeValues(attribsList);
		} catch (Exception e) {
			logger.error("Exception", e);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateNonNaturalPersonAttribValues(
			NonNaturalPerson nonnaturalPerson, String project) {
		try {
			List<AttributeValues> attribsList = new ArrayList<AttributeValues>();
			AttributeValues attributeValues = new AttributeValues();

			if (StringUtils.isNotEmpty(nonnaturalPerson.getAddress())) {
				long attributeId = 7;
				attributeValues.setParentuid(nonnaturalPerson.getPerson_gid());
				attributeValues.setValue(nonnaturalPerson.getAddress());
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(nonnaturalPerson.getInstitutionName())) {
				long attributeId = 6;
				attributeValues.setParentuid(nonnaturalPerson.getPerson_gid());
				attributeValues.setValue(nonnaturalPerson.getInstitutionName());
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(nonnaturalPerson.getPhoneNumber())) {
				long attributeId = 8;
				attributeValues.setParentuid(nonnaturalPerson.getPerson_gid());
				attributeValues.setValue(nonnaturalPerson.getPhoneNumber());
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (nonnaturalPerson.getGroupType() != null) {
				Long attributeId = 52L;
				attributeValues.setParentuid(nonnaturalPerson.getPerson_gid());
				String value = attributeOptionsDao.getAttributeOptionsId(
						attributeId.intValue(), (int) nonnaturalPerson
								.getGroupType().getGroupId());
				if (value == null) {
					System.out.println("Null value for AttributeID:"
							+ attributeId);
					throw new NullPointerException();
				}
				attributeValues.setValue(value);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			attributeValuesDao.updateAttributeValues(attribsList);
		} catch (Exception e) {
			logger.error("Exception", e);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateGeneralAttribValues(SpatialUnitTable spatialunit,
			String project) {
		try {
			List<AttributeValues> attribsList = new ArrayList<AttributeValues>();
			AttributeValues attributeValues = new AttributeValues();


/*			if (StringUtils.isNotEmpty(spatialunit.getTypeName())) {
				long attributeId = 14;

			if (spatialunit.getLandType() != null) {
				Long attributeId = 37L;

				attributeValues.setParentuid(spatialunit.getUsin());
				String value = attributeOptionsDao.getAttributeOptionsId(
						attributeId.intValue(), (int) spatialunit.getLandType()
								.getLandTypeId());
				if (value == null) {
					System.out.println("Null value for AttributeID:"
							+ attributeId);
					throw new NullPointerException();
				}
				attributeValues.setValue(value);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}*/
			if (spatialunit.getHousehidno() != 0) {
				long attributeId = 15;
				attributeValues.setParentuid(spatialunit.getUsin());
				attributeValues.setValue(spatialunit.getHousehidno() + "");
				// //attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(spatialunit.getComments())) {
				long attributeId = 17;
				attributeValues.setParentuid(spatialunit.getUsin());
				attributeValues.setValue(spatialunit.getComments());
				// //attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(spatialunit.getAddress1())) {
				long attributeId = 34;
				attributeValues.setParentuid(spatialunit.getUsin());
				attributeValues.setValue(spatialunit.getAddress1());
				// //attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(spatialunit.getAddress2())) {
				long attributeId = 35;
				attributeValues.setParentuid(spatialunit.getUsin());
				attributeValues.setValue(spatialunit.getAddress2());
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(spatialunit.getPostal_code())) {
				long attributeId = 36;
				attributeValues.setParentuid(spatialunit.getUsin());
				attributeValues.setValue(spatialunit.getPostal_code());
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (spatialunit.getProposedUse() != null) {
				Long attributeId = 9L;
				String value = attributeOptionsDao.getAttributeOptionsId(
						attributeId.intValue(), (int) spatialunit
								.getProposedUse().getLandUseTypeId());
				if (value == null) {
					System.out.println("Null value for AttributeID:"
							+ attributeId);
					throw new NullPointerException();
				}
				attributeValues.setParentuid(spatialunit.getUsin());
				attributeValues.setValue(value);
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (spatialunit.getExistingUse() != null) {
				Long attributeId = 16L;
				String value = attributeOptionsDao.getAttributeOptionsId(
						attributeId.intValue(), (int) spatialunit
								.getExistingUse().getLandUseTypeId());
				if (value == null) {
					System.out.println("Null value for AttributeID:"
							+ attributeId);
					throw new NullPointerException();
				}
				attributeValues.setParentuid(spatialunit.getUsin());
				attributeValues.setValue(value);
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (spatialunit.getSoilQualityValues() != null) {
				Long attributeId = 38L;
				attributeValues.setParentuid(spatialunit.getUsin());
				String value = attributeOptionsDao.getAttributeOptionsId(
						attributeId.intValue(), (int) spatialunit
								.getSlopeValues().getId());
				if (value == null) {
					System.out.println("Null value for AttributeID:"
							+ attributeId);
					throw new NullPointerException();
				}
				attributeValues.setValue(value);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (spatialunit.getSlopeValues() != null) {
				Long attributeId = 39L;
				attributeValues.setParentuid(spatialunit.getUsin());
				String value = attributeOptionsDao.getAttributeOptionsId(
						attributeId.intValue(), (int) spatialunit
								.getSlopeValues().getId());
				if (value == null) {
					System.out.println("Null value for AttributeID:"
							+ attributeId);
					throw new NullPointerException();
				}
				attributeValues.setValue(value);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(spatialunit.getNeighbor_north())) {
				long attributeId = 44;
				attributeValues.setParentuid(spatialunit.getUsin());
				attributeValues.setValue(spatialunit.getNeighbor_north());
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(spatialunit.getNeighbor_south())) {
				long attributeId = 45;
				attributeValues.setParentuid(spatialunit.getUsin());
				attributeValues.setValue(spatialunit.getNeighbor_south());
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(spatialunit.getNeighbor_east())) {
				long attributeId = 46;
				attributeValues.setParentuid(spatialunit.getUsin());
				attributeValues.setValue(spatialunit.getNeighbor_east());
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(spatialunit.getNeighbor_west())) {
				long attributeId = 47;
				attributeValues.setParentuid(spatialunit.getUsin());
				attributeValues.setValue(spatialunit.getNeighbor_west());
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(spatialunit.getWitness_1())) {
				long attributeId = 48;
				attributeValues.setParentuid(spatialunit.getUsin());
				attributeValues.setValue(spatialunit.getWitness_1());
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(spatialunit.getWitness_2())) {
				long attributeId = 49;
				attributeValues.setParentuid(spatialunit.getUsin());
				attributeValues.setValue(spatialunit.getWitness_2());
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(spatialunit.getWitness_3())) {
				long attributeId = 50;
				attributeValues.setParentuid(spatialunit.getUsin());
				attributeValues.setValue(spatialunit.getWitness_3());
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(spatialunit.getWitness_4())) {
				long attributeId = 51;
				attributeValues.setParentuid(spatialunit.getUsin());
				attributeValues.setValue(spatialunit.getWitness_4());
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(spatialunit.getOtherUseType())) {
				long attributeId = 53;
				attributeValues.setParentuid(spatialunit.getUsin());
				attributeValues.setValue(spatialunit.getOtherUseType());
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			attributeValuesDao.updateAttributeValues(attribsList);
		} catch (Exception e) {
			logger.error("Exception", e);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateMultimediaAttribValues(SourceDocument sourcedocument,
			String project) {
		try {
			List<AttributeValues> attribsList = new ArrayList<AttributeValues>();
			AttributeValues attributeValues = new AttributeValues();

			if (StringUtils.isNotEmpty(sourcedocument.getScanedSourceDoc())) {
				long attributeId = 10;
				attributeValues.setParentuid(Long.parseLong(sourcedocument
						.getGid() + ""));
				attributeValues.setValue(sourcedocument.getScanedSourceDoc());
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}
			if (StringUtils.isNotEmpty(sourcedocument.getComments())) {
				long attributeId = 11;
				attributeValues.setParentuid(Long.parseLong(sourcedocument
						.getGid() + ""));
				attributeValues.setValue(sourcedocument.getComments());
				// attributeValues.setAttributevalueid(attributeId);
				attributeValues.setUid(surveyProjectAttribute
						.getSurveyProjectAttributeId(attributeId, project));
				attribsList.add(attributeValues);
				attributeValues = new AttributeValues();
			}

			attributeValuesDao.updateAttributeValues(attribsList);
		} catch (Exception e) {
			logger.error("Exception", e);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional(noRollbackFor = Exception.class)
	public List<Long> updateAdjudicatedData(Long userId, List<Long> usinList) {

		try {

			List<Long> sucessfulUpdateList = new ArrayList<Long>();

			WorkflowStatusHistory statusHistory;

			Iterator<Long> usinIter = usinList.iterator();

			// Get Spatial Unit by usin
			while (usinIter.hasNext()) {

				/**
				 * 1) Updating Status and Stautsv update time in Spatial Unit
				 */
				Long usin = usinIter.next();
				Status statusId = status.getStatusById(2);

				SpatialUnit spatialUnit = spatialUnitDao
						.getSpatialUnitByUsin(usin);

				Date statusUpdateTime = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss").parse(new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss").format(new Date()));
				spatialUnit.setStatus(statusId);
				spatialUnit.setStatusUpdateTime(statusUpdateTime);

				/**
				 * 2) Updating Status History in Workflow Status History
				 */
				statusHistory = new WorkflowStatusHistory();

				statusHistory.setStatus_change_time(statusUpdateTime);
				statusHistory.setUserid(userId);
				statusHistory.setUsin(usin);
				statusHistory.setWorkflow_status_id(statusId
						.getWorkflowStatusId());

				sucessfulUpdateList.add(spatialUnitDao.addSpatialUnit(
						spatialUnit).getUsin());
				workflowStatusHistoryDao
						.addWorkflowStatusHistory(statusHistory);
			}
			return sucessfulUpdateList;
		} catch (Exception e) {
			logger.error("Exception in saving STATUS UPDATE in SPATIAL UNIT and STATUS HISTORY::: "
					+ e);
			e.printStackTrace();
			return null;
		}
	}

}
