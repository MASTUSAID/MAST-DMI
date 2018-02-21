package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the la_partygroup_occupation database table.
 * 
 */
@Entity
@Table(name="la_partygroup_occupation")
@NamedQuery(name="LaPartygroupOccupation.findAll", query="SELECT l FROM LaPartygroupOccupation l")
public class LaPartygroupOccupation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="occupationid")
	private Integer occupationid;

	@Column(name="isactive")
	private Boolean isactive;

	@Column(name="occupation")
	private String occupation;

	@Column(name="occupation_en")
	private String occupationEn;

	//bi-directional many-to-one association to LaPartyPerson
	@OneToMany(mappedBy="laPartygroupOccupation")
	@JsonIgnore
	private List<NaturalPerson> laPartyPersons;

	public LaPartygroupOccupation() {
	}

	public Integer getOccupationid() {
		return occupationid;
	}

	public void setOccupationid(Integer occupationid) {
		this.occupationid = occupationid;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getOccupationEn() {
		return occupationEn;
	}

	public void setOccupationEn(String occupationEn) {
		this.occupationEn = occupationEn;
	}

	public List<NaturalPerson> getLaPartyPersons() {
		return laPartyPersons;
	}

	public void setLaPartyPersons(List<NaturalPerson> laPartyPersons) {
		this.laPartyPersons = laPartyPersons;
	}

	

}