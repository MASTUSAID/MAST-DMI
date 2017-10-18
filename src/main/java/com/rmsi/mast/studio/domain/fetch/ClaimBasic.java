package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.Formula;

@Entity
@Table(name = "spatial_unit")
public class ClaimBasic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private long usin;

    @Column(name="uka_propertyno")
    private String uka;

    @Column(name = "hamlet_id")
    private Long hamletId;

    @Column(name="current_workflow_status_id")
    private Long statusId;
    
    @Column(name = "workflow_status_update_time")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date statusUpdateTime;
    
    @Column(name = "polygon_number")
    private String claimNumber;
    
    @Column(name = "survey_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date surveyDate;
    
    @Column(name = "imei_number")
    private String imei;

    @Column(name = "witness_1")
    private String adjudicator1;

    @Column(name = "witness_2")
    private String adjudicator2;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "claim_type")
    private String claimType;
    
    @Column(name = "gtype")
    private String geomType;
    
    @Formula("get_coordinates(the_geom)")
    private String coordinates;
    
    @Column(name="userid")
    private Integer userId;
    
    @Column
    private Boolean active;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private List<ClaimAttributeValue> attributes;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "usin")
    List<DisputeBasic> disputes;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "usin")
    List<RightBasic> rights;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "usin")
    List<MediaBasic> media;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "usin")
    List<PoiBasic> pois;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "usin")
    List<SpatialunitDeceasedPerson> deceased;
    
    public ClaimBasic() {
    }

    public long getUsin() {
        return usin;
    }

    public void setUsin(long usin) {
        this.usin = usin;
    }

    public String getUka() {
        return uka;
    }

    public void setUka(String uka) {
        this.uka = uka;
    }

    public Long getHamletId() {
        return hamletId;
    }

    public void setHamletId(Long hamletId) {
        this.hamletId = hamletId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getClaimNumber() {
        return claimNumber;
    }

    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
    }

    public Date getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(Date surveyDate) {
        this.surveyDate = surveyDate;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getAdjudicator1() {
        return adjudicator1;
    }

    public void setAdjudicator1(String adjudicator1) {
        this.adjudicator1 = adjudicator1;
    }

    public String getAdjudicator2() {
        return adjudicator2;
    }

    public void setAdjudicator2(String adjudicator2) {
        this.adjudicator2 = adjudicator2;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public String getGeomType() {
        return geomType;
    }

    public void setGeomType(String geomType) {
        this.geomType = geomType;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<DisputeBasic> getDisputes() {
        return disputes;
    }

    public void setDisputes(List<DisputeBasic> disputes) {
        this.disputes = disputes;
    }

    public List<RightBasic> getRights() {
        return rights;
    }

    public void setRights(List<RightBasic> rights) {
        this.rights = rights;
    }

    public List<MediaBasic> getMedia() {
        return media;
    }

    public void setMedia(List<MediaBasic> media) {
        this.media = media;
    }

    public List<PoiBasic> getPois() {
        return pois;
    }

    public void setPois(List<PoiBasic> pois) {
        this.pois = pois;
    }

    public List<SpatialunitDeceasedPerson> getDeceased() {
        return deceased;
    }

    public void setDeceased(List<SpatialunitDeceasedPerson> deceased) {
        this.deceased = deceased;
    }

    public Date getStatusUpdateTime() {
        return statusUpdateTime;
    }

    public void setStatusUpdateTime(Date statusUpdateTime) {
        this.statusUpdateTime = statusUpdateTime;
    }

    public List<ClaimAttributeValue> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ClaimAttributeValue> attributes) {
        this.attributes = attributes;
    }
}
