package com.rmsi.mast.viewer.service.impl;

import com.rmsi.mast.studio.dao.AcquisitionTypeDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.dao.AttributeCategoryDAO;
import com.rmsi.mast.studio.dao.AttributeMasterDAO;
import com.rmsi.mast.studio.dao.AttributeValueFetchDAO;
import com.rmsi.mast.studio.dao.ClaimTypeDao;
import com.rmsi.mast.studio.dao.DisputeDao;
import com.rmsi.mast.studio.dao.DisputeStatusDao;
import com.rmsi.mast.studio.dao.DisputeTypeDao;
import com.rmsi.mast.studio.dao.DocumentTypeDao;
import com.rmsi.mast.studio.dao.GenderDAO;
import com.rmsi.mast.studio.dao.GroupTypeDAO;
import com.rmsi.mast.studio.dao.IdTypeDao;
import com.rmsi.mast.studio.dao.LandTypeDAO;
import com.rmsi.mast.studio.dao.MaritalStatusDAO;
import com.rmsi.mast.studio.dao.OccupancyDAO;
import com.rmsi.mast.studio.dao.PersonDAO;
import com.rmsi.mast.studio.dao.PersonTypeDAO;
import com.rmsi.mast.studio.dao.ProjectAdjudicatorDAO;
import com.rmsi.mast.studio.dao.ProjectAreaDAO;
import com.rmsi.mast.studio.dao.RelationshipTypeDao;
import com.rmsi.mast.studio.dao.SUnitHistoryDAO;
import com.rmsi.mast.studio.dao.ShareTypeDAO;
import com.rmsi.mast.studio.dao.SlopeValuesDAO;
import com.rmsi.mast.studio.dao.SocialTenureRelationshipDAO;
import com.rmsi.mast.studio.dao.SoilQualityValuesDAO;
import com.rmsi.mast.studio.dao.SourceDocumentDAO;
import com.rmsi.mast.studio.dao.TenureClassDAO;
import com.rmsi.mast.studio.dao.UnitDAO;
import com.rmsi.mast.studio.dao.UsertableDAO;
import com.rmsi.mast.studio.domain.AcquisitionType;
import com.rmsi.mast.studio.domain.AttributeCategory;
import com.rmsi.mast.studio.domain.AttributeValues;
import com.rmsi.mast.studio.domain.Citizenship;
import com.rmsi.mast.studio.domain.ClaimType;
import com.rmsi.mast.studio.domain.Dispute;
import com.rmsi.mast.studio.domain.DisputeStatus;
import com.rmsi.mast.studio.domain.DisputeType;
import com.rmsi.mast.studio.domain.DocumentType;
import com.rmsi.mast.studio.domain.EducationLevel;
import com.rmsi.mast.studio.domain.Gender;
import com.rmsi.mast.studio.domain.GroupType;
import com.rmsi.mast.studio.domain.IdType;
import com.rmsi.mast.studio.domain.LandType;
import com.rmsi.mast.studio.domain.LandUseType;
import com.rmsi.mast.studio.domain.MaritalStatus;
import com.rmsi.mast.studio.domain.NaturalPerson;
import com.rmsi.mast.studio.domain.NonNaturalPerson;
import com.rmsi.mast.studio.domain.OccupancyType;
import com.rmsi.mast.studio.domain.Person;
import com.rmsi.mast.studio.domain.PersonType;
import com.rmsi.mast.studio.domain.ProjectAdjudicator;
import com.rmsi.mast.studio.domain.ProjectArea;
import com.rmsi.mast.studio.domain.RelationshipType;
import com.rmsi.mast.studio.domain.ShareType;
import com.rmsi.mast.studio.domain.SlopeValues;
import com.rmsi.mast.studio.domain.SocialTenureRelationship;
import com.rmsi.mast.studio.domain.SoilQualityValues;
import com.rmsi.mast.studio.domain.SourceDocument;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.studio.domain.TenureClass;
import com.rmsi.mast.studio.domain.Unit;
import com.rmsi.mast.studio.domain.fetch.AttributeValuesFetch;
import com.rmsi.mast.studio.domain.fetch.ClaimProfile;
import com.rmsi.mast.studio.domain.fetch.ClaimSummary;
import com.rmsi.mast.studio.domain.fetch.PersonAdministrator;
import com.rmsi.mast.studio.domain.fetch.PersonForEditing;
import com.rmsi.mast.studio.domain.fetch.ProjectDetails;
import com.rmsi.mast.studio.domain.fetch.RegistryBook;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitBasic;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitGeom;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitStatusHistory;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTable;
import com.rmsi.mast.studio.domain.fetch.SpatialUnitTemp;
import com.rmsi.mast.studio.domain.fetch.SpatialunitDeceasedPerson;
import com.rmsi.mast.studio.domain.fetch.SpatialunitPersonadministrator;
import com.rmsi.mast.studio.domain.fetch.SpatialunitPersonwithinterest;
import com.rmsi.mast.studio.domain.fetch.Usertable;
import com.rmsi.mast.studio.mobile.dao.AttributeValuesDao;
import com.rmsi.mast.studio.mobile.dao.CitizenshipDao;
import com.rmsi.mast.studio.mobile.dao.EducationLevelDao;
import com.rmsi.mast.studio.mobile.dao.LandUseTypeDao;
import com.rmsi.mast.studio.mobile.dao.NaturalPersonDao;
import com.rmsi.mast.studio.mobile.dao.NonNaturalPersonDao;
import com.rmsi.mast.studio.mobile.dao.StatusDao;
import com.rmsi.mast.studio.mobile.dao.SurveyProjectAttributeDao;
import com.rmsi.mast.studio.util.StringUtils;
import com.rmsi.mast.viewer.dao.LandRecordsDao;
import com.rmsi.mast.viewer.dao.PersonAdministratorDao;
import com.rmsi.mast.viewer.dao.SpatialStatusDao;
import com.rmsi.mast.viewer.dao.SpatialUnitDeceasedPersonDao;
import com.rmsi.mast.viewer.dao.SpatialUnitPersonAdministratorDao;
import com.rmsi.mast.viewer.dao.SpatialUnitPersonWithInterestDao;
import com.rmsi.mast.viewer.dao.SpatialUnitTempDao;
import com.rmsi.mast.viewer.service.LandRecordsService;
import java.util.Calendar;

@Service
public class LandRecordsServiceImpl implements LandRecordsService {

    private static final Logger logger = Logger.getLogger(LandRecordsServiceImpl.class);

    @Autowired
    private LandRecordsDao landRecordsDao;

    @Autowired
    private LandUseTypeDao landUseTypeDao;

    @Autowired
    private SpatialStatusDao spatialStatusDao;

    @Autowired
    private SocialTenureRelationshipDAO socialTenureRelationshipDAO;

    @Autowired
    private NaturalPersonDao naturalPersonDao;

    @Autowired
    private SUnitHistoryDAO sUnitHistoryDAO;

    @Autowired
    private NonNaturalPersonDao nonNaturalPersonDao;
    @Autowired
    private GenderDAO genderDAO;

    @Autowired
    private MaritalStatusDAO maritalStatusDAO;

    @Autowired
    private PersonTypeDAO personTypeDAO;

    @Autowired
    private EducationLevelDao educationLevelDao;

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private OccupancyDAO occupancyDAO;

    @Autowired
    private TenureClassDAO tenureClassDAO;

    @Autowired
    private SourceDocumentDAO sourceDocumentDAO;

    @Autowired
    private ShareTypeDAO socialTenureRelationshipTypeDAO;

    @Autowired
    private StatusDao statusDao;

    @Autowired
    private UsertableDAO usertableDAO;

    @Autowired
    private UnitDAO unitDAO;

    @Autowired
    private AttributeCategoryDAO attributeCategoryDAO;

    @Autowired
    private AttributeMasterDAO attributeMasterDAO;

    @Autowired
    private AttributeValueFetchDAO attributeValueFetchDAO;

    @Autowired
    private ProjectAreaDAO projectAreaDAO;

    @Autowired
    private LandTypeDAO landTypeDAO;
    @Autowired
    private SoilQualityValuesDAO soilQualityValuesDAO;
    @Autowired
    private SlopeValuesDAO slopeValuesDAO;

    @Autowired
    private GroupTypeDAO groupTypeDAO;

    @Autowired
    private SpatialUnitTempDao spatialUnitTempDao;

    @Autowired
    private AttributeValuesDao attributeValuesDao;

    @Autowired
    private SpatialUnitPersonAdministratorDao spatialUnitPersonAdministratorDao;

    @Autowired
    private PersonAdministratorDao personAdministratorDao;

    @Autowired
    private SurveyProjectAttributeDao surveyProjectAttributeDao;

    @Autowired
    private SpatialUnitPersonWithInterestDao spatialUnitPersonWithInterestDao;

    @Autowired
    private SpatialUnitDeceasedPersonDao spatialUnitDeceasedPersonDao;

    @Autowired
    private ProjectAdjudicatorDAO projectAdjudicatorDAO;

    @Autowired
    private CitizenshipDao citizenshipDao;

    @Autowired
    private ClaimTypeDao claimTypeDao;

    @Autowired
    private AcquisitionTypeDao acquisitionTypeDao;

    @Autowired
    private RelationshipTypeDao relationshipTypeDao;

    @Autowired
    private IdTypeDao idTypeDao;

    @Autowired
    private DocumentTypeDao documentTypeDao;

    @Autowired
    private DisputeDao disputeDao;

    @Autowired
    private DisputeTypeDao disputeTypeDao;

    @Autowired
    private DisputeStatusDao disputeStatusDao;

    @Override
    public List<SpatialUnitTable> findAllSpatialUnit(String defaultProject) {
        return landRecordsDao.findallspatialUnit(defaultProject);
    }

    public void updateStatusHistory(Long id, long userid, int statusCode) {
        try {
            SpatialUnitStatusHistory spatialUnitStatusHistory = new SpatialUnitStatusHistory();
            spatialUnitStatusHistory.setStatus_change_time(new Date());
            spatialUnitStatusHistory.setUserid(userid);
            spatialUnitStatusHistory.setUsin(id);
            spatialUnitStatusHistory.setWorkflow_status_id(statusCode);
            sUnitHistoryDAO.makePersistent(spatialUnitStatusHistory);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public List<Status> findallStatus() {
        return spatialStatusDao.findAll();
    }

    @Override
    public List<IdType> getIdTypes() {
        return idTypeDao.findAll();
    }

    @Override
    public IdType getIdType(int code) {
        return idTypeDao.findById(code, false);
    }

    @Override
    public List<ClaimType> getClaimTypes() {
        return claimTypeDao.findAll();
    }

    @Override
    public List<SpatialUnitTable> findSpatialUnitbyId(Long id) {
        return landRecordsDao.findSpatialUnitById(id);
    }

    @Override
    public SpatialUnitBasic getSpatialUnitBasic(Long usin){
        return landRecordsDao.getSpatialUnitBasic(usin);
    }
    
    @Override
    public SpatialUnitTable getSpatialUnit(Long id) {
        return landRecordsDao.getSpatialUnit(id);
    }

    @Override
    public SpatialUnitGeom getParcelGeometry(long usin) {
        return landRecordsDao.getParcelGeometry(usin);
    }

    @Override
    public boolean referClaim(SpatialUnitTable claim, long userId) {
        claim.setStatus(statusDao.getStatusById(Status.STATUS_REFERRED));
        updateStatusHistory(claim.getUsin(), userId, Status.STATUS_REFERRED);
        return update(claim);
    }

    @Override
    public boolean makeClaimValidated(SpatialUnitTable claim, long userId) {
        claim.setStatus(statusDao.getStatusById(Status.STATUS_VALIDATED));
        updateStatusHistory(claim.getUsin(), userId, Status.STATUS_VALIDATED);
        return update(claim);
    }

    @Override
    public boolean approveClaim(SpatialUnitTable claim, long userId) {
        claim.setStatus(statusDao.getStatusById(Status.STATUS_APPROVED));
        updateStatusHistory(claim.getUsin(), userId, Status.STATUS_APPROVED);
        return update(claim);
    }

    @Override
    public List<SpatialUnitTable> findAllSpatialUnitlist() {
        return landRecordsDao.findAll();
    }

    @Override
    public boolean update(SpatialUnitTable spatialUnit) {
        try {
            landRecordsDao.makePersistent(spatialUnit);
            return true;
        } catch (Exception e) {

            logger.error(e);
            return false;
        }
    }

    @Override
    public List<SocialTenureRelationship> findAllSocialTenure() {

        return socialTenureRelationshipDAO.findAll();
    }

    @Override
    public List<NaturalPerson> naturalPersonById(Long id) {

        try {
            return naturalPersonDao.findById(id);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }

    }

    @Override
    public List<Gender> findAllGenders() {
        return genderDAO.findAll();
    }

    @Override
    public Gender findGenderById(Long id) {
        return genderDAO.findById(id, false);
    }

    @Override
    public NaturalPerson editnatural(NaturalPerson naturalPerson) {
        try {
            return naturalPersonDao.makePersistent(naturalPerson);

        } catch (Exception e) {

            logger.error(e);

        }
        return null;
    }

    @Override
    public MaritalStatus findMaritalById(int maritalid) {
        return maritalStatusDAO.findById(maritalid, false);
    }

    @Override
    public PersonType findPersonTypeById(long persontype) {
        return personTypeDAO.findById(persontype, false);
    }

    @Override
    public List<MaritalStatus> findAllMaritalStatus() {
        return maritalStatusDAO.findAll();
    }

    @Override
    public List<SocialTenureRelationship> findAllSocialTenureByUsin(Long id) {
        return socialTenureRelationshipDAO.findbyUsin(id);
    }

    @Override
    public List<NonNaturalPerson> nonnaturalPersonById(Long id) {
        return nonNaturalPersonDao.findById(id);
    }

    @Override
    public NonNaturalPerson getNonNaturalPersonBy(Long id) {
        return nonNaturalPersonDao.findById(id, false);
    }

    @Override
    public NonNaturalPerson editNonNatural(NonNaturalPerson nonnaturalPerson) {
        try {
            return nonNaturalPersonDao.makePersistent(nonnaturalPerson);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public List<Dispute> getDisputes(long usin) {
        return disputeDao.findActiveByPropId(usin);
    }

    @Override
    public Dispute getDispute(long id) {
        return disputeDao.findById(id, false);
    }

    @Override
    public List<DisputeType> getDisputeTypes() {
        return disputeTypeDao.findAll();
    }

    @Override
    public DisputeType getDisputeType(int code) {
        return disputeTypeDao.findById(code, false);
    }

    @Override
    public ShareType findTenureType(Long type) {

        try {
            return socialTenureRelationshipTypeDAO.findById(type.intValue(), false);
        } catch (Exception e) {

            logger.error(e);
            return null;
        }
    }

    @Override
    public SocialTenureRelationship edittenure(SocialTenureRelationship socialTenureRelationship) {
        try {
            return socialTenureRelationshipDAO.makePersistent(socialTenureRelationship);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public Person findPersonGidById(long persontype) {
        return personDAO.findById(persontype, false);
    }

    @Override
    public OccupancyType findOccupancyTyoeById(int occid) {
        return occupancyDAO.findById(occid, false);
    }

    @Override
    public TenureClass findtenureClasseById(int tenureclassid) {
        return tenureClassDAO.findById(tenureclassid, false);
    }

    @Override
    public List<SocialTenureRelationship> findSocialTenureByGid(Integer id) {
        return socialTenureRelationshipDAO.findByGid(id);
    }

    @Override
    public SocialTenureRelationship getSocialTenure(Integer id) {
        return socialTenureRelationshipDAO.getSocialTenure(id);
    }

    @Override
    public List<ShareType> findAllTenureList() {
        return socialTenureRelationshipTypeDAO.findAll();
    }

    @Override
    public List<SourceDocument> findMultimediaByUsin(Long id) {
        return sourceDocumentDAO.findSourceDocumentById(id);

    }

    @Override
    public List<SourceDocument> findAllMultimedia() {

        return sourceDocumentDAO.findAll();
    }

    @Override
    public boolean updateMultimedia(SourceDocument sourceDocument) {
        try {
            sourceDocumentDAO.makePersistent(sourceDocument);
            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    public Dispute updateDispute(Dispute dispute) {
        try {
            dispute = disputeDao.makePersistent(dispute);
            SpatialUnitTable parcel = findSpatialUnitbyId(dispute.getUsin()).get(0);

            // Check for parcel to have new or referred status and be new claim or disputed
            if ((parcel.getStatus().getWorkflowStatusId() == Status.STATUS_NEW
                    || parcel.getStatus().getWorkflowStatusId() != Status.STATUS_REFERRED)
                    && parcel.getClaimType().getCode().equals(ClaimType.CODE_NEW)) {
                parcel.setClaimType(claimTypeDao.findById(ClaimType.CODE_DISPUTED, false));
                parcel.setStatus(statusDao.getStatusById(Status.STATUS_REFERRED));
                update(parcel);
            }

            return dispute;
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public boolean deleteDispute(Dispute dispute) {
        try {
            dispute.setDeleted(true);
            disputeDao.makePersistent(dispute);
            SpatialUnitTable parcel = findSpatialUnitbyId(dispute.getUsin()).get(0);

            // Check for parcel to have new or referred status and be new claim or disputed
            if (parcel.getStatus().getWorkflowStatusId() == Status.STATUS_REFERRED) {
                parcel.setStatus(statusDao.getStatusById(Status.STATUS_NEW));
            }
            if (parcel.getClaimType().getCode().equals(ClaimType.CODE_DISPUTED)) {
                parcel.setClaimType(claimTypeDao.findById(ClaimType.CODE_NEW, false));
            }

            update(parcel);
            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    public boolean resolveDispute(Dispute dispute) {
        try {
            dispute.setStatus(disputeStatusDao.findById(DisputeStatus.STATUS_RESOLVED, false));
            dispute.setResolutionDate(Calendar.getInstance().getTime());
            disputeDao.makePersistent(dispute);

            SpatialUnitTable parcel = findSpatialUnitbyId(dispute.getUsin()).get(0);

            // Check for parcel to have new or referred status and be new claim or disputed
            if (parcel.getStatus().getWorkflowStatusId() == Status.STATUS_REFERRED) {
                parcel.setStatus(statusDao.getStatusById(Status.STATUS_NEW));
            }
            if (parcel.getClaimType().getCode().equals(ClaimType.CODE_DISPUTED)) {
                parcel.setClaimType(claimTypeDao.findById(ClaimType.CODE_NEW, false));
            }

            update(parcel);
            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteDisputant(Long disputeId, Long partyId) {
        try {
            Dispute dispute = disputeDao.findById(disputeId, false);
            if (dispute == null) {
                return false;
            }

            if (dispute.getDisputingPersons() != null && dispute.getDisputingPersons().size() > 0) {
                for (int i = 0; i < dispute.getDisputingPersons().size(); i++) {
                    if (dispute.getDisputingPersons().get(i).getPerson_gid() == partyId) {
                        dispute.getDisputingPersons().remove(i);
                        break;
                    }
                }
                disputeDao.makePersistent(dispute);
            }

            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    public List<SourceDocument> findMultimediaByGid(Long id) {
        return sourceDocumentDAO.findByGId(id);
    }

    @Override
    public boolean deleteMultimedia(Long id) {
        return sourceDocumentDAO.deleteMultimedia(id);

    }

    @Override
    public boolean deleteNatural(Long id) {
        //check if source document is not present against person_gid
        if (getdocumentByPerson(id) != null) {
            deleteMultimedia(Long.valueOf(getdocumentByPerson(id).getGid()));
        }

        return socialTenureRelationshipDAO.deleteNatural(id);
    }

    @Override
    public boolean deleteNonNatural(Long id) {

        return socialTenureRelationshipDAO.deleteNonNatural(id);

    }

    @Override
    public boolean deleteTenure(Long id) {

        return socialTenureRelationshipDAO.deleteTenure(id);

    }

    @Override
    public Status findAllSTatus(Long statusId) {
        return statusDao.findById(statusId.intValue(), false);
    }

    @Override
    public List<EducationLevel> findAllEducation() {
        return educationLevelDao.findAll();
    }

    @Override
    public List<LandUseType> findAllLanduserType() {
        return landUseTypeDao.findAll();
    }

    @Override
    public List<TenureClass> findAllTenureClass() {
        return tenureClassDAO.findAll();
    }

    @Override
    public EducationLevel findEducationById(Long education) {
        return educationLevelDao.findById(education.intValue(), false);
    }

    @Override
    public LandUseType findLandUseById(int existingUse) {
        return landUseTypeDao.findById(existingUse, false);
    }

    @Override
    public LandUseType findProposedUseById(int proposedUse) {
        return landUseTypeDao.findById(proposedUse, false);
    }

    @Override
    public Usertable findUserByID(int userid) {
        return usertableDAO.findById(userid, false);
    }

    @Override
    public String findukaNumberByUsin(Long id) {

        return landRecordsDao.findukaNumberByUsin(id);
    }

    @Override
    public List<OccupancyType> findAllOccupancyTypes() {
        return occupancyDAO.findAll();
    }

    @Override
    public List<AcquisitionType> findAllAcquisitionTypes() {
        return acquisitionTypeDao.findAll();
    }

    @Override
    public List<DocumentType> getDocumentTypes() {
        return documentTypeDao.findAll();
    }

    @Override
    public DocumentType getDocumentType(long code) {
        return documentTypeDao.findById(code, false);
    }

    @Override
    public AcquisitionType findAcquisitionType(int code) {
        return acquisitionTypeDao.findById(code, false);
    }

    @Override
    public List<RelationshipType> findAllRelationshipTypes() {
        return relationshipTypeDao.findAll();
    }

    @Override
    public RelationshipType findRelationshipType(Long code) {
        return relationshipTypeDao.findById(code, false);
    }

    @Override
    public boolean rejectStatus(Long id, long userid) {
        updateStatusHistory(id, userid, Status.STATUS_DENIED);
        return landRecordsDao.rejectStatus(id);
    }

    @Override
    public Unit findAllMeasurementUnit(String measurement_unit) {
        return unitDAO.findByName(measurement_unit);
    }

    @Override
    public List<AttributeCategory> findAllAttributeCategories() {

        return attributeCategoryDAO.findAll();
    }

    @Override
    public List<AttributeValuesFetch> findAttributelistByCategoryId(Long parentid, Long id) {
        try {
            return attributeMasterDAO.fetchCustomAttribs(parentid, id.intValue());
        } catch (Exception e) {
            logger.error(e);
            return null;
        }

    }

    @Override
    public boolean updateAttributeValues(Long value_key, String alias) {
        return attributeValueFetchDAO.updateValue(value_key, alias);
    }

    @Override
    public boolean updateFinal(Long id, long userid) {

        try {

            SpatialUnitStatusHistory spatialUnitStatusHistory = new SpatialUnitStatusHistory();

            spatialUnitStatusHistory.setStatus_change_time(new Date());
            spatialUnitStatusHistory.setUserid(userid);
            spatialUnitStatusHistory.setUsin(id);
            spatialUnitStatusHistory.setWorkflow_status_id(7);

            sUnitHistoryDAO.makePersistent(spatialUnitStatusHistory);
        } catch (Exception e) {

            logger.error(e);
        }

        return landRecordsDao.updateFinal(id);
    }

    @Override
    public SourceDocument saveUploadedDocuments(SourceDocument sourceDocument) {

        try {
            return sourceDocumentDAO.makePersistent(sourceDocument);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public boolean updateAdjudicated(Long id, long userid) {

        try {

            SpatialUnitStatusHistory spatialUnitStatusHistory = new SpatialUnitStatusHistory();

            spatialUnitStatusHistory.setStatus_change_time(new Date());
            spatialUnitStatusHistory.setUserid(userid);
            spatialUnitStatusHistory.setUsin(id);
            spatialUnitStatusHistory.setWorkflow_status_id(2);

            sUnitHistoryDAO.makePersistent(spatialUnitStatusHistory);
        } catch (Exception e) {

            logger.error(e);
        }

        return landRecordsDao.updateAdjudicated(id);
    }

    @Override
    public SourceDocument getDocumentbyGid(Long id) {

        try {
            return sourceDocumentDAO.findById(id.intValue(), false);
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<ProjectArea> findProjectArea(String projectName) {
        return projectAreaDAO.findByProjectName(projectName);
    }

    @Override
    public List<SoilQualityValues> findAllsoilQuality() {

        return soilQualityValuesDAO.findAll();
    }

    @Override
    public List<SlopeValues> findAllSlopeValues() {
        return slopeValuesDAO.findAll();
    }

    @Override
    public List<LandType> findAllLandType() {
        return landTypeDAO.findAll();
    }

    @Override
    public SoilQualityValues findSoilQuality(int quality_soil) {
        return soilQualityValuesDAO.findById(quality_soil, false);
    }

    @Override
    public SlopeValues findSlopeValues(int slope_values) {
        return slopeValuesDAO.findById(slope_values, false);
    }

    @Override
    public LandType findLandType(int land_type) {
        return landTypeDAO.findById(land_type, false);
    }

    @Override
    public List<GroupType> findAllGroupType() {
        return groupTypeDAO.findAll();
    }

    @Override
    public GroupType findGroupType(int group_type) {
        return groupTypeDAO.findById(group_type, false);
    }

    @Override
    public boolean updateCCRO(Long id, long userid) {
        try {
            SpatialUnitStatusHistory spatialUnitStatusHistory = new SpatialUnitStatusHistory();
            spatialUnitStatusHistory.setStatus_change_time(new Date());
            spatialUnitStatusHistory.setUserid(userid);
            spatialUnitStatusHistory.setUsin(id);
            spatialUnitStatusHistory.setWorkflow_status_id(6);

            sUnitHistoryDAO.makePersistent(spatialUnitStatusHistory);
        } catch (Exception e) {
            logger.error(e);
        }

        SpatialUnitTable spatialUnit = new SpatialUnitTable();
        try {
            spatialUnit = landRecordsDao.findSpatialUnitById(id).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Status status = findAllSTatus(6L);
        spatialUnit.setStatus(status);
        landRecordsDao.makePersistent(spatialUnit);
        return true;
    }

    @Override
    public List<SpatialUnitTemp> findAllSpatialUnitTemp(String defaultProject, int startfrom) {
        return spatialUnitTempDao.findOrderedSpatialUnit(defaultProject, startfrom);
    }

    @Override
    public List<SpatialUnitTable> search(String usinStr, String ukaNumber,
            String projname, String dateto, String datefrom,
            Long status, String claimType, Integer startpos) {
        return landRecordsDao.search(usinStr, ukaNumber, projname, dateto, datefrom, status, claimType, startpos);
    }

    @Override
    public ArrayList<Long> findOwnerPersonByUsin(Long id) {
        List<SocialTenureRelationship> socailTenure = socialTenureRelationshipDAO.findbyUsin(id);
        ArrayList<Long> naturalPerson = new ArrayList<Long>();

        for (int i = 0; i < socailTenure.size(); i++) {

            naturalPerson.add(socailTenure.get(i).getPerson_gid().getPerson_gid());

        }

        return naturalPersonDao.findOwnerByGid(naturalPerson);

    }

    @Override
    public List<Long> getAdminId(Long id) {

        return spatialUnitPersonAdministratorDao.findAdminIdbyUsin(id);
    }

    @Override
    public PersonAdministrator getAdministratorById(Long admiLong) {
        return personAdministratorDao.findAdminById(admiLong);

    }

    @Override
    public SourceDocument getdocumentByAdminId(Long adminID) {
        return sourceDocumentDAO.findDocumentByAdminId(adminID);
    }

    @Override
    public Integer AllSpatialUnitTemp(String defaultProject) {
        return spatialUnitTempDao.AllSpatialUnitTemp(defaultProject);
    }

    @Override
    public boolean deleteSpatialUnit(Long id) {

        return landRecordsDao.deleteSpatial(id);
    }

    @Override
    public List<SocialTenureRelationship> showDeletedPerson(Long id) {
        try {
            return socialTenureRelationshipDAO.findDeletedPerson(id);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public boolean addDeletedPerson(Long gid) {
        return socialTenureRelationshipDAO.addDeletedPerson(gid);
    }

    @Override
    public Integer searchListSize(String usinStr, String ukaNumber,
            String projname, String dateto, String datefrom, long status, String claimType) {
        // 
        return landRecordsDao.searchSize(usinStr, ukaNumber, projname, dateto, datefrom, status, claimType);
    }

    @Override
    public SourceDocument getdocumentByPerson(Long person_gid) {
        return sourceDocumentDAO.getDocumentByPerson(person_gid);
    }

    @Override
    public PersonAdministrator editAdmin(PersonAdministrator personobj) {

        try {
            return personAdministratorDao.makePersistent(personobj);
        } catch (Exception e) {

            logger.error(e);
            return null;
        }
    }

    @Override
    public boolean deleteAdminById(Long id) {

        return personAdministratorDao.deleteAdminById(id);

    }

    @Override
    public boolean addAdminById(Long adminId) {

        return personAdministratorDao.addAdmin(adminId);
    }

    @Override
    public SpatialunitPersonadministrator updateSpatialAdmin(SpatialunitPersonadministrator spaobj) {
        try {
            return spatialUnitPersonAdministratorDao.makePersistent(spaobj);
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<String> findnaturalCustom(String project) {
        return surveyProjectAttributeDao.findnaturalCustom(project);
    }

    @Override
    public boolean saveAttributealues(AttributeValues tmpvalue) {
        try {
            attributeValuesDao.makePersistent(tmpvalue);
            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;

        }

    }

    @Override
    public List<SpatialunitPersonwithinterest> findpersonInterestByUsin(Long usin) {
        return spatialUnitPersonWithInterestDao.findByUsin(usin);
    }

    @Override
    public SpatialunitPersonwithinterest getPoi(Long id) {
        return spatialUnitPersonWithInterestDao.findById(id, false);
    }

    @Override
    public List<SpatialUnitTemp> findSpatialUnitforUKAGeneration(
            String project) {
        return spatialUnitTempDao.findSpatialUnitforUKAGeneration(project);
    }

    @Override
    public void addSpatialUnitTemp(SpatialUnitTemp spatialunitmp) {
        try {
            spatialUnitTempDao.makePersistent(spatialunitmp);
        } catch (Exception e) {
            logger.error(e);
        }

    }

    @Override
    public String findNextUkaNumber(String ukaPrefix) {
        try {
            String uka = landRecordsDao.findBiggestUkaNumber(ukaPrefix);
            if (StringUtils.isEmpty(uka)) {
                return ukaPrefix + "1";
            } else {
                String number = uka.substring(ukaPrefix.length());
                return ukaPrefix + Integer.toString(Integer.parseInt(number) + 1);
            }
        } catch (Exception e) {
            logger.error(e);
            return ukaPrefix + "1";
        }
    }

    @Override
    public List<Long> findUsinforUKAGeneration(String project,
            String hamletCode) {
        return spatialUnitTempDao.findUsinforUKAGeneration(project, hamletCode);
    }

    @Override
    public boolean updateUKAnumber(Long long1, String uka) {
        return spatialUnitTempDao.updateUKAnumber(long1, uka);
    }

    @Override
    public boolean addnxtTokin(SpatialunitPersonwithinterest spi) {
        try {
            spatialUnitPersonWithInterestDao.makePersistent(spi);
            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    public boolean deletePersonWithInterest(Long id) {
        try {
            spatialUnitPersonWithInterestDao.makeTransientByID(id);
            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    public List<PersonType> AllPersonType() {
        return personTypeDAO.findAll();
    }

    @Override
    public List<SpatialUnitTable> getSpatialUnitByBbox(String bbox, String project_name) {

        return landRecordsDao.getSpatialUnitByBbox(bbox, project_name);
    }

    @Override
    public AttributeValues getAttributeValue(Long value_key) {
        try {
            return attributeValuesDao.findById(value_key, false);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public Long getAttributeKey(long person_gid, long uid) {
        return attributeValuesDao.getAttributeKeyById(person_gid, uid);
    }

    @Override
    public boolean findExistingHamlet(long hamlet_id) {
        return landRecordsDao.findExistingHamlet(hamlet_id);
    }

    @Override
    public List<SpatialunitDeceasedPerson> findDeceasedPersonByUsin(
            Long usin) {

        return spatialUnitDeceasedPersonDao.findPersonByUsin(usin);
    }

    @Override
    public boolean saveDeceasedPerson(SpatialunitDeceasedPerson spdeceased) {
        try {
            spatialUnitDeceasedPersonDao.makePersistent(spdeceased);
            return true;
        } catch (Exception e) {

            logger.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteDeceasedPerson(Long id) {

        try {
            spatialUnitDeceasedPersonDao.makeTransientByID(id);
            return true;
        } catch (Exception e) {

            logger.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteAllVertexLabel() {
        return landRecordsDao.deleteAllVertexLabel();
    }

    @Override
    public boolean addAllVertexLabel(int k, String lat, String lon) {
        return landRecordsDao.addAllVertexLabel(k, lat, lon);
    }

    @Override
    public ProjectAdjudicator findAdjudicatorByID(int witness1) {
        return projectAdjudicatorDAO.findById(witness1, false);
    }

    @Override
    public boolean updateSharePercentage(String alias, long personGid) {
        return socialTenureRelationshipDAO.updateSharePercentage(alias, personGid);
    }

    @Override
    public Citizenship findcitizenship(long citizenship) {
        return citizenshipDao.findBycitizenId(citizenship);
    }

    @Override
    public List<Citizenship> findAllCitizenShip() {
        try {
            return citizenshipDao.findAll();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteNaturalImage(Long id) {
        return sourceDocumentDAO.deleteNaturalPersonImage(id);
    }

    @Override
    public boolean checkActivePerson(Long id) {
        return sourceDocumentDAO.checkPersonImage(id);
    }

    @Override
    public List<ClaimSummary> getClaimsForAdjudicationForms(Long usin, int startRecord, int endRecord, int statusId, String projectName) {
        return landRecordsDao.getClaimsSummary(usin, startRecord, endRecord, projectName, statusId, ClaimType.CODE_NEW);
    }

    @Override
    public List<ClaimSummary> getClaimsForCcro(Long usin, int startRecord, int endRecord, String projectName) {
        return landRecordsDao.getClaimsSummary(usin, startRecord, endRecord, projectName, Status.STATUS_APPROVED, ClaimType.CODE_NEW);
    }

    @Override
    public List<RegistryBook> getRegistryBook(String projectName, long usin){
        return landRecordsDao.getRegistryBook(projectName, usin);
    }
    
    @Override
    public ClaimProfile getClaimsProfile(String projectName){
        return landRecordsDao.getClaimsProfile(projectName);
    }
    
    @Override
    public ProjectDetails getProjectDetails(String projectName) {
        return landRecordsDao.getProjectDetails(projectName);
    }
    
    @Override
    public List<PersonForEditing> getPersonsForEditing(String projectName, long usin, String firstName, String lastName, String middleName, String idNumber, String claimNumber, String neighbourN, String neighbourS, String neighbourE, String neighbourW){
        return landRecordsDao.getPersonsForEditing(projectName, usin, firstName, lastName, middleName, idNumber, claimNumber, neighbourN, neighbourS, neighbourE, neighbourW);
    }
    
    @Override
    public PersonForEditing updatePersonForEditing(PersonForEditing pfe) throws Exception {
        return landRecordsDao.updatePersonForEditing(pfe);
    }
}
