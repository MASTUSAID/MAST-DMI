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
@Table(name = "la_ext_resourceattributevalue")
public class ResourceAttributeValues implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@SequenceGenerator(name="pk_la_ext_resourceattributevalue",sequenceName="la_ext_resourceattributevalue_attributevalueid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_ext_resourceattributevalue")
	private Integer attributevalueid;
	
	private String attributevalue;
	
	@Column(name="projectid")
	private Integer projectid;
	

	@Column(name="landid")
	private Integer landid;
	
	@ManyToOne
	@JoinColumn(name="attributemasterid")
	private AttributeMaster attributemaster;
	
	private Integer groupid;
	

	private String geomtype;
	
	@Transient
	private String Fieldname;

	
	public ResourceAttributeValues(){
		
	}


	

	public String getFieldname() {
		return Fieldname;
	}




	public void setFieldname(String fieldname) {
		Fieldname = fieldname;
	}




	public Integer getGroupid() {
		return groupid;
	}




	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}




	public AttributeMaster getAttributemaster() {
		return attributemaster;
	}


	public void setAttributemaster(AttributeMaster attributemaster) {
		this.attributemaster = attributemaster;
	}

	
	public Integer getAttributevalueid() {
		return attributevalueid;
	}


	public void setAttributevalueid(Integer attributevalueid) {
		this.attributevalueid = attributevalueid;
	}


	public String getAttributevalue() {
		return attributevalue;
	}


	public void setAttributevalue(String attributevalue) {
		this.attributevalue = attributevalue;
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


	
	
	
}
