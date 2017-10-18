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
 * Returns list of rights and owners for filling registry book
 */
@Entity
@Table(name = "view_registry_book")
public class RegistryBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "gid")
    private long id;
    
    @Column
    private long usin;

    @Column
    private String uka;

    @Column
    private Double acres;

    @Column(name = "hamlet_name")
    private String hamletName;

    @Column(name = "neighbor_north")
    private String neighborNorth;

    @Column(name = "neighbor_south")
    private String neighborSouth;

    @Column(name = "neighbor_east")
    private String neighborEast;

    @Column(name = "neighbor_west")
    private String neighborWest;

    @Column(name = "project_name")
    private String projectName;
    
    @Column(name = "cert_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date certDate;

    @Column(name = "cert_number")
    private String certNumber;

    @Column(name = "file_number")
    private String fileNumber;
    
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
    private String mobile;
    
    @Column(name="id_number")
    private String idNumber;
    
    @Column
    private String share;
    
    @Column
    private String gender;
    
    @Column
    private Integer age;
    
    @Column(name="id_type")
    private String idType;

    @Column(name="ownership_type")
    private String ownershipType;
    
    @Column
    private boolean resident;
    
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

    public String getUka() {
        return uka;
    }

    public void setUka(String uka) {
        this.uka = uka;
    }

    public Double getAcres() {
        return acres;
    }

    public void setAcres(Double acres) {
        this.acres = acres;
    }

    public String getHamletName() {
        return hamletName;
    }

    public void setHamletName(String hamletName) {
        this.hamletName = hamletName;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getCertDate() {
        return certDate;
    }

    public void setCertDate(Date certDate) {
        this.certDate = certDate;
    }

    public String getCertNumber() {
        return certNumber;
    }

    public void setCertNumber(String certNumber) {
        this.certNumber = certNumber;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
    }

    public boolean isResident() {
        return resident;
    }

    public void setResident(boolean resident) {
        this.resident = resident;
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
