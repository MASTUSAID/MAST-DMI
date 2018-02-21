

package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table(name="la_ext_role")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
    public final static Integer ROLE_ADMIN=1;
    public final static Integer DPI=2;
    public final static Integer PM=3;
    public final static Integer PUBLIC_USER=4;
    public final static Integer SFR=5;
    public final static Integer TRUSTED_INTERMEDIARY=6;
    public final static Integer USER=7;
	
		
    public Role() {
    }
   

	@Id
    @SequenceGenerator(name = "roleid_GENERATOR", sequenceName = "la_ext_role_roleid_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleid_GENERATOR")
	private Integer roleid;
	@Column(name="roletype")
	private String name;
	private String roletype_en;
	private String description;
	@Column(name="isactive")
    private Boolean active;
	

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoletype_en() {
		return roletype_en;
	}

	public void setRoletype_en(String roletype_en) {
		this.roletype_en = roletype_en;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	
	
	
}