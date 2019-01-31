package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vividsolutions.jts.geom.Geometry;

/**
 * Entity implementation class for Entity: WorkflowStatusHistory
 *
 */
@Entity
@Table(name = "la_spatialunit_land")
public class LaSpatialunitLand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long landid;
    private String landno;
    private Integer projectnameid;
    private Integer spatialunitgroupid1;
    private Integer hierarchyid1;
    private Integer spatialunitgroupid2;
    private Integer hierarchyid2;
    private Integer spatialunitgroupid3;
    private Integer hierarchyid3;
    private Integer spatialunitgroupid4;
    private Integer hierarchyid4;
    private Integer spatialunitgroupid5;
    private Integer hierarchyid5;
    private Integer spatialunitgroupid6;
    private Integer hierarchyid6;
    private Integer landtypeid;
    private Integer landusetypeid;
    private Integer landsoilqualityid;
    private Integer acquisitiontypeid;
    private Integer claimtypeid;
    private Integer landsharetypeid;
    private Integer tenureclassid;
    private Integer slopevalueid;
    private Integer unitid;
    private double area;
    private String neighbor_east;
    private String neighbor_west;
    private String neighbor_north;
    private String neighbor_south;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date surveydate;
    private String geometrytype;

    private Integer proposedused;

    @Column(name = "geometry", columnDefinition = "Geometry")
    private Geometry geometry;
    private boolean isactive;
    private Integer createdby;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createddate;
    private Integer modifiedby;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date modifieddate;

    @Transient
    private Integer transactionid;

    @Transient
    private String applicationstatus_en;

    @Transient
    private Integer applicationstatusid;

    @Transient
    private Integer workflowstatusid;

    @Transient
    private String workflowstatus;

    @Transient
    private String tenancyType;

    @Transient
    private String shareType;

    @Transient
    private String claimtype_en;

    @Transient
    private String firstname;

    @Transient
    private String lastname;

    @Transient
    private String address;

    @Transient
    private String landusetype_en;

    @Transient
    private String landnostrwithzero;

    @Transient
    private String registrationNo;

    public Long getLandid() {
        return landid;
    }

    public void setLandid(Long landid) {
        this.landid = landid;
    }

    public String getLandno() {
        return landno;
    }

    public void setLandno(String landno) {
        this.landno = landno;
    }

    public Integer getProjectnameid() {
        return projectnameid;
    }

    public void setProjectnameid(Integer projectnameid) {
        this.projectnameid = projectnameid;
    }

    public Integer getSpatialunitgroupid1() {
        return spatialunitgroupid1;
    }

    public void setSpatialunitgroupid1(Integer spatialunitgroupid1) {
        this.spatialunitgroupid1 = spatialunitgroupid1;
    }

    public Integer getHierarchyid1() {
        return hierarchyid1;
    }

    public void setHierarchyid1(Integer hierarchyid1) {
        this.hierarchyid1 = hierarchyid1;
    }

    public Integer getSpatialunitgroupid2() {
        return spatialunitgroupid2;
    }

    public void setSpatialunitgroupid2(Integer spatialunitgroupid2) {
        this.spatialunitgroupid2 = spatialunitgroupid2;
    }

    public Integer getHierarchyid2() {
        return hierarchyid2;
    }

    public void setHierarchyid2(Integer hierarchyid2) {
        this.hierarchyid2 = hierarchyid2;
    }

    public Integer getSpatialunitgroupid3() {
        return spatialunitgroupid3;
    }

    public void setSpatialunitgroupid3(Integer spatialunitgroupid3) {
        this.spatialunitgroupid3 = spatialunitgroupid3;
    }

    public Integer getHierarchyid3() {
        return hierarchyid3;
    }

    public void setHierarchyid3(Integer hierarchyid3) {
        this.hierarchyid3 = hierarchyid3;
    }

    public Integer getSpatialunitgroupid4() {
        return spatialunitgroupid4;
    }

    public void setSpatialunitgroupid4(Integer spatialunitgroupid4) {
        this.spatialunitgroupid4 = spatialunitgroupid4;
    }

    public Integer getHierarchyid4() {
        return hierarchyid4;
    }

    public void setHierarchyid4(Integer hierarchyid4) {
        this.hierarchyid4 = hierarchyid4;
    }

    public Integer getSpatialunitgroupid5() {
        return spatialunitgroupid5;
    }

    public void setSpatialunitgroupid5(Integer spatialunitgroupid5) {
        this.spatialunitgroupid5 = spatialunitgroupid5;
    }

    public Integer getHierarchyid5() {
        return hierarchyid5;
    }

    public void setHierarchyid5(Integer hierarchyid5) {
        this.hierarchyid5 = hierarchyid5;
    }

    public Integer getSpatialunitgroupid6() {
        return spatialunitgroupid6;
    }

    public void setSpatialunitgroupid6(Integer spatialunitgroupid6) {
        this.spatialunitgroupid6 = spatialunitgroupid6;
    }

    public Integer getHierarchyid6() {
        return hierarchyid6;
    }

    public void setHierarchyid6(Integer hierarchyid6) {
        this.hierarchyid6 = hierarchyid6;
    }

    public Integer getLandtypeid() {
        return landtypeid;
    }

    public void setLandtypeid(Integer landtypeid) {
        this.landtypeid = landtypeid;
    }

    public Integer getLandusetypeid() {
        return landusetypeid;
    }

    public void setLandusetypeid(Integer landusetypeid) {
        this.landusetypeid = landusetypeid;
    }

    public Integer getLandsoilqualityid() {
        return landsoilqualityid;
    }

    public void setLandsoilqualityid(Integer landsoilqualityid) {
        this.landsoilqualityid = landsoilqualityid;
    }

    public Integer getAcquisitiontypeid() {
        return acquisitiontypeid;
    }

    public void setAcquisitiontypeid(Integer acquisitiontypeid) {
        this.acquisitiontypeid = acquisitiontypeid;
    }

    public Integer getClaimtypeid() {
        return claimtypeid;
    }

    public void setClaimtypeid(Integer claimtypeid) {
        this.claimtypeid = claimtypeid;
    }

    public Integer getLandsharetypeid() {
        return landsharetypeid;
    }

    public void setLandsharetypeid(Integer landsharetypeid) {
        this.landsharetypeid = landsharetypeid;
    }

    public Integer getTenureclassid() {
        return tenureclassid;
    }

    public void setTenureclassid(Integer tenureclassid) {
        this.tenureclassid = tenureclassid;
    }

    public Integer getSlopevalueid() {
        return slopevalueid;
    }

    public void setSlopevalueid(Integer slopevalueid) {
        this.slopevalueid = slopevalueid;
    }

    public Integer getUnitid() {
        return unitid;
    }

    public void setUnitid(Integer unitid) {
        this.unitid = unitid;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
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

    public Date getSurveydate() {
        return surveydate;
    }

    public void setSurveydate(Date surveydate) {
        this.surveydate = surveydate;
    }

    @JsonIgnore
    public String getGeometrytype() {
        return geometrytype;
    }

    public void setGeometrytype(String geometrytype) {
        geometry.setSRID(4326);
        this.geometrytype = geometrytype;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public boolean isIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    public Integer getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Integer createdby) {
        this.createdby = createdby;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public Integer getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(Integer modifiedby) {
        this.modifiedby = modifiedby;
    }

    public Date getModifieddate() {
        return modifieddate;
    }

    public void setModifieddate(Date modifieddate) {
        this.modifieddate = modifieddate;
    }

    public Integer getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(Integer transactionid) {
        this.transactionid = transactionid;
    }

    public String getApplicationstatus_en() {
        return applicationstatus_en;
    }

    public void setApplicationstatus_en(String applicationstatus_en) {
        this.applicationstatus_en = applicationstatus_en;
    }

    //@@ rmsi
    public Integer getApplicationstatusid() {
        return applicationstatusid;
    }

    public void setApplicationstatusid(Integer applicationstatusid) {
        this.applicationstatusid = applicationstatusid;
    }

    public String getClaimtype_en() {
        return claimtype_en;
    }

    public void setClaimtype_en(String claimtype_en) {
        this.claimtype_en = claimtype_en;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandusetype_en() {
        return landusetype_en;
    }

    public void setLandusetype_en(String landusetype_en) {
        this.landusetype_en = landusetype_en;
    }

    public Integer getWorkflowstatusid() {
        return workflowstatusid;
    }

    public void setWorkflowstatusid(Integer workflowstatusid) {
        this.workflowstatusid = workflowstatusid;
    }

    public String getWorkflowstatus() {
        return workflowstatus;
    }

    public void setWorkflowstatus(String workflowstatus) {
        this.workflowstatus = workflowstatus;
    }

    public String getLandnostrwithzero() {
        return landnostrwithzero;
    }

    public void setLandnostrwithzero(String landnostrwithzero) {
        this.landnostrwithzero = landnostrwithzero;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public Integer getProposedused() {
        return proposedused;
    }

    public void setProposedused(Integer proposedused) {
        this.proposedused = proposedused;
    }

    public String getTenancyType() {
        return tenancyType;
    }

    public void setTenancyType(String tenancyType) {
        this.tenancyType = tenancyType;
    }

}
