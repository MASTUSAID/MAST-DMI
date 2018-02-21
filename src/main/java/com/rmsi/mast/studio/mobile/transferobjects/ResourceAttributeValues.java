package com.rmsi.mast.studio.mobile.transferobjects;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.rmsi.mast.studio.domain.AttributeMaster;

public class ResourceAttributeValues implements Serializable{

	
	
	private Integer attributevalueid;
	private String attributevalue;
	private Integer projectid;
	private Integer landid;
	private Integer attributemasterid;
	
	public ResourceAttributeValues(){
		
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

	public Integer getAttributemasterid() {
		return attributemasterid;
	}

	public void setAttributemasterid(Integer attributemasterid) {
		this.attributemasterid = attributemasterid;
	}

	
	
	
	
}
