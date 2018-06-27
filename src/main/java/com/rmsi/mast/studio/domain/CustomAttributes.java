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
import javax.persistence.Transient;

@Entity
@Table(name = "la_ext_resource_custom_attributevalue")
public class CustomAttributes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="pk_la_ext_resourcecustomattributevalue",sequenceName="la_ext_resourcecustomattributevalue_customattributevalueid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_ext_resourcecustomattributevalue")
	private Integer customattributevalueid;
	
	private String attributevalue;
	
	@Column(name="projectid")
	private Integer projectid;
	

	@Column(name="landid")
	private Integer landid;
	
	@ManyToOne
	@JoinColumn(name="customattributeid")
	private ResourceCustomAttributes customattributeid;
	
	private Integer subclassificationid;
	
	private String geomtype;
	
	@Transient
	private Integer Fieldname;
	
	private Integer attributeoptionsid;

	
	
	public Integer getAttributeoptionsid() {
		return attributeoptionsid;
	}


	public void setAttributeoptionsid(Integer attributeoptionsid) {
		this.attributeoptionsid = attributeoptionsid;
	}


	public CustomAttributes(){
		
	}
	

	public String getGeomtype() {
		return geomtype;
	}


	public void setGeomtype(String geomtype) {
		this.geomtype = geomtype;
	}


	public Integer getCustomattributevalueid() {
		return customattributevalueid;
	}

	public void setCustomattributevalueid(Integer customattributevalueid) {
		this.customattributevalueid = customattributevalueid;
	}

	public String getAttributevalue() {
		return attributevalue;
	}

	public void setAttributevalue(String attributevalue) {
		this.attributevalue = attributevalue;
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

	public ResourceCustomAttributes getCustomattributeid() {
		return customattributeid;
	}

	public void setCustomattributeid(ResourceCustomAttributes customattributeid) {
		this.customattributeid = customattributeid;
	}

	public Integer getSubclassificationid() {
		return subclassificationid;
	}

	public void setSubclassificationid(Integer subclassificationid) {
		this.subclassificationid = subclassificationid;
	}


	public Integer getFieldname() {
		return Fieldname;
	}


	public void setFieldname(Integer fieldname) {
		Fieldname = fieldname;
	}
	
	
	
	

}
