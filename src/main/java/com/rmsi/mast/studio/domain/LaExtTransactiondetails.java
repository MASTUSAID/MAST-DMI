package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "la_ext_transactiondetails")
public class LaExtTransactiondetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer transactionid;
	private Integer moduleid;
	private Integer applicationstatusid;
	private Integer moduletransid;
	private String remarks;
	private boolean isactive;
	private Integer createdby;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date createddate;
	
	public Integer getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(Integer transactionid) {
		this.transactionid = transactionid;
	}
	public Integer getModuleid() {
		return moduleid;
	}
	public void setModuleid(Integer moduleid) {
		this.moduleid = moduleid;
	}
	public Integer getApplicationstatusid() {
		return applicationstatusid;
	}
	public void setApplicationstatusid(Integer applicationstatusid) {
		this.applicationstatusid = applicationstatusid;
	}
	public Integer getModuletransid() {
		return moduletransid;
	}
	public void setModuletransid(Integer moduletransid) {
		this.moduletransid = moduletransid;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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


}
