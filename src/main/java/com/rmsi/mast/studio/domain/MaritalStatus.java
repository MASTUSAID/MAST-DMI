package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: MaritalStatus
 *
 * @author Shruti.Thakur
 */
@Entity
@Table(name = "la_partygroup_maritalstatus")
public class MaritalStatus implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer maritalstatusid;

	private Boolean isactive;

	private String maritalstatus;

	@Column(name="maritalstatus_en")
	private String maritalstatusEn;


	public MaritalStatus() {
		super();
	}

	public Integer getMaritalstatusid() {
		return maritalstatusid;
	}

	public void setMaritalstatusid(Integer maritalstatusid) {
		this.maritalstatusid = maritalstatusid;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getMaritalstatus() {
		return maritalstatus;
	}

	public void setMaritalstatus(String maritalstatus) {
		this.maritalstatus = maritalstatus;
	}

	public String getMaritalstatusEn() {
		return maritalstatusEn;
	}

	public void setMaritalstatusEn(String maritalstatusEn) {
		this.maritalstatusEn = maritalstatusEn;
	}


}
