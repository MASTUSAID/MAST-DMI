package com.rmsi.mast.studio.mobile.transferobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Right implements Serializable {
    private Long id;
    private int rightTypeId;
    private int shareTypeId;
    private String certNumber;
    private String certDate;
    private Double juridicalArea;
    private List<Person> naturalPersons = new ArrayList<>();
    private Person nonNaturalPerson;
    private List<Attribute> attributes = new ArrayList<>();
    private int relationshipId;

    public Right(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRightTypeId() {
        return rightTypeId;
    }

    public void setRightTypeId(int rightTypeId) {
        this.rightTypeId = rightTypeId;
    }

    public int getShareTypeId() {
        return shareTypeId;
    }

    public void setShareTypeId(int shareTypeId) {
        this.shareTypeId = shareTypeId;
    }

    public String getCertNumber() {
        return certNumber;
    }

    public void setCertNumber(String certNumber) {
        this.certNumber = certNumber;
    }

    public String getCertDate() {
        return certDate;
    }

    public void setCertDate(String certDate) {
        this.certDate = certDate;
    }

    public Double getJuridicalArea() {
        return juridicalArea;
    }

    public void setJuridicalArea(Double juridicalArea) {
        this.juridicalArea = juridicalArea;
    }

    public List<Person> getNaturalPersons() {
        return naturalPersons;
    }

    public void setNaturalPersons(List<Person> naturalPersons) {
        this.naturalPersons = naturalPersons;
    }

    public Person getNonNaturalPerson() {
        return nonNaturalPerson;
    }

    public void setNonNaturalPerson(Person nonNaturalPerson) {
        this.nonNaturalPerson = nonNaturalPerson;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public int getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(int relationshipId) {
        this.relationshipId = relationshipId;
    }
}
