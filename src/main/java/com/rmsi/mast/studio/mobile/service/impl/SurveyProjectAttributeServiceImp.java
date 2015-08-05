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
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.Person;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.studio.domain.SpatialUnit;
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

			List<Object> spatialAttributes;
			List<Object> tenureAttributes;
			List<Object> naturalAttributes;
			List<Object> nonNaturalAttributes;
			List<Object> sourceDocumentAttribute;
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
						.getAttributeValueandId(usin));

				/** 3) Fetch social tenure by Spatial Unit Id(usin) */
				List<SocialTenureRelationship> socilaTenureList = tenureDao
						.findSocailTenureByUsin(usin);

				Iterator<SocialTenureRelationship> tenureIter = socilaTenureList
						.iterator();

				attributeValuesList.add(spatialAttributes);

				tenureAttributes = new ArrayList<Object>();

				naturalAttributes = new ArrayList<Object>();
				nonNaturalAttributes = new ArrayList<Object>();

				while (tenureIter.hasNext()) {

					SocialTenureRelationship socialTenure = tenureIter.next();

					/** 4) Fetch attribute value and ID for social tenure */
					tenureAttributes.add(attributeValuesDao
							.getAttributeValueandId(socialTenure.getGid()));

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
												.getPerson_gid()));

						/**
						 * 6.2) Find natural person associated with non-natural
						 * and fetch attribute values and Id for natural person
						 */
						naturalAttributes.add(attributeValuesDao
								.getAttributeValueandId(nonNaturalPersonDao
										.findById(person.getPerson_gid())
										.get(0).getPoc_gid()));

					} else if (person.getPerson_type_gid().getPerson_type_gid() == 1) {

						/**
						 * 6.3) if person is natural than fetch attribute value
						 * and Id for it
						 */
						naturalAttributes
								.add(attributeValuesDao
										.getAttributeValueandId(person
												.getPerson_gid()));
					}
				}

				/**
				 * 7) Fetch media attribute for person value and Id from Source
				 * Document
				 */
				sourceDocumentAttribute = new ArrayList<Object>();
				List<SourceDocument> sourceDocumentList;

				if ((sourceDocumentList = sourceDocumentDao
						.findByUsin(usin))!= null) {

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
											.getGid()));

						}
					}
				}
				attributeValuesList.add(tenureAttributes);
				attributeValuesList.add(naturalAttributes);
				attributeValuesList.add(nonNaturalAttributes);
				attributeValuesList.add(sourceDocumentAttribute);
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

}
