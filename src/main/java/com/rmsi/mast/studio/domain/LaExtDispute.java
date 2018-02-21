package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the la_ext_dispute database table.
 * 
 */
@Entity
@Table(name="la_ext_dispute")
@NamedQuery(name="LaExtDispute.findAll", query="SELECT l FROM LaExtDispute l")
public class LaExtDispute implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="la_ext_dispute_sequence",sequenceName="la_ext_dispute_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="la_ext_dispute_sequence") 
	private Integer disputeid;
	
	@OneToMany(mappedBy="laExtDispute", fetch=FetchType.EAGER)
	private List<LaExtDisputelandmapping> laExtDisputelandmappings;
	
	

	private String comment;

	private Integer createdby;

	@Temporal(TemporalType.DATE)
	private Date createddate;

	private Boolean isactive;

	
	private Integer modifiedby;

	@Temporal(TemporalType.DATE)
	private Date modifieddate;

	private Integer disputetypeid;

	
	private Long landid;


    @Column(name = "disputestatusid")
    private Integer status;
    
    
 
    
	public LaExtDispute() {
	}

	public Integer getDisputeid() {
		return disputeid;
	}

	public void setDisputeid(Integer disputeid) {
		this.disputeid = disputeid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	

	public Long getLandid() {
		return landid;
	}

	public void setLandid(Long landid) {
		this.landid = landid;
	}



	public Integer getDisputetypeid() {
		return disputetypeid;
	}

	public void setDisputetypeid(Integer disputetypeid) {
		this.disputetypeid = disputetypeid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<LaExtDisputelandmapping> getLaExtDisputelandmappings() {
		return laExtDisputelandmappings;
	}

	public void setLaExtDisputelandmappings(
			List<LaExtDisputelandmapping> laExtDisputelandmappings) {
		this.laExtDisputelandmappings = laExtDisputelandmappings;
	}

	

	
}