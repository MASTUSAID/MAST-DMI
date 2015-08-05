package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: LandType
 *
 */
@Entity
@Table(name = "land_type")
public class LandType implements Serializable {
	   
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "landtype_id")
	private int landTypeId;
	
	@Column(name = "landtype_value")
	private String landTypeValue;
	
	@Column(name = "landtype_value_sw")
	private String landTypeValue_sw;

	public LandType() {
		super();
	}   
	public int getLandTypeId() {
		return this.landTypeId;
	}

	public void setLandTypeId(int landTypeId) {
		this.landTypeId = landTypeId;
	}   
	public String getLandTypeValue() {
		return this.landTypeValue;
	}

	public void setLandTypeValue(String landTypeValue) {
		this.landTypeValue = landTypeValue;
	}
	public String getLandTypeValue_sw() {
		return landTypeValue_sw;
	}
	public void setLandTypeValue_sw(String landTypeValue_sw) {
		this.landTypeValue_sw = landTypeValue_sw;
	}
   
}
