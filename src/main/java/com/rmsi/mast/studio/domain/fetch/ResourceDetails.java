package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * 
 * @author Kamal.Upreti
 *
 */

@Entity
public class ResourceDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer landid;
	private String classificationName;
	private String  subclassificationName;
	private String  categoryName;
	private String  geometryName;
	private String  projectName;
	private String  personName;
	
	public Integer getLandid() {
		return landid;
	}
	public void setLandid(Integer landid) {
		this.landid = landid;
	}
	public String getClassificationName() {
		return classificationName;
	}
	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}
	public String getSubclassificationName() {
		return subclassificationName;
	}
	public void setSubclassificationName(String subclassificationName) {
		this.subclassificationName = subclassificationName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getGeometryName() {
		return geometryName;
	}
	public void setGeometryName(String geometryName) {
		this.geometryName = geometryName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	
	
	
}
	
