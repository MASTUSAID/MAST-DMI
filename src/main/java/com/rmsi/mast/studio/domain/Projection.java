package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the la_ext_projection database table.
 * 
 */
@Entity
@Table(name="la_ext_projection")
public class Projection implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer projectionid;

	private String description;

	private Boolean isactive;

	@Column(name="projection",unique=true)
	private String projection;

	public Projection() {
	}

	public Integer getProjectionid() {
		return this.projectionid;
	}

	public void setProjectionid(Integer projectionid) {
		this.projectionid = projectionid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getProjection() {
		return this.projection;
	}

	public void setProjection(String projection) {
		this.projection = projection;
	}

	

}