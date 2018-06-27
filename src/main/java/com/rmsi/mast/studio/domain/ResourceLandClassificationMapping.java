package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "la_ext_resourcelandclassificationmapping")
public class ResourceLandClassificationMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Id
	@SequenceGenerator(name="pk_la_ext_resourcelandclassificationmapping",sequenceName="la_ext_resourcelandclassificationmapping_landclassmappingid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_ext_resourcelandclassificationmapping") 
	private Integer landclassmappingid;
	
	@Column(name="projectid")
	private Integer projectid;
	

	private Integer landid;
	
	@ManyToOne
	@JoinColumn(name="classificationid")
	private ResourceClassification classificationid;
	
	@ManyToOne
	@JoinColumn(name="subclassificationid")
	private ResourceSubClassification subclassificationid;
	
	private String geomtype;
	
	private Integer categoryid;
	
	public Integer getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
	}

	public ResourceLandClassificationMapping(){
		
	}

	public Integer getLandclassmappingid() {
		return landclassmappingid;
	}

	public void setLandclassmappingid(Integer landclassmappingid) {
		this.landclassmappingid = landclassmappingid;
	}



	public String getGeomtype() {
		return geomtype;
	}

	public void setGeomtype(String geomtype) {
		this.geomtype = geomtype;
	}

	public Integer getProjectid() {
		return projectid;
	}

	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}

	public Integer getLandid() {
		return landid;
	}

	public void setLandid(Integer landid) {
		this.landid = landid;
	}

	public ResourceClassification getClassificationid() {
		return classificationid;
	}

	public void setClassificationid(ResourceClassification classificationid) {
		this.classificationid = classificationid;
	}

	public ResourceSubClassification getSubclassificationid() {
		return subclassificationid;
	}

	public void setSubclassificationid(ResourceSubClassification subclassificationid) {
		this.subclassificationid = subclassificationid;
	}

	
	
	
}
