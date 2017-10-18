package com.rmsi.mast.studio.domain.fetch;

import com.rmsi.mast.studio.util.StringUtils;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * Returns natural person with right from the view
 */
@Entity
@Table(name = "view_claimants_for_editing")
public class PersonForEditing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private long id;

    @Column(name = "person_id")
    private long personId;

    @Column(name = "right_id")
    private long rightId;

    @Column
    private long usin;

    @Column
    private String uka;
    
    @Column(name = "hamlet_id")
    private Long hamletId;
    
    @Column(name = "claim_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date claimDate;

    @Column(name = "claim_number")
    private String claimNumber;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column
    private Integer gender;

    @Column(name = "marital_status")
    private Integer maritalStatus;

    @Column(name = "share_type")
    private String shareType;
    
    @Column(name = "id_number")
    private String idNumber;
    
    @Column(name = "id_type")
    private Integer idType;
    
    @Column
    private String mobile;
    
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dob;
    
    @Column
    private Integer age;

    @Column(name = "neighbor_north")
    private String neighborNorth;
    
    @Column(name = "neighbor_south")
    private String neighborSouth;
    
    @Column(name = "neighbor_east")
    private String neighborEast;
    
    @Column(name = "neighbor_west")
    private String neighborWest;
    
    @Column(name ="claim_status")
    private int claimStatus;
    
    public PersonForEditing() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public long getRightId() {
        return rightId;
    }

    public void setRightId(long rightId) {
        this.rightId = rightId;
    }

    public long getUsin() {
        return usin;
    }

    public void setUsin(long usin) {
        this.usin = usin;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public String getClaimNumber() {
        return claimNumber;
    }

    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getNeighborNorth() {
        return neighborNorth;
    }

    public void setNeighborNorth(String neighborNorth) {
        this.neighborNorth = neighborNorth;
    }

    public String getNeighborSouth() {
        return neighborSouth;
    }

    public void setNeighborSouth(String neighborSouth) {
        this.neighborSouth = neighborSouth;
    }

    public String getNeighborEast() {
        return neighborEast;
    }

    public void setNeighborEast(String neighborEast) {
        this.neighborEast = neighborEast;
    }

    public String getNeighborWest() {
        return neighborWest;
    }

    public void setNeighborWest(String neighborWest) {
        this.neighborWest = neighborWest;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUka() {
        return uka;
    }

    public void setUka(String uka) {
        this.uka = uka;
    }

    public Long getHamletId() {
        return hamletId;
    }

    public void setHamletId(Long hamletId) {
        this.hamletId = hamletId;
    }


    public int getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(int claimStatus) {
        this.claimStatus = claimStatus;
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
