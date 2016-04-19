

package com.rmsi.mast.studio.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * The persistent class for the unit database table.
 * 
 */
@Entity
public class Unit implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	//private Integer id;
	private String tenantid;
	
    public Unit() {
    }


	@Id
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
	
}