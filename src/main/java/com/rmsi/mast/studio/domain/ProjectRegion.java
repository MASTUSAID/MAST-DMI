package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the project_region database table.
 * @author Prashant.Nigam
 */
@Entity
@Table(name="project_region")
public class ProjectRegion implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PROJECT_REGION_GID_GENERATOR", sequenceName="PROJECT_REGION_GID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROJECT_REGION_GID_GENERATOR")
	private Integer gid;

	@Column(name="country_name")
	private String countryName;

	@Column(name="district_name")
	private String districtName;

	private String division;

	//@Column(name="hamlet")
	private String hamlet;

	private String municipality;
	

	private String province;

	private String region;

	@Column(name="state_name")
	private String stateName;

	//@Column(name="village")
	private String village;

	private String wards;

	public ProjectRegion() {
	}

	public Integer getGid() {
		return this.gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getDistrictName() {
		return this.districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getDivision() {
		return this.division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getHamlet() {
		return this.hamlet;
	}

	public void setHamlet(String hamlet) {
		this.hamlet = hamlet;
	}

	public String getMunicipality() {
		return this.municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getVillage() {
		return this.village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getWards() {
		return this.wards;
	}

	public void setWards(String wards) {
		this.wards = wards;
	}
}