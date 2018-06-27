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
@Table(name="la_ext_transactionhistory")
public class LaExtTransactionHistory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="pk_la_ext_transactionhistory",sequenceName="la_transactionhistory_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_ext_transactionhistory") 
	private Integer transactionhistoryid;
	
	
	private String oldownerid;
	private String newownerid;
	private Long landid;
	private Integer transactionid;
	private Boolean isactive;
	
	private Integer createdby;
	
	@Temporal(TemporalType.DATE)
	private Date createddate;
	
	private Integer modifiedby;
	
	@Temporal(TemporalType.DATE)
	private Date modifieddate;
	
	
	
	public Integer getTransactionhistoryid() {
		return transactionhistoryid;
	}
	public void setTransactionhistoryid(Integer transactionhistoryid) {
		this.transactionhistoryid = transactionhistoryid;
	}
	public Integer getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(Integer transactionid) {
		this.transactionid = transactionid;
	}
	/*public Long getOldownerid() {
		return oldownerid;
	}
	public void setOldownerid(Long oldownerid) {
		this.oldownerid = oldownerid;
	}
	public Long getNewownerid() {
		return newownerid;
	}
	public void setNewownerid(Long newownerid) {
		this.newownerid = newownerid;
	}*/
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

	public Long getLandid() {
		return landid;
	}
	public void setLandid(Long landid) {
		this.landid = landid;
	}
	public String getOldownerid() {
		return oldownerid;
	}
	public void setOldownerid(String oldownerid) {
		this.oldownerid = oldownerid;
	}
	public String getNewownerid() {
		return newownerid;
	}
	public void setNewownerid(String newownerid) {
		this.newownerid = newownerid;
	}


}
