package com.rmsi.mast.studio.mobile.transferobjects;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.rmsi.mast.studio.domain.ResourceClassification;
import com.rmsi.mast.studio.domain.ResourceSubClassification;

public class ResourceLandClassificationMapping {
	
    
	private Integer landclassmappingid;
	
	
	private Integer projectid;
	

	private Integer landid;
	
	
	private Integer classificationid;
	
	private Integer subclassificationid;
	
	
	public ResourceLandClassificationMapping(){
		
	}


	public Integer getLandclassmappingid() {
		return landclassmappingid;
	}


	public void setLandclassmappingid(Integer landclassmappingid) {
		this.landclassmappingid = landclassmappingid;
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


	public Integer getClassificationid() {
		return classificationid;
	}


	public void setClassificationid(Integer classificationid) {
		this.classificationid = classificationid;
	}


	public Integer getSubclassificationid() {
		return subclassificationid;
	}


	public void setSubclassificationid(Integer subclassificationid) {
		this.subclassificationid = subclassificationid;
	}
	
	

}
