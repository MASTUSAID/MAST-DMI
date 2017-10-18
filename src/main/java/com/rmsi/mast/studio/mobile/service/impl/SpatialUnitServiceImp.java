package com.rmsi.mast.studio.mobile.service.impl;

import com.rmsi.mast.studio.dao.AcquisitionTypeDao;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsi.mast.studio.domain.Citizenship;
import com.rmsi.mast.studio.domain.ClaimType;
import com.rmsi.mast.studio.domain.Gender;
import com.rmsi.mast.studio.domain.GroupType;
import com.rmsi.mast.studio.domain.LandType;
import com.rmsi.mast.studio.domain.LandUseType;
import com.rmsi.mast.studio.domain.MaritalStatus;
import com.rmsi.mast.studio.domain.OccupancyType;
import com.rmsi.mast.studio.domain.ShareType;
import com.rmsi.mast.studio.domain.SlopeValues;
import com.rmsi.mast.studio.domain.SoilQualityValues;
import com.rmsi.mast.studio.domain.SpatialUnit;
import com.rmsi.mast.studio.domain.TenureClass;
import com.rmsi.mast.studio.mobile.dao.CitizenshipDao;
import com.rmsi.mast.studio.mobile.dao.GenderDao;
import com.rmsi.mast.studio.mobile.dao.GroupTypeDao;
import com.rmsi.mast.studio.mobile.dao.LandTypeDao;
import com.rmsi.mast.studio.mobile.dao.LandUseTypeDao;
import com.rmsi.mast.studio.mobile.dao.MaritalStatusDao;
import com.rmsi.mast.studio.mobile.dao.OccupancyTypeDao;
import com.rmsi.mast.studio.mobile.dao.ShareTypeDao;
import com.rmsi.mast.studio.mobile.dao.SlopeValuesDao;
import com.rmsi.mast.studio.mobile.dao.SoilQualityValuesDao;
import com.rmsi.mast.studio.mobile.dao.TenureClassDao;
import com.rmsi.mast.studio.mobile.dao.hibernate.SpatialUnitHibernateDao;
import com.rmsi.mast.studio.mobile.service.SpatialUnitService;
import com.rmsi.mast.studio.util.GeometryConversion;
import com.rmsi.mast.studio.dao.ClaimTypeDao;
import com.rmsi.mast.studio.dao.DisputeTypeDao;
import com.rmsi.mast.studio.dao.DocumentTypeDao;
import com.rmsi.mast.studio.domain.AcquisitionType;
import com.rmsi.mast.studio.domain.DisputeType;
import com.rmsi.mast.studio.domain.DocumentType;

@Service
public class SpatialUnitServiceImp implements SpatialUnitService {

    @Autowired
    SpatialUnitHibernateDao spatialUnitDao;

    @Autowired
    GenderDao genderDao;

    @Autowired
    MaritalStatusDao maritalStatusDao;

    @Autowired
    OccupancyTypeDao occupancyTypeDao;

    @Autowired
    TenureClassDao tenureClassDao;

    @Autowired
    ShareTypeDao tenureRelationTypeDao;

    @Autowired
    LandUseTypeDao landUseTypeDao;

    @Autowired
    SlopeValuesDao slopeValuesDao;

    @Autowired
    SoilQualityValuesDao soilQualityValuesDao;

    @Autowired
    GroupTypeDao groupTypeDao;

    @Autowired
    LandTypeDao landTypeDao;

    @Autowired
    CitizenshipDao citizenshipDao;

    @Autowired
    ClaimTypeDao claimTypeDAO;
    
    @Autowired
    DisputeTypeDao disputeTypeDao;
    
    @Autowired
    AcquisitionTypeDao acquisitionTypeDao;
    
    @Autowired
    DocumentTypeDao documentTypeDao;
    
    @Override
    public List<SpatialUnit> getSpatialUnitDataByProjectId(String projectId) {

        // Convert WKT to Geometry Type and returns the List<SpatialUnit>
        return spatialUnitDao.getSpatialUnitByProject(projectId);

    }

    @Override
    public Gender getGenderById(long genderId) {

        return genderDao.getGenderById(genderId);

    }

    @Override
    public MaritalStatus getMartitalStatus(int maritalStatusId) {

        return maritalStatusDao.getMaritalStatusById(maritalStatusId);

    }

    @Override
    public OccupancyType getOccupancyTypeById(int occId) {

        return occupancyTypeDao.getOccupancyTypeById(occId);

    }

    @Override
    public TenureClass getTenureClassById(int tenureId) {

        return tenureClassDao.getTenureClassById(tenureId);

    }

    @Override
    public ShareType getShareTypeById(int tenureRelationId) {
        return tenureRelationTypeDao.getTenureRelationshipTypeById(tenureRelationId);
    }

    @Override
    public LandUseType getLandUseTypeById(int landUseTypeId) {

        return landUseTypeDao.getLandUseTypeById(landUseTypeId);

    }

    @Override
    public SpatialUnit getSpatialUnitByUsin(long usin) {

        return spatialUnitDao.getSpatialUnitByUsin(usin);

    }

    @Override
    public List<Long> getSpatialUnitByStatusId(String projectId, int statusId) {

        List<Long> usinList = new ArrayList<Long>();

        List<SpatialUnit> spatialUnit = spatialUnitDao
                .findSpatialUnitByStatusId(projectId, statusId);

        if (spatialUnit != null) {

            Iterator<SpatialUnit> spatialUnitIter = spatialUnit.iterator();

            while (spatialUnitIter.hasNext()) {

                usinList.add(spatialUnitIter.next().getUsin());
            }
        }
        return usinList;
    }

    @Override
    public GroupType getGroupTypeById(int groupTypeId) {

        return groupTypeDao.getGroupTypeById(groupTypeId);

    }

    @Override
    public SoilQualityValues getSoilQualityValuesById(int soilQualityValueId) {

        return soilQualityValuesDao
                .getSoilQualityValuesById(soilQualityValueId);

    }

    @Override
    public SlopeValues getSlopeValuesById(int slopeValuesId) {

        return slopeValuesDao.getSlopeValuesById(slopeValuesId);

    }

    @Override
    public LandType getLandTypeById(int landTypeId) {

        return landTypeDao.getLandTypeById(landTypeId);

    }

    @Override
    public Citizenship getCitizenship(int val) {
        return citizenshipDao.getCitizensbyId(val);
    }

    @Override
    public List<ClaimType> getClaimTypes() {
        return claimTypeDAO.findAll();
    }
    
    @Override
    public ClaimType getClaimTypeById(String code) {
        return claimTypeDAO.findById(code, true);
    }
    
    @Override
    public List<DisputeType> getDisputeTypes(){
        return disputeTypeDao.findAll();
    }
    
    @Override
    public DisputeType getDisputeTypeById(int code){
        return disputeTypeDao.findById(code, true);
    }
    
    @Override
    public List<AcquisitionType> getAcquisitionTypes(){
        return acquisitionTypeDao.findAll();
    }
    
    @Override
    public AcquisitionType getAcquisitionTypeByAttributeOptionId(int optId){
        return acquisitionTypeDao.getTypeByAttributeOptionId(optId);
    }
    
    @Override
    public DocumentType getDocumentTypeByAttributeOptionId(int optId){
        return documentTypeDao.getTypeByAttributeOptionId(optId);
    }

    @Override
    public List<ShareType> getShareTypes() {
        return tenureRelationTypeDao.findAll();
    }

    @Override
    public List<TenureClass> getTenureClasses() {
        return tenureClassDao.findAll();
    }
    
    @Override
    public List<Gender> getGenders(){
        return genderDao.findAll();
    }
}
