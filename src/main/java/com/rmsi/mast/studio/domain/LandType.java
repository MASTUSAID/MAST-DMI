package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: LandType
 *
 */
@Entity
@Table(name = "la_baunit_landtype")
public class LandType implements Serializable {
	   
	private static final long serialVersionUID = 1L;
	
	
	
	@Id
	private Integer landtypeid;

	private Boolean isactive;

	private String landtype;

	@Column(name="landtype_en")
	private String landtypeEn;
	
	public LandType() {
		super();
	}

	public Integer getLandtypeid() {
		return landtypeid;
	}

	public void setLandtypeid(Integer landtypeid) {
		this.landtypeid = landtypeid;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getLandtype() {
		return landtype;
	}

	public void setLandtype(String landtype) {
		this.landtype = landtype;
	}

	public String getLandtypeEn() {
		return landtypeEn;
	}

	public void setLandtypeEn(String landtypeEn) {
		this.landtypeEn = landtypeEn;
	}   
	
	

//	@Id
//	@Column(name = "landtype_id")
//	private int landTypeId;
//	
//	@Column(name = "landtype_value")
//	private String landTypeValue;
//	
//	@Column(name = "landtype_value_sw")
//	private String landTypeValue_sw;
//
//	public LandType() {
//		super();
//	}   
//	public int getLandTypeId() {
//		return this.landTypeId;
//	}
//
//	public void setLandTypeId(int landTypeId) {
//		this.landTypeId = landTypeId;
//	}   
//	public String getLandTypeValue() {
//		return this.landTypeValue;
//	}
//
//	public void setLandTypeValue(String landTypeValue) {
//		this.landTypeValue = landTypeValue;
//	}
//	public String getLandTypeValue_sw() {
//		return landTypeValue_sw;
//	}
//	public void setLandTypeValue_sw(String landTypeValue_sw) {
//		this.landTypeValue_sw = landTypeValue_sw;
//	}
//   
}
