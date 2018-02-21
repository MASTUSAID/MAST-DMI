package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;


/**
 * The persistent class for the la_ext_workflow database table.
 * 
 */
@Entity
@Table(name="la_ext_workflow")
public class Workflow implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer workflowid;

	private Boolean isactive;

	private String workflow;

	@Column(name="workflow_en")
	private String workflowEn;

	private Integer workfloworder;
	
	@ManyToOne
	@JoinColumn(name="workflowdefid")
	private LaExtWorkflowdef laExtWorkflowdef;
	
	

	//bi-directional many-to-one association to LaExtWorkflowactionmapping
	@OneToMany(mappedBy="workflow", fetch=FetchType.EAGER)
	@javax.persistence.OrderBy("worder")
	@JsonIgnore
	private Set<WorkflowActionmapping> workflowactionmappings;

	public Integer getWorkflowid() {
		return workflowid;
	}

	public void setWorkflowid(Integer workflowid) {
		this.workflowid = workflowid;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

	public String getWorkflowEn() {
		return workflowEn;
	}

	public void setWorkflowEn(String workflowEn) {
		this.workflowEn = workflowEn;
	}

	public Integer getWorkfloworder() {
		return workfloworder;
	}

	public void setWorkfloworder(Integer workfloworder) {
		this.workfloworder = workfloworder;
	}

	public Set<WorkflowActionmapping> getWorkflowactionmappings() {
		return workflowactionmappings;
	}

	public void setWorkflowactionmappings(
			Set<WorkflowActionmapping> workflowactionmappings) {
		this.workflowactionmappings = workflowactionmappings;
	}

	
	
	
	
	
}