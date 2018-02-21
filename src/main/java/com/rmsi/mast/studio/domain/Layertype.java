

package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the layertype database table.
 * 
 */
@Entity
@Table(name = "la_ext_layertype")
public class Layertype implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name="layertypeid")
	private Long layertypeid;
	
	//private String name;
	@Column(name="layertype")
	private String description;
	//private Integer id;
	@Column(name="layertype_en")
	private String layertypeEn;
	//private String tenantid;
	//private List<Layer> layers;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "layertype", fetch = FetchType.EAGER) 
	private List<Layer> layers;
	
	
	public Layertype() {
	}

	


	public Long getLayertypeid() {
		return layertypeid;
	}




	public void setLayertypeid(Long layertypeid) {
		this.layertypeid = layertypeid;
	}







	public List<Layer> getLayers() {
		return layers;
	}



	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}



	/*public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}

*/


	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}



/*
	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}

*/


	public String getLayertypeEn() {
		return layertypeEn;
	}




	public void setLayertypeEn(String layertypeEn) {
		this.layertypeEn = layertypeEn;
	}




	



	
	
	
	

	/*@Id
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
	}*/

	/*public String getTenantid() {
		return this.tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}*/

	// bi-directional many-to-one association to Layer
	
	 /* @JsonIgnore
	  
	 @OneToMany(mappedBy = "layertype", fetch = FetchType.EAGER) public
	 List<Layer> getLayers() { return this.layers; }
	 
	 @JsonIgnore public void setLayers(Set<Layer> layers) { this.layers =
	 (List<Layer>) layers; }*/
	
	
	
	 

}