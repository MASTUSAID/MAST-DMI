package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: WorkflowStatusHistory
 *
package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the la_ext_landworkflowhistory database table.
 * 
 */
@Entity
@Table(name="la_ext_landworkflowhistory")
public class WorkflowStatusHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@SequenceGenerator(name = "WORK_FLOW_STATUS_HISTORY", sequenceName = "la_ext_landworkflowhistory_landworkflowhistoryid_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WORK_FLOW_STATUS_HISTORY")
	@Id
	private Integer landworkflowhistoryid;

	private String comments;

	private Integer createdby;

	@Temporal(TemporalType.DATE)
	private Date createddate;

	private Boolean isactive;

	private Integer modifiedby;

	@Temporal(TemporalType.DATE)
	private Date modifieddate;

	@Temporal(TemporalType.DATE)
	private Date statuschangedate;

	private Integer userid;

	//bi-directional many-to-one association to LaExtApplicationstatus
	@ManyToOne
	@JoinColumn(name="applicationstatusid")
	private Status status;
	
	
	@ManyToOne
	@JoinColumn(name="workflowid" )
	private Workflow workflow;
	

	//bi-directional many-to-one association to LaSpatialunitLand
	/*@ManyToOne
	@JoinColumn(name="landid")
	private LaSpatialunitLand laSpatialunitLand;*/
	
	
	@Transient
	User user;
	
	
	private long landid;

	public WorkflowStatusHistory() {
		
	}

	public Integer getLandworkflowhistoryid() {
		return landworkflowhistoryid;
	}

	public void setLandworkflowhistoryid(Integer landworkflowhistoryid) {
		this.landworkflowhistoryid = landworkflowhistoryid;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public Date getStatuschangedate() {
		return statuschangedate;
	}

	public void setStatuschangedate(Date statuschangedate) {
		this.statuschangedate = statuschangedate;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public long getLandid() {
		return landid;
	}

	public void setLandid(long landid) {
		this.landid = landid;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	@Transient
	public User getUser() {
		return user;
	}

	@Transient
	public void setUser(User user) {
		this.user = user;
	}

	
  
	
	
	
	

}