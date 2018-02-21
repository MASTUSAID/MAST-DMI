package com.rmsi.mast.studio.domain;


import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the la_ext_baselayer database table.
 * 
 */
@Entity
@Table(name="la_ext_baselayer")
public class Baselayer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer baselayerid;

	private String baselayer;

	@Column(name="baselayer_en")
	private String baselayerEn;

	private Boolean isactive;

    @OneToMany(mappedBy="Baselayers", fetch = FetchType.EAGER, cascade=CascadeType.ALL)	
	@BatchSize(size=16)
	private List<ProjectBaselayer> projectBaselayers;


	public Baselayer() {
	}

	public Integer getBaselayerid() {
		return this.baselayerid;
	}

	public void setBaselayerid(Integer baselayerid) {
		this.baselayerid = baselayerid;
	}

	public String getBaselayer() {
		return this.baselayer;
	}

	public void setBaselayer(String baselayer) {
		this.baselayer = baselayer;
	}

	public String getBaselayerEn() {
		return this.baselayerEn;
	}

	public void setBaselayerEn(String baselayerEn) {
		this.baselayerEn = baselayerEn;
	}

	public Boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	@JsonIgnore
	public List<ProjectBaselayer> getProjectBaselayers() {
		return projectBaselayers;
	}

	public void setProjectBaselayers(List<ProjectBaselayer> projectBaselayers) {
		this.projectBaselayers = projectBaselayers;
	}



}