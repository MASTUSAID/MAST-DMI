package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="la_ext_applicationstatus")
public class Status implements Serializable {
	private static final long serialVersionUID = 1L;
        public static final int STATUS_APPROVED = 2;
        public static final int STATUS_DENIED = 5;
        public static final int STATUS_NEW = 1;
        public static final int STATUS_VALIDATED = 3;
        public static final int STATUS_REFERRED = 4;

	@Id
	@Column(name="applicationstatusid")
	@SequenceGenerator(name="pk_la_applicationstatus",sequenceName="la_ext_applicationstatus_applicationstatusid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_applicationstatus") 
	private Integer workflowStatusId;

	@Column(name="applicationstatus")
	private String workflowStatus;
	private String applicationstatus_en;
	@Column(name="isactive")
    private Boolean active;
	

	public Status() {
	}
	
	

	public Integer getWorkflowStatusId() {
		return workflowStatusId;
	}


	public void setWorkflowStatusId(Integer workflowStatusId) {
		this.workflowStatusId = workflowStatusId;
	}


	public String getWorkflowStatus() {
		return workflowStatus;
	}


	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}


	public String getApplicationstatus_en() {
		return applicationstatus_en;
	}


	public void setApplicationstatus_en(String applicationstatus_en) {
		this.applicationstatus_en = applicationstatus_en;
	}


	public Boolean getActive() {
		return active;
	}


	public void setActive(Boolean active) {
		this.active = active;
	}

	

	
	
	
}