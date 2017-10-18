package com.rmsi.mast.studio.domain.fetch;

import com.rmsi.mast.studio.domain.Gender;
import com.rmsi.mast.studio.domain.RelationshipType;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The persistent class for the spatialunit_pesonwithinterest database table.
 *
 */
@Entity
@Table(name = "spatialunit_personwithinterest")
public class SpatialunitPersonwithinterest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PERSON_WITH_INTEREST_ID_GENERATOR", sequenceName = "spatial_unit_person_with_interest_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSON_WITH_INTEREST_ID_GENERATOR")
    private Long id;

    @Column(name = "person_name")
    private String personName;

    @Column(name = "dob")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dob;

    @ManyToOne
    @JoinColumn(name = "relationship_type")
    private RelationshipType relationshipType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gender_id")
    private Gender gender;

    private Long usin;

    public SpatialunitPersonwithinterest() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonName() {
        return this.personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Long getUsin() {
        return this.usin;
    }

    public void setUsin(Long usin) {
        this.usin = usin;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

}
