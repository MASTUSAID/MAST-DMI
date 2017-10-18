package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Gender
 * 
 * @author Shruti.Thakur
 */
@Entity
@Table(name = "gender")
public class Gender implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "gender_id")
	@SequenceGenerator(name = "GENDER_ID_GENERATOR", sequenceName = "GENDER_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GENDER_ID_GENERATOR")
	private long genderId;

	@Column(nullable = false)
	private String gender;

	@Column(name="gender_sw")
	private String gender_sw;
	
	public Gender() {
		super();
	}

	public long getGenderId() {
		return this.genderId;
	}

	public void setGenderId(long genderId) {
		this.genderId = genderId;
	}

	public String getGender() {
		return this.gender;
	}

	public String getGender_sw() {
		return gender_sw;
	}

	public void setGender_sw(String gender_sw) {
		this.gender_sw = gender_sw;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
