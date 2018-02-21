package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "la_ext_personlandmapping")
public class LaExtPersonLandMapping implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer personlandid;
	private Long partyid;
	private Long landid;
	private Integer persontypeid;
	private Integer transactionid;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date certificateissuedate;
	private String certificateno;
	private String sharepercentage;
	private boolean isactive;
	private Integer createdby;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date createddate;
	private Integer modifiedby;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date modifieddate;
	public Integer getPersonlandid() {
		return personlandid;
	}
	public void setPersonlandid(Integer personlandid) {
		this.personlandid = personlandid;
	}
	public Long getPartyid() {
		return partyid;
	}
	public void setPartyid(Long partyid) {
		this.partyid = partyid;
	}
	public Long getLandid() {
		return landid;
	}
	public void setLandid(Long landid) {
		this.landid = landid;
	}
	public Integer getPersontypeid() {
		return persontypeid;
	}
	public void setPersontypeid(Integer persontypeid) {
		this.persontypeid = persontypeid;
	}
	public Integer getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(Integer transactionid) {
		this.transactionid = transactionid;
	}
	public Date getCertificateissuedate() {
		return certificateissuedate;
	}
	public void setCertificateissuedate(Date certificateissuedate) {
		this.certificateissuedate = certificateissuedate;
	}
	public String getCertificateno() {
		return certificateno;
	}
	public void setCertificateno(String certificateno) {
		this.certificateno = certificateno;
	}
	public String getSharepercentage() {
		return sharepercentage;
	}
	public void setSharepercentage(String sharepercentage) {
		this.sharepercentage = sharepercentage;
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
	

}
