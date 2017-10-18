package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the surveyprojectattributes database table.
 * 
 */
@Entity
@Table(name = "surveyprojectattributes")
public class Surveyprojectattribute implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SURVEYPROJECTATTRIBUTES_UID_GENERATOR", sequenceName = "SURVEYPROJECTATTRIBUTES_UID_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SURVEYPROJECTATTRIBUTES_UID_GENERATOR")
	private Long uid;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	// bi-directional many-to-one association to Attribute
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "uid")
	private List<AttributeValues> attributes;

	// bi-directional many-to-one association to AttributeMaster
	@ManyToOne
	@JoinColumn(name = "id")
	private AttributeMaster attributeMaster;

	// bi-directional many-to-one association to Project
	// @ManyToOne
	// @JoinColumn(name="name")
	// private Project project;
	private String name;

	private Integer attributecategoryid;
	
	private Integer  attributeorder;
	

	public Integer getAttributeorder() {
		return attributeorder;
	}

	public void setAttributeorder(Integer attributeorder) {
		this.attributeorder = attributeorder;
	}

	public Surveyprojectattribute() {
	}

	public List<AttributeValues> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(List<AttributeValues> attributes) {
		this.attributes = attributes;
	}

	public AttributeMaster getAttributeMaster() {
		return this.attributeMaster;
	}

	public void setAttributeMaster(AttributeMaster attributeMaster) {
		this.attributeMaster = attributeMaster;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAttributecategoryid() {
		return attributecategoryid;
	}

	public void setAttributecategoryid(Integer attributecategoryid) {
		this.attributecategoryid = attributecategoryid;
	}

	/*
	 * public Project getProject() { return this.project; }
	 * 
	 * public void setProject(Project project) { this.project = project; }
	 */

}