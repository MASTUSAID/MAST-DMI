package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: SpatialUnitPersonWithInterest
 *
 */
@Entity
@Table(name = "spatialunit_personwithinterest")
public class SpatialUnitPersonWithInterest implements Serializable {

    @Id
    @SequenceGenerator(name = "SPATIAL_UNIT_PERSON_WITH_INTEREST_GENERATOR", sequenceName = "SPATIAL_UNIT_PERSON_WITH_INTEREST_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPATIAL_UNIT_PERSON_WITH_INTEREST_GENERATOR")
    private long id;
    private long usin;
    private String person_name;
    private static final long serialVersionUID = 1L;

    @Column(name = "dob")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dob;
    
    @ManyToOne
    @JoinColumn(name = "relationship_type")
    private RelationshipType relationshipType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gender_id", nullable = false)
    private Gender gender;

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
    
    public SpatialUnitPersonWithInterest() {
        super();
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUsin() {
        return this.usin;
    }

    public void setUsin(long usin) {
        this.usin = usin;
    }

    public String getPerson_name() {
        return this.person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

}
