package com.rmsi.mast.studio.mobile.transferobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dispute implements Serializable {

    private Long id;
    private int disputeTypeId;
    private String description;
    private String regDate;
    private List<Person> disputingPersons = new ArrayList<>();
    private List<Media> media = new ArrayList<>();

    public Dispute() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDisputeTypeId() {
        return disputeTypeId;
    }

    public void setDisputeTypeId(int disputeTypeId) {
        this.disputeTypeId = disputeTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public List<Person> getDisputingPersons() {
        return disputingPersons;
    }

    public void setDisputingPersons(List<Person> disputingPersons) {
        this.disputingPersons = disputingPersons;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }
}
