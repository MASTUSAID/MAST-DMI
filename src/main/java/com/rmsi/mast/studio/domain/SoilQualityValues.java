package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: SoilQualityValues
 *
 */
@Entity
@Table(name = "la_baunit_landsoilquality")
public class SoilQualityValues implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer landsoilqualityid;

	private Boolean isactive;

	private String landsoilqualitytype;

	@Column(name="landsoilqualitytype_en")
	private String landsoilqualitytypeEn;

	//bi-directional many-to-one association to LaSpatialunitLand
//	@OneToMany(mappedBy="laBaunitLandsoilquality")
//	private List<SpatialUnit> laSpatialunitLands;
	
	public SoilQualityValues() {
		super();
	}

	public Integer getLandsoilqualityid() {
		return landsoilqualityid;
	}

	public void setLandsoilqualityid(Integer landsoilqualityid) {
		this.landsoilqualityid = landsoilqualityid;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getLandsoilqualitytype() {
		return landsoilqualitytype;
	}

	public void setLandsoilqualitytype(String landsoilqualitytype) {
		this.landsoilqualitytype = landsoilqualitytype;
	}

	public String getLandsoilqualitytypeEn() {
		return landsoilqualitytypeEn;
	}

	public void setLandsoilqualitytypeEn(String landsoilqualitytypeEn) {
		this.landsoilqualitytypeEn = landsoilqualitytypeEn;
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
//	private String quality;   
//
//	public SoilQualityValues() {
//		super();
//	}   
//	public String getQuality() {
//		return this.quality;
//	}
//
//	public void setQuality(String quality) {
//		this.quality = quality;
//	}   
//	public int getId() {
//		return this.id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//   
}
