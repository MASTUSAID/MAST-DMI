

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
@Table(name = "la_rrr")
public class LaRrr implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer rrrid;

	private String rrrtype;

	private Integer rrrtypeid;

	public LaRrr() {
	}
	
	

	public Integer getRrrid() {
		return this.rrrid;
	}

	public void setRrrid(Integer rrrid) {
		this.rrrid = rrrid;
	}

	public String getRrrtype() {
		return this.rrrtype;
	}

	public void setRrrtype(String rrrtype) {
		this.rrrtype = rrrtype;
	}

	public Integer getRrrtypeid() {
		return this.rrrtypeid;
	}

	public void setRrrtypeid(Integer rrrtypeid) {
		this.rrrtypeid = rrrtypeid;
	}
	
	
	 

}