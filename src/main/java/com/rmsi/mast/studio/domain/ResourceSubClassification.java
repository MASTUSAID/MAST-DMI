package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "la_ext_resourcesubclassification")
public class ResourceSubClassification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	@Id
	private Integer subclassificationid;
	
	
	private String subclassificationname;
	
	private Boolean isactive;
	
	@ManyToOne
	@JoinColumn(name="classificationid")
	private ResourceClassification classificationid;
	
	@ManyToOne
	@JoinColumn(name="geometrytypeid")
	private GeometryType geometrytypeid;
	
	
	public ResourceSubClassification(){
		
	}


	public Integer getSubclassificationid() {
		return subclassificationid;
	}


	public void setSubclassificationid(Integer subclassificationid) {
		this.subclassificationid = subclassificationid;
	}


	public String getSubclassificationname() {
		return subclassificationname;
	}


	public void setSubclassificationname(String subclassificationname) {
		this.subclassificationname = subclassificationname;
	}


	public Boolean getIsactive() {
		return isactive;
	}


	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}


	public ResourceClassification getClassificationid() {
		return classificationid;
	}


	public void setClassificationid(ResourceClassification classificationid) {
		this.classificationid = classificationid;
	}


	public GeometryType getGeometrytypeid() {
		return geometrytypeid;
	}


	public void setGeometrytypeid(GeometryType geometrytypeid) {
		this.geometrytypeid = geometrytypeid;
	}


	

}
