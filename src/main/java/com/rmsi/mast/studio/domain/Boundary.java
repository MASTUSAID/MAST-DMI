package com.rmsi.mast.studio.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rmsi.mast.studio.util.JsonDateSerializer;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = "la_ext_boundary")
public class Boundary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "seq_la_ext_boundary_seq", sequenceName = "la_ext_boundary_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_la_ext_boundary_seq")
    private Integer id;

    @Column(name = "project_id")
    private Integer projectId;
    
    @Column(name = "quarters_num")
    private Integer quartersNum;
    
    @Column(name = "population")
    private Integer population;
    
    @Column(name = "survey_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date surveyDate;
    
    @Column(name = "village_leader")
    private String villageLeader;

    @ColumnTransformer(read = "st_astext(geometry)", write = "st_geomfromtext(?, 4326)")
    private String geometry;
    
    @Column(name = "created_by")
    private Integer createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "modified_by")
    private Integer modifiedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "modify_date")
    private Date modifyDate;

    @Column
    private boolean isactive;
    
    @Column
    private boolean approved;
    
    public Boundary() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getQuartersNum() {
        return quartersNum;
    }

    public void setQuartersNum(Integer quartersNum) {
        this.quartersNum = quartersNum;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(Date surveyDate) {
        this.surveyDate = surveyDate;
    }

    public String getVillageLeader() {
        return villageLeader;
    }

    public void setVillageLeader(String villageLeader) {
        this.villageLeader = villageLeader;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public boolean isIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }    
}
