package com.rmsi.mast.studio.domain;


import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the la_ext_projectbaselayermapping database table.
 * 
 */
@Entity
@Table(name="la_ext_projectbaselayermapping")
public class ProjectBaselayer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ProjectBaselayer_sequence",sequenceName="la_ext_projectbaselayermapping_projectbaselayerid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="ProjectBaselayer_sequence") 
	private Integer projectbaselayerid;

	private Integer createdby;

	@Temporal( TemporalType.DATE)
	private Date createddate;

	private Boolean isactive;

	private Integer modifiedby;

	@Temporal( TemporalType.DATE)
	private Date modifieddate;
	
	
	public ProjectBaselayer()
	{
		
	}

	
	@ManyToOne
	@JoinColumn(name="baselayerid")
	private Baselayer Baselayers;

	
	@ManyToOne
	@JoinColumn(name="projectnameid")
	private Project project;


	public Integer getProjectbaselayerid() {
		return projectbaselayerid;
	}

	public void setProjectbaselayerid(Integer projectbaselayerid) {
		this.projectbaselayerid = projectbaselayerid;
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


	@JsonIgnore
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	
	public Baselayer getBaselayers() {
		return Baselayers;
	}

	public void setBaselayers(Baselayer baselayers) {
		Baselayers = baselayers;
	}

	



	
}