package com.rmsi.mast.studio.domain.fetch;
import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the spatialunit_pesonwithinterest database table.
 * 
 */
@Entity
@Table(name="spatialunit_deceased_person")
public class SpatialunitDeceasedPerson implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DECEASED_PERSON_ID_GENERATOR", sequenceName="spatial_unit_deceased_person_id_seq")
	@GeneratedValue(strategy=GenerationType.AUTO , generator="DECEASED_PERSON_ID_GENERATOR")
	private Long id;
	private String firstname;
	private String middlename;
	private String lastname;
	private long usin;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public long getUsin() {
		return usin;
	}
	public void setUsin(long usin) {
		this.usin = usin;
	}

	
}