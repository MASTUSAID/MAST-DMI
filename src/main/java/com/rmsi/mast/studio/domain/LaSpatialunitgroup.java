package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the la_spatialunitgroup database table.
 * 
 */
@Entity
@Table(name="la_spatialunitgroup")
@NamedQuery(name="LaSpatialunitgroup.findAll", query="SELECT l FROM LaSpatialunitgroup l")
public class LaSpatialunitgroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer spatialunitgroupid;

	private String hierarchy;

	@Column(name="hierarchy_en")
	private String hierarchyEn;

	private Boolean isactive;


	public LaSpatialunitgroup() {
	}

	public Integer getSpatialunitgroupid() {
		return spatialunitgroupid;
	}

	public void setSpatialunitgroupid(Integer spatialunitgroupid) {
		this.spatialunitgroupid = spatialunitgroupid;
	}

	public String getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(String hierarchy) {
		this.hierarchy = hierarchy;
	}

	public String getHierarchyEn() {
		return hierarchyEn;
	}

	public void setHierarchyEn(String hierarchyEn) {
		this.hierarchyEn = hierarchyEn;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}


}