

package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the savedquery database table.
 * 
 */
@Entity
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
public class Savedquery implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private String tenantid;
	private String whereexpression;
	private Layer layerBean;
	private Project projectBean;

    public Savedquery() {
    }


	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
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

	public String getTenantid() {
		return this.tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}


	public String getWhereexpression() {
		return this.whereexpression;
	}

	public void setWhereexpression(String whereexpression) {
		this.whereexpression = whereexpression;
	}


	//bi-directional many-to-one association to Layer
	@JsonIgnore
/*	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})*/
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="layer")
	public Layer getLayerBean() {
		return this.layerBean;
	}
	@JsonIgnore
	public void setLayerBean(Layer layerBean) {
		this.layerBean = layerBean;
	}
	

	//bi-directional many-to-one association to Project
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="project")
	public Project getProjectBean() {
		return this.projectBean;
	}
	@JsonIgnore
	public void setProjectBean(Project projectBean) {
		this.projectBean = projectBean;
	}
	
}