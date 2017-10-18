package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the spatialunit_personadministrator database table.
 * 
 */
@Entity
@Table(name="spatialunit_personadministrator")
public class SpatialunitPersonadministrator implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SPATIAL_PERSON_ADMIN_ID_GENERATOR", sequenceName = "spatialunit_personadministrator_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPATIAL_PERSON_ADMIN_ID_GENERATOR")
	private Long gid;

	//bi-directional many-to-one association to PersonAdministrator
	@ManyToOne(fetch = FetchType.EAGER , cascade=CascadeType.ALL)
	@JoinColumn(name="adminid")
	private PersonAdministrator personAdministrator;

	private Long usin;

	public SpatialunitPersonadministrator() {
	}

	public Long getGid() {
		return this.gid;
	}

	public void setGid(Long gid) {
		this.gid = gid;
	}

	
	public PersonAdministrator getPersonAdministrator() {
		return this.personAdministrator;
	}

	public void setPersonAdministrator(PersonAdministrator personAdministrator) {
		this.personAdministrator = personAdministrator;
	}

	public Long getUsin() {
		return usin;
	}

	public void setUsin(Long usin) {
		this.usin = usin;
	}
}