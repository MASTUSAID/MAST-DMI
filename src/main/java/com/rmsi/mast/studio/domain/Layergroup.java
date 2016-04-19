

package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;


/**
 * The persistent class for the layergroup database table.
 * 
 */
@Entity
public class Layergroup implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String alias;
	//private Integer id;
	private String tenantid;
	private Set<LayerLayergroup> layerLayergroups;
	private Set<Overviewmap> overviewmaps;
	private Set<ProjectLayergroup> projectLayergroups;
	
	
	@Transient
	private String[] layergroupProjects;
	@Transient
    public String[] getLayergroupProjects() {
		return layergroupProjects;
	}



    @Transient
	public void setLayergroupProjects(String[] layergroupProjects) {
		this.layergroupProjects = layergroupProjects;
	}




	public Layergroup() {
    }

    


	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}


	/*public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}*/


	public String getTenantid() {
		return this.tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}


	//bi-directional many-to-one association to LayerLayergroup
	@OneToMany(mappedBy="layergroupBean", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JsonProperty("layers")
	@BatchSize(size=16)
	@javax.persistence.OrderBy("layerorder")
	public Set<LayerLayergroup> getLayerLayergroups() {
		return this.layerLayergroups;
	}

	public void setLayerLayergroups(Set<LayerLayergroup> layerLayergroups) {
		this.layerLayergroups = layerLayergroups;
	}
	

	//bi-directional many-to-one association to Overviewmap
	@JsonIgnore
	@OneToMany(mappedBy="layergroup", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@BatchSize(size=16)
	public Set<Overviewmap> getOverviewmaps() {
		return this.overviewmaps;
	}

	public void setOverviewmaps(Set<Overviewmap> overviewmaps) {
		this.overviewmaps = overviewmaps;
	}
	

	//bi-directional many-to-one association to ProjectLayergroup
	@JsonIgnore
	@OneToMany(mappedBy="layergroupBean", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@BatchSize(size=16)
	public Set<ProjectLayergroup> getProjectLayergroups() {
		return this.projectLayergroups;
	}

	public void setProjectLayergroups(Set<ProjectLayergroup> projectLayergroups) {
		this.projectLayergroups = projectLayergroups;
	}
	
}