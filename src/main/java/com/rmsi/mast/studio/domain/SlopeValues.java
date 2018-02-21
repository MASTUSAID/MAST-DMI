package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: SlopeValues
 *
 */
@Entity
@Table(name = "la_ext_slopevalue")
public class SlopeValues implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	private static final long serialVersionUID = 1L;
	
	
	@Id
	private Integer slopevalueid;

	private Boolean isactive;

	private String slopevalue;

	//bi-directional many-to-one association to LaSpatialunitLand
//	@OneToMany(mappedBy="laExtSlopevalue")
//	private List<SpatialUnit> laSpatialunitLands;
	
	public SlopeValues() {
		super();
	}

	public Integer getSlopevalueid() {
		return slopevalueid;
	}

	public void setSlopevalueid(Integer slopevalueid) {
		this.slopevalueid = slopevalueid;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getSlopevalue() {
		return slopevalue;
	}

	public void setSlopevalue(String slopevalue) {
		this.slopevalue = slopevalue;
	}

//	public List<SpatialUnit> getLaSpatialunitLands() {
//		return laSpatialunitLands;
//	}
//
//	public void setLaSpatialunitLands(List<SpatialUnit> laSpatialunitLands) {
//		this.laSpatialunitLands = laSpatialunitLands;
//	}  
	   
//	@Id
//	private int id;
//	private String slope_value;
//
//	public SlopeValues() {
//		super();
//	}   
//	public int getId() {
//		return this.id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}   
//	public String getSlope_value() {
//		return this.slope_value;
//	}
//
//	public void setSlope_value(String slope_value) {
//		this.slope_value = slope_value;
//	}
   
}
