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
public class LeaseHistoryForFetch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long rnum;
	
	private Long landid;
	private String firstname;
	private String middlename;
	private String lastname;
	private String address;
	private String identityno;
	private Long leaseyear;
	private Long monthid;
	private Long leaseamount;
	
	@Temporal(TemporalType.DATE)
	private Date createddate;
	
	
	public Long getRnum() {
		return rnum;
	}

	public void setRnum(Long rnum) {
		this.rnum = rnum;
	}
	
	public Long getLeaseyear() {
		return leaseyear;
	}

	public void setLeaseyear(Long leaseyear) {
		this.leaseyear = leaseyear;
	}

	public Long getMonthid() {
		return monthid;
	}

	public void setMonthid(Long monthid) {
		this.monthid = monthid;
	}

	public Long getLeaseamount() {
		return leaseamount;
	}

	public void setLeaseamount(Long leaseamount) {
		this.leaseamount = leaseamount;
	}

	public Long getLandid() {
		return landid;
	}

	public void setLandid(Long landid) {
		this.landid = landid;
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

	public String getIdentityno() {
		return identityno;
	}

	public void setIdentityno(String identityno) {
		this.identityno = identityno;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	

}