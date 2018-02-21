package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: LandUseType
 *
 */
@Entity
@Table(name = "la_baunit_landusetype")
public class LandUseType implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer landusetypeid;

	private Boolean isactive;

	private String landusetype;

	@Column(name="landusetype_en")
	private String landusetypeEn;

	//bi-directional many-to-one association to LaSpatialunitLand
//	@OneToMany(mappedBy="laBaunitLandusetype")
//	private List<SpatialUnit> laSpatialunitLands;
	
	public LandUseType() {
		super();
	}

	public Integer getLandusetypeid() {
		return landusetypeid;
	}

	public void setLandusetypeid(Integer landusetypeid) {
		this.landusetypeid = landusetypeid;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getLandusetype() {
		return landusetype;
	}

	public void setLandusetype(String landusetype) {
		this.landusetype = landusetype;
	}

	public String getLandusetypeEn() {
		return landusetypeEn;
	}

	public void setLandusetypeEn(String landusetypeEn) {
		this.landusetypeEn = landusetypeEn;
	}

//	public List<SpatialUnit> getLaSpatialunitLands() {
//		return laSpatialunitLands;
//	}
//
//	public void setLaSpatialunitLands(List<SpatialUnit> laSpatialunitLands) {
//		this.laSpatialunitLands = laSpatialunitLands;
//	}

	
	
	
//	@Id
//	@Column(name = "use_type_id")
//	private int landUseTypeId;
//
//	@Column(name = "land_use_type")
//	private String landUseType;
//	
//	@Column(name = "land_use_type_sw")
//	private String landUseType_sw;
//
//
//	public LandUseType() {
//		super();
//	}
//
//	public int getLandUseTypeId() {
//		return this.landUseTypeId;
//	}
//
//	public void setLandUseTypeId(int landUseTypeId) {
//		this.landUseTypeId = landUseTypeId;
//	}
//
//	public String getLandUseType() {
//		return this.landUseType;
//	}
//
//	public void setLandUseType(String landUseType) {
//		this.landUseType = landUseType;
//	}
//
//	public String getLandUseType_sw() {
//		return landUseType_sw;
//	}
//
//	public void setLandUseType_sw(String landUseType_sw) {
//		this.landUseType_sw = landUseType_sw;
//	}

}
