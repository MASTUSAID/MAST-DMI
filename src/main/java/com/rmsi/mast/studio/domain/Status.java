package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the sunit_status database table.
 * 
 */
@Entity
@Table(name="sunit_status")
public class Status implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="workflow_status_id")
	private Integer workflowStatusId;

	@Column(name="workflow_status")
	private String workflowStatus;

	public Status() {
	}

	public Integer getWorkflowStatusId() {
		return this.workflowStatusId;
	}

	public void setWorkflowStatusId(Integer workflowStatusId) {
		this.workflowStatusId = workflowStatusId;
	}

	public String getWorkflowStatus() {
		return this.workflowStatus;
	}

	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
}