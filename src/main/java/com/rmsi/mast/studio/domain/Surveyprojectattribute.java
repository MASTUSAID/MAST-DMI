package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the surveyprojectattributes database table.
 * 
 */
@Entity
@Table(name = "la_ext_surveyprojectattributes")
public class Surveyprojectattribute implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="pk_la_ext_surveyprojectattributes",sequenceName="la_ext_surveyprojectattributes_surveyprojectattributesid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_ext_surveyprojectattributes") 
	@Column(name="surveyprojectattributesid")
	private Long uid;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	// bi-directional many-to-one association to Attribute
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "parentuid")
	private List<AttributeValues> attributes;

	// bi-directional many-to-one association to AttributeMaster
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "attributemasterid")
	private AttributeMaster attributeMaster;

	
	@ManyToOne
	@JoinColumn(name="projectnameid")
	private Project name;

	@Column(name="attributecategoryid")
	private Integer attributecategoryid;
	
	@Column(name="attributeorder")
	private Integer  attributeorder;
	

	private long createdby;
	private long modifiedby;
	
	@Temporal( TemporalType.DATE )
    private Date createddate;
	
	@Temporal( TemporalType.DATE )
    private Date modifieddate;
	
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

	public Project getName() {
		return name;
	}

	public void setName(Project name) {
		this.name = name;
	}

	public Integer getAttributecategoryid() {
		return attributecategoryid;
	}

	public void setAttributecategoryid(Integer attributecategoryid) {
		this.attributecategoryid = attributecategoryid;
	}

	public long getCreatedby() {
		return createdby;
	}

	public void setCreatedby(long createdby) {
		this.createdby = createdby;
	}

	public long getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(long modifiedby) {
		this.modifiedby = modifiedby;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public Date getModifieddate() {
		return modifieddate;
	}

	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}

	/*
	 * public Project getProject() { return this.project; }
	 * 
	 * public void setProject(Project project) { this.project = project; }
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

//	@Id
//	@SequenceGenerator(name = "SURVEYPROJECTATTRIBUTES_UID_GENERATOR", sequenceName = "SURVEYPROJECTATTRIBUTES_UID_SEQ", allocationSize=1)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SURVEYPROJECTATTRIBUTES_UID_GENERATOR")
//	private Long uid;
//
//	public Long getUid() {
//		return uid;
//	}
//
//	public void setUid(Long uid) {
//		this.uid = uid;
//	}
//
//	// bi-directional many-to-one association to Attribute
//	@OneToMany(fetch = FetchType.EAGER)
//	@JoinColumn(name = "uid")
//	private List<AttributeValues> attributes;
//
//	// bi-directional many-to-one association to AttributeMaster
//	@ManyToOne
//	@JoinColumn(name = "id")
//	private AttributeMaster attributeMaster;
//
//	// bi-directional many-to-one association to Project
//	// @ManyToOne
//	// @JoinColumn(name="name")
//	// private Project project;
//	private String name;
//
//	private Integer attributecategoryid;
//	
//	private Integer  attributeorder;
//	
//
//	public Integer getAttributeorder() {
//		return attributeorder;
//	}
//
//	public void setAttributeorder(Integer attributeorder) {
//		this.attributeorder = attributeorder;
//	}
//
//	public Surveyprojectattribute() {
//	}
//
//	public List<AttributeValues> getAttributes() {
//		return this.attributes;
//	}
//
//	public void setAttributes(List<AttributeValues> attributes) {
//		this.attributes = attributes;
//	}
//
//	public AttributeMaster getAttributeMaster() {
//		return this.attributeMaster;
//	}
//
//	public void setAttributeMaster(AttributeMaster attributeMaster) {
//		this.attributeMaster = attributeMaster;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public Integer getAttributecategoryid() {
//		return attributecategoryid;
//	}
//
//	public void setAttributecategoryid(Integer attributecategoryid) {
//		this.attributecategoryid = attributecategoryid;
//	}
//
//	/*
//	 * public Project getProject() { return this.project; }
//	 * 
//	 * public void setProject(Project project) { this.project = project; }
//	 */

}