package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "la_ext_geometrytype")
public class GeometryType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Integer geometrytypeid;
	
	private String geometryname;
	
	private Boolean isactive;
	
	
	public GeometryType(){
		
	}


	public Integer getGeometrytypeid() {
		return geometrytypeid;
	}


	public void setGeometrytypeid(Integer geometrytypeid) {
		this.geometrytypeid = geometrytypeid;
	}


	public String getGeometryname() {
		return geometryname;
	}


	public void setGeometryname(String geometryname) {
		this.geometryname = geometryname;
	}


	public Boolean getIsactive() {
		return isactive;
	}


	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}
	
	
}
