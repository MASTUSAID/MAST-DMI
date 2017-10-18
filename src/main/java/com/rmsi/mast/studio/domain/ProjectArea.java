package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the project_area database table.
 */
@Entity
@Table(name = "project_area")
public class ProjectArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PROJECT_AREA_AREAID_GENERATOR", sequenceName = "PROJECT_AREA_GID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJECT_AREA_AREAID_GENERATOR")
    @Column(name = "area_id")
    private Long areaId;

    @Column(name = "village_chairman")
    private String villageChairman;

    @Column(name = "approving_executive")
    private String approvingExecutive;

    @Temporal(TemporalType.DATE)
    @Column(name = "village_chairman_approval_date")
    private Date villageChairmanApprovalDate;

    @Column(name = "district_officer")
    private String districtOfficer;

    @Column(name = "authority_approve")
    private Boolean authorityApprove;

    @Column(name = "bounding_box")
    private String boundingBox;

    private String category;

    private String province;

    private String city;

    @Temporal(TemporalType.DATE)
    @Column(name = "executive_approval_date")
    private Date executiveApprovalDate;

    private String municipality;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "executive_approve")
    private Boolean executiveApprove;

    private Integer gid;

    @Temporal(TemporalType.DATE)
    @Column(name = "initiation_date")
    private Date initiationDate;

    private String location;

    private String perimeter;

    private Integer projectid;

    private String region;

    @Column(name = "state_name")
    private String stateName;

    private String village;

    private String wards;

    private String village_code;
    private String address;

    @Temporal(TemporalType.DATE)
    @Column(name = "recommendation_date")
    private Date recommendationDate;

    @Column(name = "name")
    private String projectName;

    @Column(name = "region_code")
    private String regionCode;

    @Column(name = "vc_meeting_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date vcMeetingDate;
    
    @Column(name = "village_chairman_signature")
    String villageChairmanSignaturePath;
    
    @Column(name = "village_executive_signature")
    String villageExecutiveSignaturePath;
    
    @Column(name = "district_officer_signature")
    String districtOfficerSignaturePath;
    
    public ProjectArea() {
    }

    public Long getAreaId() {
        return this.areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getApprovingExecutive() {
        return this.approvingExecutive;
    }

    public void setApprovingExecutive(String approvingExecutive) {
        this.approvingExecutive = approvingExecutive;
    }

    public Boolean getAuthorityApprove() {
        return this.authorityApprove;
    }

    public void setAuthorityApprove(Boolean authorityApprove) {
        this.authorityApprove = authorityApprove;
    }

    public String getBoundingBox() {
        return this.boundingBox;
    }

    public void setBoundingBox(String boundingBox) {
        this.boundingBox = boundingBox;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getExecutiveApprovalDate() {
        return this.executiveApprovalDate;
    }

    public void setExecutiveApprovalDate(Date executiveApprovalDate) {
        this.executiveApprovalDate = executiveApprovalDate;
    }

    public Boolean getExecutiveApprove() {
        return this.executiveApprove;
    }

    public void setExecutiveApprove(Boolean executiveApprove) {
        this.executiveApprove = executiveApprove;
    }

    public Integer getGid() {
        return this.gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Date getInitiationDate() {
        return this.initiationDate;
    }

    public void setInitiationDate(Date initiationDate) {
        this.initiationDate = initiationDate;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPerimeter() {
        return this.perimeter;
    }

    public void setPerimeter(String perimeter) {
        this.perimeter = perimeter;
    }

    public Integer getProjectid() {
        return this.projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public Date getRecommendationDate() {
        return this.recommendationDate;
    }

    public void setRecommendationDate(Date recommendationDate) {
        this.recommendationDate = recommendationDate;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getStateName() {
        return this.stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getVillage() {
        return this.village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getWards() {
        return this.wards;
    }

    public void setWards(String wards) {
        this.wards = wards;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMunicipality() {
        return this.municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getDistrictName() {
        return this.districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getVillageChairman() {
        return villageChairman;
    }

    public void setVillageChairman(String villageChairman) {
        this.villageChairman = villageChairman;
    }

    public Date getVillageChairmanApprovalDate() {
        return villageChairmanApprovalDate;
    }

    public void setVillageChairmanApprovalDate(Date villageChairmanApprovalDate) {
        this.villageChairmanApprovalDate = villageChairmanApprovalDate;
    }

    public String getDistrictOfficer() {
        return districtOfficer;
    }

    public void setDistrictOfficer(String districtOfficer) {
        this.districtOfficer = districtOfficer;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getVillage_code() {
        return village_code;
    }

    public void setVillage_code(String village_code) {
        this.village_code = village_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getVcMeetingDate() {
        return vcMeetingDate;
    }

    public void setVcMeetingDate(Date vcMeetingDate) {
        this.vcMeetingDate = vcMeetingDate;
    }

    public String getVillageChairmanSignaturePath() {
        return villageChairmanSignaturePath;
    }

    public void setVillageChairmanSignaturePath(String villageChairmanSignaturePath) {
        this.villageChairmanSignaturePath = villageChairmanSignaturePath;
    }

    public String getVillageExecutiveSignaturePath() {
        return villageExecutiveSignaturePath;
    }

    public void setVillageExecutiveSignaturePath(String villageExecutiveSignaturePath) {
        this.villageExecutiveSignaturePath = villageExecutiveSignaturePath;
    }

    public String getDistrictOfficerSignaturePath() {
        return districtOfficerSignaturePath;
    }

    public void setDistrictOfficerSignaturePath(String districtOfficerSignaturePath) {
        this.districtOfficerSignaturePath = districtOfficerSignaturePath;
    }
}
