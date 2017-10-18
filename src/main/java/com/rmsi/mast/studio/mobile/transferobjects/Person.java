package com.rmsi.mast.studio.mobile.transferobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Person implements Serializable {
    private Long id;
    private int resident;
    private int isNatural;
    private int subTypeId;
    private String share;
    private int acquisitionTypeId;
    private List<Attribute> attributes = new ArrayList<>();
    private List<Media> media = new ArrayList<>();

    public Person(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getResident() {
        return resident;
    }

    public void setResident(int resident) {
        this.resident = resident;
    }

    public int getIsNatural() {
        return isNatural;
    }

    public void setIsNatural(int isNatural) {
        this.isNatural = isNatural;
    }

    public int getSubTypeId() {
        return subTypeId;
    }

    public void setSubTypeId(int subTypeId) {
        this.subTypeId = subTypeId;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public int getAcquisitionTypeId() {
        return acquisitionTypeId;
    }

    public void setAcquisitionTypeId(int acquisitionTypeId) {
        this.acquisitionTypeId = acquisitionTypeId;
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
}
