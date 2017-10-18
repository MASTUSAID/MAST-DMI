package com.rmsi.mast.studio.domain.fetch;

import com.rmsi.mast.studio.util.StringUtils;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Returns natural person with right from the view
 */
@Entity
@Table(name = "view_natural_persons_with_right")
public class PersonWithRightSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "gid")
    private Long id;

    @Column(name="is_active")
    private Boolean active;
    
    @Column
    private Long usin;
    
    @Column(name="first_name")
    private String firstName;
    
    @Column(name="last_name")
    private String lastName;
    
    @Column(name="middle_name")
    private String middleName;
    
    @Column(name="person_type")
    private String personType;
    
    @Column(name = "person_type_id")
    private int personTypeId;
    
    @Column
    private String gender;
    
    @Column(name="marital_status")
    private String maritalStatus;
    
    @Column
    private String citizenship;
    
    @Column
    private String mobile;
    
    @Column(name="id_number")
    private String idNumber;
    
    @Column
    private String share;
    
    @Column(name="id_type")
    private String idType;
    
    @Column
    private Integer age;
    
    @Column
    private String resident;
    
    @Column(name = "village_resident")
    private Boolean villageResident;
    
    public PersonWithRightSummary(){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getUsin() {
        return usin;
    }

    public void setUsin(Long usin) {
        this.usin = usin;
    }

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

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public int getPersonTypeId() {
        return personTypeId;
    }

    public void setPersonTypeId(int personTypeId) {
        this.personTypeId = personTypeId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getResident() {
        return resident;
    }

    public void setResident(String resident) {
        this.resident = resident;
    }

    public Boolean getVillageResident() {
        return villageResident;
    }

    public void setVillageResident(Boolean villageResident) {
        this.villageResident = villageResident;
    }
    
    public String getFullName() {
        String name = "";
        if (!StringUtils.isEmpty(getFirstName())) {
            name = getFirstName().trim();
        }
        if (!StringUtils.isEmpty(getMiddleName())) {
            if (name.length() > 0) {
                name = name + " " + getMiddleName().trim();
            } else {
                name = getMiddleName().trim();
            }
        }
        if (!StringUtils.isEmpty(getLastName())) {
            if (name.length() > 0) {
                name = name + " " + getLastName().trim();
            } else {
                name = getLastName().trim();
            }
        }
        return name;
    }
}
