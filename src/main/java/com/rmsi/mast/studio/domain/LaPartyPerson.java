package com.rmsi.mast.studio.domain;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "la_party_person")
public class LaPartyPerson implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long personid;
	private String firstname;
	private String middlename;
	private String lastname;
	private Integer genderid;
	private Integer spatialunitgroupid1;
	private Integer hierarchyid1;
	private Integer spatialunitgroupid2;
	private Integer hierarchyid2;
	private Integer spatialunitgroupid3;
	private Integer hierarchyid3;
	private Integer spatialunitgroupid4;
	private Integer hierarchyid4;
	private Integer spatialunitgroupid5;
	private Integer hierarchyid5;
	private Integer spatialunitgroupid6;
	private Integer hierarchyid6;
	private Integer occupationid;
	private Integer relationshiptypeid;
	private Integer maritalstatusid;
	private Integer educationlevelid;
	private Integer tenureclassid;
	private Integer identitytypeid;
	private String identityno;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date dateofbirth;
	private String fathername;
	private String husbandname;
	private String mothername;
	private String contactno;
	private String address;
	private byte[] photo;
	private boolean isactive;
	private Integer createdby;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date createddate;
	private Integer modifiedby;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date modifieddate;
	
	
	private String gendername;
	
	@javax.persistence.Transient
	private String dob;
	
	public Long getPersonid() {
		return personid;
	}
	public void setPersonid(Long personid) {
		this.personid = personid;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public Integer getGenderid() {
		return genderid;
	}
	public void setGenderid(Integer genderid) {
		this.genderid = genderid;
	}
	public Integer getSpatialunitgroupid1() {
		return spatialunitgroupid1;
	}
	public void setSpatialunitgroupid1(Integer spatialunitgroupid1) {
		this.spatialunitgroupid1 = spatialunitgroupid1;
	}
	public Integer getHierarchyid1() {
		return hierarchyid1;
	}
	public void setHierarchyid1(Integer hierarchyid1) {
		this.hierarchyid1 = hierarchyid1;
	}
	public Integer getSpatialunitgroupid2() {
		return spatialunitgroupid2;
	}
	public void setSpatialunitgroupid2(Integer spatialunitgroupid2) {
		this.spatialunitgroupid2 = spatialunitgroupid2;
	}
	public Integer getHierarchyid2() {
		return hierarchyid2;
	}
	public void setHierarchyid2(Integer hierarchyid2) {
		this.hierarchyid2 = hierarchyid2;
	}
	public Integer getSpatialunitgroupid3() {
		return spatialunitgroupid3;
	}
	public void setSpatialunitgroupid3(Integer spatialunitgroupid3) {
		this.spatialunitgroupid3 = spatialunitgroupid3;
	}
	public Integer getHierarchyid3() {
		return hierarchyid3;
	}
	public void setHierarchyid3(Integer hierarchyid3) {
		this.hierarchyid3 = hierarchyid3;
	}
	public Integer getSpatialunitgroupid4() {
		return spatialunitgroupid4;
	}
	public void setSpatialunitgroupid4(Integer spatialunitgroupid4) {
		this.spatialunitgroupid4 = spatialunitgroupid4;
	}
	public Integer getHierarchyid4() {
		return hierarchyid4;
	}
	public void setHierarchyid4(Integer hierarchyid4) {
		this.hierarchyid4 = hierarchyid4;
	}
	public Integer getSpatialunitgroupid5() {
		return spatialunitgroupid5;
	}
	public void setSpatialunitgroupid5(Integer spatialunitgroupid5) {
		this.spatialunitgroupid5 = spatialunitgroupid5;
	}
	public Integer getHierarchyid5() {
		return hierarchyid5;
	}
	public void setHierarchyid5(Integer hierarchyid5) {
		this.hierarchyid5 = hierarchyid5;
	}
	public Integer getSpatialunitgroupid6() {
		return spatialunitgroupid6;
	}
	public void setSpatialunitgroupid6(Integer spatialunitgroupid6) {
		this.spatialunitgroupid6 = spatialunitgroupid6;
	}
	public Integer getHierarchyid6() {
		return hierarchyid6;
	}
	public void setHierarchyid6(Integer hierarchyid6) {
		this.hierarchyid6 = hierarchyid6;
	}
	public Integer getOccupationid() {
		return occupationid;
	}
	public void setOccupationid(Integer occupationid) {
		this.occupationid = occupationid;
	}
	public Integer getRelationshiptypeid() {
		return relationshiptypeid;
	}
	public void setRelationshiptypeid(Integer relationshiptypeid) {
		this.relationshiptypeid = relationshiptypeid;
	}
	public Integer getMaritalstatusid() {
		return maritalstatusid;
	}
	public void setMaritalstatusid(Integer maritalstatusid) {
		this.maritalstatusid = maritalstatusid;
	}
	public Integer getEducationlevelid() {
		return educationlevelid;
	}
	public void setEducationlevelid(Integer educationlevelid) {
		this.educationlevelid = educationlevelid;
	}
	public Integer getTenureclassid() {
		return tenureclassid;
	}
	public void setTenureclassid(Integer tenureclassid) {
		this.tenureclassid = tenureclassid;
	}
	public Integer getIdentitytypeid() {
		return identitytypeid;
	}
	public void setIdentitytypeid(Integer identitytypeid) {
		this.identitytypeid = identitytypeid;
	}
	public String getIdentityno() {
		return identityno;
	}
	public void setIdentityno(String identityno) {
		this.identityno = identityno;
	}
	public Date getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public String getFathername() {
		return fathername;
	}
	public void setFathername(String fathername) {
		this.fathername = fathername;
	}
	public String getHusbandname() {
		return husbandname;
	}
	public void setHusbandname(String husbandname) {
		this.husbandname = husbandname;
	}
	public String getMothername() {
		return mothername;
	}
	public void setMothername(String mothername) {
		this.mothername = mothername;
	}
	public String getContactno() {
		return contactno;
	}
	public void setContactno(String contactno) {
		this.contactno = contactno;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public boolean isIsactive() {
		return isactive;
	}
	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}
	public Integer getCreatedby() {
		return createdby;
	}
	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}
	public Date getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
	public Integer getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(Integer modifiedby) {
		this.modifiedby = modifiedby;
	}
	public Date getModifieddate() {
		return modifieddate;
	}
	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}
	@Transient
	public String getGendername() {
		return gendername;
	}
	
	public void setGendername(String gendername) {
		this.gendername = gendername;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}

}
