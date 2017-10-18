package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "spatial_unit")
public class SpatialUnitBasic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private long usin;

    @Column(name = "spatial_unit_type")
    private String type;

    @Column(name = "project_name")
    private String project;

    @Column(name = "existing_use")
    private Integer existingUse;

    @Column(name = "proposed_use")
    private Integer proposedUse;

    @Column(name = "identity")
    private String identity;

    @Column(name = "house_type")
    private String houseType;

    @Column(name = "total_househld_no")
    private int househidno;

    @Column(name = "other_use_type")
    private String otherUseType;

    private Double perimeter;

    @Column(name = "house_shape")
    private String houseshape;

    private Double area;

    @Column(name = "measurement_unit")
    private String measurementUnit;

    @Column(name = "land_owner")
    private String landOwner;

    @Column(name = "uka_propertyno", nullable = false)
    private String propertyno;

    @Column(nullable = false)
    private String comments;

    @Column(nullable = false)
    private String gtype;

    @Column(name = "current_workflow_status_id")
    private Long status;

    @Column(name = "workflow_status_update_time")
    private Date statusUpdateTime;

    @Column
    private int userid;

    @Column(name = "survey_date", nullable = false)
    private Date surveyDate;

    @Column(name = "imei_number", nullable = false)
    private String imeiNumber;

    private String address1;

    private String address2;

    private String postal_code;

    private String neighbor_north;
    private String neighbor_south;
    private String neighbor_east;
    private String neighbor_west;
    private String witness_1;
    private String witness_2;
    private String witness_3;
    private String witness_4;

    @Column(name = "usin_str")
    private String usinStr;

    @Column(name = "quality_of_soil")
    private Integer soilQualityValues;

    @Column(name = "slope")
    private Integer slopeValues;

    @Column(name = "type_name")
    private String landType;

    private Boolean active;

    @Column(name = "hamlet_id")
    private Long hamletId;

    @Column(name = "claim_type")
    String claimType;
    
    @Column(name = "polygon_number")
    String claimNumber;

    public long getUsin() {
        return usin;
    }

    public void setUsin(long usin) {
        this.usin = usin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Integer getExistingUse() {
        return existingUse;
    }

    public void setExistingUse(Integer existingUse) {
        this.existingUse = existingUse;
    }

    public Integer getProposedUse() {
        return proposedUse;
    }

    public void setProposedUse(Integer proposedUse) {
        this.proposedUse = proposedUse;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public int getHousehidno() {
        return househidno;
    }

    public void setHousehidno(int househidno) {
        this.househidno = househidno;
    }

    public String getOtherUseType() {
        return otherUseType;
    }

    public void setOtherUseType(String otherUseType) {
        this.otherUseType = otherUseType;
    }

    public Double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(Double perimeter) {
        this.perimeter = perimeter;
    }

    public String getHouseshape() {
        return houseshape;
    }

    public void setHouseshape(String houseshape) {
        this.houseshape = houseshape;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public String getLandOwner() {
        return landOwner;
    }

    public void setLandOwner(String landOwner) {
        this.landOwner = landOwner;
    }

    public String getPropertyno() {
        return propertyno;
    }

    public void setPropertyno(String propertyno) {
        this.propertyno = propertyno;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getGtype() {
        return gtype;
    }

    public void setGtype(String gtype) {
        this.gtype = gtype;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Date getStatusUpdateTime() {
        return statusUpdateTime;
    }

    public void setStatusUpdateTime(Date statusUpdateTime) {
        this.statusUpdateTime = statusUpdateTime;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Date getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(Date surveyDate) {
        this.surveyDate = surveyDate;
    }

    public String getImeiNumber() {
        return imeiNumber;
    }

    public void setImeiNumber(String imeiNumber) {
        this.imeiNumber = imeiNumber;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getNeighbor_north() {
        return neighbor_north;
    }

    public void setNeighbor_north(String neighbor_north) {
        this.neighbor_north = neighbor_north;
    }

    public String getNeighbor_south() {
        return neighbor_south;
    }

    public void setNeighbor_south(String neighbor_south) {
        this.neighbor_south = neighbor_south;
    }

    public String getNeighbor_east() {
        return neighbor_east;
    }

    public void setNeighbor_east(String neighbor_east) {
        this.neighbor_east = neighbor_east;
    }

    public String getNeighbor_west() {
        return neighbor_west;
    }

    public void setNeighbor_west(String neighbor_west) {
        this.neighbor_west = neighbor_west;
    }

    public String getWitness_1() {
        return witness_1;
    }

    public void setWitness_1(String witness_1) {
        this.witness_1 = witness_1;
    }

    public String getWitness_2() {
        return witness_2;
    }

    public void setWitness_2(String witness_2) {
        this.witness_2 = witness_2;
    }

    public String getWitness_3() {
        return witness_3;
    }

    public void setWitness_3(String witness_3) {
        this.witness_3 = witness_3;
    }

    public String getWitness_4() {
        return witness_4;
    }

    public void setWitness_4(String witness_4) {
        this.witness_4 = witness_4;
    }

    public String getUsinStr() {
        return usinStr;
    }

    public void setUsinStr(String usinStr) {
        this.usinStr = usinStr;
    }

    public Integer getSoilQualityValues() {
        return soilQualityValues;
    }

    public void setSoilQualityValues(Integer soilQualityValues) {
        this.soilQualityValues = soilQualityValues;
    }

    public Integer getSlopeValues() {
        return slopeValues;
    }

    public void setSlopeValues(Integer slopeValues) {
        this.slopeValues = slopeValues;
    }

    public String getLandType() {
        return landType;
    }

    public void setLandType(String landType) {
        this.landType = landType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getHamletId() {
        return hamletId;
    }

    public void setHamletId(Long hamletId) {
        this.hamletId = hamletId;
    }

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public String getClaimNumber() {
        return claimNumber;
    }

    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
    }
}
