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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "dispute")
public class DisputeBasic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "dispute_type")
    private Integer disputeTypeId;

    @Column
    private Long usin;

    @Column
    private String description;

    @Column
    private Integer status;
    
    @Column
    private Boolean deleted;
    
    @Column(name = "reg_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date regDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "dispute_person", catalog = "public", joinColumns = {
        @JoinColumn(name = "dispute_id", nullable = false, updatable = false)
    }, inverseJoinColumns = {
        @JoinColumn(name = "person_id", nullable = false, updatable = false)
    })
    List<NaturalPersonBasic> disputingPersons;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "dispute_id")
    List<MediaBasic> media;
    
    public DisputeBasic() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDisputeTypeId() {
        return disputeTypeId;
    }

    public void setDisputeTypeId(Integer disputeTypeId) {
        this.disputeTypeId = disputeTypeId;
    }

    public Long getUsin() {
        return usin;
    }

    public void setUsin(Long usin) {
        this.usin = usin;
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

    public List<NaturalPersonBasic> getDisputingPersons() {
        return disputingPersons;
    }

    public void setDisputingPersons(List<NaturalPersonBasic> disputingPersons) {
        this.disputingPersons = disputingPersons;
    }

    public List<MediaBasic> getMedia() {
        return media;
    }

    public void setMedia(List<MediaBasic> media) {
        this.media = media;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
