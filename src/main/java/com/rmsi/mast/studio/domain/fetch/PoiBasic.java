package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * Returns person of interest from the view
 */
@Entity
@Table(name = "spatialunit_personwithinterest")
public class PoiBasic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column
    private Long usin;

    @Column(name = "person_name")
    private String personName;

    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dob;
    
    @Column(name = "gender_id")
    private Integer genderId;

    @Column(name="relationship_type")
    private Integer relationshipTypeId;

    public PoiBasic(){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsin() {
        return usin;
    }

    public void setUsin(Long usin) {
        this.usin = usin;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public Integer getRelationshipTypeId() {
        return relationshipTypeId;
    }

    public void setRelationshipTypeId(Integer relationshipTypeId) {
        this.relationshipTypeId = relationshipTypeId;
    }
}
