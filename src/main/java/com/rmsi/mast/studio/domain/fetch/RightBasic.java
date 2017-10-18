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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "social_tenure_relationship")
public class RightBasic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer gid;
    
    @Column
    private Long usin;

    @Column(name="tenureclass_id")
    private Integer rightTypeId;

    @Column(name = "share")
    private Integer shareTypeId;

    @Column(name="cert_number")
    private String certNumber;
    
    @Column(name = "ccro_issue_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date certDate;
    
    @Column(name = "juridical_area")
    private Double juridicalArea;

    @Column(name = "relationship_type")
    private Integer relationshipTypeId;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="person_gid")
    PersonBasic person;
        
    @Column(name = "isactive")
    private Boolean active;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private List<RightAttributeValue> attributes;
    
    public RightBasic() {
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getRightTypeId() {
        return rightTypeId;
    }

    public void setRightTypeId(Integer rightTypeId) {
        this.rightTypeId = rightTypeId;
    }

    public Integer getShareTypeId() {
        return shareTypeId;
    }

    public void setShareTypeId(Integer shareTypeId) {
        this.shareTypeId = shareTypeId;
    }

    public String getCertNumber() {
        return certNumber;
    }

    public void setCertNumber(String certNumber) {
        this.certNumber = certNumber;
    }

    public Date getCertDate() {
        return certDate;
    }

    public void setCertDate(Date certDate) {
        this.certDate = certDate;
    }

    public Double getJuridicalArea() {
        return juridicalArea;
    }

    public void setJuridicalArea(Double juridicalArea) {
        this.juridicalArea = juridicalArea;
    }

    public Integer getRelationshipTypeId() {
        return relationshipTypeId;
    }

    public void setRelationshipTypeId(Integer relationshipTypeId) {
        this.relationshipTypeId = relationshipTypeId;
    }

    public Long getUsin() {
        return usin;
    }

    public void setUsin(Long usin) {
        this.usin = usin;
    }

    public PersonBasic getPerson() {
        return person;
    }

    public void setPerson(PersonBasic person) {
        this.person = person;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<RightAttributeValue> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<RightAttributeValue> attributes) {
        this.attributes = attributes;
    }
}
