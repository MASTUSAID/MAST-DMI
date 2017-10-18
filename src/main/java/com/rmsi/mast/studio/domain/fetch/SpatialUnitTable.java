package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rmsi.mast.studio.domain.ClaimType;
import com.rmsi.mast.studio.domain.LandType;
import com.rmsi.mast.studio.domain.LandUseType;
import com.rmsi.mast.studio.domain.ProjectHamlet;
import com.rmsi.mast.studio.domain.SlopeValues;
import com.rmsi.mast.studio.domain.SoilQualityValues;
import com.rmsi.mast.studio.domain.Status;
import com.rmsi.mast.studio.domain.Unit;
import com.rmsi.mast.studio.util.JsonDateSerializer;

@Entity
@Table(name = "spatial_unit")
public class SpatialUnitTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private long usin;

    @Column(name = "spatial_unit_type")
    private String type;

    @Column(name = "project_name")
    private String project;

    @ManyToOne
    @JoinColumn(name = "existing_use")
    private LandUseType existingUse;

    @ManyToOne
    @JoinColumn(name = "proposed_use")
    private LandUseType proposedUse;

    @Column(name = "identity", unique = true)
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

    @ManyToOne
    @JoinColumn(name = "measurement_unit", nullable = false)
    private Unit measurementUnit;

    @Column(name = "land_owner")
    private String landOwner;

    @Column(name = "uka_propertyno", nullable = false)
    private String propertyno;

    @Column(nullable = false)
    private String comments;

    @Column(nullable = false)
    private String gtype;

    @ManyToOne
    @JoinColumn(name = "current_workflow_status_id", nullable = false)
    private Status status;

    @Column(name = "workflow_status_update_time", nullable = false)
    private Date statusUpdateTime;

    @Column(nullable = false)
    private int userid;

    @ManyToOne
    @JoinColumn(name = "userid", insertable = false, updatable = false)
    private Usertable user;

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

    @ManyToOne
    @JoinColumn(name = "quality_of_soil", nullable = false)
    private SoilQualityValues soilQualityValues;

    @ManyToOne
    @JoinColumn(name = "slope", nullable = false)
    private SlopeValues slopeValues;

    @ManyToOne
    @JoinColumn(name = "type_name", nullable = false)
    private LandType landType;

    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "hamlet_id")
    private ProjectHamlet hamlet_Id;

    @ManyToOne
    @JoinColumn(name = "claim_type")
    ClaimType claimType;
    
    @Column(name = "polygon_number")
    String claimNumber;
    
    public Unit getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(Unit measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public LandUseType getExistingUse() {
        return existingUse;
    }

    public void setExistingUse(LandUseType existingUse) {
        this.existingUse = existingUse;
    }

    public LandUseType getProposedUse() {
        return proposedUse;
    }

    public void setProposedUse(LandUseType proposedUse) {
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

    /*
	public Status getStatusId() {
		return status;
	}

	public void setStatusId(Status status) {
		this.status = status;
	}*/

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

    @JsonSerialize(using = JsonDateSerializer.class)
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

    public Usertable getUser() {
        return user;
    }

    public void setUser(Usertable user) {
        this.user = user;
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

    public SoilQualityValues getSoilQualityValues() {
        return soilQualityValues;
    }

    public void setSoilQualityValues(SoilQualityValues soilQualityValues) {
        this.soilQualityValues = soilQualityValues;
    }

    public SlopeValues getSlopeValues() {
        return slopeValues;
    }

    public void setSlopeValues(SlopeValues slopeValues) {
        this.slopeValues = slopeValues;
    }

    public LandType getLandType() {
        return landType;
    }

    public void setLandType(LandType landType) {
        this.landType = landType;
    }

    public String getUsinStr() {
        return usinStr;
    }

    public void setUsinStr(String usinStr) {
        this.usinStr = usinStr;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ProjectHamlet getHamlet_Id() {
        return hamlet_Id;
    }

    public void setHamlet_Id(ProjectHamlet hamlet_Id) {
        this.hamlet_Id = hamlet_Id;
    }

    public ClaimType getClaimType() {
        return claimType;
    }

    public void setClaimType(ClaimType claimType) {
        this.claimType = claimType;
    }

    public String getClaimNumber() {
        return claimNumber;
    }

    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
    }
}
