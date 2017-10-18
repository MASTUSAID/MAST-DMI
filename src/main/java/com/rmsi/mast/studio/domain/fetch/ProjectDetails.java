package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * Returns project details 
 */
@Entity
@Table(name = "project_area")
public class ProjectDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String name;

    @Column
    private String region;

    @Column(name = "region_code")
    private String regionCode;

    @Column(name = "district_name")
    private String districtName;

    @Column
    private String village;

    @Column(name = "village_code")
    private String villageCode;

    @Column
    private String address;

    @Column(name = "village_chairman")
    private String villageChairman;

    @Column(name = "village_chairman_signature")
    private String villageChairmanSignature;

    @Column(name = "approving_executive")
    private String villageExecutive;

    @Column(name = "village_executive_signature")
    private String villageExecutiveSignature;

    @Column(name = "district_officer")
    private String districtOfficer;

    @Column(name = "district_officer_signature")
    private String districtOfficerSignature;

    @Column(name = "vc_meeting_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date vcMeetingDate;
    
    public ProjectDetails(){
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
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

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getVillageCode() {
        return villageCode;
    }

    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVillageChairman() {
        return villageChairman;
    }

    public void setVillageChairman(String villageChairman) {
        this.villageChairman = villageChairman;
    }

    public String getVillageChairmanSignature() {
        return villageChairmanSignature;
    }

    public void setVillageChairmanSignature(String villageChairmanSignature) {
        this.villageChairmanSignature = villageChairmanSignature;
    }

    public String getVillageExecutive() {
        return villageExecutive;
    }

    public void setVillageExecutive(String villageExecutive) {
        this.villageExecutive = villageExecutive;
    }

    public String getVillageExecutiveSignature() {
        return villageExecutiveSignature;
    }

    public void setVillageExecutiveSignature(String villageExecutiveSignature) {
        this.villageExecutiveSignature = villageExecutiveSignature;
    }

    public String getDistrictOfficer() {
        return districtOfficer;
    }

    public void setDistrictOfficer(String districtOfficer) {
        this.districtOfficer = districtOfficer;
    }

    public String getDistrictOfficerSignature() {
        return districtOfficerSignature;
    }

    public void setDistrictOfficerSignature(String districtOfficerSignature) {
        this.districtOfficerSignature = districtOfficerSignature;
    }

    public Date getVcMeetingDate() {
        return vcMeetingDate;
    }

    public void setVcMeetingDate(Date vcMeetingDate) {
        this.vcMeetingDate = vcMeetingDate;
    }
}
