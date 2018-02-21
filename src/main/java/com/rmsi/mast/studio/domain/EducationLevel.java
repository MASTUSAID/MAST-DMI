package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: EducationLevel
 *
 */
@Entity
@Table(name="la_partygroup_educationlevel")
public class EducationLevel implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	private static final long serialVersionUID = 1L;

	@Id
	private Integer educationlevelid;

	private String educationlevel;

	@Column(name="educationlevel_en")
	private String educationlevelEn;

	private Boolean isactive;

	/*//bi-directional many-to-one association to LaPartyPerson
	@OneToMany(mappedBy="laPartygroupEducationlevel")
	private List<NaturalPerson> laPartyPersons;
	*/
	public EducationLevel() {
		super();
	}

	public Integer getEducationlevelid() {
		return educationlevelid;
	}

	public void setEducationlevelid(Integer educationlevelid) {
		this.educationlevelid = educationlevelid;
	}

	public String getEducationlevel() {
		return educationlevel;
	}

	public void setEducationlevel(String educationlevel) {
		this.educationlevel = educationlevel;
	}

	public String getEducationlevelEn() {
		return educationlevelEn;
	}

	public void setEducationlevelEn(String educationlevelEn) {
		this.educationlevelEn = educationlevelEn;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	
	
	
	
}
