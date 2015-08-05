package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: SoilQualityValues
 *
 */
@Entity
@Table(name = "soil_quality_values")
public class SoilQualityValues implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	private String quality;   

	public SoilQualityValues() {
		super();
	}   
	public String getQuality() {
		return this.quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
   
}
