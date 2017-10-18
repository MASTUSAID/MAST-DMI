package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "natural_person")
@PrimaryKeyJoinColumn(name = "GID", referencedColumnName = "PERSON_GID")
public class NaturalPerson extends Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(nullable = false)
    private String alias;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gender", nullable = false)
    private Gender gender;

    private String mobile;

    private String identity;

    private int age;

    private String occupation;

    private boolean active;

    @Column(name = "occ_age_below")
    private int occAgeBelow;

    @Column(name = "tenure_relation")
    private String tenure_Relation;

    @Column(name = "household_relation")
    private String householdRelation;

    private String witness;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "marital_status")
    private MaritalStatus marital_status;

    @Column(name = "household_gid")
    private int householdGid;

    @ManyToOne
    @JoinColumn(name = "education")
    private EducationLevel education;

    private String administator;
    private String citizenship;
    private Boolean owner;
    private Boolean resident_of_village;

    @ManyToOne
    @JoinColumn(name = "personsub_type")
    private PersonType personSubType;

    @ManyToOne
    @JoinColumn(name = "citizenship_id")
    private Citizenship citizenship_id;

    @ManyToOne
    @JoinColumn(name = "id_type")
    private IdType idType;

    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "dob")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dob;

    @Column(name = "share")
    private String share;
    
    @ManyToOne
    @JoinColumn(name = "acquisition_type")
    private AcquisitionType acquisitionType;
        
    public IdType getIdType() {
        return idType;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public AcquisitionType getAcquisitionType() {
        return acquisitionType;
    }

    public void setAcquisitionType(AcquisitionType acquisitionType) {
        this.acquisitionType = acquisitionType;
    }

    public Citizenship getCitizenship_id() {
        return citizenship_id;
    }

    public void setCitizenship_id(Citizenship citizenship_id) {
        this.citizenship_id = citizenship_id;
    }

    public NaturalPerson() {
        super();
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public EducationLevel getEducation() {
        return education;
    }

    public void setEducation(EducationLevel education) {
        this.education = education;
    }

    public int getOccAgeBelow() {
        return occAgeBelow;
    }

    public void setOccAgeBelow(int occAgeBelow) {
        this.occAgeBelow = occAgeBelow;
    }

    public String getTenure_Relation() {
        return tenure_Relation;
    }

    public void setTenure_Relation(String tenure_Relation) {
        this.tenure_Relation = tenure_Relation;
    }

    public String getHouseholdRelation() {
        return householdRelation;
    }

    public void setHouseholdRelation(String householdRelation) {
        this.householdRelation = householdRelation;
    }

    public String getWitness() {
        return witness;
    }

    public void setWitness(String witness) {
        this.witness = witness;
    }

    public MaritalStatus getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(MaritalStatus marital_status) {
        this.marital_status = marital_status;
    }

    public int getHouseholdGid() {
        return householdGid;
    }

    public void setHouseholdGid(int householdGid) {
        this.householdGid = householdGid;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAdministator() {
        return administator;
    }

    public void setAdministator(String administator) {
        this.administator = administator;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public Boolean getOwner() {
        return owner;
    }

    public void setOwner(Boolean owner) {
        this.owner = owner;
    }

    public Boolean getResident_of_village() {
        return resident_of_village;
    }

    public void setResident_of_village(Boolean resident_of_village) {
        this.resident_of_village = resident_of_village;
    }

    public PersonType getPersonSubType() {
        return personSubType;
    }

    public void setPersonSubType(PersonType personSubType) {
        this.personSubType = personSubType;
    }
}
