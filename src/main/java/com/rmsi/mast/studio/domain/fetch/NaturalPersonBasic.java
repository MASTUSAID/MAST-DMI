package com.rmsi.mast.studio.domain.fetch;

import com.rmsi.mast.studio.domain.Person;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "natural_person")
@PrimaryKeyJoinColumn(name = "GID", referencedColumnName = "PERSON_GID")
public class NaturalPersonBasic extends PersonBasic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "middle_name")
    private String middleName;

    @Column
    private Integer gender;

    @Column
    private String mobile;
    
    @Column
    private String identity;
    
    @Column
    private Integer age;
    
    @Column
    private String occupation;
    
    @Column(name = "tenure_relation")
    private String tenureRelation;
    
    @Column(name = "marital_status")
    private Integer maritalStatus;
    
    @Column
    private Boolean active;
    
    @Column
    private Integer education;

    @Column(name = "resident_of_village")
    private Boolean residentOfVillage;

    @Column(name = "personsub_type")
    private Integer personType;
    
    @Column(name = "citizenship_id")
    private Long citizenshipId;
    
    @Column(name = "id_number")
    private String idNumber;
    
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dob;

    @Column(name = "id_type")
    private Integer idType;

    @Column
    private String share;

    @Column(name = "acquisition_type")
    private Integer acquisitionType;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private List<NaturalPersonAttributeValue> attributes;
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getTenureRelation() {
        return tenureRelation;
    }

    public void setTenureRelation(String tenureRelation) {
        this.tenureRelation = tenureRelation;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Boolean getResidentOfVillage() {
        return residentOfVillage;
    }

    public void setResidentOfVillage(Boolean residentOfVillage) {
        this.residentOfVillage = residentOfVillage;
    }

    public Integer getPersonType() {
        return personType;
    }

    public void setPersonType(Integer personType) {
        this.personType = personType;
    }

    public Long getCitizenshipId() {
        return citizenshipId;
    }

    public void setCitizenshipId(Long citizenshipId) {
        this.citizenshipId = citizenshipId;
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

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public Integer getAcquisitionType() {
        return acquisitionType;
    }

    public void setAcquisitionType(Integer acquisitionType) {
        this.acquisitionType = acquisitionType;
    }

    public List<NaturalPersonAttributeValue> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<NaturalPersonAttributeValue> attributes) {
        this.attributes = attributes;
    }
}
