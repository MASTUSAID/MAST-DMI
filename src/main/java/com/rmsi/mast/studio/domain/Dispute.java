package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "dispute")
public class Dispute implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "dispute_id", sequenceName = "dispute_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dispute_id")
    private long id;
    
    @Column
    private long usin;
    
    @ManyToOne
    @JoinColumn(name = "dispute_type")
    private DisputeType disputeType;
    
    @Column
    private String description;
    
    @Column(name="reg_date")
    Date regDate;
    
    @Column(name="resolution_text")
    private String resolutionText;
    
    @Column(name="resolution_date")
    private Date resolutionDate;
    
    @ManyToOne
    @JoinColumn(name = "status")
    private DisputeStatus status;
    
    @Column
    boolean deleted;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
            @JoinTable(name="dispute_person", catalog = "public", joinColumns = {
                @JoinColumn(name="dispute_id", nullable = false, updatable = false)
            }, inverseJoinColumns = {
                @JoinColumn(name="person_id", nullable = false, updatable = false)
            })
    List<NaturalPerson> disputingPersons;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="dispute_id")
    List<SourceDocument> documents;
    
    public Dispute(){
        
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUsin() {
        return usin;
    }

    public void setUsin(long usin) {
        this.usin = usin;
    }

    public DisputeType getDisputeType() {
        return disputeType;
    }

    public void setDisputeType(DisputeType disputeType) {
        this.disputeType = disputeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getResolutionText() {
        return resolutionText;
    }

    public void setResolutionText(String resolutionText) {
        this.resolutionText = resolutionText;
    }

    public Date getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(Date resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public DisputeStatus getStatus() {
        return status;
    }

    public void setStatus(DisputeStatus status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<NaturalPerson> getDisputingPersons() {
        return disputingPersons;
    }

    public void setDisputingPersons(List<NaturalPerson> disputingPersons) {
        this.disputingPersons = disputingPersons;
    }

    public List<SourceDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<SourceDocument> documents) {
        this.documents = documents;
    }
}
