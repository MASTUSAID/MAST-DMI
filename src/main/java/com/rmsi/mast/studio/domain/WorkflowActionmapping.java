package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;



/**
 * The persistent class for the la_ext_workflowactionmapping database table.
 * 
 */
@Entity
@Table(name="la_ext_workflowactionmapping")
public class WorkflowActionmapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer workflowactionid;

	private String actionname;

	@Column(name="actionname_en")
	private String actionnameEn;

	private Integer worder;

	private Integer roleid;

	private String action;
	//bi-directional many-to-one association to LaExtWorkflow
	@ManyToOne
	@JoinColumn(name="workflowid")
	private Workflow workflow;
	
	
	
	private Boolean isactive;

	public Integer getWorkflowactionid() {
		return workflowactionid;
	}

	public void setWorkflowactionid(Integer workflowactionid) {
		this.workflowactionid = workflowactionid;
	}

	public String getActionname() {
		return actionname;
	}

	public void setActionname(String actionname) {
		this.actionname = actionname;
	}

	public String getActionnameEn() {
		return actionnameEn;
	}

	public void setActionnameEn(String actionnameEn) {
		this.actionnameEn = actionnameEn;
	}

	

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public Integer getWorder() {
		return worder;
	}

	public void setWorder(Integer worder) {
		this.worder = worder;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	

	
	
	
	
}