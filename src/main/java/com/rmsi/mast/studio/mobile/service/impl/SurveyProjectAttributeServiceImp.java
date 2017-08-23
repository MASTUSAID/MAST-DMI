/**
 * 
 */
package com.rmsi.mast.studio.mobile.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.AttributeMasterDAO;
import com.rmsi.mast.studio.dao.ProjectAdjudicatorDAO;
import com.rmsi.mast.studio.dao.ProjectHamletDAO;
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.Person;
import com.rmsi.mast.studio.domain.ProjectAdjudicator;
import com.rmsi.mast.studio.domain.ProjectHamlet;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.studio.domain.SpatialUnit;
import com.rmsi.mast.studio.domain.fetch.SpatialunitDeceasedPerson;
import com.rmsi.mast.studio.domain.fetch.SpatialunitPersonwithinterest;
import com.rmsi.mast.studio.mobile.dao.AttributeOptionsDao;
import com.rmsi.mast.studio.mobile.dao.AttributeValuesDao;
import com.rmsi.mast.studio.mobile.dao.NaturalPersonDao;
import com.rmsi.mast.studio.mobile.dao.NonNaturalPersonDao;
import com.rmsi.mast.studio.mobile.dao.PersonDao;
import com.rmsi.mast.studio.mobile.dao.SocialTenureDao;
import com.rmsi.mast.studio.mobile.dao.SourceDocumentDao;
import com.rmsi.mast.studio.mobile.dao.SurveyProjectAttributeDao;
import com.rmsi.mast.studio.mobile.dao.hibernate.SocialTenureHibernateDao;
import com.rmsi.mast.studio.mobile.dao.hibernate.SpatialUnitHibernateDao;
import com.rmsi.mast.studio.mobile.service.SurveyProjectAttributeService;
import com.rmsi.mast.studio.util.GeometryConversion;
import com.rmsi.mast.viewer.dao.SpatialUnitDeceasedPersonDao;
import com.rmsi.mast.viewer.dao.SpatialUnitPersonWithInterestDao;

/**
 * @author shruti.thakur
 *
 */
@Service
public class SurveyProjectAttributeServiceImp implements
		SurveyProjectAttributeService {

	@Autowired
	SurveyProjectAttributeDao attributes;

	@Autowired
	AttributeOptionsDao attributeOptions;

	@Autowired
	NaturalPersonDao naturalPerson;

	@Autowired
	SocialTenureDao socailTenure;

	@Autowired
	AttributeMasterDAO attributeMasterDao;

	@Autowired
	SpatialUnitHibernateDao spatialUnitiHibernateDao;

	@Autowired
	SocialTenureHibernateDao tenureDao;

	@Autowired
	PersonDao personDao;

	@Autowired
	AttributeValuesDao attributeValuesDao;

	@Autowired
	NonNaturalPersonDao nonNaturalPersonDao;

	@Autowired
	SourceDocumentDao sourceDocumentDao;

	@Autowired
	ProjectAdjudicatorDAO projectAdjudicatorDAO;

	@Autowired
	ProjectHamletDAO projectHamletDAO;
	
	@Autowired
	SpatialUnitPersonWithInterestDao spatialUnitPersonWithInterestDao; 
	
	@Autowired
	SpatialUnitDeceasedPersonDao spatialUnitDeceasedPersonDao; 
	
	
	private static final Logger logger = Logger
			.getLogger(SurveyProjectAttributeServiceImp.class.getName());

	@Override
	public List<AttributeMaster> getSurveyAttributesByProjectId(String projectId) {

		List<AttributeMaster> attributeMasterList = attributes
				.getSurveyAttributes(projectId);
		try {
			Iterator<AttributeMaster> surveyProjectAttribItr = attributeMasterList
					.iterator();

			while (surveyProjectAttribItr.hasNext()) {

				AttributeMaster attributeMaster = surveyProjectAttribItr.next();

				if (attributeMaster.getDatatypeIdBean().getDatatype()
						.equalsIgnoreCase("dropdown")) {
					attributeMaster.setAttributeOptions(attributeOptions
							.getAttributeOptions(attributeMaster.getId()));
				}
			}

		} catch (Exception ex) {
			logger.error("Exception", ex);
			System.out.println("Exception ::: " + ex);
		}
		return attributeMasterList;

	}

	@Override
	public Map<Long, List<List<Object>>> getSurveyAttributeValuesByProjectId(
			String projectId, int statusId) {

		try {
			Map<Long, List<List<Object>>> attributeValuesMap = new HashMap<Long, List<List<Object>>>();

			Map< Long, Long> personSubtypeMap = new HashMap<Long, Long>();
			List<Object> personSubTypeList;
			List<Object> spatialAttributes;
			List<Object> tenureAttributes;
			List<Object> naturalAttributes;
			List<Object> nonNaturalAttributes;
			List<Object> sourceDocumentAttribute;
			List<Object> naturalMaster;
			//Add for POI and deceased person 18-Nov
			List<Object> personOfIntersest;
			List<Object> deceasedPerson;
			
			List<List<Object>> attributeValuesList;

			/** 1) Get a list of spatial unit Id's for the specified project */

			Iterator<SpatialUnit> usinItr = (new GeometryConversion()
					.converGeometryToString(spatialUnitiHibernateDao
							.findSpatialUnitByStatusId(projectId, statusId)))
					.iterator();

			attributeValuesMap = new HashMap<Long, List<List<Object>>>();

			while (usinItr.hasNext()) {

				SpatialUnit spatialUnit = usinItr.next();

				Long usin = spatialUnit.getUsin();

				spatialAttributes = new ArrayList<Object>();
				attributeValuesList = new ArrayList<List<Object>>();

				/** 2.1) Adding spatial unit to the list */
				spatialAttributes.add(spatialUnit);

				/** 2.2) Fetch attribute value and ID for spatial unit */
				spatialAttributes.add(attributeValuesDao
						.getAttributeValueandId(usin, 1));
				spatialAttributes.add(attributeValuesDao
						.getAttributeValueandId(usin, 7));
				
				/** 3) Fetch social tenure by Spatial Unit Id(usin) */
				List<SocialTenureRelationship> socilaTenureList = tenureDao
						.findSocailTenureByUsin(usin);

				Iterator<SocialTenureRelationship> tenureIter = socilaTenureList
						.iterator();

				attributeValuesList.add(spatialAttributes);

				tenureAttributes = new ArrayList<Object>();

				naturalAttributes = new ArrayList<Object>();
				naturalMaster = new ArrayList<Object>();
				nonNaturalAttributes = new ArrayList<Object>();
				personSubTypeList = new ArrayList<Object>();

				while (tenureIter.hasNext()) {

					SocialTenureRelationship socialTenure = tenureIter.next();

					/** 4) Fetch attribute value and ID for social tenure */
					tenureAttributes.add(attributeValuesDao
							.getAttributeValueandId(socialTenure.getGid(), 4));

					/** 5) Fetch attribute value with ParentId == Person_gid */
					Person person = socialTenure.getPerson_gid();

					/** 6) Fetch data on the bases of person type */
					if (person.getPerson_type_gid().getPerson_type_gid() == 2) {

						/**
						 * 6.1) if person is non-natural fetch attribute values
						 * and Id for non natural person
						 */
						nonNaturalAttributes
								.add(attributeValuesDao
										.getAttributeValueandId(person
												.getPerson_gid(), 5));

						/**
						 * 6.2) Find natural person associated with non-natural
						 * and fetch attribute values and Id for natural person
						 */
//						naturalAttributes.add(attributeValuesDao
//								.getAttributeValueandId(nonNaturalPersonDao
//										.findById(person.getPerson_gid())
//										.get(0).getPoc_gid(), 2));
//					
						long pocId = nonNaturalPersonDao.findById(person.getPerson_gid()).get(0).getPoc_gid();
						naturalAttributes = attributeValuesDao
								.getAttributeValueandId(pocId, 2);
					
						naturalAttributes.add(naturalPerson.findById(pocId).get(0).getPersonSubType().getPerson_type_gid());
						//naturalAttributes.add(naturalPerson.findById(person.getPerson_gid()).get(0).getPersonSubType().getPerson_type_gid());

						naturalMaster.add(naturalAttributes);
						//personSubtypeMap.put(pocId, naturalPerson.findById(pocId).get(0).getPersonSubType().getPerson_type_gid());
					} else if (person.getPerson_type_gid().getPerson_type_gid() == 1) {

						/**
						 * 6.3) if person is natural than fetch attribute value
						 * and Id for it
						 */
						naturalAttributes = attributeValuesDao
								.getAttributeValueandId(person
										.getPerson_gid(), 2);
				//		naturalAttributes.add(naturalPerson.findById(person.getPerson_gid()).get(0).getPersonSubType().getPerson_type_gid());
//						naturalAttributes
//								.add(attributeValuesDao
//										.getAttributeValueandId(person
//												.getPerson_gid(), 2));
					    naturalAttributes.add(naturalPerson.findById(person.getPerson_gid()).get(0).getPersonSubType().getPerson_type_gid());
						naturalMaster.add(naturalAttributes);
						//personSubtypeMap.put(person.getPerson_gid(), naturalPerson.findById(person.getPerson_gid()).get(0).getPersonSubType().getPerson_type_gid());
						
						
					}
				}

				/**
				 * 7) Fetch media attribute for person value and Id from Source
				 * Document
				 */
				sourceDocumentAttribute = new ArrayList<Object>();
				List<SourceDocument> sourceDocumentList;

				if ((sourceDocumentList = sourceDocumentDao.findByUsin(usin)) != null) {

					Iterator<SourceDocument> sourceDocumentItr = sourceDocumentList
							.iterator();

					while (sourceDocumentItr.hasNext()) {

						SourceDocument sourceDocument = sourceDocumentItr
								.next();

						/**
						 * 7.1) Get attribute values only if media doesn't
						 * belong to person
						 */
						if (sourceDocument.getPerson_gid() == null
								&& sourceDocument.getSocial_tenure_gid() == null) {

							sourceDocumentAttribute.add(attributeValuesDao
									.getAttributeValueandId(sourceDocument
											.getGid(), 3));

						}
					}
				}
				
				
				/**
				 * @data: Person of interest list
				 * @date: 18-Nov
				 * 
				 */
				
				
				personOfIntersest = new ArrayList<Object>();
				List<SpatialunitPersonwithinterest> poiList;

				if ((poiList = spatialUnitPersonWithInterestDao.findByUsin(usin)) != null) {

					Iterator<SpatialunitPersonwithinterest> poiItr = poiList
							.iterator();

					while (poiItr.hasNext()) {

						SpatialunitPersonwithinterest spatialunitpoi = poiItr
								.next();
						personOfIntersest.add(spatialunitpoi);
						
					}
				}
				
				
				/**
				 * @data: deceased person list
				 * @date: 18-Nov
				 * 
				 */
				
				
				deceasedPerson = new ArrayList<Object>();
				List<SpatialunitDeceasedPerson> deceasedlst;

				if ((deceasedlst = spatialUnitDeceasedPersonDao.findPersonByUsin(usin)) != null) {

					Iterator<SpatialunitDeceasedPerson> deceasedItr = deceasedlst
							.iterator();

					while (deceasedItr.hasNext()) {

						SpatialunitDeceasedPerson deceasedpersontmp = deceasedItr
								.next();
						deceasedPerson.add(deceasedpersontmp);
						
					}
				}
				
				
				attributeValuesList.add(tenureAttributes);
				attributeValuesList.add(naturalMaster);
				attributeValuesList.add(nonNaturalAttributes);
				attributeValuesList.add(sourceDocumentAttribute);
				attributeValuesList.add(personOfIntersest);
				attributeValuesList.add(deceasedPerson);
				/*personSubTypeList.add(personSubtypeMap);
				attributeValuesList.add(personSubTypeList);*/
				attributeValuesMap.put(usin, attributeValuesList);
				
			}
			return attributeValuesMap;
		} catch (Exception ex) {
			logger.error("Exception", ex);
			throw ex;
		}
	}

	@Override
	public Long getSurveyProjectAttributeId(long attributeId, String projectId) {

		return attributes.getSurveyProjectAttributeId(attributeId, projectId)
				.getUid();
	}

	@Override
	public List<ProjectAdjudicator> getProjectAdjudicatorByProjectId(
			String projectId) {

		return projectAdjudicatorDAO.findByProject(projectId);
	}

	@Override
	public List<ProjectHamlet> getProjectHamletsByProjectId(String projectId) {

		return projectHamletDAO.findHamlets(projectId);
		
	}

}
