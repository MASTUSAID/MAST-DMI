package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rmsi.mast.studio.util.JsonDateSerializer;
import javax.persistence.Column;

/**
 * Entity implementation class for Entity: SocialTenureRelationship
 */
@Entity
@Table(name = "social_tenure_relationship")
public class SocialTenureRelationship implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "social_tenure_relationship_id", sequenceName = "social_tenure_relationship_gid_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "social_tenure_relationship_id")
    private int gid;

    @ManyToOne
    @JoinColumn(name = "share", nullable = false)
    private ShareType share_type;

    private Long usin;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_gid")
    private Person person_gid;

    @ManyToOne
    @JoinColumn(name = "occupancy_type_id")
    private OccupancyType occupancyTypeId;

    @ManyToOne
    @JoinColumn(name = "tenureclass_id")
    private TenureClass tenureclassId;

    @ManyToOne
    @JoinColumn(name = "acquisition_type")
    private AcquisitionType acquisitionType;
    
    @ManyToOne
    @JoinColumn(name = "relationship_type")
    private RelationshipType relationshipType;
    
    @Column(name = "cert_number")
    private String certNumber;
    
    @Column(name = "file_number")
    private String fileNumber;
    
    @Column(name = "ccro_issue_date")
    private Date certIssueDate;
    
    @Column(name = "juridical_area")
    private Double juridicalArea;
    
    private Date social_tenure_startdate;
    private Date social_tenure_enddate;
    
    @Column(name = "tenure_duration")
    private float tenureDuration;
    private boolean isActive;

    private String sharePercentage;
    private boolean resident;

    public SocialTenureRelationship() {
        super();
    }

    public AcquisitionType getAcquisitionType() {
        return acquisitionType;
    }

    public void setAcquisitionType(AcquisitionType acquisitionType) {
        this.acquisitionType = acquisitionType;
    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }

    public String getCertNumber() {
        return certNumber;
    }

    public void setCertNumber(String certNumber) {
        this.certNumber = certNumber;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public Date getCertIssueDate() {
        return certIssueDate;
    }

    public void setCertIssueDate(Date certIssueDate) {
        this.certIssueDate = certIssueDate;
    }

    public Double getJuridicalArea() {
        return juridicalArea;
    }

    public void setJuridicalArea(Double juridicalArea) {
        this.juridicalArea = juridicalArea;
    }

    public ShareType getShare_type() {
        return this.share_type;
    }

    public void setShare_type(ShareType share_type) {
        this.share_type = share_type;
    }

    public int getGid() {
        return this.gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public Long getUsin() {
        return this.usin;
    }

    public void setUsin(Long usin) {
        this.usin = usin;
    }

    public Person getPerson_gid() {
        return this.person_gid;
    }

    public void setPerson_gid(Person person_gid) {
        this.person_gid = person_gid;
    }

    public OccupancyType getOccupancyTypeId() {
        return this.occupancyTypeId;
    }

    public void setOccupancyTypeId(OccupancyType occupancyTypeId) {
        this.occupancyTypeId = occupancyTypeId;
    }

    public TenureClass getTenureclassId() {
        return this.tenureclassId;
    }

    public void setTenureclassId(TenureClass tenureclassId) {
        this.tenureclassId = tenureclassId;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getSocial_tenure_startdate() {
        return this.social_tenure_startdate;
    }

    public void setSocial_tenure_startdate(Date social_tenure_startdate) {
        this.social_tenure_startdate = social_tenure_startdate;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getSocial_tenure_enddate() {
        return this.social_tenure_enddate;
    }

    public void setSocial_tenure_enddate(Date social_tenure_enddate) {
        this.social_tenure_enddate = social_tenure_enddate;
    }

    public float getTenureDuration() {
        return this.tenureDuration;
    }

    public void setTenureDuration(float tenureDuration) {
        this.tenureDuration = tenureDuration;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getSharePercentage() {
        return sharePercentage;
    }

    public void setSharePercentage(String sharePercentage) {
        this.sharePercentage = sharePercentage;
    }

    public boolean isResident() {
        return resident;
    }

    public void setResident(boolean resident) {
        this.resident = resident;
    }

}
