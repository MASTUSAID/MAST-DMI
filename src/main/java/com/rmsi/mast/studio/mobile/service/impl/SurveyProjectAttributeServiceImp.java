package com.rmsi.mast.studio.mobile.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rmsi.mast.studio.dao.AttributeMasterDAO;
import com.rmsi.mast.studio.dao.DisputeDao;
import com.rmsi.mast.studio.dao.ProjectAdjudicatorDAO;
import com.rmsi.mast.studio.dao.ProjectHamletDAO;
import com.rmsi.mast.studio.domain.AttributeMaster;
import com.rmsi.mast.studio.domain.ProjectAdjudicator;
import com.rmsi.mast.studio.domain.ProjectHamlet;
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
import com.rmsi.mast.viewer.dao.SpatialUnitDeceasedPersonDao;
import com.rmsi.mast.studio.domain.Surveyprojectattribute;
import com.rmsi.mast.studio.domain.fetch.AttributeValue;
import com.rmsi.mast.studio.domain.fetch.ClaimBasic;
import com.rmsi.mast.studio.domain.fetch.DisputeBasic;
import com.rmsi.mast.studio.domain.fetch.MediaBasic;
import com.rmsi.mast.studio.domain.fetch.NaturalPersonBasic;
import com.rmsi.mast.studio.domain.fetch.NonNaturalPersonBasic;
import com.rmsi.mast.studio.domain.fetch.PersonBasic;
import com.rmsi.mast.studio.domain.fetch.PoiBasic;
import com.rmsi.mast.studio.domain.fetch.RightBasic;
import com.rmsi.mast.studio.mobile.dao.SpatialUnitPersonWithInterestDao;
import com.rmsi.mast.studio.mobile.transferobjects.Attribute;
import com.rmsi.mast.studio.mobile.transferobjects.DeceasedPerson;
import com.rmsi.mast.studio.mobile.transferobjects.Media;
import com.rmsi.mast.studio.mobile.transferobjects.PersonOfInterest;
import com.rmsi.mast.studio.mobile.transferobjects.Property;
import com.rmsi.mast.studio.mobile.transferobjects.Right;
import com.rmsi.mast.studio.util.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class SurveyProjectAttributeServiceImp implements
        SurveyProjectAttributeService {

    @Autowired
    SurveyProjectAttributeDao attributes;

    @Autowired
    AttributeOptionsDao attributeOptions;

    @Autowired
    NaturalPersonDao naturalPersonDao;

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

    @Autowired
    DisputeDao disputeDao;

    private static final Logger logger = Logger
            .getLogger(SurveyProjectAttributeServiceImp.class.getName());

    @Override
    public List<AttributeMaster> getSurveyAttributesByProjectId(String projectId) {
        List<AttributeMaster> attributeMasterList = attributes.getSurveyAttributes(projectId);
        try {
            Iterator<AttributeMaster> surveyProjectAttribItr = attributeMasterList.iterator();
            while (surveyProjectAttribItr.hasNext()) {
                AttributeMaster attributeMaster = surveyProjectAttribItr.next();
                if (attributeMaster.getDatatypeIdBean().getDatatype().equalsIgnoreCase("dropdown")) {
                    attributeMaster.setAttributeOptions(attributeOptions.getAttributeOptions(attributeMaster.getId()));
                }
            }
        } catch (Exception ex) {
            logger.error("Exception", ex);
            System.out.println("Exception ::: " + ex);
        }
        return attributeMasterList;
    }

    @Override
    public List<Property> getProperties(String projectId, int statusId) {
        List<Property> props = new ArrayList<>();
        List<ClaimBasic> claims;

        if (statusId > 0) {
            claims = spatialUnitiHibernateDao.getClaimsBasicByStatus(projectId, statusId);
        } else {
            claims = spatialUnitiHibernateDao.getClaimsBasicByProject(projectId);
        }

        if (claims != null && claims.size() > 0) {
            SimpleDateFormat dfDateAndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
            SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");

            for (ClaimBasic claim : claims) {
                long usin = claim.getUsin();

                Property prop = new Property();
                prop.setAdjudicator1(StringUtils.empty(claim.getAdjudicator1()));
                prop.setAdjudicator2(StringUtils.empty(claim.getAdjudicator2()));
                prop.setClaimTypeCode(claim.getClaimType());
                prop.setCoordinates(StringUtils.empty(claim.getCoordinates()));
                if (claim.getStatusUpdateTime() != null) {
                    prop.setCompletionDate(dfDateAndTime.format(claim.getStatusUpdateTime()));
                }
                prop.setGeomType(claim.getGeomType());
                prop.setHamletId(claim.getHamletId());
                prop.setId(usin);
                prop.setImei(StringUtils.empty(claim.getImei()));
                prop.setUkaNumber(StringUtils.empty(claim.getUka()));
                prop.setPolygonNumber(StringUtils.empty(claim.getClaimNumber()));
                prop.setServerId(usin);
                prop.setStatus(claim.getStatusId().toString());
                prop.setUserId(claim.getUserId());
                if (claim.getSurveyDate() != null) {
                    prop.setSurveyDate(dfDate.format(claim.getSurveyDate()));
                }

                // Property attributes
                prop.setAttributes(new ArrayList<Attribute>());
                fillAttributes(prop.getAttributes(), claim.getAttributes());

                // Right
                Right propRight = null;

                if (claim.getRights() != null && claim.getRights().size() > 0) {
                    for (RightBasic right : claim.getRights()) {
                        if (!right.getActive()) {
                            continue;
                        }

                        if (propRight == null) {
                            propRight = new Right();
                            if (right.getCertDate() != null) {
                                propRight.setCertDate(dfDate.format(right.getCertDate()));
                            }
                            propRight.setCertNumber(StringUtils.empty(right.getCertNumber()));
                            propRight.setId((long) right.getGid());
                            if (right.getJuridicalArea() != null) {
                                propRight.setJuridicalArea(right.getJuridicalArea());
                            }
                            if (right.getRelationshipTypeId() != null) {
                                propRight.setRelationshipId(right.getRelationshipTypeId());
                            }
                            if (right.getRightTypeId() != null) {
                                propRight.setRightTypeId(right.getRightTypeId());
                            }
                            if (right.getShareTypeId() != null) {
                                propRight.setShareTypeId(right.getShareTypeId());
                            }
                            propRight.setAttributes(new ArrayList<Attribute>());
                            propRight.setNaturalPersons(new ArrayList<com.rmsi.mast.studio.mobile.transferobjects.Person>());
                            fillAttributes(propRight.getAttributes(), right.getAttributes());

                            prop.setRight(propRight);
                        }

                        // Persons
                        PersonBasic person = right.getPerson();
                        NaturalPersonBasic naturalPerson = null;

                        // Non natural person
                        if (person.getPersonTypeId() == 2) {
                            NonNaturalPersonBasic nonPerson = (NonNaturalPersonBasic) person;
                            com.rmsi.mast.studio.mobile.transferobjects.Person propNonPerson = new com.rmsi.mast.studio.mobile.transferobjects.Person();
                            propNonPerson.setIsNatural(0);
                            if (!StringUtils.isEmpty(person.getMobileGroupId())) {
                                propNonPerson.setId(Long.parseLong(person.getMobileGroupId()));
                            } else {
                                propNonPerson.setId(person.getPersonGid());
                            }
                            propNonPerson.setResident(person.isResident() ? 1 : 0);
                            propNonPerson.setAttributes(new ArrayList<Attribute>());
                            fillAttributes(propNonPerson.getAttributes(), nonPerson.getAttributes());
                            propRight.setNonNaturalPerson(propNonPerson);

                            // Get natural person assosiated with non natural
                            if (nonPerson.getRepresentative() != null) {
                                try {
                                    naturalPerson = nonPerson.getRepresentative();
                                } catch (Exception e) {
                                    logger.error(e);
                                }
                            }
                        } else if (person.getPersonTypeId() == 1 && person instanceof NaturalPersonBasic) {
                            try {
                                naturalPerson = (NaturalPersonBasic) person;
                            } catch (Exception e) {
                                logger.error(e);
                            }
                        }

                        if (naturalPerson != null) {
                            propRight.getNaturalPersons().add(createPropPerson(naturalPerson));
                        }
                    }
                }

                // POIs
                if (claim.getPois() != null && claim.getPois().size() > 0) {
                    prop.setPersonOfInterests(new ArrayList<PersonOfInterest>());

                    for (PoiBasic poi : claim.getPois()) {
                        PersonOfInterest propPoi = new PersonOfInterest();
                        if (poi.getDob() != null) {
                            propPoi.setDob(dfDate.format(poi.getDob()));
                        }
                        if (poi.getGenderId() != null) {
                            propPoi.setGenderId(poi.getGenderId());
                        }
                        propPoi.setId(poi.getId());
                        propPoi.setName(StringUtils.empty(poi.getPersonName()));
                        if (poi.getRelationshipTypeId() != null) {
                            propPoi.setRelationshipId(poi.getRelationshipTypeId());
                        }
                        prop.getPersonOfInterests().add(propPoi);
                    }
                }

                // Deceased person
                if (claim.getDeceased() != null && claim.getDeceased().size() > 0) {
                    DeceasedPerson deceasedPerson = new DeceasedPerson();
                    deceasedPerson.setId(claim.getDeceased().get(0).getId());
                    deceasedPerson.setFirstName(StringUtils.empty(claim.getDeceased().get(0).getFirstname()));
                    deceasedPerson.setLastName(StringUtils.empty(claim.getDeceased().get(0).getLastname()));
                    deceasedPerson.setMiddleName(StringUtils.empty(claim.getDeceased().get(0).getMiddlename()));
                    prop.setDeceasedPerson(deceasedPerson);
                }

                // Property media
                if (claim.getMedia() != null && claim.getMedia().size() > 0) {
                    prop.setMedia(new ArrayList<Media>());

                    for (MediaBasic doc : claim.getMedia()) {
                        if (doc.getActive() && doc.getPersonId() == null && doc.getRightId() == null && doc.getDisputeId() == null) {
                            Media media = new Media();
                            media.setId((long) doc.getGid());
                            media.setType(doc.getMediaType());
                            media.setAttributes(new ArrayList<Attribute>());
                            fillAttributes(media.getAttributes(), doc.getAttributes());
                            prop.getMedia().add(media);
                        }
                    }
                }

                // Dispute. If dispue found set claim type to disputed
                if (claim.getDisputes() != null && claim.getDisputes().size() > 0) {
                    for (DisputeBasic dispute : claim.getDisputes()) {
                        if (!dispute.getDeleted() && dispute.getStatus() != null && dispute.getStatus() == 1) {
                            // Make sure property is disputed. set dispute type
                            prop.setClaimTypeCode("dispute");

                            // Add dispute information
                            com.rmsi.mast.studio.mobile.transferobjects.Dispute propDispute = new com.rmsi.mast.studio.mobile.transferobjects.Dispute();
                            propDispute.setDescription(StringUtils.empty(dispute.getDescription()));
                            if (dispute.getDisputeTypeId() != null) {
                                propDispute.setDisputeTypeId(dispute.getDisputeTypeId());
                            }
                            propDispute.setId(dispute.getId());
                            if (dispute.getRegDate() != null) {
                                propDispute.setRegDate(dfDate.format(dispute.getRegDate()));
                            }

                            // Add disputeing parties
                            if (dispute.getDisputingPersons() != null && dispute.getDisputingPersons().size() > 0) {
                                propDispute.setDisputingPersons(new ArrayList<com.rmsi.mast.studio.mobile.transferobjects.Person>());
                                for (NaturalPersonBasic person : dispute.getDisputingPersons()) {
                                    propDispute.getDisputingPersons().add(createPropPerson(person));
                                }
                            }

                            // Add media
                            if (claim.getMedia() != null && claim.getMedia().size() > 0) {
                                propDispute.setMedia(new ArrayList<Media>());

                                for (MediaBasic doc : claim.getMedia()) {
                                    if (doc.getActive() && doc.getPersonId() == null && doc.getRightId() == null && doc.getDisputeId() != null) {
                                        Media media = new Media();
                                        media.setId((long) doc.getGid());
                                        media.setType(doc.getMediaType());
                                        media.setAttributes(new ArrayList<Attribute>());
                                        fillAttributes(media.getAttributes(), doc.getAttributes());
                                        propDispute.getMedia().add(media);
                                    }
                                }
                            }

                            prop.setDispute(propDispute);
                            break;
                        }
                    }
                }

                props.add(prop);
            }
        }
        return props;
    }

    private com.rmsi.mast.studio.mobile.transferobjects.Person createPropPerson(NaturalPersonBasic naturalPerson) {
        com.rmsi.mast.studio.mobile.transferobjects.Person propPerson = new com.rmsi.mast.studio.mobile.transferobjects.Person();
        propPerson.setIsNatural(1);
        if (!StringUtils.isEmpty(naturalPerson.getMobileGroupId())) {
            propPerson.setId(Long.parseLong(naturalPerson.getMobileGroupId()));
        } else {
            propPerson.setId(naturalPerson.getPersonGid());
        }
        if (naturalPerson.getAcquisitionType() != null) {
            propPerson.setAcquisitionTypeId(naturalPerson.getAcquisitionType());
        }
        propPerson.setShare(StringUtils.empty(naturalPerson.getShare()));
        if (naturalPerson.getPersonType() != null) {
            propPerson.setSubTypeId(naturalPerson.getPersonType());
        }
        propPerson.setResident(naturalPerson.isResident() ? 1 : 0);
        propPerson.setAttributes(new ArrayList<Attribute>());
        fillAttributes(propPerson.getAttributes(), naturalPerson.getAttributes());
        return propPerson;
    }

    private void fillAttributes(List<Attribute> attributes, List<? extends AttributeValue> sourceAttributes) {
        if (attributes == null || sourceAttributes == null || sourceAttributes.size() < 1) {
            return;
        }
        for (AttributeValue attribute : sourceAttributes) {
            Attribute attr = new Attribute();
            attr.setId(attribute.getId());
            attr.setValue(attribute.getValue());
            attributes.add(attr);
        }
    }

    @Override
    public Long getSurveyProjectAttributeId(long attributeId, String projectId) {
        Surveyprojectattribute attribute = attributes.getSurveyProjectAttributeId(attributeId, projectId);
        if (attribute != null) {
            return attribute.getUid();
        } else {
            return null;
        }
    }

    @Override
    public List<Surveyprojectattribute> getSurveyProjectAttributes(String projectId) {
        return attributes.getSurveyProjectAttributes(projectId);
    }

    @Override
    public List<ProjectAdjudicator> getProjectAdjudicatorByProjectId(String projectId) {
        return projectAdjudicatorDAO.findByProject(projectId);
    }

    @Override
    public List<ProjectHamlet> getProjectHamletsByProjectId(String projectId) {
        return projectHamletDAO.findHamlets(projectId);
    }
}
