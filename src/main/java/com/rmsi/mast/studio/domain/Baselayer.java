

package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the baselayer database table.
 * 
 */
@Entity
@Table(name="baselayer")
public class Baselayer implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	//private Integer id;
	private Set<ProjectBaselayer> projectBaselayers;

    public Baselayer() {
    }
    
    @JsonIgnore
	@OneToMany(mappedBy="baselayerBean", fetch = FetchType.EAGER, cascade=CascadeType.ALL)	
	@BatchSize(size=16)
	public Set<ProjectBaselayer> getProjectBaselayers() {
		return projectBaselayers;
	}

	public void setProjectBaselayers(Set<ProjectBaselayer> projectBaselayers) {
		this.projectBaselayers = projectBaselayers;
	}


	@Id
	//@Column(unique=true, nullable=false, length=50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Column(length=100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	/*@Column(nullable=false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}*/

}