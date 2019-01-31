package com.rmsi.mast.studio.domain;

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
@Table(name = "la_ext_boundary_point")
public class BoundaryPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "seq_ext_boundary_point", sequenceName = "la_ext_boundary_point_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ext_boundary_point")
    private Integer id;

    @Column(name = "project_id")
    private Integer projectId;
    
    @Column(name = "neighbor_village_id")
    private Integer neighborVillageId;
    
    @Column(name = "feature_type")
    private String featureType;

    @Column(name = "feature_description")
    private String featureDescription;
    
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
    
    public BoundaryPoint() {

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

    public Integer getNeighborVillageId() {
        return neighborVillageId;
    }

    public void setNeighborVillageId(Integer neighborVillageId) {
        this.neighborVillageId = neighborVillageId;
    }

    public String getFeatureType() {
        return featureType;
    }

    public void setFeatureType(String featureType) {
        this.featureType = featureType;
    }

    public String getFeatureDescription() {
        return featureDescription;
    }

    public void setFeatureDescription(String featureDescription) {
        this.featureDescription = featureDescription;
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
