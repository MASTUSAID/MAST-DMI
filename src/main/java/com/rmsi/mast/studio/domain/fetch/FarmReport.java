package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FarmReport implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	 private Long landid;
	 
	 private String project;
	 private  Date createddate;
	 private String username;
	 private String county;
	 private String District;
	 private String Clanname;
	 private String community;
	 private String town;
	 private double area;
	 private String EnterpriseGroupname;
	 private String primarycrop;
	 private  String primarycropdate;
	 private String primarycropduration;
	 private String seccrop;
	 private String seccropdate;
	 private String seccropduration;
	 private String Ethnicity;
	 private String Name;
	 private String Gender;
	 private String MaritalStatus;
	 private String Resident;
	 private String  DOB;
	 private String MobileNo;
	 private String classificationname;
	 private String subclassificationname;
	 private String ispoi;
//	 private String relationship;
	 private String categoryname;
	 private String persontype;
	public Long getLandid() {
		return landid;
	}
	public void setLandid(Long landid) {
		this.landid = landid;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getDistrict() {
		return District;
	}
	public void setDistrict(String district) {
		District = district;
	}
	public String getClanname() {
		return Clanname;
	}
	public void setClanname(String clanname) {
		Clanname = clanname;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public String getEnterpriseGroupname() {
		return EnterpriseGroupname;
	}
	public void setEnterpriseGroupname(String enterpriseGroupname) {
		EnterpriseGroupname = enterpriseGroupname;
	}
	public String getPrimarycrop() {
		return primarycrop;
	}
	public void setPrimarycrop(String primarycrop) {
		this.primarycrop = primarycrop;
	}
	public String getPrimarycropdate() {
		return primarycropdate;
	}
	public void setPrimarycropdate(String primarycropdate) {
		this.primarycropdate = primarycropdate;
	}
	public String getPrimarycropduration() {
		return primarycropduration;
	}
	public void setPrimarycropduration(String primarycropduration) {
		this.primarycropduration = primarycropduration;
	}
	public String getSeccrop() {
		return seccrop;
	}
	public void setSeccrop(String seccrop) {
		this.seccrop = seccrop;
	}
	public String getSeccropdate() {
		return seccropdate;
	}
	public void setSeccropdate(String seccropdate) {
		this.seccropdate = seccropdate;
	}
	public String getSeccropduration() {
		return seccropduration;
	}
	public void setSeccropduration(String seccropduration) {
		this.seccropduration = seccropduration;
	}
	public String getEthnicity() {
		return Ethnicity;
	}
	public void setEthnicity(String ethnicity) {
		Ethnicity = ethnicity;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	public String getMaritalStatus() {
		return MaritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		MaritalStatus = maritalStatus;
	}
	public String getResident() {
		return Resident;
	}
	public void setResident(String resident) {
		Resident = resident;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getMobileNo() {
		return MobileNo;
	}
	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}
	public String getClassificationname() {
		return classificationname;
	}
	public void setClassificationname(String classificationname) {
		this.classificationname = classificationname;
	}
	public String getSubclassificationname() {
		return subclassificationname;
	}
	public void setSubclassificationname(String subclassificationname) {
		this.subclassificationname = subclassificationname;
	}
	public String getIspoi() {
		return ispoi;
	}
	public void setIspoi(String ispoi) {
		this.ispoi = ispoi;
	}
//	public String getRelationship() {
//		return relationship;
//	}
//	public void setRelationship(String relationship) {
//		this.relationship = relationship;
//	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public String getPersontype() {
		return persontype;
	}
	public void setPersontype(String persontype) {
		this.persontype = persontype;
	}
	public Date getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	 
	 
	 
	 
}
