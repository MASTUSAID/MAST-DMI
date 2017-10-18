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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rmsi.mast.studio.util.JsonDateSerializer;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 * Entity implementation class for Entity: SourceDocument
 *
 * @author Shruti.Thakur
 */
@Entity
@Table(name = "source_document")
public class SourceDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SOURCE_DOCUMENT_ID_GENERATOR", sequenceName = "SOURCE_DOCUMENT_GID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOURCE_DOCUMENT_ID_GENERATOR")
    private int gid;

    private String id;

    private boolean active;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date recordation;

    @Column(name = "scanned_source_document")
    private String ScanedSourceDoc;

    @Column(name = "location_scanned_source_document")
    private String locScannedSourceDoc;

    @Column(name = "quality_type")
    private String qualityType;

    @Column(name = "social_tenure_inventory_type")
    private String socialTenureInvantoryType;

    @Column(name = "spatial_unit_inventory_type")
    private String spatilaUnitInventoryType;

    private String comments;

    private int srs_id;

    @Column(name = "source_doc_admin_unit_id")
    private int sourceDoc;

    private String mediaType;

    private Long usin;

    private String entity_name;

    private int househld_gid;

    private Long person_gid;

    @Column(name = "social_tenure_gid", nullable = false)
    private Integer social_tenure_gid;

    @Column(name = "dispute_id")
    private Long disputeId;

    @ManyToOne
    @JoinColumn(name = "document_type")
    private DocumentType documentType;

    private Long adminid;

    public SourceDocument() {
        super();
    }

    public int getGid() {
        return this.gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getRecordation() {
        return this.recordation;
    }

    public void setRecordation(Date recordation) {
        this.recordation = recordation;
    }

    public String getScanedSourceDoc() {
        return this.ScanedSourceDoc;
    }

    public void setScanedSourceDoc(String ScanedSourceDoc) {
        this.ScanedSourceDoc = ScanedSourceDoc;
    }

    public String getLocScannedSourceDoc() {
        return this.locScannedSourceDoc;
    }

    public void setLocScannedSourceDoc(String locScannedSourceDoc) {
        this.locScannedSourceDoc = locScannedSourceDoc;
    }

    public String getQualityType() {
        return this.qualityType;
    }

    public void setQualityType(String qualityType) {
        this.qualityType = qualityType;
    }

    public String getSocialTenureInvantoryType() {
        return this.socialTenureInvantoryType;
    }

    public void setSocialTenureInvantoryType(String socialTenureInvantoryType) {
        this.socialTenureInvantoryType = socialTenureInvantoryType;
    }

    public String getSpatilaUnitInventoryType() {
        return this.spatilaUnitInventoryType;
    }

    public void setSpatilaUnitInventoryType(String spatilaUnitInventoryType) {
        this.spatilaUnitInventoryType = spatilaUnitInventoryType;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getSrs_id() {
        return this.srs_id;
    }

    public void setSrs_id(int srs_id) {
        this.srs_id = srs_id;
    }

    public int getSourceDoc() {
        return this.sourceDoc;
    }

    public void setSourceDoc(int sourceDoc) {
        this.sourceDoc = sourceDoc;
    }

    public String getEntity_name() {
        return entity_name;
    }

    public void setEntity_name(String entity_name) {
        this.entity_name = entity_name;
    }

    public int getHousehld_gid() {
        return this.househld_gid;
    }

    public void setHousehld_gid(int househld_gid) {
        this.househld_gid = househld_gid;
    }

    public Long getUsin() {
        return usin;
    }

    public void setUsin(Long usin) {
        this.usin = usin;
    }

    public Long getPerson_gid() {
        return person_gid;
    }

    public void setPerson_gid(Long person_gid) {
        this.person_gid = person_gid;
    }

    public Integer getSocial_tenure_gid() {
        return social_tenure_gid;
    }

    public void setSocial_tenure_gid(Integer social_tenure_gid) {
        this.social_tenure_gid = social_tenure_gid;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Long getAdminid() {
        return adminid;
    }

    public void setAdminid(Long adminid) {
        this.adminid = adminid;
    }

    public Long getDisputeId() {
        return disputeId;
    }

    public void setDisputeId(Long disputeId) {
        this.disputeId = disputeId;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

}
