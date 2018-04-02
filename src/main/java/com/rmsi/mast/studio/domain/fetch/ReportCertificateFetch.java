package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the attribute database table.
 * 
 */
@Entity
public class ReportCertificateFetch implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	private Long rnum;
	
	private Long usin;
	private String certificateno;	
	private String landno;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	private Long  area;
	private String landusetype;
	private String neighbor_east;
	private String neighbor_west;
	private String neighbor_north;
	private String neighbor_south;	
	private String sharepercentage;
	private String firstname;
	private String middlename;
	private String lastname;
	private String address;
	private String landofficersignature;
	private String partyid;
	@Temporal(TemporalType.DATE)
	private Date capture_date;
	
	private String landsharetype;
	private String gender;
	private Long age;
	private String maritalstatus;
	private String identitytype;	
	private String identityno;
	@Temporal(TemporalType.DATE)
	private Date dateofregistration;
	private Long duration;
	private Long contactno;
	private String landtype;
	private String country;
	private String region;
	private String province;
	private String commune;
	private String place;
	private String udparcelno;
	
		
	public Long getRnum() {
		return rnum;
	}
	public void setRnum(Long rnum) {
		this.rnum = rnum;
	}
	public Long getUsin() {
		return usin;
	}
	public void setUsin(Long usin) {
		this.usin = usin;
	}
	public String getCertificateno() {
		return certificateno;
	}
	public void setCertificateno(String certificateno) {
		this.certificateno = certificateno;
	}
	public String getLandno() {
		return landno;
	}
	public void setLandno(String landno) {
		this.landno = landno;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getArea() {
		return area;
	}
	public void setArea(Long area) {
		this.area = area;
	}
	public String getLandusetype() {
		return landusetype;
	}
	public void setLandusetype(String landusetype) {
		this.landusetype = landusetype;
	}
	public String getNeighbor_east() {
		return neighbor_east;
	}
	public void setNeighbor_east(String neighbor_east) {
		this.neighbor_east = neighbor_east;
	}
	public String getNeighbor_west() {
		return neighbor_west;
	}
	public void setNeighbor_west(String neighbor_west) {
		this.neighbor_west = neighbor_west;
	}
	public String getNeighbor_north() {
		return neighbor_north;
	}
	public void setNeighbor_north(String neighbor_north) {
		this.neighbor_north = neighbor_north;
	}
	public String getNeighbor_south() {
		return neighbor_south;
	}
	public void setNeighbor_south(String neighbor_south) {
		this.neighbor_south = neighbor_south;
	}
	public String getSharepercentage() {
		return sharepercentage;
	}
	public void setSharepercentage(String sharepercentage) {
		this.sharepercentage = sharepercentage;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLandofficersignature() {
		return landofficersignature;
	}
	public void setLandofficersignature(String landofficersignature) {
		this.landofficersignature = landofficersignature;
	}
	public String getPartyid() {
		return partyid;
	}
	public void setPartyid(String partyid) {
		this.partyid = partyid;
	}
	public Date getCapture_date() {
		return capture_date;
	}
	public void setCapture_date(Date capture_date) {
		this.capture_date = capture_date;
	}
	public String getLandsharetype() {
		return landsharetype;
	}
	public void setLandsharetype(String landsharetype) {
		this.landsharetype = landsharetype;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Long getAge() {
		return age;
	}
	public void setAge(Long age) {
		this.age = age;
	}
	public String getMaritalstatus() {
		return maritalstatus;
	}
	public void setMaritalstatus(String maritalstatus) {
		this.maritalstatus = maritalstatus;
	}
	public String getIdentitytype() {
		return identitytype;
	}
	public void setIdentitytype(String identitytype) {
		this.identitytype = identitytype;
	}
	public String getIdentityno() {
		return identityno;
	}
	public void setIdentityno(String identityno) {
		this.identityno = identityno;
	}
	public Date getDateofregistration() {
		return dateofregistration;
	}
	public void setDateofregistration(Date dateofregistration) {
		this.dateofregistration = dateofregistration;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public Long getContactno() {
		return contactno;
	}
	public void setContactno(Long contactno) {
		this.contactno = contactno;
	}
	public String getLandtype() {
		return landtype;
	}
	public void setLandtype(String landtype) {
		this.landtype = landtype;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCommune() {
		return commune;
	}
	public void setCommune(String commune) {
		this.commune = commune;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getUdparcelno() {
		return udparcelno;
	}
	public void setUdparcelno(String udparcelno) {
		this.udparcelno = udparcelno;
	}
	
	

}