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
@Table(name="la_ext_resourcepoiattributevalue")
public class ResourcePOIAttributeValues implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@SequenceGenerator(name="pk_la_ext_resourcepoiattributevalue",sequenceName="la_ext_resourcepoiattributevalue_attributevalueid_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_la_ext_resourcepoiattributevalue")
	private Integer poiattributevalueid;
	
	private String attributevalue;
	
	@Column(name="projectid")
	private Integer projectid;
	

	@Column(name="landid")
	private Integer landid;
	
	@ManyToOne
	@JoinColumn(name="attributemasterid")
	private AttributeMasterResourcePOI attributemaster;
	

	private String geomtype;
	
	
	public ResourcePOIAttributeValues(){
		
	}


	public Integer getPoiattributevalueid() {
		return poiattributevalueid;
	}


	public void setPoiattributevalueid(Integer poiattributevalueid) {
		this.poiattributevalueid = poiattributevalueid;
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


	public AttributeMasterResourcePOI getAttributemaster() {
		return attributemaster;
	}


	public void setAttributemaster(AttributeMasterResourcePOI attributemaster) {
		this.attributemaster = attributemaster;
	}


	public String getGeomtype() {
		return geomtype;
	}


	public void setGeomtype(String geomtype) {
		this.geomtype = geomtype;
	}
	
	

}
