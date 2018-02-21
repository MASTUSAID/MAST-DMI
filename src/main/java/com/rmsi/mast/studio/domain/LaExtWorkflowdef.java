package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the la_ext_workflowdef database table.
 * 
 */
@Entity
@Table(name="la_ext_workflowdef")
@NamedQuery(name="LaExtWorkflowdef.findAll", query="SELECT l FROM LaExtWorkflowdef l")
public class LaExtWorkflowdef implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final int PARCEL=1;
	private static final int RESOURCE=2;
	
	@Id
	private Integer workflowdefid;

	private String name;

	private Integer type;

	//bi-directional many-to-one association to LaExtWorkflow
	@OneToMany(mappedBy="laExtWorkflowdef", fetch=FetchType.EAGER)
	private List<Workflow> laExtWorkflows;

	public LaExtWorkflowdef() {
		
	}

	public Integer getWorkflowdefid() {
		return workflowdefid;
	}

	public void setWorkflowdefid(Integer workflowdefid) {
		this.workflowdefid = workflowdefid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<Workflow> getLaExtWorkflows() {
		return laExtWorkflows;
	}

	public void setLaExtWorkflows(List<Workflow> laExtWorkflows) {
		this.laExtWorkflows = laExtWorkflows;
	}

	

}