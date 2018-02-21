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
 * Entity implementation class for Entity: Gender
 * 
 * @author Shruti.Thakur
 */
@Entity
@Table(name = "la_partygroup_gender")
public class Gender implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "genderid")
	@SequenceGenerator(name = "GENDER_ID_GENERATOR", sequenceName = "la_partygroup_gender_genderid_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GENDER_ID_GENERATOR")
	private long genderId;
	
	@Column(name="isactive")
    private Boolean active;

	@Column(nullable = false)
	private String gender;

	@Column(name="gender_en")
	private String gender_en;
	


/*	 @JsonIgnore
	 @OneToOne(mappedBy="gender")
	 private User users;
*/	
	

	public long getGenderId() {
		return genderId;
	}

	public void setGenderId(long genderId) {
		this.genderId = genderId;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGender_en() {
		return gender_en;
	}

	public void setGender_en(String gender_en) {
		this.gender_en = gender_en;
	}
	
	
	
	
	
	

}
