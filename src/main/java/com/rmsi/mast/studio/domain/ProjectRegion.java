package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the project_region database table.
 * @author Prashant.Nigam
 */
@Entity
@Table(name="la_spatialunitgroup_hierarchy")
public class ProjectRegion implements Serializable 
{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="hierarchyid")
	private Integer hierarchyid;

	@Column(name="isactive")
	private Boolean isactive;

	@Column(name="name")
	private String name;

	@Column(name="name_en")
	private String nameEn;

	
	private Integer uperhierarchyid;

	//bi-directional many-to-one association to LaSpatialunitgroup
	@ManyToOne
	@JoinColumn(name="spatialunitgroupid")
	private LaSpatialunitgroup laSpatialunitgroup;
	
	
	@Column(name="code")
	private String areaCode;
	
		
	public ProjectRegion(){
		
	}

	public Integer getHierarchyid() {
		return hierarchyid;
	}

	public void setHierarchyid(Integer hierarchyid) {
		this.hierarchyid = hierarchyid;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	
	public LaSpatialunitgroup getLaSpatialunitgroup() {
		return laSpatialunitgroup;
	}

	public void setLaSpatialunitgroup(LaSpatialunitgroup laSpatialunitgroup) {
		this.laSpatialunitgroup = laSpatialunitgroup;
	}

	public Integer getUperhierarchyid() {
		return uperhierarchyid;
	}

	public void setUperhierarchyid(Integer uperhierarchyid) {
		this.uperhierarchyid = uperhierarchyid;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	
	
	
	
}