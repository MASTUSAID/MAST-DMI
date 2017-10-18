package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sunit_status")
public class Status implements Serializable {
	private static final long serialVersionUID = 1L;
        public static final int STATUS_APPROVED = 2;
        public static final int STATUS_DENIED = 5;
        public static final int STATUS_NEW = 1;
        public static final int STATUS_VALIDATED = 3;
        public static final int STATUS_REFERRED = 4;

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