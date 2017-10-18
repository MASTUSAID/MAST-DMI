package com.rmsi.mast.studio.mobile.transferobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Property implements Serializable {
    private Long id;
    private String coordinates;
    private String geomType;
    private String status;
    private Long serverId;
    private String polygonNumber;
    private String surveyDate;
    private String creationDate;
    private String completionDate;
    private String imei;
    private Long hamletId;
    private String adjudicator1;
    private String adjudicator2;
    private String claimTypeCode;
    private int userId;
    private String ukaNumber;
    
    private Right right;
    private DeceasedPerson deceasedPerson;
    private List<PersonOfInterest> personOfInterests = new ArrayList<>();
    private List<Attribute> attributes = new ArrayList<>();
    private List<Media> media = new ArrayList<>();
    private Dispute dispute;

    public Property() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUkaNumber() {
        return ukaNumber;
    }

    public void setUkaNumber(String ukaNumber) {
        this.ukaNumber = ukaNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getGeomType() {
        return geomType;
    }

    public void setGeomType(String geomType) {
        this.geomType = geomType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getPolygonNumber() {
        return polygonNumber;
    }

    public void setPolygonNumber(String polygonNumber) {
        this.polygonNumber = polygonNumber;
    }

    public String getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(String surveyDate) {
        this.surveyDate = surveyDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Long getHamletId() {
        return hamletId;
    }

    public void setHamletId(Long hamletId) {
        this.hamletId = hamletId;
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

    public String getClaimTypeCode() {
        return claimTypeCode;
    }

    public void setClaimTypeCode(String claimTypeCode) {
        this.claimTypeCode = claimTypeCode;
    }

    public Right getRight() {
        return right;
    }

    public void setRight(Right right) {
        this.right = right;
    }

    public DeceasedPerson getDeceasedPerson() {
        return deceasedPerson;
    }

    public void setDeceasedPerson(DeceasedPerson deceasedPerson) {
        this.deceasedPerson = deceasedPerson;
    }

    public List<PersonOfInterest> getPersonOfInterests() {
        return personOfInterests;
    }

    public void setPersonOfInterests(List<PersonOfInterest> personOfInterests) {
        this.personOfInterests = personOfInterests;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

    public Dispute getDispute() {
        return dispute;
    }

    public void setDispute(Dispute dispute) {
        this.dispute = dispute;
    }
}
