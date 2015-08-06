package com.rmsi.mast.studio.domain.fetch;
import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the spatialunit_pesonwithinterest database table.
 * 
 */
@Entity
@Table(name="spatialunit_personwithinterest")
public class SpatialunitPersonwithinterest implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="person_name")
	private String personName;

	private Long usin;

	public SpatialunitPersonwithinterest() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPersonName() {
		return this.personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Long getUsin() {
		return this.usin;
	}

	public void setUsin(Long usin) {
		this.usin = usin;
	}

}