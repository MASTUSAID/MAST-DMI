package com.rmsi.mast.studio.domain.fetch;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the users database table. 
 */
@Entity
@Table(name="users")
public class Usertable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id	
	@SequenceGenerator(name="ID_GENERATOR", sequenceName="users_gid_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ID_GENERATOR")	
	private Integer id;

	private Boolean active;

	private String defaultproject;

	private String name;



	public Usertable() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}


	public String getDefaultproject() {
		return this.defaultproject;
	}

	public void setDefaultproject(String defaultproject) {
		this.defaultproject = defaultproject;
	}



	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}



}