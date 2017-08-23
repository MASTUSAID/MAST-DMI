

package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the action database table.
 * 
 */
@Entity
@Table(name = "citizenship")
public class Citizenship implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private long id;
	
	private String citizenname;
	
	private String citizenname_sw;


	public Citizenship() {
		super();
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getCitizenname() {
		return citizenname;
	}


	public void setCitizenname(String citizenname) {
		this.citizenname = citizenname;
	}


	public String getCitizenname_sw() {
		return citizenname_sw;
	}


	public void setCitizenname_sw(String citizenname_sw) {
		this.citizenname_sw = citizenname_sw;
	}

	


}