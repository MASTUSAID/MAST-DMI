package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Abhay.Pandey
 *
 */

@Entity
@Table(name="la_lease")
public class LaLease implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="pk_la_lease",sequenceName="la_lease_leaseid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_lease") 
	private Integer leaseid;
	
	private Integer leaseyear;
	
	@ManyToOne
	@JoinColumn(name="monthid")
	private La_Month la_Month;
	
	//private Integer monthid;
	
	private Double leaseamount;
	
	private Boolean isactive;
	
	private Integer createdby;
	
	@Temporal(TemporalType.DATE)
	private Date createddate;
	
	private Integer modifiedby;
	
	@Temporal(TemporalType.DATE)
	private Date modifieddate;
	
	private Long personid;
	
	private Long ownerid;
	
	private Long landid;
	
	public Integer getLeaseid() {
		return leaseid;
	}
	public void setLeaseid(Integer leaseid) {
		this.leaseid = leaseid;
	}
	public Integer getLeaseyear() {
		return leaseyear;
	}
	public void setLeaseyear(Integer leaseyear) {
		this.leaseyear = leaseyear;
	}
	/*public Integer getMonthid() {
		return monthid;
	}
	public void setMonthid(Integer monthid) {
		this.monthid = monthid;
	}*/
	
	public Double getLeaseamount() {
		return leaseamount;
	}
	public La_Month getLa_Month() {
		return la_Month;
	}
	public void setLa_Month(La_Month la_Month) {
		this.la_Month = la_Month;
	}
	public void setLeaseamount(Double leaseamount) {
		this.leaseamount = leaseamount;
	}
	public Boolean getIsactive() {
		return isactive;
	}
	public void setIsactive(Boolean isactive) {
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
	public Long getPersonid() {
		return personid;
	}
	public void setPersonid(Long personid) {
		this.personid = personid;
	}
	public Long getLandid() {
		return landid;
	}
	public void setLandid(Long landid) {
		this.landid = landid;
	}
	public Long getOwnerid() {
		return ownerid;
	}
	public void setOwnerid(Long ownerid) {
		this.ownerid = ownerid;
	}
	
}
