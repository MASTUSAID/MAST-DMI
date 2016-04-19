

package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The persistent class for the layertype database table.
 * 
 */
@Entity
public class Layertype implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private Integer id;
	private String tenantid;
	private Set<Layer> layers;

	public Layertype() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTenantid() {
		return this.tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	// bi-directional many-to-one association to Layer
	/*
	 * @JsonIgnore
	 * 
	 * @OneToMany(mappedBy = "layertype", fetch = FetchType.EAGER) public
	 * Set<Layer> getLayers() { return this.layers; }
	 * 
	 * @JsonIgnore public void setLayers(Set<Layer> layers) { this.layers =
	 * layers; }
	 */

}