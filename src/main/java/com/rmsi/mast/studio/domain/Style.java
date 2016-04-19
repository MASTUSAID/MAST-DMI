

package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the style database table.
 * 
 */
@Entity
public class Style implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String filename;
	private Integer id;
	private String tenantid;

    public Style() {
    }


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}


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

}