

package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the overviewmap database table.
 * 
 */
@Entity
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
public class Overviewmap implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String tenantid;
	private Layergroup layergroup;
	private Project projectBean;

    public Overviewmap() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	
    
    /*@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="project_overviewmaps_id_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence") 
	@Column(name="id", unique=true, nullable=false)*/ 
    
    
    
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


	//bi-directional many-to-one association to Layergroup
	@JsonIgnore
    @ManyToOne
	@JoinColumn(name="layer")
	public Layergroup getLayergroup() {
		return this.layergroup;
	}
	@JsonIgnore
	public void setLayergroup(Layergroup layergroup) {
		this.layergroup = layergroup;
	}
	

	//bi-directional many-to-one association to Project
	//@JsonIgnore
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