package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: LandUseType
 *
 */
@Entity
@Table(name = "land_use_type")
public class LandUseType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "use_type_id")
	private int landUseTypeId;

	@Column(name = "land_use_type")
	private String landUseType;
	
	@Column(name = "land_use_type_sw")
	private String landUseType_sw;


	public LandUseType() {
		super();
	}

	public int getLandUseTypeId() {
		return this.landUseTypeId;
	}

	public void setLandUseTypeId(int landUseTypeId) {
		this.landUseTypeId = landUseTypeId;
	}

	public String getLandUseType() {
		return this.landUseType;
	}

	public void setLandUseType(String landUseType) {
		this.landUseType = landUseType;
	}

	public String getLandUseType_sw() {
		return landUseType_sw;
	}

	public void setLandUseType_sw(String landUseType_sw) {
		this.landUseType_sw = landUseType_sw;
	}

}
