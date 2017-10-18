package com.rmsi.mast.studio.mobile.service.impl;

import com.rmsi.mast.studio.dao.AcquisitionTypeDao;
import com.rmsi.mast.studio.dao.ClaimTypeDao;
import com.rmsi.mast.studio.dao.DisputeDao;
import com.rmsi.mast.studio.dao.DisputeStatusDao;
import com.rmsi.mast.studio.dao.DisputeTypeDao;
import com.rmsi.mast.studio.dao.DocumentTypeDao;
import com.rmsi.mast.studio.dao.IdTypeDao;
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

import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.rmsi.mast.studio.dao.RelationshipTypeDao;
import com.rmsi.mast.studio.dao.UserDAO;
import com.rmsi.mast.studio.domain.AttributeValues;
import com.rmsi.mast.studio.domain.ClaimType;
import com.rmsi.mast.studio.domain.Dispute;
import com.rmsi.mast.studio.domain.NaturalPerson;
import com.rmsi.mast.studio.domain.NonNaturalPerson;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.studio.domain.SpatialUnit;
import com.rmsi.mast.studio.domain.SpatialUnitPersonWithInterest;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.studio.domain.Surveyprojectattribute;
import com.rmsi.mast.studio.domain.User;
import com.rmsi.mast.studio.domain.WorkflowStatusHistory;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.studio.domain.fetch.SpatialunitDeceasedPerson;
import com.rmsi.mast.studio.mobile.dao.AttributeOptionsDao;
import com.rmsi.mast.studio.mobile.dao.AttributeValuesDao;
import com.rmsi.mast.studio.mobile.dao.CitizenshipDao;
import com.rmsi.mast.studio.mobile.dao.EducationLevelDao;
import com.rmsi.mast.studio.mobile.dao.GenderDao;
import com.rmsi.mast.studio.mobile.dao.GroupTypeDao;
import com.rmsi.mast.studio.mobile.dao.LandTypeDao;
import com.rmsi.mast.studio.mobile.dao.LandUseTypeDao;
import com.rmsi.mast.studio.mobile.dao.MaritalStatusDao;
import com.rmsi.mast.studio.mobile.dao.NaturalPersonDao;
import com.rmsi.mast.studio.mobile.dao.NonNaturalPersonDao;
import com.rmsi.mast.studio.mobile.dao.OccupancyTypeDao;
import com.rmsi.mast.studio.mobile.dao.PersonDao;
import com.rmsi.mast.studio.mobile.dao.PersonTypeDao;
import com.rmsi.mast.studio.mobile.dao.ShareTypeDao;
import com.rmsi.mast.studio.mobile.dao.SlopeValuesDao;
import com.rmsi.mast.studio.mobile.dao.SocialTenureDao;
import com.rmsi.mast.studio.mobile.dao.SoilQualityValuesDao;
import com.rmsi.mast.studio.mobile.dao.SourceDocumentDao;
import com.rmsi.mast.studio.mobile.dao.SpatialUnitDao;
import com.rmsi.mast.studio.mobile.dao.SpatialUnitPersonWithInterestDao;
import com.rmsi.mast.studio.mobile.dao.StatusDao;
import com.rmsi.mast.studio.mobile.dao.TenureClassDao;
import com.rmsi.mast.studio.mobile.dao.UserDataDao;
import com.rmsi.mast.studio.mobile.dao.WorkflowStatusHistoryDao;
import com.rmsi.mast.studio.mobile.service.SurveyProjectAttributeService;
import com.rmsi.mast.studio.mobile.service.UserDataService;
import com.rmsi.mast.studio.mobile.transferobjects.Attribute;
import com.rmsi.mast.studio.mobile.transferobjects.Person;
import com.rmsi.mast.studio.mobile.transferobjects.PersonOfInterest;
import com.rmsi.mast.studio.mobile.transferobjects.Property;
import com.rmsi.mast.studio.mobile.transferobjects.Right;
import com.rmsi.mast.studio.util.FileUtils;
import com.rmsi.mast.studio.util.GeometryConversion;
import com.rmsi.mast.studio.util.StringUtils;
import com.rmsi.mast.viewer.dao.SpatialUnitDeceasedPersonDao;
import java.util.IdentityHashMap;

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
    GenderDao genderDao;

    @Autowired
    CitizenshipDao citizenshipDao;

    @Autowired
    DisputeTypeDao disputeTypeDao;

    @Autowired
    AcquisitionTypeDao acquisitionTypeDao;

    @Autowired
    DocumentTypeDao documentTypeDao;

    @Autowired
    MaritalStatusDao maritalStatusDao;

    @Autowired
    OccupancyTypeDao occupancyTypeDao;

    @Autowired
    TenureClassDao tenureClassDao;

    @Autowired
    ShareTypeDao tenureRelationTypeDao;

    @Autowired
    ClaimTypeDao claimTypeDAO;

    @Autowired
    LandTypeDao landTypeDao;

    @Autowired
    SlopeValuesDao slopeValuesDao;

    @Autowired
    SoilQualityValuesDao soilQualityValuesDao;

    @Autowired
    LandUseTypeDao landUseTypeDao;

    @Autowired
    PersonTypeDao personTypeDao;

    @Autowired
    EducationLevelDao educationLevelDao;

    @Autowired
    IdTypeDao idTypeDao;

    @Autowired
    GroupTypeDao groupTypeDao;

    @Autowired
    ShareTypeDao shareTypeDao;

    @Autowired
    RelationshipTypeDao relationshipTypeDao;

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

    @Autowired
    DisputeDao disputeDao;

    @Autowired
    DisputeStatusDao disputeStatusDao;

    private static final Logger logger = Logger.getLogger(UserDataServiceImpl.class.getName());

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
    @Transactional
    public Map<String, String> saveClaims(List<Property> properties, String projectName, int userId) throws Exception {
        Long featureId = 0L;
        Long serverPropId;
        Map<String, String> result = new IdentityHashMap<String, String>();

        if (properties == null || properties.size() < 1 || projectName == null || projectName.equals("") || userId < 1) {
            return null;
        }

        try {
            // Get list of all attributes defined for the project
            List<Surveyprojectattribute> projectAttributes = surveyProjectAttribute.getSurveyProjectAttributes(projectName);
            for (Property prop : properties) {

                featureId = prop.getId();
                Date creationDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a").parse(prop.getCompletionDate());
                SpatialUnit spatialUnit = spatialUnitDao.findByImeiandTimeStamp(prop.getImei(), creationDate);

                if (spatialUnit != null) {
                    result.put(featureId.toString(), Long.toString(spatialUnit.getUsin()));
                    continue;
                }

                spatialUnit = new SpatialUnit();
                spatialUnit.setClaimType(claimTypeDAO.findById(prop.getClaimTypeCode(), false));
                if(spatialUnit.getClaimType() != null && spatialUnit.getClaimType().getCode().equalsIgnoreCase(ClaimType.CODE_DISPUTED)){
                    // Set referred status
                    spatialUnit.setStatus(status.getStatusById(4));
                } else {
                    // Set default new status
                    spatialUnit.setStatus(status.getStatusById(1));
                }
                if (prop.getClaimTypeCode().equalsIgnoreCase("existingClaim") && !StringUtils.isEmpty(prop.getUkaNumber())) {
                    spatialUnit.setPropertyno(prop.getUkaNumber());
                }
                spatialUnit.setPolygonNumber(prop.getPolygonNumber());
                spatialUnit.setSurveyDate(new SimpleDateFormat("yyyy-MM-dd").parse(prop.getSurveyDate()));
                spatialUnit.setStatusUpdateTime(creationDate);
                spatialUnit.setImeiNumber(prop.getImei());
                spatialUnit.setProject(projectName);
                spatialUnit.setUserid(userId);
                spatialUnit.setHamletId(prop.getHamletId());
                spatialUnit.setWitness1(prop.getAdjudicator1());
                spatialUnit.setWitness2(prop.getAdjudicator2());

                GeometryConversion geomConverter = new GeometryConversion();

                spatialUnit.setGtype(prop.getGeomType());

                // Setting geometry
                if (spatialUnit.getGtype().equalsIgnoreCase("point")) {
                    spatialUnit.setPoint(geomConverter.convertWktToPoint(prop.getCoordinates()));
                    spatialUnit.getPoint().setSRID(4326);
                    spatialUnit.setTheGeom(spatialUnit.getPoint());
                } else if (spatialUnit.getGtype().equalsIgnoreCase("line")) {
                    spatialUnit.setLine(geomConverter.convertWktToLineString(prop.getCoordinates()));
                    spatialUnit.getLine().setSRID(4326);
                    spatialUnit.setTheGeom(spatialUnit.getLine());
                } else if (spatialUnit.getGtype().equalsIgnoreCase("polygon")) {
                    spatialUnit.setPolygon(geomConverter.convertWktToPolygon(prop.getCoordinates()));
                    spatialUnit.setArea(spatialUnit.getPolygon().getArea());
                    spatialUnit.getPolygon().setSRID(4326);
                    spatialUnit.setPerimeter((float) spatialUnit.getPolygon().getLength());
                    spatialUnit.setTheGeom(spatialUnit.getPolygon());
                }

                spatialUnit.getTheGeom().setSRID(4326);
                spatialUnit.setActive(true);
                
                setPropAttibutes(spatialUnit, prop);

                serverPropId = spatialUnitDao.addSpatialUnit(spatialUnit).getUsin();
                spatialUnitDao.clear();

                // Save property attributes
                List<AttributeValues> attributes = createAttributesList(projectAttributes, prop.getAttributes());
                attributeValuesDao.addAttributeValues(attributes, serverPropId);

                // Save Natural persons
                if (prop.getRight() != null && prop.getRight().getNonNaturalPerson() == null) {
                    for (Person propPerson : prop.getRight().getNaturalPersons()) {
                        // Save natural person
                        NaturalPerson person = new NaturalPerson();
                        setNaturalPersonAttributes(person, propPerson);
                        person = naturalPersonDao.addNaturalPerson(person);
                        attributes = createAttributesList(projectAttributes, propPerson.getAttributes());
                        attributeValuesDao.addAttributeValues(attributes, person.getPerson_gid());

                        // Save right
                        SocialTenureRelationship right = new SocialTenureRelationship();
                        setRightAttributes(right, prop.getRight());
                        right.setUsin(serverPropId);
                        right.setPerson_gid(person);
                        long rightId = socialTenureDao.addSocialTenure(right).getGid();
                        attributes = createAttributesList(projectAttributes, prop.getRight().getAttributes());
                        attributeValuesDao.addAttributeValues(attributes, rightId);
                    }
                }

                // Save Non-natural person
                if (prop.getRight() != null && prop.getRight().getNonNaturalPerson() != null) {
                    for (Person propPerson : prop.getRight().getNaturalPersons()) {
                        NaturalPerson person = new NaturalPerson();
                        setNaturalPersonAttributes(person, propPerson);
                        // Natural person for non-natural is administrator
                        person.setPersonSubType(personTypeDao.findById(4L, false));

                        // Save natural person
                        Long personId = naturalPersonDao.addNaturalPerson(person).getPerson_gid();
                        attributes = createAttributesList(projectAttributes, propPerson.getAttributes());
                        attributeValuesDao.addAttributeValues(attributes, personId);

                        // Save non natural person
                        NonNaturalPerson nonPerson = new NonNaturalPerson();
                        setNonNaturalPersonAttributes(nonPerson, prop.getRight().getNonNaturalPerson());
                        nonPerson.setPoc_gid(personId);
                        nonPerson = nonNaturalPersonDao.addNonNaturalPerson(nonPerson);
                        attributes = createAttributesList(projectAttributes, prop.getRight().getNonNaturalPerson().getAttributes());
                        attributeValuesDao.addAttributeValues(attributes, nonPerson.getPerson_gid());

                        // Save right
                        SocialTenureRelationship right = new SocialTenureRelationship();
                        setRightAttributes(right, prop.getRight());
                        right.setUsin(serverPropId);
                        right.setPerson_gid(nonPerson);
                        long rightId = socialTenureDao.addSocialTenure(right).getGid();
                        attributes = createAttributesList(projectAttributes, prop.getRight().getAttributes());
                        attributeValuesDao.addAttributeValues(attributes, rightId);
                        // Only 1 natural person is allowed for non-natural
                        break;
                    }
                }

                // Save person of interests
                List<SpatialUnitPersonWithInterest> pois = new ArrayList<>();

                for (PersonOfInterest propPoi : prop.getPersonOfInterests()) {
                    SpatialUnitPersonWithInterest poi = new SpatialUnitPersonWithInterest();
                    if (!StringUtils.isEmpty(propPoi.getDob())) {
                        poi.setDob(new SimpleDateFormat("yyyy-MM-dd").parse(propPoi.getDob()));
                    }
                    if (propPoi.getRelationshipId() > 0) {
                        poi.setRelationshipType(relationshipTypeDao.findById((long) propPoi.getRelationshipId(), false));
                    }
                    if (propPoi.getGenderId() > 0) {
                        poi.setGender(genderDao.getGenderById(propPoi.getGenderId()));
                    }
                    poi.setPerson_name(propPoi.getName());
                    pois.add(poi);
                }

                if (pois.size() > 0) {
                    spatialUnitPersonWithInterestDao.addNextOfKin(pois, serverPropId);
                }

                // Save decesed person
                if (prop.getDeceasedPerson() != null) {
                    SpatialunitDeceasedPerson deadPerson = new SpatialunitDeceasedPerson();
                    deadPerson.setFirstname(prop.getDeceasedPerson().getFirstName());
                    deadPerson.setLastname(prop.getDeceasedPerson().getLastName());
                    deadPerson.setMiddlename(prop.getDeceasedPerson().getMiddleName());
                    deadPerson.setUsin(serverPropId);

                    List<SpatialunitDeceasedPerson> deadPersons = new ArrayList();
                    deadPersons.add(deadPerson);

                    spatialUnitDeceasedPersonDao.addDeceasedPerson(deadPersons, serverPropId);
                }

                // Save dispute
                if (prop.getDispute() != null) {
                    Dispute dispute = new Dispute();
                    dispute.setDescription(prop.getDispute().getDescription());
                    dispute.setDisputeType(disputeTypeDao.findById(prop.getDispute().getDisputeTypeId(), false));
                    dispute.setRegDate(new SimpleDateFormat("yyyy-MM-dd").parse(prop.getDispute().getRegDate()));
                    dispute.setStatus(disputeStatusDao.findById(1, false));
                    dispute.setUsin(serverPropId);
                    dispute.setDisputingPersons(new ArrayList());
                    for (Person propPerson : prop.getDispute().getDisputingPersons()) {
                        NaturalPerson person = new NaturalPerson();
                        setNaturalPersonAttributes(person, propPerson);
                        person = naturalPersonDao.addNaturalPerson(person);
                        attributes = createAttributesList(projectAttributes, propPerson.getAttributes());
                        attributeValuesDao.addAttributeValues(attributes, person.getPerson_gid());
                        dispute.getDisputingPersons().add(person);
                    }
                    disputeDao.save(dispute);
                }

                // Add workflow record
                WorkflowStatusHistory workflowStatusHistory = new WorkflowStatusHistory();

                workflowStatusHistory.setUsin(serverPropId);
                workflowStatusHistory.setWorkflow_status_id(spatialUnit.getStatus().getWorkflowStatusId());
                workflowStatusHistory.setUserid(spatialUnit.getUserid());
                workflowStatusHistory.setStatus_change_time(
                        new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())
                        )
                );

                workflowStatusHistoryDao.addWorkflowStatusHistory(workflowStatusHistory);

                // Add server property ID to the result
                result.put(featureId.toString(), Long.toString(serverPropId));
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Failed to save property: ID " + featureId.toString(), e);
            throw e;
        }
    }

    private void setRightAttributes(SocialTenureRelationship right, Right propRight) throws ParseException {
        if (right == null || propRight == null || propRight.getAttributes() == null || propRight.getAttributes().size() < 1) {
            return;
        }

        if (!StringUtils.isEmpty(propRight.getCertDate())) {
            right.setCertIssueDate(new SimpleDateFormat("yyyy-MM-dd").parse(propRight.getCertDate()));
        }
        right.setCertNumber(propRight.getCertNumber());
        right.setJuridicalArea(propRight.getJuridicalArea());
        if (propRight.getRightTypeId() > 0) {
            right.setTenureclassId(tenureClassDao.getTenureClassById(propRight.getRightTypeId()));
        }
        if (propRight.getRelationshipId() > 0) {
            right.setRelationshipType(relationshipTypeDao.findById((long) propRight.getRelationshipId(), false));
        }
        if (propRight.getShareTypeId() > 0) {
            right.setShare_type(shareTypeDao.findById(propRight.getShareTypeId(), false));
        }
        right.setIsActive(true);

        for (Attribute attribute : propRight.getAttributes()) {
            String value = attribute.getValue();
            Long id = attribute.getId();

            if (id == 31) {
                right.setShare_type(shareTypeDao.getTenureRelationshipTypeById(Integer.parseInt(value)));
            } else if (id == 24) {
                right.setOccupancyTypeId(occupancyTypeDao.getOccupancyTypeById(Integer.parseInt(value)));
            } else if (id == 23) {
                right.setTenureclassId(tenureClassDao.getTenureClassById(Integer.parseInt(value)));
            } else if (id == 32) {
                try {
                    right.setSocial_tenure_startdate(new SimpleDateFormat("yyyy-MM-dd").parse(value.trim()));
                } catch (java.text.ParseException e) {
                }
            } else if (id == 33) {
                try {
                    right.setSocial_tenure_enddate(new SimpleDateFormat("yyyy-MM-dd").parse(value.trim()));
                } catch (java.text.ParseException e) {
                }
            } else if (id == 13) {
                right.setTenureDuration(Float.parseFloat(value));
            } else if (id == 300) {
                right.setAcquisitionType(acquisitionTypeDao.getTypeByAttributeOptionId(Integer.parseInt(value)));
            }
        }
    }

    private void setNonNaturalPersonAttributes(NonNaturalPerson person, Person propPerson) throws ParseException {
        if (person == null || propPerson == null || propPerson.getAttributes() == null || propPerson.getAttributes().size() < 1) {
            return;
        }

        person.setPerson_type_gid(personTypeDao.getPersonTypeById(2));
        person.setMobileGroupId(propPerson.getId().toString());
        person.setResident(propPerson.getResident() == 1);
        person.setMobileGroupId(propPerson.getId().toString());
        person.setActive(true);

        for (Attribute attribute : propPerson.getAttributes()) {
            String value = attribute.getValue();
            Long id = attribute.getId();

            if (id == 6) {
                person.setInstitutionName(value);
            } else if (id == 7) {
                person.setAddress(value);
            } else if (id == 8) {
                person.setPhoneNumber(value);
            } else if (id == 52) {
                person.setGroupType(groupTypeDao.getGroupTypeById(Integer.parseInt(value)));
            }
        }
    }

    private void setNaturalPersonAttributes(NaturalPerson naturalPerson, Person propPerson) throws ParseException {
        if (naturalPerson == null || propPerson == null || propPerson.getAttributes() == null || propPerson.getAttributes().size() < 1) {
            return;
        }

        naturalPerson.setPerson_type_gid(personTypeDao.getPersonTypeById(1));
        naturalPerson.setMobileGroupId(propPerson.getId().toString());
        naturalPerson.setResident(propPerson.getResident() == 1);
        naturalPerson.setResident_of_village(naturalPerson.getResident());
        naturalPerson.setShare(propPerson.getShare());
        if (propPerson.getSubTypeId() > 0) {
            naturalPerson.setPersonSubType(personTypeDao.getPersonTypeById(propPerson.getSubTypeId()));
        }
        if (propPerson.getAcquisitionTypeId() > 0) {
            naturalPerson.setAcquisitionType(acquisitionTypeDao.findById(propPerson.getAcquisitionTypeId(), false));
        }
        naturalPerson.setActive(true);

        for (Attribute attribute : propPerson.getAttributes()) {
            String value = attribute.getValue();
            Long id = attribute.getId();

            if (id == 1) {
                naturalPerson.setFirstName(value);
                naturalPerson.setAlias(value);
            } else if (id == 2) {
                naturalPerson.setLastName(value);
            } else if (id == 3) {
                naturalPerson.setMiddleName(value);
            } else if (id == 29) {
                naturalPerson.setAlias(value);
            } else if (id == 4) {
                naturalPerson.setGender(genderDao.getGenderById(Long.parseLong(value)));
            } else if (id == 5) {
                naturalPerson.setMobile(value);
            } else if (id == 30) {
                naturalPerson.setIdentity(value);
            } else if (id == 21) {
                naturalPerson.setAge(Integer.parseInt(value));
            } else if (id == 19) {
                naturalPerson.setOccupation(value);
            } else if (id == 20) {
                naturalPerson.setEducation(educationLevelDao.getEducationLevelById(Integer.parseInt(value)));
            } else if (id == 25) {
                naturalPerson.setTenure_Relation(value);
            } else if (id == 26) {
                naturalPerson.setHouseholdRelation(value);
            } else if (id == 27) {
                naturalPerson.setWitness(value);
            } else if (id == 22) {
                naturalPerson.setMarital_status(maritalStatusDao.getMaritalStatusById(Integer.parseInt(value)));
            } else if (id == 40) {
                if (value.equalsIgnoreCase("yes")) {
                    naturalPerson.setOwner(true);
                } else {
                    naturalPerson.setOwner(false);
                }
            } else if (id == 41) {
                naturalPerson.setAdministator(value);
            } else if (id == 42) {
                naturalPerson.setCitizenship_id(citizenshipDao.getCitizensbyId(Integer.parseInt(value)));
            } else if (id == 310) {
                naturalPerson.setIdNumber(value);
            } else if (id == 320) {
                naturalPerson.setIdType(idTypeDao.getTypeByAttributeOptionId(Integer.parseInt(value)));
            } else if (id == 330 && value != null && !value.equals("")) {
                naturalPerson.setDob(new SimpleDateFormat("yyyy-MM-dd").parse(value));
            }
        }
    }

    private List<AttributeValues> createAttributesList(List<Surveyprojectattribute> projectAttributes, List<Attribute> propAttributes) {
        List<AttributeValues> attributes = new ArrayList<>();

        if (propAttributes == null || propAttributes.size() < 1 || projectAttributes == null || projectAttributes.size() < 1) {
            return attributes;
        }

        for (Attribute propAttribute : propAttributes) {
            if (propAttribute.getValue() != null && !propAttribute.getValue().equalsIgnoreCase("null")) {
                AttributeValues attribute = new AttributeValues();
                attribute.setValue(propAttribute.getValue());
                for (Surveyprojectattribute projectAttribute : projectAttributes) {
                    if (projectAttribute.getAttributeMaster().getId() == propAttribute.getId()) {
                        attribute.setUid(projectAttribute.getUid());
                        break;
                    }
                }
                attributes.add(attribute);
            }
        }
        return attributes;
    }

    /**
     * Sets Spatial unit object attributes based on property object
     */
    private void setPropAttibutes(SpatialUnit parcel, Property prop) {
        if (parcel == null || prop == null || prop.getAttributes() == null || prop.getAttributes().size() < 1) {
            return;
        }

        // Set proposed land use from right
        if (prop.getRight() != null) {
            for (Attribute attribute : prop.getRight().getAttributes()) {
                if (attribute.getId() == 9) {
                    parcel.setProposedUse(landUseTypeDao.getLandUseTypeById(Integer.parseInt(attribute.getValue())));
                    break;
                }
            }
        }

        for (Attribute attribute : prop.getAttributes()) {
            if (attribute.getId() == 9) {
                parcel.setProposedUse(landUseTypeDao.getLandUseTypeById(Integer.parseInt(attribute.getValue())));
            } else if (attribute.getId() == 15) {
                parcel.setHousehidno(Integer.parseInt(attribute.getValue()));
            } else if (attribute.getId() == 16) {
                parcel.setExistingUse(landUseTypeDao.getLandUseTypeById(Integer.parseInt(attribute.getValue())));
            } else if (attribute.getId() == 17) {
                parcel.setComments(attribute.getValue());
            } else if (attribute.getId() == 28) {
                parcel.setLandOwner(attribute.getValue());
            } else if (attribute.getId() == 34) {
                parcel.setAddress1(attribute.getValue());
            } else if (attribute.getId() == 35) {
                parcel.setAddress2(attribute.getValue());
            } else if (attribute.getId() == 36) {
                parcel.setPostal_code(attribute.getValue());
            } else if (attribute.getId() == 37) {
                parcel.setTypeName(landTypeDao.getLandTypeById(Integer.parseInt(attribute.getValue())));
            } else if (attribute.getId() == 38) {
                parcel.setSoilQuality(soilQualityValuesDao.getSoilQualityValuesById(Integer.parseInt(attribute.getValue())));
            } else if (attribute.getId() == 39) {
                parcel.setSlope(slopeValuesDao.getSlopeValuesById(Integer.parseInt(attribute.getValue())));
            } else if (attribute.getId() == 44) {
                parcel.setNeighborNorth(attribute.getValue());
            } else if (attribute.getId() == 45) {
                parcel.setNeighborSouth(attribute.getValue());
            } else if (attribute.getId() == 46) {
                parcel.setNeighborEast(attribute.getValue());
            } else if (attribute.getId() == 47) {
                parcel.setNeighborWest(attribute.getValue());
            } else if (attribute.getId() == 53) {
                parcel.setOtherUseType(attribute.getValue());
            }
        }
    }

    @Override
    @Transactional(noRollbackFor = Exception.class)
    public SourceDocument uploadMultimedia(SourceDocument sourceDocument, MultipartFile mpFile, File documentsDir) {

        /**
         * 1) Insert source document
         */
        sourceDocument = sourceDocumentDao.addSourceDocument(sourceDocument);

        /**
         * 2) Insert values in AttributeValues
         */
        AttributeValues attributeValues;

        List<AttributeValues> attributeValuesList = new ArrayList<AttributeValues>();
        String projectName = spatialUnitDao.getSpatialUnitByUsin(sourceDocument.getUsin()).getProject();

        if ((sourceDocument.getComments() != null)) {

            attributeValues = new AttributeValues();

            attributeValues.setUid(surveyProjectAttribute
                    .getSurveyProjectAttributeId(10, projectName));

            attributeValues.setValue(sourceDocument.getComments());
            attributeValuesList.add(attributeValues);
        }

        if ((sourceDocument.getEntity_name() != null)) {
            attributeValues = new AttributeValues();
            attributeValues.setUid(surveyProjectAttribute
                    .getSurveyProjectAttributeId(11, projectName));

            attributeValues.setValue(sourceDocument.getEntity_name());
            attributeValuesList.add(attributeValues);
        }

        if ((sourceDocument.getDocumentType() != null)) {
            attributeValues = new AttributeValues();
            attributeValues.setUid(surveyProjectAttribute
                    .getSurveyProjectAttributeId(340, projectName));

            attributeValues.setValue(attributeOptionsDao.getAttributeOptionsId(340, sourceDocument.getDocumentType().getCode().intValue()));
            attributeValuesList.add(attributeValues);
        }

        attributeValuesDao.addAttributeValues(attributeValuesList, Long.valueOf(sourceDocument.getGid()));

        /**
         * 3) Save file on server *
         */
        try {
            byte[] document = mpFile.getBytes();

            if (sourceDocument.getGid() != 0) {
                /**
                 * Create the file on Server
                 */
                File serverFile = new File(documentsDir + File.separator
                        + sourceDocument.getGid() + "." 
                        + FileUtils.getFileExtension(sourceDocument.getScanedSourceDoc())
                );

                try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
                    outputStream.write(document);
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
    public Long findPersonByMobileGroupId(String mobileGroupId, Long usin) {
        try {
            return personDao.findPersonIdClientId(mobileGroupId, usin);
        } catch (Exception ex) {
            logger.error("Exception", ex);
            System.out.println("Exception while finding PERSON:: " + ex);
            throw ex;
        }
    }

    /**
     * This methods decrypts the password
     *
     * @param enycPasswd : Encrypted Password
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
    public boolean updateNaturalPersonAttribValues(NaturalPerson naturalPerson, String project) {
        try {
            List<AttributeValues> attribsList = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long parentUid = naturalPerson.getPerson_gid();

            if (!StringUtils.isEmpty(naturalPerson.getFirstName())) {
                addAttribute(1, project, parentUid, naturalPerson.getFirstName(), attribsList);
            }
            if (!StringUtils.isEmpty(naturalPerson.getLastName())) {
                addAttribute(2, project, parentUid, naturalPerson.getLastName(), attribsList);
            }
            if (!StringUtils.isEmpty(naturalPerson.getMiddleName())) {
                addAttribute(3, project, parentUid, naturalPerson.getMiddleName(), attribsList);
            }
            if (naturalPerson.getGender() != null) {
                addAttribute(4, project, parentUid,
                        attributeOptionsDao.getAttributeOptionsId(4, (int) naturalPerson.getGender().getGenderId()),
                        attribsList);
            }
            if (!StringUtils.isEmpty(naturalPerson.getMobile())) {
                addAttribute(5, project, parentUid, naturalPerson.getMobile(), attribsList);
            }
            if (!StringUtils.isEmpty(naturalPerson.getIdentity())) {
                addAttribute(30, project, parentUid, naturalPerson.getIdentity(), attribsList);
            }
            if (naturalPerson.getAge() != 0) {
                addAttribute(21, project, parentUid, Integer.toString(naturalPerson.getAge()), attribsList);
            }
            if (!StringUtils.isEmpty(naturalPerson.getOccupation())) {
                addAttribute(19, project, parentUid, naturalPerson.getOccupation(), attribsList);
            }
            if (naturalPerson.getEducation() != null) {
                addAttribute(20, project, parentUid,
                        attributeOptionsDao.getAttributeOptionsId(20, (int) naturalPerson.getEducation().getLevelId()),
                        attribsList);
            }
            if (!StringUtils.isEmpty(naturalPerson.getTenure_Relation())) {
                addAttribute(25, project, parentUid, naturalPerson.getTenure_Relation(), attribsList);
            }
            if (!StringUtils.isEmpty(naturalPerson.getHouseholdRelation())) {
                addAttribute(26, project, parentUid, naturalPerson.getHouseholdRelation(), attribsList);
            }
            if (!StringUtils.isEmpty(naturalPerson.getWitness())) {
                addAttribute(27, project, parentUid, naturalPerson.getWitness(), attribsList);
            }
            if (naturalPerson.getMarital_status() != null) {
                addAttribute(22, project, parentUid,
                        attributeOptionsDao.getAttributeOptionsId(22, (int) naturalPerson.getMarital_status().getMaritalStatusId()),
                        attribsList);
            }
            if (naturalPerson.getOwner() != null) {
                addAttribute(40, project, parentUid, naturalPerson.getOwner().toString(), attribsList);
            }
            if (!StringUtils.isEmpty(naturalPerson.getAdministator())) {
                addAttribute(41, project, parentUid, naturalPerson.getAdministator(), attribsList);
            }
            if (naturalPerson.getCitizenship_id() != null) {
                addAttribute(42, project, parentUid,
                        attributeOptionsDao.getAttributeOptionsId(42, (int) naturalPerson.getCitizenship_id().getId()),
                        attribsList);
            }
            if (naturalPerson.getResident_of_village() != null) {
                addAttribute(43, project, parentUid, naturalPerson.getResident_of_village().toString(), attribsList);
            }
            if (naturalPerson.getPersonSubType() != null) {
                addAttribute(54, project, parentUid,
                        attributeOptionsDao.getAttributeOptionsId(54, (int) naturalPerson.getPersonSubType().getPerson_type_gid()),
                        attribsList);
            }
            if (!StringUtils.isEmpty(naturalPerson.getIdNumber())) {
                addAttribute(310, project, parentUid, naturalPerson.getIdNumber(), attribsList);
            }
            if (naturalPerson.getDob() != null) {
                addAttribute(330, project, parentUid, dateFormat.format(naturalPerson.getDob()), attribsList);
            }
            if (naturalPerson.getIdType() != null) {
                addAttribute(320, project, parentUid,
                        attributeOptionsDao.getAttributeOptionsId(320, (int) naturalPerson.getIdType().getCode()),
                        attribsList);
            }

            attributeValuesDao.updateAttributeValues(attribsList);
        } catch (Exception e) {
            logger.error("Exception", e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void addAttribute(long attributeId, String project, Long parentUid, String value, List<AttributeValues> list) {
        Long attributeProjectId = surveyProjectAttribute.getSurveyProjectAttributeId(attributeId, project);
        if (attributeProjectId != null && parentUid != null) {
            AttributeValues attributeValues = new AttributeValues();
            attributeValues.setParentuid(parentUid);
            attributeValues.setValue(value);
            attributeValues.setUid(attributeProjectId);
            list.add(attributeValues);
        }
    }

    @Override
    public boolean updateTenureAttribValues(SocialTenureRelationship socialTenure, String project) {
        try {
            List<AttributeValues> attribsList = new ArrayList<AttributeValues>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long parentUid = socialTenure.getGid();

            if (socialTenure.getSocial_tenure_startdate() != null) {
                addAttribute(32, project, parentUid, dateFormat.format(socialTenure.getSocial_tenure_startdate()), attribsList);
            }
            if (socialTenure.getSocial_tenure_enddate() != null) {
                addAttribute(33, project, parentUid, dateFormat.format(socialTenure.getSocial_tenure_enddate()), attribsList);
            }
            if (socialTenure.getTenureDuration() != 0) {
                addAttribute(13, project, parentUid, socialTenure.getTenureDuration() + "", attribsList);
            }
            if (socialTenure.getShare_type() != null) {
                addAttribute(31, project, parentUid,
                        attributeOptionsDao.getAttributeOptionsId(31, socialTenure.getShare_type().getGid()),
                        attribsList);
            }
            if (socialTenure.getOccupancyTypeId() != null) {
                addAttribute(24, project, parentUid,
                        attributeOptionsDao.getAttributeOptionsId(24, socialTenure.getOccupancyTypeId().getOccId()),
                        attribsList);
            }
            if (socialTenure.getTenureclassId() != null) {
                addAttribute(23, project, parentUid,
                        attributeOptionsDao.getAttributeOptionsId(23, socialTenure.getTenureclassId().getTenureId()),
                        attribsList);
            }
            if (socialTenure.getAcquisitionType() != null) {
                addAttribute(300, project, parentUid,
                        attributeOptionsDao.getAttributeOptionsId(300, socialTenure.getAcquisitionType().getCode()),
                        attribsList);
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
            List<AttributeValues> attribsList = new ArrayList<>();
            long parentUid = nonnaturalPerson.getPerson_gid();

            if (StringUtils.isNotEmpty(nonnaturalPerson.getAddress())) {
                addAttribute(7, project, parentUid, nonnaturalPerson.getAddress(), attribsList);
            }
            if (StringUtils.isNotEmpty(nonnaturalPerson.getInstitutionName())) {
                addAttribute(6, project, parentUid, nonnaturalPerson.getInstitutionName(), attribsList);
            }
            if (StringUtils.isNotEmpty(nonnaturalPerson.getPhoneNumber())) {
                addAttribute(8, project, parentUid, nonnaturalPerson.getPhoneNumber(), attribsList);
            }
            if (nonnaturalPerson.getGroupType() != null) {
                addAttribute(52, project, parentUid,
                        attributeOptionsDao.getAttributeOptionsId(52, (int) nonnaturalPerson.getGroupType().getGroupId()), 
                        attribsList);
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
    public boolean updateGeneralAttribValues(SpatialUnitTable spatialunit, String project) {
        try {
            List<SocialTenureRelationship> rights = socialTenureDao.findSocailTenureByUsin(spatialunit.getUsin());
            List<AttributeValues> attribsList = new ArrayList<AttributeValues>();
            long parentUid = spatialunit.getUsin();

            if (spatialunit.getHousehidno() != 0) {
                addAttribute(15, project, parentUid, spatialunit.getHousehidno() + "", attribsList);
            }
            if (StringUtils.isNotEmpty(spatialunit.getComments())) {
                addAttribute(17, project, parentUid, spatialunit.getComments(), attribsList);
            }
            if (StringUtils.isNotEmpty(spatialunit.getAddress1())) {
                addAttribute(34, project, parentUid, spatialunit.getAddress1(), attribsList);
            }
            if (StringUtils.isNotEmpty(spatialunit.getAddress2())) {
                addAttribute(35, project, parentUid, spatialunit.getAddress2(), attribsList);
            }
            if (StringUtils.isNotEmpty(spatialunit.getPostal_code())) {
                addAttribute(36, project, parentUid, spatialunit.getPostal_code(), attribsList);
            }
            if (spatialunit.getProposedUse() != null) {
                String value = attributeOptionsDao.getAttributeOptionsId(9, (int) spatialunit.getProposedUse().getLandUseTypeId());
                addAttribute(9, project, parentUid, value, attribsList);

                if (rights != null) {
                    for (SocialTenureRelationship right : rights) {
                        addAttribute(9, project, (long) right.getGid(), value, attribsList);
                    }
                }
            }
            if (spatialunit.getExistingUse() != null) {
                String value = attributeOptionsDao.getAttributeOptionsId(16, (int) spatialunit.getExistingUse().getLandUseTypeId());
                addAttribute(16, project, parentUid, value, attribsList);
            }
            if (spatialunit.getLandType() != null) {
                String value = attributeOptionsDao.getAttributeOptionsId(37, (int) spatialunit.getLandType().getLandTypeId());
                addAttribute(37, project, parentUid, value, attribsList);
            }
            if (spatialunit.getSoilQualityValues() != null) {
                String value = attributeOptionsDao.getAttributeOptionsId(38, (int) spatialunit.getSoilQualityValues().getId());
                addAttribute(38, project, parentUid, value, attribsList);
            }
            if (spatialunit.getSlopeValues() != null) {
                String value = attributeOptionsDao.getAttributeOptionsId(39, (int) spatialunit.getSlopeValues().getId());
                addAttribute(39, project, parentUid, value, attribsList);
            }
            if (StringUtils.isNotEmpty(spatialunit.getNeighbor_north())) {
                addAttribute(44, project, parentUid, spatialunit.getNeighbor_north(), attribsList);
            }
            if (StringUtils.isNotEmpty(spatialunit.getNeighbor_south())) {
                addAttribute(45, project, parentUid, spatialunit.getNeighbor_south(), attribsList);
            }
            if (StringUtils.isNotEmpty(spatialunit.getNeighbor_east())) {
                addAttribute(46, project, parentUid, spatialunit.getNeighbor_east(), attribsList);
            }
            if (StringUtils.isNotEmpty(spatialunit.getNeighbor_west())) {
                addAttribute(47, project, parentUid, spatialunit.getNeighbor_west(), attribsList);
            }
            if (StringUtils.isNotEmpty(spatialunit.getOtherUseType())) {
                addAttribute(53, project, parentUid, spatialunit.getOtherUseType(), attribsList);
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
            List<AttributeValues> attribsList = new ArrayList<>();
            long parentUid = Long.parseLong(sourcedocument.getGid() + "");

            if (StringUtils.isNotEmpty(sourcedocument.getScanedSourceDoc())) {
                addAttribute(10, project, parentUid, sourcedocument.getScanedSourceDoc(), attribsList);
            }
            if (StringUtils.isNotEmpty(sourcedocument.getComments())) {
                addAttribute(11, project, parentUid, sourcedocument.getComments(), attribsList);
            }

            if (sourcedocument.getDocumentType() != null) {
                addAttribute(340, project, parentUid,
                        attributeOptionsDao.getAttributeOptionsId(340, sourcedocument.getDocumentType().getCode().intValue()),
                        attribsList);
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
