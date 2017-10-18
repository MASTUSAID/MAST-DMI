package com.rmsi.mast.studio.mobile.service;

import com.rmsi.mast.studio.domain.AcquisitionType;
import java.util.List;

import com.rmsi.mast.studio.domain.Citizenship;
import com.rmsi.mast.studio.domain.ClaimType;
import com.rmsi.mast.studio.domain.DisputeType;
import com.rmsi.mast.studio.domain.DocumentType;
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

public interface SpatialUnitService {

    /**
     * This method declaration will be used to get Spatial Unit Data from the
     * database
     *
     * @param projectId
     * @return
     */
    List<SpatialUnit> getSpatialUnitDataByProjectId(String projectId);

    /**
     * This will get gender by gender id
     *
     * @param GenderId
     * @return
     */
    Gender getGenderById(long genderId);

    /**
     * This will get marital status based on marital status by id
     *
     * @param maritalId
     * @return
     */
    MaritalStatus getMartitalStatus(int maritalId);

    /**
     * This will get Occupation Type by Id
     *
     * @param occId
     * @return
     */
    OccupancyType getOccupancyTypeById(int occId);

    /**
     * This will get Tenure Class by Id
     *
     * @param tenureId
     * @return
     */
    TenureClass getTenureClassById(int tenureId);

    /**
     * This will get Social TenureRelation Type by id
     *
     * @param tenureRelationId
     * @return
     */
    ShareType getShareTypeById(int tenureRelationId);
    
    /**
     * This will get Social TenureRelation Types
     *
     * @return
     */
    List<ShareType> getShareTypes();
    
    /**
     * This will get Tenure Classes
     *
     * @return
     */
    List<TenureClass> getTenureClasses();

    /**
     * This will get Genders
     *
     * @return
     */
    List<Gender> getGenders();
    
    /**
     * This will get Land Use Type by id
     */
    LandUseType getLandUseTypeById(int landUseTypeId);

    /**
     * This will get Spatial Unit by its Usin
     *
     * @param usin
     * @return
     */
    SpatialUnit getSpatialUnitByUsin(long usin);

    /**
     * This will get Spatial Unit by StatusId
     *
     * @param statusId
     * @param projectId
     * @return
     */
    List<Long> getSpatialUnitByStatusId(String projectId, int statusId);

    /**
     * This will get GroupType by Id
     *
     * @param groupTypeId
     * @return
     */
    GroupType getGroupTypeById(int groupTypeId);

    /**
     * This will get Social SoilQualityValues Type by id
     *
     * @param soilQualityValueId
     * @return
     */
    SoilQualityValues getSoilQualityValuesById(int soilQualityValueId);

    /**
     * This will get Slope Values by id
     *
     * @param slopeValuesId
     * @return
     */
    SlopeValues getSlopeValuesById(int slopeValuesId);

    /**
     * This will get LandType by id
     *
     * @param landTypeId
     * @return
     */
    LandType getLandTypeById(int landTypeId);

    Citizenship getCitizenship(int value);

    /**
     * Returns claim types
     *
     * @return
     */
    List<ClaimType> getClaimTypes();

    /**
     * Returns claim type by code
     *
     * @param code Claim type code
     * @return
     */
    ClaimType getClaimTypeById(String code);

    /**
     * Returns list of dispute types
     *
     * @return
     */
    List<DisputeType> getDisputeTypes();
    
    /**
     * Returns dispute type by code
     *
     * @param code Claim type code
     * @return
     */
    DisputeType getDisputeTypeById(int code);

    /**
     * Returns Acquisition types list
     *
     * @return
     */
    List<AcquisitionType> getAcquisitionTypes();
    
    /**
     * Returns Acquisition type by option id
     *
     * @param optId Option id
     * @return
     */
    AcquisitionType getAcquisitionTypeByAttributeOptionId(int optId);

    /**
     * Returns Document type by option id
     *
     * @param optId Option id
     * @return
     */
    DocumentType getDocumentTypeByAttributeOptionId(int optId);
}
