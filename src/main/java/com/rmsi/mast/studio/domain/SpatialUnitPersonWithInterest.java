package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: SpatialUnitPersonWithInterest
 *
 */
@Entity
@Table(name = "spatialunit_personwithinterest")
public class SpatialUnitPersonWithInterest implements Serializable {

	   
	@Id
	@SequenceGenerator(name = "SPATIAL_UNIT_PERSON_WITH_INTEREST_GENERATOR", sequenceName = "SPATIAL_UNIT_PERSON_WITH_INTEREST_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SPATIAL_UNIT_PERSON_WITH_INTEREST_GENERATOR")	
	private long id;
	private long usin;
	private String person_name;
	private static final long serialVersionUID = 1L;

	public SpatialUnitPersonWithInterest() {
		super();
	}   
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}   
	public long getUsin() {
		return this.usin;
	}

	public void setUsin(long usin) {
		this.usin = usin;
	}   
	public String getPerson_name() {
		return this.person_name;
	}

	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}
   
}
