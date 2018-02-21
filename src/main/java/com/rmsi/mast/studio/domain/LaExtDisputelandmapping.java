package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import com.fasterxml.jackson.annotation.JsonIgnore;




/**
 * The persistent class for the la_ext_disputelandmapping database table.
 * 
 */
@Entity
@Table(name="la_ext_disputelandmapping")
@NamedQuery(name="LaExtDisputelandmapping.findAll", query="SELECT l FROM LaExtDisputelandmapping l")
public class LaExtDisputelandmapping implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="la_ext_disputelandmapping_sequence",sequenceName="la_ext_disputelandmapping_disputelandid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="la_ext_disputelandmapping_sequence") 
	@Column(name="disputelandid")
	private Integer disputelandid;

	@Column(name="createdby")
	private Integer createdby;

	@Temporal(TemporalType.DATE)
	private Date createddate;

	@Column(name="isactive")
	private Boolean isactive;

	@Column(name="modifiedby")
	private Integer modifiedby;

	@Temporal(TemporalType.DATE)
	private Date modifieddate;

	//bi-directional many-to-one association to LaExtDisputetype
	@ManyToOne
	@JoinColumn(name="disputetypeid")
	private DisputeType laExtDisputetype;

	//bi-directional many-to-one association to LaExtTransactiondetail
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="transactionid")
	private LaExtTransactiondetail laExtTransactiondetail;

	@ManyToOne
	@JoinColumn(name="disputeid")
	@JsonIgnore
	private LaExtDispute laExtDispute;
	
	

	private String comment;
	

	

	
	private long partyid;
	
	private Integer persontypeid;

	
	private Long landid;

	
	public LaExtDisputelandmapping() {
	}

	
	public Integer getDisputelandid() {
		return disputelandid;
	}

	public void setDisputelandid(Integer disputelandid) {
		this.disputelandid = disputelandid;
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

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
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

	public DisputeType getLaExtDisputetype() {
		return laExtDisputetype;
	}

	public void setLaExtDisputetype(DisputeType laExtDisputetype) {
		this.laExtDisputetype = laExtDisputetype;
	}

	

	public Integer getPersontypeid() {
		return persontypeid;
	}

	public void setPersontypeid(Integer persontypeid) {
		this.persontypeid = persontypeid;
	}

	public Long getLandid() {
		return landid;
	}

	public void setLandid(Long landid) {
		this.landid = landid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}



	public LaExtTransactiondetail getLaExtTransactiondetail() {
		return laExtTransactiondetail;
	}

	public void setLaExtTransactiondetail(
			LaExtTransactiondetail laExtTransactiondetail) {
		this.laExtTransactiondetail = laExtTransactiondetail;
	}


	public long getPartyid() {
		return partyid;
	}

	public void setPartyid(long partyid) {
		this.partyid = partyid;
	}

	public LaExtDispute getLaExtDispute() {
		return laExtDispute;
	}

	public void setLaExtDispute(LaExtDispute laExtDispute) {
		this.laExtDispute = laExtDispute;
	}

	
	
	




	
	

}


