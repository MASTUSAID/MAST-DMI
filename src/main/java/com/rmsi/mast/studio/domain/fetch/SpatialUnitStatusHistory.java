package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the users database table. 
 */
@Entity
@Table(name="sunit_workflow_status_history")
public class SpatialUnitStatusHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id	
	@SequenceGenerator(name="STATUS_HISTORY_ID_GENERATOR", sequenceName="sunit_workflow_status_history_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STATUS_HISTORY_ID_GENERATOR")
	private long status_hist_id;
	
	@Column(name="usin", nullable=false)
	private long usin;
	
	@Column(name="workflow_status_id", nullable=false)
	private int workflow_status_id;
	
	@Column(name="userid", nullable=false)
	private long userid;
	
	@Column(name="status_change_time", nullable=false)
	private Date status_change_time;

	public long getStatus_hist_id() {
		return status_hist_id;
	}

	public void setStatus_hist_id(long status_hist_id) {
		this.status_hist_id = status_hist_id;
	}

	public long getUsin() {
		return usin;
	}

	public void setUsin(long usin) {
		this.usin = usin;
	}

	public int getWorkflow_status_id() {
		return workflow_status_id;
	}

	public void setWorkflow_status_id(int workflow_status_id) {
		this.workflow_status_id = workflow_status_id;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public Date getStatus_change_time() {
		return status_change_time;
	}

	public void setStatus_change_time(Date status_change_time) {
		this.status_change_time = status_change_time;
	}



	




}