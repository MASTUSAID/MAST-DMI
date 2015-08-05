package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: SlopeValues
 *
 */
@Entity
@Table(name = "slope_values")
public class SlopeValues implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	private int id;
	private String slope_value;

	public SlopeValues() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getSlope_value() {
		return this.slope_value;
	}

	public void setSlope_value(String slope_value) {
		this.slope_value = slope_value;
	}
   
}
