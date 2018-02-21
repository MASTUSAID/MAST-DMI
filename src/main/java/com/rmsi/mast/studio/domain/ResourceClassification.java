package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "la_ext_resourceclassification")
public class ResourceClassification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	private Integer classificationid;
	
	
	private String classificationname;
	
	
	private Boolean isactive;

	
	public ResourceClassification(){
		
	}


	public Integer getClassificationid() {
		return classificationid;
	}


	public void setClassificationid(Integer classificationid) {
		this.classificationid = classificationid;
	}


	public String getClassificationname() {
		return classificationname;
	}


	public void setClassificationname(String classificationname) {
		this.classificationname = classificationname;
	}


	public Boolean getIsactive() {
		return isactive;
	}


	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}
	
	
	

}
