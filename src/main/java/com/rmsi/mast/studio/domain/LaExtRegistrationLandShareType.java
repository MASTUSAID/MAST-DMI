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
 * @author kamal.upreti
 *
 */

@Entity
@Table(name="la_ext_registrationsharetype")
public class LaExtRegistrationLandShareType implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="pk_la_ext_registrationsharetype",sequenceName="la_registrationsharetype_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_ext_registrationsharetype") 
	private Integer registrationsharetypeid;
	private Long landid;
	private Long landsharetypeid;

	private Boolean isactive;
	
	private Integer createdby;
	
	@Temporal(TemporalType.DATE)
	private Date createddate;
	
	private Integer modifiedby;
	
	@Temporal(TemporalType.DATE)
	private Date modifieddate;

	public Integer getRegistrationsharetypeid() {
		return registrationsharetypeid;
	}

	public void setRegistrationsharetypeid(Integer registrationsharetypeid) {
		this.registrationsharetypeid = registrationsharetypeid;
	}

	public Long getLandid() {
		return landid;
	}

	public void setLandid(Long landid) {
		this.landid = landid;
	}

	public Long getLandsharetypeid() {
		return landsharetypeid;
	}

	public void setLandsharetypeid(Long landsharetypeid) {
		this.landsharetypeid = landsharetypeid;
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
	
	
	
	
	

}
