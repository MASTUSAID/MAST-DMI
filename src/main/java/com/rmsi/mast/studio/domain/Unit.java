

package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the unit database table.
 * 
 */
@Entity
@Table(name="la_ext_unit")
public class Unit implements Serializable {
	private static final long serialVersionUID = 1L;
	
	 @Id
     private Integer unitid;
	
	private Boolean isactive;
	
	@Column(name="unit_en",unique=true)
	private String unitEn;
	
	@Column(name="unit")
	private String unit;
	
	
    public Unit() {
    }

	public Integer getUnitid() {
		return unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid = unitid;
	}

	
	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getUnitEn() {
		return unitEn;
	}

	public void setUnitEn(String unitEn) {
		this.unitEn = unitEn;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
    
    
	
}