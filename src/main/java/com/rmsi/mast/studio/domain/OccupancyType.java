package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: OccupancyType
 *
 * @author Shruti.Thakur
 */
@Entity
@Table(name = "occupancy_type")
public class OccupancyType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "occupancy_type_id")
	private int occId;

	@Column(nullable = false)
	private String description;

/*	public OccupancyType() {
		super();
	}*/

	public int getOccId() {
		return this.occId;
	}

	public void setOccId(int occId) {
		this.occId = occId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
