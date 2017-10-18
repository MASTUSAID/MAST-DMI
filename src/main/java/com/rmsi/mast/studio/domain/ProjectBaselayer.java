

package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The persistent class for the project_baselayer database table.
 * 
 */
@Entity
@Table(name="project_baselayer")
public class ProjectBaselayer implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer baselayerorder;
	private Baselayer baselayerBean;
	private Project projectBean;

    public ProjectBaselayer() {
    }
    
    @Id
	@SequenceGenerator(name="pk_project_baselayer_id_seq",sequenceName="project_baselayer_id_seq", allocationSize=1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_project_baselayer_id_seq") 
	@Column(name="id", unique=true, nullable=false) 

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Column(nullable=false)
	public Integer getBaselayerorder() {
		return this.baselayerorder;
	}

	public void setBaselayerorder(Integer baselayerorder) {
		this.baselayerorder = baselayerorder;
	}


	//bi-directional many-to-one association to Baselayer
    @ManyToOne
	@JoinColumn(name="baselayer")
	@JsonProperty("baselayers")
	public Baselayer getBaselayerBean() {
		return this.baselayerBean;
	}

	public void setBaselayerBean(Baselayer baselayerBean) {
		this.baselayerBean = baselayerBean;
	}
	

	//bi-directional many-to-one association to Project
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="project")
	public Project getProjectBean() {
		return this.projectBean;
	}

	public void setProjectBean(Project projectBean) {
		this.projectBean = projectBean;
	}
	
}